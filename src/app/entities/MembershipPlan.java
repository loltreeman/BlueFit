package app.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class MembershipPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long pk;

	@Column(unique = true)
	@NotBlank(message = "Plan name is required.")
	@Size(max = 60, message = "Plan name must be at most 60 characters.")
	private String name;

	@Column
	@NotNull(message = "Price is required.")
	@DecimalMin(value = "0.01", message = "Price must be greater than 0.")
	private BigDecimal price;

	@Column
	@NotNull(message = "Duration is required.")
	@Min(value = 1, message = "Duration must be 1 to 366 days.")
	@Max(value = 366, message = "Duration must be 1 to 366 days.")
	private Integer durationDays;

	@Column
	@NotBlank(message = "Eligible affiliation is required.")
	@Pattern(regexp = "STUDENT|FACULTY|STAFF|ALUMNI|ALL", message = "Eligible affiliation must be STUDENT, FACULTY, STAFF, ALUMNI or ALL.")
	private String eligibleAffiliation;

	public MembershipPlan() {
	}

	public MembershipPlan(String name, BigDecimal price, Integer durationDays, String eligibleAffiliation) {
		this.name = name;
		this.price = price;
		this.durationDays = durationDays;
		this.eligibleAffiliation = eligibleAffiliation;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(Integer durationDays) {
		this.durationDays = durationDays;
	}

	public String getEligibleAffiliation() {
		return eligibleAffiliation;
	}

	public void setEligibleAffiliation(String eligibleAffiliation) {
		this.eligibleAffiliation = eligibleAffiliation;
	}

	@Override
	public String toString() {
		return "MembershipPlan [pk=" + pk
				+ ", name=" + name
				+ ", price=" + price
				+ ", durationDays=" + durationDays
				+ ", eligibleAffiliation=" + eligibleAffiliation + "]";
	}

}
