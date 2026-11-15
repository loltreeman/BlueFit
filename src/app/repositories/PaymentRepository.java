package app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Payment;
import app.entities.Subscription;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	public List<Payment> findBySubscription(Subscription subscription);

	public List<Payment> findByPaidAtBetween(LocalDateTime from, LocalDateTime to);

	public long countBySubscription(Subscription subscription);

}
