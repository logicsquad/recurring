package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

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
	 * Fifth Saturdays starting 1 January 2024
	 */
	private List<LocalDate> expectedFifthSaturdays = new ArrayList<>(Arrays.asList(
			LocalDate.of(2024, 3, 30),
			LocalDate.of(2024, 6, 29),
			LocalDate.of(2024, 8, 31),
			LocalDate.of(2024, 11, 30),
			LocalDate.of(2025, 3, 29),
			LocalDate.of(2025, 5, 31),
			LocalDate.of(2025, 8, 30),
			LocalDate.of(2025, 11, 29),
			LocalDate.of(2026, 1, 31),
			LocalDate.of(2026, 5, 30)));

	/**
	 * Fifth Saturdays from the end of the month starting 1 January 2024
	 */
	private List<LocalDate> expectedMinusFifthSaturdays = new ArrayList<>(Arrays.asList(
			LocalDate.of(2024, 3, 2),
			LocalDate.of(2024, 6, 1),
			LocalDate.of(2024, 8, 3),
			LocalDate.of(2024, 11, 2),
			LocalDate.of(2025, 3, 1),
			LocalDate.of(2025, 5, 3),
			LocalDate.of(2025, 8, 2),
			LocalDate.of(2025, 11, 1),
			LocalDate.of(2026, 1, 3),
			LocalDate.of(2026, 5, 2)));

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
	@Test
	public void ofThrowsOnNullDayOfWeek() {
		assertThrows(NullPointerException.class, () -> DayInMonth.of(null, 1));
		return;
	}

	/**
	 * A value of 0 for {@code ordinal} doesn't make sense.
	 */
	@Test
	public void ofThrowsOnZeroOrdinal() {
		assertThrows(IllegalArgumentException.class, () -> DayInMonth.of(DayOfWeek.MONDAY, 0));
		return;
	}

	/**
	 * {@code ordinal} can be at most 5 (either side of 0).
	 */
	@Test
	public void ofThrowsOnOrdinalOver5() {
		assertThrows(IllegalArgumentException.class, () -> DayInMonth.of(DayOfWeek.MONDAY, 6));
		return;
	}

	/**
	 * {@code ordinal} can be at most 5 (either side of 0).
	 */
	@Test
	public void ofThrowsOnOrdinalUnderMinus5() {
		assertThrows(IllegalArgumentException.class, () -> DayInMonth.of(DayOfWeek.MONDAY, -6));
		return;
	}

	/**
	 * Obviously not every month has a "fifth Saturday", for example. Here we're making sure that the sequence of dates is correct, and we don't
	 * get a result where, say, the "fifth Saturday" in March falls through to the first Saturday of April when it doesn't exist in March at
	 * all.
	 */
	@Test
	public void fifthSaturdaysIsExpectedSequence() {
		DayInMonth fifthSaturday = DayInMonth.of(DayOfWeek.SATURDAY, 5);
		ScheduleElement<String> fifthElement = ScheduleElement.of("fifth", fifthSaturday);
		Schedule<String> fifthSchedule = Schedule.of(fifthElement);
		List<LocalDate> fifthSaturdays = fifthSchedule.futureDates("fifth", LocalDate.of(2024, 1, 1)).limit(10).collect(Collectors.toList());
		assertEquals(expectedFifthSaturdays, fifthSaturdays);
		return;
	}

	/**
	 * Similarly, not every month has a "fifth Saturday from the end": it only occurs in those months that have five Saturdays to begin with, in
	 * which case it's the first Saturday in that month.
	 */
	@Test
	public void minusFifthSaturdaysIsExpectedSequence() {
		DayInMonth minusFifthSaturday = DayInMonth.of(DayOfWeek.SATURDAY, -5);
		ScheduleElement<String> minusFifthElement = ScheduleElement.of("fifth", minusFifthSaturday);
		Schedule<String> minusFifthSchedule = Schedule.of(minusFifthElement);
		List<LocalDate> minusFifthSaturdays = minusFifthSchedule.futureDates("fifth", LocalDate.of(2024, 1, 1)).limit(10).collect(Collectors.toList());
		assertEquals(expectedMinusFifthSaturdays, minusFifthSaturdays);
		return;
	}
}
