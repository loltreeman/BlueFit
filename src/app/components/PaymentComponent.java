package app.components;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.DeleteDTO;
import app.dto.PaymentDTO;
import app.entities.Payment;
import app.entities.Subscription;
import app.repositories.PaymentRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class PaymentComponent {

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private SubscriptionComponent subscriptionComponent;

	public PaymentDTO recordPayment(Long subscriptionPk, BigDecimal amount, String method, String referenceNo)
			throws ServiceException {
		Subscription s = subscriptionComponent.find(subscriptionPk);
		if (Subscription.CANCELLED.equals(s.getStatus())) {
			throw new ServiceException("Subscription " + subscriptionPk + " is cancelled and cannot accept payments.");
		}

		if (("GCASH".equals(method) || "CARD".equals(method)) && (referenceNo == null || referenceNo.trim().isEmpty())) {
			throw new ServiceException("A reference number is required for GCASH and CARD payments.");
		}

		BigDecimal balance = subscriptionComponent.balance(s);
		if (amount.compareTo(balance) > 0) {
			throw new ServiceException("Payment of " + amount + " is more than the balance of " + balance + ".");
		}

		Payment p = paymentRepo.save(new Payment(s, amount, method, referenceNo, LocalDateTime.now().withNano(0)));
		return toDTO(p);
	}

	public PaymentDTO getPayment(Long pk) throws ServiceException {
		return toDTO(find(pk));
	}

	public List<PaymentDTO> getBySubscription(Long subscriptionPk) throws ServiceException {
		List<PaymentDTO> list = new ArrayList<>();
		for (Payment p : paymentRepo.findBySubscription(subscriptionComponent.find(subscriptionPk))) {
			list.add(toDTO(p));
		}
		return list;
	}

	public DeleteDTO deletePayment(Long pk) throws ServiceException {
		paymentRepo.delete(find(pk));
		return new DeleteDTO(true, pk);
	}

	private Payment find(Long pk) throws ServiceException {
		return paymentRepo.findById(pk).orElseThrow(() -> new ServiceException("Payment " + pk + " not found."));
	}

	private PaymentDTO toDTO(Payment p) {
		return new PaymentDTO(p.getPk(), p.getSubscription().getPk(), p.getAmount(), p.getMethod(),
				p.getReferenceNo(), TimeFormat.of(p.getPaidAt()), subscriptionComponent.balance(p.getSubscription()));
	}

}
