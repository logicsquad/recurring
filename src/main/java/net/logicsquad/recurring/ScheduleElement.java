package net.logicsquad.recurring;

import java.time.LocalDate;

public interface ScheduleElement {
	boolean isOccurring(LocalDate date);
}
