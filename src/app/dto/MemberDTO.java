package app.dto;

public class MemberDTO {

	private Long pk;

	private String name;

	private String schoolId;

	private String cellphoneNumber;

	private String email;

	private String affiliation;

	private String joinedOn;

	public MemberDTO() {
	}

	public MemberDTO(Long pk, String name, String schoolId, String cellphoneNumber, String email, String affiliation, String joinedOn) {
		this.pk = pk;
		this.name = name;
		this.schoolId = schoolId;
		this.cellphoneNumber = cellphoneNumber;
		this.email = email;
		this.affiliation = affiliation;
		this.joinedOn = joinedOn;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getCellphoneNumber() {
		return cellphoneNumber;
	}

	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getJoinedOn() {
		return joinedOn;
	}

	public void setJoinedOn(String joinedOn) {
		this.joinedOn = joinedOn;
	}

	@Override
	public String toString() {
		return "MemberDTO [pk=" + pk
				+ ", name=" + name
				+ ", schoolId=" + schoolId
				+ ", cellphoneNumber=" + cellphoneNumber
				+ ", email=" + email
				+ ", affiliation=" + affiliation
				+ ", joinedOn=" + joinedOn + "]";
	}

}
