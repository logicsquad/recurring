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

	private static final String EVENT_1 = "Some event";
	private static final Integer EVENT_2 = Integer.valueOf(12);

	private ScheduleElement<String> stringElement = ScheduleElement.of(EVENT_1, intersection);
	private ScheduleElement<Integer> integerElement = ScheduleElement.of(EVENT_2, intersection);

	private LocalDate in_1 = LocalDate.of(2018, 1, 8);
	private LocalDate in_2 = LocalDate.of(2018, 2, 12);
	private LocalDate in_3 = LocalDate.of(2018, 3, 12);

	private LocalDate out_1 = LocalDate.of(2018, 7, 9);
	private LocalDate out_2 = LocalDate.of(2018, 8, 13);
	private LocalDate out_3 = LocalDate.of(2018, 9, 10);

	@Test
	public void includesExpectedDates() {
		assertTrue(stringElement.isOccurring(in_1));
		assertTrue(stringElement.isOccurring(in_2));
		assertTrue(stringElement.isOccurring(in_3));

		assertTrue(integerElement.isOccurring(in_1));
		assertTrue(integerElement.isOccurring(in_2));
		assertTrue(integerElement.isOccurring(in_3));
		return;
	}

	@Test
	public void excludesExpectedDates() {
		assertFalse(stringElement.isOccurring(out_1));
		assertFalse(stringElement.isOccurring(out_2));
		assertFalse(stringElement.isOccurring(out_3));

		assertFalse(integerElement.isOccurring(out_1));
		assertFalse(integerElement.isOccurring(out_2));
		assertFalse(integerElement.isOccurring(out_3));
		return;
	}
}
