package net.logicsquad.recurring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class IntersectionTest {
	private TemporalExpression dayInMonth = new DayInMonth(DayOfWeek.MONDAY, 2);
	private TemporalExpression range = new RangeEveryYear(Month.JANUARY, Month.JUNE);

	private Intersection intersection = Intersection.of(dayInMonth, range);

	private LocalDate in_1 = LocalDate.of(2018, 1, 8);
	private LocalDate in_2 = LocalDate.of(2018, 2, 12);
	private LocalDate in_3 = LocalDate.of(2018, 3, 12);
	private LocalDate in_4 = LocalDate.of(2018, 4, 9);
	private LocalDate in_5 = LocalDate.of(2018, 5, 14);
	private LocalDate in_6 = LocalDate.of(2018, 6, 11);

	// Correct Monday, outside month range
	private LocalDate out_1 = LocalDate.of(2018, 7, 9);
	private LocalDate out_2 = LocalDate.of(2018, 8, 13);
	private LocalDate out_3 = LocalDate.of(2018, 9, 10);
	private LocalDate out_4 = LocalDate.of(2018, 10, 9);
	private LocalDate out_5 = LocalDate.of(2018, 11, 12);
	private LocalDate out_6 = LocalDate.of(2018, 12, 10);

	// Correct month range, not second Monday
	private LocalDate out_7 = LocalDate.of(2018, 1, 1);
	private LocalDate out_8 = LocalDate.of(2018, 2, 1);
	private LocalDate out_9 = LocalDate.of(2018, 3, 1);
	private LocalDate out_10 = LocalDate.of(2018, 4, 1);
	private LocalDate out_11 = LocalDate.of(2018, 5, 1);
	private LocalDate out_12 = LocalDate.of(2018, 6, 1);

	@Test
	public void includesExpectedDates() {
		assertTrue(intersection.includes(in_1));
		assertTrue(intersection.includes(in_2));
		assertTrue(intersection.includes(in_3));
		assertTrue(intersection.includes(in_4));
		assertTrue(intersection.includes(in_5));
		assertTrue(intersection.includes(in_6));
		return;
	}

	@Test
	public void excludesExpectedDates() {
		assertFalse(intersection.includes(out_1));
		assertFalse(intersection.includes(out_2));
		assertFalse(intersection.includes(out_3));
		assertFalse(intersection.includes(out_4));
		assertFalse(intersection.includes(out_5));
		assertFalse(intersection.includes(out_6));
		assertFalse(intersection.includes(out_7));
		assertFalse(intersection.includes(out_8));
		assertFalse(intersection.includes(out_9));
		assertFalse(intersection.includes(out_10));
		assertFalse(intersection.includes(out_11));
		assertFalse(intersection.includes(out_12));
		return;
	}
}
