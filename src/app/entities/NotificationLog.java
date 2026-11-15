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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class NotificationLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long pk;

	// null only for OWNER_SUMMARY
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_pk")
	private Member member;

	// set for renewal reminders
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subscription_pk")
	private Subscription subscription;

	@Column
	@NotBlank(message = "Recipient number is required.")
	@Pattern(regexp = "^\\+639[0-9]{9}$", message = "Cellphone number must look like +639XXXXXXXXX.")
	private String toNumber;

	@Column
	@NotBlank(message = "Notification type is required.")
	@Pattern(regexp = "REMINDER_7D|REMINDER_1D|INACTIVITY|OWNER_SUMMARY|MANUAL", message = "Notification type must be REMINDER_7D, REMINDER_1D, INACTIVITY, OWNER_SUMMARY or MANUAL.")
	private String type;

	@Column(length = 320)
	@NotBlank(message = "Message must be 1 to 320 characters.")
	@Size(max = 320, message = "Message must be 1 to 320 characters.")
	private String message;

	// status from the SMS reply (e.g. accepted), or FAILED
	@Column
	@NotBlank(message = "Status is required.")
	private String status;

	@Column
	@NotNull(message = "Send time is required.")
	private LocalDateTime sentAt;

	public NotificationLog() {
	}

	public NotificationLog(Member member, Subscription subscription, String toNumber, String type, String message, String status, LocalDateTime sentAt) {
		this.member = member;
		this.subscription = subscription;
		this.toNumber = toNumber;
		this.type = type;
		this.message = message;
		this.status = status;
		this.sentAt = sentAt;
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

	public String getToNumber() {
		return toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public static final String REMINDER_7D = "REMINDER_7D";
	public static final String REMINDER_1D = "REMINDER_1D";
	public static final String INACTIVITY = "INACTIVITY";
	public static final String OWNER_SUMMARY = "OWNER_SUMMARY";
	public static final String MANUAL = "MANUAL";
	public static final String ACCEPTED = "accepted";
	public static final String FAILED = "FAILED";

	@Override
	public String toString() {
		return "NotificationLog [pk=" + pk
				+ ", member=" + (member == null ? null : member.getPk())
				+ ", subscription=" + (subscription == null ? null : subscription.getPk())
				+ ", toNumber=" + toNumber
				+ ", type=" + type
				+ ", message=" + message
				+ ", status=" + status
				+ ", sentAt=" + sentAt + "]";
	}

}
