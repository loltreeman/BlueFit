package app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.CheckIn;
import app.entities.Member;
import app.entities.Subscription;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

	public List<CheckIn> findByMemberOrderByCheckedInAtDesc(Member member);

	public CheckIn findFirstByMemberOrderByCheckedInAtDesc(Member member);

	public List<CheckIn> findByCheckedInAtBetween(LocalDateTime from, LocalDateTime to);

	public long countByMember(Member member);

	public long countBySubscription(Subscription subscription);

}
