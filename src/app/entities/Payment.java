package app.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long pk;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subscription_pk")
	@NotNull(message = "Subscription is required.")
	private Subscription subscription;

	@Column
	@NotNull(message = "Amount is required.")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0.")
	private BigDecimal amount;

	@Column
	@NotBlank(message = "Payment method is required.")
	@Pattern(regexp = "CASH|GCASH|CARD", message = "Payment method must be CASH, GCASH or CARD.")
	private String method;

	@Column
	@Size(max = 50, message = "Reference number must be at most 50 characters.")
	private String referenceNo;

	@Column
	@NotNull(message = "Payment time is required.")
	private LocalDateTime paidAt;

	public Payment() {
	}

	public Payment(Subscription subscription, BigDecimal amount, String method, String referenceNo, LocalDateTime paidAt) {
		this.subscription = subscription;
		this.amount = amount;
		this.method = method;
		this.referenceNo = referenceNo;
		this.paidAt = paidAt;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	@Override
	public String toString() {
		return "Payment [pk=" + pk
				+ ", subscription=" + (subscription == null ? null : subscription.getPk())
				+ ", amount=" + amount
				+ ", method=" + method
				+ ", referenceNo=" + referenceNo
				+ ", paidAt=" + paidAt + "]";
	}

}
