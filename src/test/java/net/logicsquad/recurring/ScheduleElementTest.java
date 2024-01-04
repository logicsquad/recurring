package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class ScheduleElementTest {
	private TemporalExpression dayInMonth = DayInMonth.of(DayOfWeek.MONDAY, 2);
	private TemporalExpression range = RangeEveryYear.of(Month.JANUARY, Month.JUNE);

	private Intersection intersection = Intersection.of(dayInMonth, range);

	private static final String EVENT = "Some event";

	private ScheduleElement<String> element = ScheduleElement.of(EVENT, intersection);

	private LocalDate in_1 = LocalDate.of(2018, 1, 8);
	private LocalDate in_2 = LocalDate.of(2018, 2, 12);
	private LocalDate in_3 = LocalDate.of(2018, 3, 12);

	private LocalDate out_1 = LocalDate.of(2018, 7, 9);
	private LocalDate out_2 = LocalDate.of(2018, 8, 13);
	private LocalDate out_3 = LocalDate.of(2018, 9, 10);

	@Test
	public void includesExpectedDates() {
		assertTrue(element.isOccurring(in_1));
		assertTrue(element.isOccurring(in_2));
		assertTrue(element.isOccurring(in_3));
		return;
	}

	@Test
	public void excludesExpectedDates() {
		assertFalse(element.isOccurring(out_1));
		assertFalse(element.isOccurring(out_2));
		assertFalse(element.isOccurring(out_3));
		return;
	}
}
