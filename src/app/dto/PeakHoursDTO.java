package app.dto;

import java.util.List;

public class PeakHoursDTO {

	private String from;

	private String to;

	private Integer totalCheckIns;

	private List<HourCountDTO> byHour;

	public PeakHoursDTO() {
	}

	public PeakHoursDTO(String from, String to, Integer totalCheckIns, List<HourCountDTO> byHour) {
		this.from = from;
		this.to = to;
		this.totalCheckIns = totalCheckIns;
		this.byHour = byHour;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Integer getTotalCheckIns() {
		return totalCheckIns;
	}

	public void setTotalCheckIns(Integer totalCheckIns) {
		this.totalCheckIns = totalCheckIns;
	}

	public List<HourCountDTO> getByHour() {
		return byHour;
	}

	public void setByHour(List<HourCountDTO> byHour) {
		this.byHour = byHour;
	}

	@Override
	public String toString() {
		return "PeakHoursDTO [from=" + from
				+ ", to=" + to
				+ ", totalCheckIns=" + totalCheckIns
				+ ", byHour=" + byHour + "]";
	}

}
