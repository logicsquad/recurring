package net.logicsquad.recurring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
	 * Expression that should be equal to {@link #exp}
	 */
	private DayInMonth same1 = DayInMonth.of(DayOfWeek.MONDAY, 2);

	/**
	 * Expression that should be equal to {@link #exp}
	 */
	private DayInMonth same2 = DayInMonth.of(DayOfWeek.MONDAY, 2);

	/**
	 * Expression that should be unequal to {@link #exp}
	 */
	private DayInMonth diff1 = DayInMonth.of(DayOfWeek.SUNDAY, 3);

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

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(exp.equals(exp));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(exp.equals(same1));
		assertTrue(same1.equals(exp));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(exp.equals(same1));
		assertTrue(same1.equals(same2));
		assertTrue(exp.equals(same2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(exp.equals(same1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(exp.equals(null));
		assertFalse(same1.equals(null));
		assertFalse(same2.equals(null));
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int hashCode = exp.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, exp.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(exp.hashCode(), same1.hashCode());
		assertEquals(exp.hashCode(), same2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(exp.hashCode(), diff1.hashCode());
		return;
	}

	/**
	 * {@link DayOfWeek} argument cannot be {@code null}.
	 */
	@Test(expected = NullPointerException.class)
	public void ofThrowsOnNullDayOfWeek() {
		DayInMonth.of(null, 1);
	}

	/**
	 * A value of 0 for {@code ordinal} doesn't make sense.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsOnZeroOrdinal() {
		DayInMonth.of(DayOfWeek.MONDAY, 0);
	}

	/**
	 * {@code ordinal} can be at most 5 (either side of 0).
	 */
	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsOnOrdinalOver5() {
		DayInMonth.of(DayOfWeek.MONDAY, 6);
	}

	/**
	 * {@code ordinal} can be at most 5 (either side of 0).
	 */
	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsOnOrdinalUnderMinus5() {
		DayInMonth.of(DayOfWeek.MONDAY, -6);
	}
}
