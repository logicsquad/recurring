package net.logicsquad.recurring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

import org.junit.Test;

public class RangeEveryYearTest {
	private RangeEveryYear fullRange = new RangeEveryYear(MonthDay.of(Month.MARCH, 12), MonthDay.of(Month.JULY, 20));
	private RangeEveryYear monthsRange = new RangeEveryYear(Month.APRIL, Month.AUGUST);

	private RangeEveryYear monthRange = new RangeEveryYear(Month.JUNE);

	private LocalDate fullRange_in1 = LocalDate.of(2018, 3, 12);
	private LocalDate fullRange_in2 = LocalDate.of(2018, 7, 20);
	private LocalDate fullRange_in3 = LocalDate.of(2018, 6, 30);
	private LocalDate fullRange_out1 = LocalDate.of(2019, 8, 1);
	private LocalDate fullRange_out2 = LocalDate.of(2020, 1, 31);
	private LocalDate fullRange_out3 = LocalDate.of(2020, 3, 11);

	private LocalDate monthsRange_in1 = LocalDate.of(2018, 4, 1);
	private LocalDate monthsRange_in2 = LocalDate.of(2018, 8, 1);
	private LocalDate monthsRange_in3 = LocalDate.of(2018, 5, 11);
	private LocalDate monthsRange_out1 = LocalDate.of(2019, 3, 3);
	private LocalDate monthsRange_out2 = LocalDate.of(2020, 9, 9);

	private LocalDate monthRange_in1 = LocalDate.of(2018, 6, 1);
	private LocalDate monthRange_in2 = LocalDate.of(2018, 6, 30);
	private LocalDate monthRange_in3 = LocalDate.of(2020, 6, 12);
	private LocalDate monthRange_out1 = LocalDate.of(2019, 12, 31);
	private LocalDate monthRange_out2 = LocalDate.of(2020, 1, 1);

	@Test
	public void includesReturnsTrueForDatesInFullRange() {
		assertTrue(fullRange.includes(fullRange_in1));
		assertTrue(fullRange.includes(fullRange_in2));
		assertTrue(fullRange.includes(fullRange_in3));
		return;
	}

	@Test
	public void includesReturnsFalseForDatesNotInFullRange() {
		assertFalse(fullRange.includes(fullRange_out1));
		assertFalse(fullRange.includes(fullRange_out2));
		assertFalse(fullRange.includes(fullRange_out3));
		return;
	}

	@Test
	public void includesReturnsTrueForDatesInMonthsRange() {
		assertTrue(monthsRange.includes(monthsRange_in1));
		assertTrue(monthsRange.includes(monthsRange_in2));
		assertTrue(monthsRange.includes(monthsRange_in3));
		return;
	}

	@Test
	public void includesReturnsFalseForDatesNotInMonthsRange() {
		assertFalse(monthsRange.includes(monthsRange_out1));
		assertFalse(monthsRange.includes(monthsRange_out2));
		return;
	}

	@Test
	public void includesReturnsTrueForDatesInMonthRange() {
		assertTrue(monthRange.includes(monthRange_in1));
		assertTrue(monthRange.includes(monthRange_in2));
		assertTrue(monthRange.includes(monthRange_in3));
		return;
	}

	@Test
	public void includesReturnsFalseForDatesNotInMonthRange() {
		assertFalse(monthRange.includes(monthRange_out1));
		assertFalse(monthRange.includes(monthRange_out2));
		return;
	}
}
