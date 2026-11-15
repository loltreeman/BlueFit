package app.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Member;
import app.entities.MembershipPlan;
import app.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	public List<Subscription> findByMember(Member member);

	public List<Subscription> findByMemberAndStatusIn(Member member, List<String> statuses);

	public List<Subscription> findByStatus(String status);

	public List<Subscription> findByStatusIn(List<String> statuses);

	public List<Subscription> findByStatusAndEndDateBetween(String status, LocalDate from, LocalDate to);

	public List<Subscription> findByStatusAndEndDateBefore(String status, LocalDate date);

	public List<Subscription> findByStatusAndStartDateLessThanEqual(String status, LocalDate date);

	public long countByMember(Member member);

	public long countByPlan(MembershipPlan plan);

}
