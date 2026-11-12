package app.dto;

public class CheckInDTO {

	private Long pk;

	private Long memberPk;

	private String memberName;

	private Long subscriptionPk;

	private String checkedInAt;

	private Long daysRemaining;

	public CheckInDTO() {
	}

	public CheckInDTO(Long pk, Long memberPk, String memberName, Long subscriptionPk, String checkedInAt, Long daysRemaining) {
		this.pk = pk;
		this.memberPk = memberPk;
		this.memberName = memberName;
		this.subscriptionPk = subscriptionPk;
		this.checkedInAt = checkedInAt;
		this.daysRemaining = daysRemaining;
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

	public Long getSubscriptionPk() {
		return subscriptionPk;
	}

	public void setSubscriptionPk(Long subscriptionPk) {
		this.subscriptionPk = subscriptionPk;
	}

	public String getCheckedInAt() {
		return checkedInAt;
	}

	public void setCheckedInAt(String checkedInAt) {
		this.checkedInAt = checkedInAt;
	}

	public Long getDaysRemaining() {
		return daysRemaining;
	}

	public void setDaysRemaining(Long daysRemaining) {
		this.daysRemaining = daysRemaining;
	}

	@Override
	public String toString() {
		return "CheckInDTO [pk=" + pk
				+ ", memberPk=" + memberPk
				+ ", memberName=" + memberName
				+ ", subscriptionPk=" + subscriptionPk
				+ ", checkedInAt=" + checkedInAt
				+ ", daysRemaining=" + daysRemaining + "]";
	}

}
