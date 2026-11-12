package app.components;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.DeleteDTO;
import app.dto.MemberDTO;
import app.entities.Member;
import app.repositories.CheckInRepository;
import app.repositories.MemberRepository;
import app.repositories.NotificationLogRepository;
import app.repositories.SubscriptionRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class MemberComponent {

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private CheckInRepository checkInRepo;

	@Autowired
	private NotificationLogRepository notificationRepo;

	public MemberDTO addMember(String name, String schoolId, String cellphoneNumber, String email, String affiliation)
			throws ServiceException {
		if (schoolId != null && memberRepo.findBySchoolId(schoolId) != null) {
			throw new ServiceException("School ID " + schoolId + " is already registered.");
		}

		Member member = new Member(name, schoolId, cellphoneNumber, email, affiliation, LocalDate.now());
		return toDTO(memberRepo.save(member));
	}

	public MemberDTO getMember(Long pk) throws ServiceException {
		return toDTO(find(pk));
	}

	public List<MemberDTO> getAllMembers() {
		return toDTOs(memberRepo.findAll());
	}

	public List<MemberDTO> searchMembers(String name) {
		return toDTOs(memberRepo.findByNameContainingIgnoreCase(name));
	}

	public MemberDTO updatePhone(Long pk, String cellphoneNumber) throws ServiceException {
		Member member = find(pk);
		member.setCellphoneNumber(cellphoneNumber);
		return toDTO(memberRepo.save(member));
	}

	public DeleteDTO deleteMember(Long pk) throws ServiceException {
		Member member = find(pk);

		long history = subscriptionRepo.countByMember(member) + checkInRepo.countByMember(member)
				+ notificationRepo.countByMember(member);
		if (history > 0) {
			throw new ServiceException(
					"Member " + pk + " has subscriptions, check-ins or notifications and cannot be deleted.");
		}

		memberRepo.delete(member);
		return new DeleteDTO(true, pk);
	}

	// shared lookup used by the other components too
	public Member find(Long pk) throws ServiceException {
		return memberRepo.findById(pk).orElseThrow(() -> new ServiceException("Member " + pk + " not found."));
	}

	public MemberDTO toDTO(Member m) {
		return new MemberDTO(m.getPk(), m.getName(), m.getSchoolId(), m.getCellphoneNumber(), m.getEmail(),
				m.getAffiliation(), String.valueOf(m.getJoinedOn()));
	}

	private List<MemberDTO> toDTOs(List<Member> members) {
		List<MemberDTO> list = new ArrayList<>();
		for (Member m : members) {
			list.add(toDTO(m));
		}
		return list;
	}

}
