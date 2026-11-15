package app.dto;

import java.math.BigDecimal;

public class PaymentDTO {

	private Long pk;

	private Long subscriptionPk;

	private BigDecimal amount;

	private String method;

	private String referenceNo;

	private String paidAt;

	private BigDecimal remainingBalance;

	public PaymentDTO() {
	}

	public PaymentDTO(Long pk, Long subscriptionPk, BigDecimal amount, String method, String referenceNo, String paidAt, BigDecimal remainingBalance) {
		this.pk = pk;
		this.subscriptionPk = subscriptionPk;
		this.amount = amount;
		this.method = method;
		this.referenceNo = referenceNo;
		this.paidAt = paidAt;
		this.remainingBalance = remainingBalance;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Long getSubscriptionPk() {
		return subscriptionPk;
	}

	public void setSubscriptionPk(Long subscriptionPk) {
		this.subscriptionPk = subscriptionPk;
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

	public String getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(String paidAt) {
		this.paidAt = paidAt;
	}

	public BigDecimal getRemainingBalance() {
		return remainingBalance;
	}

	public void setRemainingBalance(BigDecimal remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	@Override
	public String toString() {
		return "PaymentDTO [pk=" + pk
				+ ", subscriptionPk=" + subscriptionPk
				+ ", amount=" + amount
				+ ", method=" + method
				+ ", referenceNo=" + referenceNo
				+ ", paidAt=" + paidAt
				+ ", remainingBalance=" + remainingBalance + "]";
	}

}
