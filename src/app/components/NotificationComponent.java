package app.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.dto.DeleteDTO;
import app.dto.NotificationDTO;
import app.dto.TwilioReply;
import app.entities.CheckIn;
import app.entities.Member;
import app.entities.NotificationLog;
import app.entities.Payment;
import app.entities.Subscription;
import app.repositories.CheckInRepository;
import app.repositories.NotificationLogRepository;
import app.repositories.PaymentRepository;
import app.repositories.SubscriptionRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class NotificationComponent {

	private static final int INACTIVE_DAYS = 14;

	@Value("${bluefit.owner.number}")
	private String ownerNumber;

	@Autowired
	private NotificationLogRepository notificationRepo;

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private CheckInRepository checkInRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private MemberComponent memberComponent;

	@Autowired
	private SubscriptionComponent subscriptionComponent;

	@Autowired
	private TwilioComponent twilio;

	public NotificationDTO sendManual(Long memberPk, String message) throws ServiceException {
		Member member = memberComponent.find(memberPk);
		if (message.length() > 320) {
			throw new ServiceException("Message must be 1 to 320 characters.");
		}

		NotificationLog log = send(member, null, member.getCellphoneNumber(), NotificationLog.MANUAL, message);
		if (NotificationLog.FAILED.equals(log.getStatus())) {
			throw new ServiceException("SMS could not be sent. Check that the SMS service is running.");
		}
		return toDTO(log);
	}

	public NotificationDTO getNotification(Long pk) throws ServiceException {
		return toDTO(find(pk));
	}

	public List<NotificationDTO> getByMember(Long memberPk) throws ServiceException {
		List<NotificationDTO> list = new ArrayList<>();
		for (NotificationLog n : notificationRepo.findByMemberOrderBySentAtDesc(memberComponent.find(memberPk))) {
			list.add(toDTO(n));
		}
		return list;
	}

	public DeleteDTO deleteNotification(Long pk) throws ServiceException {
		notificationRepo.delete(find(pk));
		return new DeleteDTO(true, pk);
	}

	// ---------- called by the scheduler ----------

	public void sendRenewalReminders() {
		LocalDate today = LocalDate.now();

		for (Subscription s : subscriptionRepo.findByStatusAndEndDateBetween(Subscription.ACTIVE, today,
				today.plusDays(7))) {
			Member m = s.getMember();

			// already renewed, no need to remind
			if (!subscriptionComponent.findCurrent(m, Subscription.PENDING).isEmpty()) {
				continue;
			}

			long daysLeft = ChronoUnit.DAYS.between(today, s.getEndDate());
			if (notSent(s, NotificationLog.REMINDER_7D)) {
				send(m, s, m.getCellphoneNumber(), NotificationLog.REMINDER_7D, "Hi " + firstName(m)
						+ ", your BlueFit plan ends on " + s.getEndDate() + ". Renew at the front desk to keep training!");
			}
			if (daysLeft <= 1 && notSent(s, NotificationLog.REMINDER_1D)) {
				send(m, s, m.getCellphoneNumber(), NotificationLog.REMINDER_1D, "Hi " + firstName(m)
						+ ", your BlueFit plan ends on " + s.getEndDate() + ". This is your last reminder to renew.");
			}
		}
	}

	public void sendInactivityMessages() {
		LocalDateTime now = LocalDateTime.now();

		for (Subscription s : subscriptionRepo.findByStatus(Subscription.ACTIVE)) {
			Member m = s.getMember();

			CheckIn last = checkInRepo.findFirstByMemberOrderByCheckedInAtDesc(m);
			LocalDateTime lastSeen = last != null ? last.getCheckedInAt() : s.getStartDate().atStartOfDay();
			if (ChronoUnit.DAYS.between(lastSeen, now) <= INACTIVE_DAYS) {
				continue;
			}

			NotificationLog lastNudge = notificationRepo.findFirstByMemberAndTypeAndStatusOrderBySentAtDesc(m,
					NotificationLog.INACTIVITY, NotificationLog.ACCEPTED);
			if (lastNudge != null && ChronoUnit.DAYS.between(lastNudge.getSentAt(), now) < INACTIVE_DAYS) {
				continue;
			}

			send(m, null, m.getCellphoneNumber(), NotificationLog.INACTIVITY,
					"Hi " + firstName(m) + ", we miss you at BlueFit! Your plan is still active until " + s.getEndDate() + ".");
		}
	}

	public void sendOwnerSummary() {
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime now = LocalDateTime.now();

		if (notificationRepo.countByTypeAndStatusAndSentAtBetween(NotificationLog.OWNER_SUMMARY,
				NotificationLog.ACCEPTED, startOfDay, now) > 0) {
			return;
		}

		int checkIns = checkInRepo.findByCheckedInAtBetween(startOfDay, now).size();
		BigDecimal revenue = BigDecimal.ZERO.setScale(2);
		for (Payment p : paymentRepo.findByPaidAtBetween(startOfDay, now)) {
			revenue = revenue.add(p.getAmount());
		}
		int expiring = subscriptionRepo.findByStatusAndEndDateBetween(Subscription.ACTIVE, LocalDate.now(),
				LocalDate.now().plusDays(7)).size();

		send(null, null, ownerNumber, NotificationLog.OWNER_SUMMARY, "BlueFit today: " + checkIns
				+ " check-ins, PHP " + revenue + " collected, " + expiring + " plans expiring within 7 days.");
	}

	// ---------- helpers ----------

	private NotificationLog send(Member member, Subscription subscription, String toNumber, String type,
			String message) {
		TwilioReply reply = twilio.sendSms(toNumber, message);
		String status = reply != null ? reply.getStatus() : NotificationLog.FAILED;

		NotificationLog log = new NotificationLog(member, subscription, toNumber, type, message, status,
				LocalDateTime.now().withNano(0));
		return notificationRepo.save(log);
	}

	private boolean notSent(Subscription s, String type) {
		return notificationRepo.countBySubscriptionAndTypeAndStatus(s, type, NotificationLog.ACCEPTED) == 0;
	}

	private String firstName(Member m) {
		return m.getName().split(" ")[0];
	}

	private NotificationLog find(Long pk) throws ServiceException {
		return notificationRepo.findById(pk)
				.orElseThrow(() -> new ServiceException("Notification " + pk + " not found."));
	}

	private NotificationDTO toDTO(NotificationLog n) {
		return new NotificationDTO(n.getPk(), n.getMember() == null ? null : n.getMember().getPk(),
				n.getSubscription() == null ? null : n.getSubscription().getPk(), n.getToNumber(), n.getType(),
				n.getMessage(), n.getStatus(), TimeFormat.of(n.getSentAt()));
	}

}
