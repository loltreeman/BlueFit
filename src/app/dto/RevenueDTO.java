package app.dto;

import java.math.BigDecimal;
import java.util.Map;

public class RevenueDTO {

	private String from;

	private String to;

	private BigDecimal totalRevenue;

	private Integer paymentCount;

	private Map<String, BigDecimal> byMethod;

	public RevenueDTO() {
	}

	public RevenueDTO(String from, String to, BigDecimal totalRevenue, Integer paymentCount, Map<String, BigDecimal> byMethod) {
		this.from = from;
		this.to = to;
		this.totalRevenue = totalRevenue;
		this.paymentCount = paymentCount;
		this.byMethod = byMethod;
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

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Integer getPaymentCount() {
		return paymentCount;
	}

	public void setPaymentCount(Integer paymentCount) {
		this.paymentCount = paymentCount;
	}

	public Map<String, BigDecimal> getByMethod() {
		return byMethod;
	}

	public void setByMethod(Map<String, BigDecimal> byMethod) {
		this.byMethod = byMethod;
	}

	@Override
	public String toString() {
		return "RevenueDTO [from=" + from
				+ ", to=" + to
				+ ", totalRevenue=" + totalRevenue
				+ ", paymentCount=" + paymentCount
				+ ", byMethod=" + byMethod + "]";
	}

}
