package app.dto;

public class NotificationDTO {

	private Long pk;

	private Long memberPk;

	private Long subscriptionPk;

	private String toNumber;

	private String type;

	private String message;

	private String status;

	private String sentAt;

	public NotificationDTO() {
	}

	public NotificationDTO(Long pk, Long memberPk, Long subscriptionPk, String toNumber, String type, String message, String status, String sentAt) {
		this.pk = pk;
		this.memberPk = memberPk;
		this.subscriptionPk = subscriptionPk;
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

	public Long getMemberPk() {
		return memberPk;
	}

	public void setMemberPk(Long memberPk) {
		this.memberPk = memberPk;
	}

	public Long getSubscriptionPk() {
		return subscriptionPk;
	}

	public void setSubscriptionPk(Long subscriptionPk) {
		this.subscriptionPk = subscriptionPk;
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

	public String getSentAt() {
		return sentAt;
	}

	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}

	@Override
	public String toString() {
		return "NotificationDTO [pk=" + pk
				+ ", memberPk=" + memberPk
				+ ", subscriptionPk=" + subscriptionPk
				+ ", toNumber=" + toNumber
				+ ", type=" + type
				+ ", message=" + message
				+ ", status=" + status
				+ ", sentAt=" + sentAt + "]";
	}

}
