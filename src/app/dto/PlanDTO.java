package app.dto;

import java.math.BigDecimal;

public class PlanDTO {

	private Long pk;

	private String name;

	private BigDecimal price;

	private Integer durationDays;

	private String eligibleAffiliation;

	public PlanDTO() {
	}

	public PlanDTO(Long pk, String name, BigDecimal price, Integer durationDays, String eligibleAffiliation) {
		this.pk = pk;
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
		return "PlanDTO [pk=" + pk
				+ ", name=" + name
				+ ", price=" + price
				+ ", durationDays=" + durationDays
				+ ", eligibleAffiliation=" + eligibleAffiliation + "]";
	}

}
