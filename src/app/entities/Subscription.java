package app.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

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

@Entity
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long pk;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_pk")
	@NotNull(message = "Member is required.")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_pk")
	@NotNull(message = "Plan is required.")
	private MembershipPlan plan;

	// copied from the plan at sale, so later price changes don't affect it
	@Column
	@NotNull(message = "Agreed price is required.")
	@DecimalMin(value = "0.01", message = "Agreed price must be greater than 0.")
	private BigDecimal agreedPrice;

	@Column
	@NotNull(message = "Start date is required.")
	private LocalDate startDate;

	@Column
	@NotNull(message = "End date is required.")
	private LocalDate endDate;

	@Column
	@NotBlank(message = "Status is required.")
	@Pattern(regexp = "PENDING|ACTIVE|FROZEN|EXPIRED|CANCELLED", message = "Status must be PENDING, ACTIVE, FROZEN, EXPIRED or CANCELLED.")
	private String status;

	// only set while FROZEN
	@Column
	private LocalDate frozenOn;

	public Subscription() {
	}

	public Subscription(Member member, MembershipPlan plan, BigDecimal agreedPrice, LocalDate startDate, LocalDate endDate, String status) {
		this.member = member;
		this.plan = plan;
		this.agreedPrice = agreedPrice;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MembershipPlan getPlan() {
		return plan;
	}

	public void setPlan(MembershipPlan plan) {
		this.plan = plan;
	}

	public BigDecimal getAgreedPrice() {
		return agreedPrice;
	}

	public void setAgreedPrice(BigDecimal agreedPrice) {
		this.agreedPrice = agreedPrice;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getFrozenOn() {
		return frozenOn;
	}

	public void setFrozenOn(LocalDate frozenOn) {
		this.frozenOn = frozenOn;
	}

	public static final String PENDING = "PENDING";
	public static final String ACTIVE = "ACTIVE";
	public static final String FROZEN = "FROZEN";
	public static final String EXPIRED = "EXPIRED";
	public static final String CANCELLED = "CANCELLED";

	@Override
	public String toString() {
		return "Subscription [pk=" + pk
				+ ", member=" + (member == null ? null : member.getPk())
				+ ", plan=" + (plan == null ? null : plan.getPk())
				+ ", agreedPrice=" + agreedPrice
				+ ", startDate=" + startDate
				+ ", endDate=" + endDate
				+ ", status=" + status
				+ ", frozenOn=" + frozenOn + "]";
	}

}
