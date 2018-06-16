package net.logicsquad.recurring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

public class UnionTest {
	RangeEveryYear range = RangeEveryYear.of(Month.JUNE, Month.SEPTEMBER);
	DayInMonth day = DayInMonth.of(DayOfWeek.SUNDAY, 1);

	Union union = Union.of(range, day);

	@Test
	public void unionIncludesAnyDayInRange() {
		assertTrue(union.includes(LocalDate.of(2018, 6, 1)));
		assertTrue(union.includes(LocalDate.of(2018, 6, 30)));
		assertTrue(union.includes(LocalDate.of(2018, 9, 1)));
		assertTrue(union.includes(LocalDate.of(2018, 9, 30)));
		return;
	}

	@Test
	public void unionIncludesAnyDayInMonthMatch() {
		assertTrue(union.includes(LocalDate.of(2019, 1, 6)));
		assertTrue(union.includes(LocalDate.of(2019, 2, 3)));
		assertTrue(union.includes(LocalDate.of(2019, 4, 7)));
		assertTrue(union.includes(LocalDate.of(2020, 1, 5)));
		return;
	}

	@Test
	public void unionIncludesDayInMonthAndRangeMatch() {
		assertTrue(union.includes(LocalDate.of(2018, 6, 3)));
		assertTrue(union.includes(LocalDate.of(2018, 7, 1)));
		assertTrue(union.includes(LocalDate.of(2018, 8, 5)));
		assertTrue(union.includes(LocalDate.of(2018, 9, 2)));
		return;
	}

	@Test
	public void unionExcludesOtherDates() {
		assertFalse(union.includes(LocalDate.of(2018, 4, 18)));
		assertFalse(union.includes(LocalDate.of(2019, 2, 6)));
		assertFalse(union.includes(LocalDate.of(2020, 4, 7)));
		assertFalse(union.includes(LocalDate.of(2021, 5, 11)));
		return;
	}
}
