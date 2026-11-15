package app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long pk;

	@Column
	@NotBlank(message = "Name is required.")
	@Size(max = 100, message = "Name must be at most 100 characters.")
	private String name;

	@Column(unique = true)
	@NotBlank(message = "School ID is required.")
	@Pattern(regexp = "^[0-9]{4,12}$", message = "School ID must be 4 to 12 digits.")
	private String schoolId;

	@Column
	@NotBlank(message = "Cellphone number is required.")
	@Pattern(regexp = "^\\+639[0-9]{9}$", message = "Cellphone number must look like +639XXXXXXXXX.")
	private String cellphoneNumber;

	@Column
	@Email(message = "Email is not valid.")
	private String email;

	@Column
	@NotBlank(message = "Affiliation is required.")
	@Pattern(regexp = "STUDENT|FACULTY|STAFF|ALUMNI", message = "Affiliation must be STUDENT, FACULTY, STAFF or ALUMNI.")
	private String affiliation;

	@Column
	@NotNull(message = "Join date is required.")
	private LocalDate joinedOn;

	public Member() {
	}

	public Member(String name, String schoolId, String cellphoneNumber, String email, String affiliation, LocalDate joinedOn) {
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

	public LocalDate getJoinedOn() {
		return joinedOn;
	}

	public void setJoinedOn(LocalDate joinedOn) {
		this.joinedOn = joinedOn;
	}

	@Override
	public String toString() {
		return "Member [pk=" + pk
				+ ", name=" + name
				+ ", schoolId=" + schoolId
				+ ", cellphoneNumber=" + cellphoneNumber
				+ ", email=" + email
				+ ", affiliation=" + affiliation
				+ ", joinedOn=" + joinedOn + "]";
	}

}
