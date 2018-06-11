package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.List;

/**
 * Encapsulates a "schedule" which can answer various questions about events it
 * knows about.
 * 
 * @author paulh
 */
public interface Schedule {
	boolean isOccurring(String event, LocalDate date);

	List<LocalDate> dates(String event, LocalDate start, LocalDate end);

	LocalDate nextOccurrence(String event, LocalDate date);
}
