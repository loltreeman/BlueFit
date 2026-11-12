package app.dto;

public class HourCountDTO {

	private Integer hour;

	private Integer count;

	public HourCountDTO() {
	}

	public HourCountDTO(Integer hour, Integer count) {
		this.hour = hour;
		this.count = count;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "HourCountDTO [hour=" + hour
				+ ", count=" + count + "]";
	}

}
