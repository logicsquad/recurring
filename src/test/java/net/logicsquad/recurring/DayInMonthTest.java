package net.logicsquad.recurring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.Test;

/**
 * Unit tests on {@link DayInMonth}.
 * 
 * @author paulh
 */
public class DayInMonthTest {
	/**
	 * Second Monday in month
	 */
	private DayInMonth exp = DayInMonth.of(DayOfWeek.MONDAY, 2);

	/**
	 * Second Monday in June 2018
	 */
	private LocalDate match1 = LocalDate.of(2018, 6, 11);

	/**
	 * Second Monday in August 2018
	 */
	private LocalDate match2 = LocalDate.of(2018, 8, 13);

	/**
	 * Tests that expression matches known-good dates.
	 */
	@Test
	public void expressionMatchesSecondMondays() {
		assertTrue(exp.includes(match1));
		assertTrue(exp.includes(match2));
		return;
	}

	/**
	 * Tests that expression matches no <em>other</em> dates in month.
	 */
	@Test
	public void expressionDoesNotMatchOtherDates() {
		LocalDate date = LocalDate.of(2018, 6, 1);
		while (date.isBefore(date.with(TemporalAdjusters.lastDayOfMonth()))) {
			if (!date.equals(match1)) {
				assertFalse(exp.includes(date));
			}
			date = date.plusDays(1);
		}
		return;
	}
}
