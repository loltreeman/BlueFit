package app.rest.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import app.rest.errorhandlers.ServiceException;

// turns raw form/query strings into typed values with readable errors
public class Params {

	public static String required(String name, String value) throws ServiceException {
		if (value == null || value.trim().isEmpty()) {
			throw new ServiceException(name + " is required.");
		}
		return value.trim();
	}

	public static String optional(String value) {
		return (value == null || value.trim().isEmpty()) ? null : value.trim();
	}

	public static Long toLong(String name, String value) throws ServiceException {
		try {
			return Long.parseLong(required(name, value));
		} catch (NumberFormatException e) {
			throw new ServiceException(name + " must be a valid number.");
		}
	}

	public static Integer toInt(String name, String value) throws ServiceException {
		try {
			return Integer.parseInt(required(name, value));
		} catch (NumberFormatException e) {
			throw new ServiceException(name + " must be a valid number.");
		}
	}

	public static BigDecimal toMoney(String name, String value) throws ServiceException {
		try {
			return new BigDecimal(required(name, value)).setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (NumberFormatException e) {
			throw new ServiceException(name + " must be a valid number.");
		}
	}

	public static LocalDate toDate(String name, String value) throws ServiceException {
		try {
			return LocalDate.parse(required(name, value));
		} catch (DateTimeParseException e) {
			throw new ServiceException(name + " must be a date in yyyy-MM-dd format.");
		}
	}

}
