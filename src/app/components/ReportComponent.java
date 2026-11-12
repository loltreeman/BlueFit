package app.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.HourCountDTO;
import app.dto.PeakHoursDTO;
import app.dto.RevenueDTO;
import app.entities.CheckIn;
import app.entities.Payment;
import app.repositories.CheckInRepository;
import app.repositories.PaymentRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class ReportComponent {

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private CheckInRepository checkInRepo;

	public RevenueDTO getRevenue(LocalDate from, LocalDate to) throws ServiceException {
		checkRange(from, to);

		BigDecimal zero = BigDecimal.ZERO.setScale(2);
		BigDecimal total = zero;
		Map<String, BigDecimal> byMethod = new LinkedHashMap<>();
		byMethod.put("CASH", zero);
		byMethod.put("GCASH", zero);
		byMethod.put("CARD", zero);

		List<Payment> payments = paymentRepo.findByPaidAtBetween(from.atStartOfDay(), to.plusDays(1).atStartOfDay());
		for (Payment p : payments) {
			total = total.add(p.getAmount());
			byMethod.put(p.getMethod(), byMethod.get(p.getMethod()).add(p.getAmount()));
		}

		return new RevenueDTO(from.toString(), to.toString(), total, payments.size(), byMethod);
	}

	public PeakHoursDTO getPeakHours(LocalDate from, LocalDate to) throws ServiceException {
		checkRange(from, to);

		int[] counts = new int[24];
		List<CheckIn> checkIns = checkInRepo.findByCheckedInAtBetween(from.atStartOfDay(),
				to.plusDays(1).atStartOfDay());
		for (CheckIn c : checkIns) {
			counts[c.getCheckedInAt().getHour()]++;
		}

		List<HourCountDTO> byHour = new ArrayList<>();
		for (int hour = 0; hour < 24; hour++) {
			if (counts[hour] > 0) {
				byHour.add(new HourCountDTO(hour, counts[hour]));
			}
		}

		return new PeakHoursDTO(from.toString(), to.toString(), checkIns.size(), byHour);
	}

	private void checkRange(LocalDate from, LocalDate to) throws ServiceException {
		if (from.isAfter(to)) {
			throw new ServiceException("from must not be after to.");
		}
	}

}
