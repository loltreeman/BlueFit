package app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Member;
import app.entities.NotificationLog;
import app.entities.Subscription;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

	public List<NotificationLog> findByMemberOrderBySentAtDesc(Member member);

	public long countBySubscriptionAndTypeAndStatus(Subscription subscription, String type, String status);

	public NotificationLog findFirstByMemberAndTypeAndStatusOrderBySentAtDesc(Member member, String type, String status);

	public long countByTypeAndStatusAndSentAtBetween(String type, String status, LocalDateTime from, LocalDateTime to);

	public long countByMember(Member member);

	public long countBySubscription(Subscription subscription);

}
