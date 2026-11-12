package app.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;

@Entity
public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long pk;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_pk")
	@NotNull(message = "Member is required.")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subscription_pk")
	@NotNull(message = "Subscription is required.")
	private Subscription subscription;

	@Column
	@NotNull(message = "Check-in time is required.")
	private LocalDateTime checkedInAt;

	public CheckIn() {
	}

	public CheckIn(Member member, Subscription subscription, LocalDateTime checkedInAt) {
		this.member = member;
		this.subscription = subscription;
		this.checkedInAt = checkedInAt;
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

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public LocalDateTime getCheckedInAt() {
		return checkedInAt;
	}

	public void setCheckedInAt(LocalDateTime checkedInAt) {
		this.checkedInAt = checkedInAt;
	}

	@Override
	public String toString() {
		return "CheckIn [pk=" + pk
				+ ", member=" + (member == null ? null : member.getPk())
				+ ", subscription=" + (subscription == null ? null : subscription.getPk())
				+ ", checkedInAt=" + checkedInAt + "]";
	}

}
