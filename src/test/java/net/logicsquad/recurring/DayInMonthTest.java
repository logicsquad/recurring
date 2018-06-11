package net.logicsquad.recurring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.Test;

public class DayInMonthTest {
	private DayInMonth exp = new DayInMonth(1, 2);

	private LocalDate match1 = LocalDate.of(2018, 6, 11);

	private LocalDate match2 = LocalDate.of(2018, 8, 13);

	@Test
	public void expressionMatchesSecondMondays() {
		assertTrue(exp.includes(match1));
		assertTrue(exp.includes(match2));
		return;
	}

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
