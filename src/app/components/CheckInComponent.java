package app.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.CheckInDTO;
import app.dto.DeleteDTO;
import app.entities.CheckIn;
import app.entities.Member;
import app.entities.Subscription;
import app.repositories.CheckInRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class CheckInComponent {

	@Autowired
	private CheckInRepository checkInRepo;

	@Autowired
	private MemberComponent memberComponent;

	@Autowired
	private SubscriptionComponent subscriptionComponent;

	public CheckInDTO checkIn(Long memberPk) throws ServiceException {
		Member member = memberComponent.find(memberPk);

		List<Subscription> active = subscriptionComponent.findCurrent(member, Subscription.ACTIVE);
		if (active.isEmpty()) {
			throw new ServiceException(whyNotActive(member));
		}
		Subscription s = active.get(0);

		if (subscriptionComponent.amountPaid(s).signum() == 0) {
			throw new ServiceException("No payment recorded for subscription " + s.getPk() + ".");
		}

		CheckIn last = checkInRepo.findFirstByMemberOrderByCheckedInAtDesc(member);
		if (last != null && last.getCheckedInAt().toLocalDate().equals(LocalDate.now())) {
			throw new ServiceException("Member " + memberPk + " has already checked in today.");
		}

		return toDTO(checkInRepo.save(new CheckIn(member, s, LocalDateTime.now().withNano(0))));
	}

	public CheckInDTO getCheckIn(Long pk) throws ServiceException {
		return toDTO(find(pk));
	}

	public List<CheckInDTO> getByMember(Long memberPk) throws ServiceException {
		List<CheckInDTO> list = new ArrayList<>();
		for (CheckIn c : checkInRepo.findByMemberOrderByCheckedInAtDesc(memberComponent.find(memberPk))) {
			list.add(toDTO(c));
		}
		return list;
	}

	public DeleteDTO deleteCheckIn(Long pk) throws ServiceException {
		checkInRepo.delete(find(pk));
		return new DeleteDTO(true, pk);
	}

	// picks the most useful error for a member with no ACTIVE subscription
	private String whyNotActive(Member member) {
		if (!subscriptionComponent.findCurrent(member, Subscription.FROZEN).isEmpty()) {
			return "Subscription is frozen.";
		}

		LocalDate lastEnd = null;
		for (Subscription s : subscriptionComponent.findCurrent(member, Subscription.EXPIRED)) {
			if (lastEnd == null || s.getEndDate().isAfter(lastEnd)) {
				lastEnd = s.getEndDate();
			}
		}
		if (lastEnd != null) {
			return "Subscription expired on " + lastEnd + ".";
		}

		return "Member " + member.getPk() + " has no active subscription.";
	}

	private CheckIn find(Long pk) throws ServiceException {
		return checkInRepo.findById(pk).orElseThrow(() -> new ServiceException("Check-in " + pk + " not found."));
	}

	private CheckInDTO toDTO(CheckIn c) {
		long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), c.getSubscription().getEndDate());
		return new CheckInDTO(c.getPk(), c.getMember().getPk(), c.getMember().getName(), c.getSubscription().getPk(),
				TimeFormat.of(c.getCheckedInAt()), Math.max(daysRemaining, 0));
	}

}
