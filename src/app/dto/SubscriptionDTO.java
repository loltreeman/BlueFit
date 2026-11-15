package app.dto;

import java.math.BigDecimal;

public class SubscriptionDTO {

	private Long pk;

	private Long memberPk;

	private String memberName;

	private Long planPk;

	private String planName;

	private BigDecimal agreedPrice;

	private BigDecimal amountPaid;

	private BigDecimal balance;

	private String paymentStatus;

	private String startDate;

	private String endDate;

	private String status;

	public SubscriptionDTO() {
	}

	public SubscriptionDTO(Long pk, Long memberPk, String memberName, Long planPk, String planName, BigDecimal agreedPrice, BigDecimal amountPaid, BigDecimal balance, String paymentStatus, String startDate, String endDate, String status) {
		this.pk = pk;
		this.memberPk = memberPk;
		this.memberName = memberName;
		this.planPk = planPk;
		this.planName = planName;
		this.agreedPrice = agreedPrice;
		this.amountPaid = amountPaid;
		this.balance = balance;
		this.paymentStatus = paymentStatus;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Long getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(Long memberPk) {
		this.memberPk = memberPk;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getPlanPk() {
		return planPk;
	}

	public void setPlanPk(Long planPk) {
		this.planPk = planPk;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public BigDecimal getAgreedPrice() {
		return agreedPrice;
	}

	public void setAgreedPrice(BigDecimal agreedPrice) {
		this.agreedPrice = agreedPrice;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SubscriptionDTO [pk=" + pk
				+ ", memberPk=" + memberPk
				+ ", memberName=" + memberName
				+ ", planPk=" + planPk
				+ ", planName=" + planName
				+ ", agreedPrice=" + agreedPrice
				+ ", amountPaid=" + amountPaid
				+ ", balance=" + balance
				+ ", paymentStatus=" + paymentStatus
				+ ", startDate=" + startDate
				+ ", endDate=" + endDate
				+ ", status=" + status + "]";
	}

}
