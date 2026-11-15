package app.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// timestamps in JSON always look like 2026-10-30T07:15:00
public class TimeFormat {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public static String of(LocalDateTime time) {
		return time == null ? null : time.format(FORMAT);
	}

}
