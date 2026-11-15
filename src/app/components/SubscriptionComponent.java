package app.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.DeleteDTO;
import app.dto.SubscriptionDTO;
import app.entities.Member;
import app.entities.MembershipPlan;
import app.entities.Payment;
import app.entities.Subscription;
import app.repositories.CheckInRepository;
import app.repositories.NotificationLogRepository;
import app.repositories.PaymentRepository;
import app.repositories.SubscriptionRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class SubscriptionComponent {

	// statuses that count as a member's "current" subscription
	private static final List<String> CURRENT = Arrays.asList(Subscription.ACTIVE, Subscription.FROZEN,
			Subscription.PENDING);

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private CheckInRepository checkInRepo;

	@Autowired
	private NotificationLogRepository notificationRepo;

	@Autowired
	private MemberComponent memberComponent;

	@Autowired
	private PlanComponent planComponent;

	public SubscriptionDTO newSubscription(Long memberPk, Long planPk) throws ServiceException {
		Member member = memberComponent.find(memberPk);
		MembershipPlan plan = planComponent.find(planPk);
		checkEligible(member, plan);

		if (!subscriptionRepo.findByMemberAndStatusIn(member, CURRENT).isEmpty()) {
			throw new ServiceException(
					"Member " + memberPk + " already has a current subscription. Use renew instead.");
		}

		return toDTO(create(member, plan, LocalDate.now(), Subscription.ACTIVE));
	}

	public SubscriptionDTO renewSubscription(Long memberPk, Long planPk) throws ServiceException {
		Member member = memberComponent.find(memberPk);
		MembershipPlan plan = planComponent.find(planPk);
		checkEligible(member, plan);

		Subscription active = null;
		for (Subscription s : subscriptionRepo.findByMemberAndStatusIn(member, CURRENT)) {
			if (Subscription.PENDING.equals(s.getStatus())) {
				throw new ServiceException("Member " + memberPk + " already has a renewal waiting to start.");
			}
			if (Subscription.FROZEN.equals(s.getStatus())) {
				throw new ServiceException(
						"Subscription " + s.getPk() + " is frozen. Unfreeze it before renewing.");
			}
			active = s;
		}

		// start right after the active one so no paid days are lost
		if (active != null) {
			return toDTO(create(member, plan, active.getEndDate().plusDays(1), Subscription.PENDING));
		}
		return toDTO(create(member, plan, LocalDate.now(), Subscription.ACTIVE));
	}

	public SubscriptionDTO freezeSubscription(Long pk) throws ServiceException {
		Subscription s = find(pk);
		if (!Subscription.ACTIVE.equals(s.getStatus())) {
			throw new ServiceException("Only an active subscription can be frozen.");
		}

		s.setStatus(Subscription.FROZEN);
		s.setFrozenOn(LocalDate.now());
		return toDTO(subscriptionRepo.save(s));
	}

	public SubscriptionDTO unfreezeSubscription(Long pk) throws ServiceException {
		Subscription s = find(pk);
		if (!Subscription.FROZEN.equals(s.getStatus())) {
			throw new ServiceException("Subscription " + pk + " is not frozen.");
		}

		// give back the days spent frozen
		long pausedDays = ChronoUnit.DAYS.between(s.getFrozenOn(), LocalDate.now());
		s.setEndDate(s.getEndDate().plusDays(pausedDays));
		s.setFrozenOn(null);
		s.setStatus(Subscription.ACTIVE);
		return toDTO(subscriptionRepo.save(s));
	}

	public SubscriptionDTO cancelSubscription(Long pk) throws ServiceException {
		Subscription s = find(pk);
		if (Subscription.EXPIRED.equals(s.getStatus()) || Subscription.CANCELLED.equals(s.getStatus())) {
			throw new ServiceException("Subscription " + pk + " has already ended or been cancelled.");
		}

		s.setStatus(Subscription.CANCELLED);
		return toDTO(subscriptionRepo.save(s));
	}

	public DeleteDTO deleteSubscription(Long pk) throws ServiceException {
		Subscription s = find(pk);

		long history = paymentRepo.countBySubscription(s) + checkInRepo.countBySubscription(s)
				+ notificationRepo.countBySubscription(s);
		if (history > 0) {
			throw new ServiceException("Subscription " + pk
					+ " has payments, check-ins or notifications and cannot be deleted. Cancel it instead.");
		}

		subscriptionRepo.delete(s);
		return new DeleteDTO(true, pk);
	}

	public SubscriptionDTO getSubscription(Long pk) throws ServiceException {
		return toDTO(find(pk));
	}

	public List<SubscriptionDTO> getByMember(Long memberPk) throws ServiceException {
		return toDTOs(subscriptionRepo.findByMember(memberComponent.find(memberPk)));
	}

	public List<SubscriptionDTO> getExpiring(Integer days) throws ServiceException {
		if (days < 1 || days > 90) {
			throw new ServiceException("days must be between 1 and 90.");
		}

		LocalDate today = LocalDate.now();
		return toDTOs(subscriptionRepo.findByStatusAndEndDateBetween(Subscription.ACTIVE, today, today.plusDays(days)));
	}

	public List<SubscriptionDTO> getUnpaid() {
		List<SubscriptionDTO> list = new ArrayList<>();
		for (Subscription s : subscriptionRepo.findByStatusIn(CURRENT)) {
			SubscriptionDTO dto = toDTO(s);
			if (dto.getBalance().signum() > 0) {
				list.add(dto);
			}
		}
		return list;
	}

	// called by the scheduler
	public void updateStatuses() {
		LocalDate today = LocalDate.now();

		for (Subscription s : subscriptionRepo.findByStatusAndEndDateBefore(Subscription.ACTIVE, today)) {
			s.setStatus(Subscription.EXPIRED);
			subscriptionRepo.save(s);
			System.out.println("Subscription " + s.getPk() + " expired");
		}

		for (Subscription s : subscriptionRepo.findByStatusAndStartDateLessThanEqual(Subscription.PENDING, today)) {
			s.setStatus(Subscription.ACTIVE);
			subscriptionRepo.save(s);
			System.out.println("Subscription " + s.getPk() + " started");
		}
	}

	public List<Subscription> findCurrent(Member member, String... statuses) {
		return subscriptionRepo.findByMemberAndStatusIn(member, Arrays.asList(statuses));
	}

	public Subscription find(Long pk) throws ServiceException {
		return subscriptionRepo.findById(pk)
				.orElseThrow(() -> new ServiceException("Subscription " + pk + " not found."));
	}

	public BigDecimal amountPaid(Subscription s) {
		BigDecimal total = BigDecimal.ZERO.setScale(2);
		for (Payment p : paymentRepo.findBySubscription(s)) {
			total = total.add(p.getAmount());
		}
		return total;
	}

	public BigDecimal balance(Subscription s) {
		return s.getAgreedPrice().subtract(amountPaid(s));
	}

	public SubscriptionDTO toDTO(Subscription s) {
		BigDecimal paid = amountPaid(s);
		BigDecimal balance = s.getAgreedPrice().subtract(paid);

		String paymentStatus = "PARTIAL";
		if (paid.signum() == 0) {
			paymentStatus = "UNPAID";
		} else if (balance.signum() <= 0) {
			paymentStatus = "PAID";
		}

		return new SubscriptionDTO(s.getPk(), s.getMember().getPk(), s.getMember().getName(), s.getPlan().getPk(),
				s.getPlan().getName(), s.getAgreedPrice(), paid, balance, paymentStatus,
				String.valueOf(s.getStartDate()), String.valueOf(s.getEndDate()), s.getStatus());
	}

	private Subscription create(Member member, MembershipPlan plan, LocalDate start, String status) {
		Subscription s = new Subscription(member, plan, plan.getPrice(), start,
				start.plusDays(plan.getDurationDays()), status);
		return subscriptionRepo.save(s);
	}

	private void checkEligible(Member member, MembershipPlan plan) throws ServiceException {
		String eligible = plan.getEligibleAffiliation();
		if (!"ALL".equals(eligible) && !eligible.equals(member.getAffiliation())) {
			throw new ServiceException(
					"Plan " + plan.getName() + " is not available to " + member.getAffiliation() + " members.");
		}
	}

	private List<SubscriptionDTO> toDTOs(List<Subscription> subscriptions) {
		List<SubscriptionDTO> list = new ArrayList<>();
		for (Subscription s : subscriptions) {
			list.add(toDTO(s));
		}
		return list;
	}

}
