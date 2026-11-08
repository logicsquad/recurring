package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link Difference}.
 *
 * @author paulh
 */
public class DifferenceTest {
	private TemporalExpression months = RangeEachYear.of(Month.JUNE, Month.SEPTEMBER);
	private TemporalExpression days = DayInMonth.of(DayOfWeek.TUESDAY, -1);

	private TemporalExpression difference = Difference.of(months, days);

	// Objects equal to difference, and one different to it
	private TemporalExpression differenceSame1 = Difference.of(RangeEachYear.of(Month.JUNE, Month.SEPTEMBER), DayInMonth.of(DayOfWeek.TUESDAY, -1));
	private TemporalExpression differenceSame2 = Difference.of(months, days);
	private TemporalExpression differenceDiff1 = Difference.of(days, months);

	private LocalDate in_1 = LocalDate.of(2018, 6, 1);
	private LocalDate in_2 = LocalDate.of(2018, 7, 1);
	private LocalDate in_3 = LocalDate.of(2018, 8, 1);
	private LocalDate in_4 = LocalDate.of(2018, 9, 1);
	private LocalDate in_5 = LocalDate.of(2018, 6, 27);
	private LocalDate in_6 = LocalDate.of(2018, 6, 25);

	private LocalDate out_1 = LocalDate.of(2018, 6, 26);
	private LocalDate out_2 = LocalDate.of(2018, 7, 31);
	private LocalDate out_3 = LocalDate.of(2018, 8, 28);
	private LocalDate out_4 = LocalDate.of(2018, 9, 25);

	@Test
	public void includesExpectedDays() {
		assertTrue(difference.includes(in_1));
		assertTrue(difference.includes(in_2));
		assertTrue(difference.includes(in_3));
		assertTrue(difference.includes(in_4));
		assertTrue(difference.includes(in_5));
		assertTrue(difference.includes(in_6));
		return;
	}

	@Test
	public void excludesExpectedDays() {
		assertFalse(difference.includes(out_1));
		assertFalse(difference.includes(out_2));
		assertFalse(difference.includes(out_3));
		assertFalse(difference.includes(out_4));
		return;
	}

	/**
	 * {@code included} argument cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullIncluded() {
		assertThrows(NullPointerException.class, () -> Difference.of(null, days));
		return;
	}

	/**
	 * {@code excluded} argument cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullExcluded() {
		assertThrows(NullPointerException.class, () -> Difference.of(months, null));
		return;
	}

	/**
	 * Days outside the included expression are excluded, whether or not the excluded expression matches them.
	 */
	@Test
	public void excludesDaysOutsideIncludedExpression() {
		assertFalse(difference.includes(LocalDate.of(2018, 10, 1)));
		assertFalse(difference.includes(LocalDate.of(2018, 10, 30)));
		return;
	}

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(difference.equals(difference));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(difference.equals(differenceSame1));
		assertTrue(differenceSame1.equals(difference));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(difference.equals(differenceSame1));
		assertTrue(differenceSame1.equals(differenceSame2));
		assertTrue(difference.equals(differenceSame2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(difference.equals(differenceSame1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(difference.equals(null));
		return;
	}

	/**
	 * Tests that a different included or excluded sub-expression makes a difference unequal.
	 */
	@Test
	public void equalsComparesEachPart() {
		assertNotEquals(difference, Difference.of(RangeEachYear.of(Month.JULY, Month.SEPTEMBER), days));
		assertNotEquals(difference, Difference.of(months, DayInMonth.of(DayOfWeek.TUESDAY, 1)));
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int hashCode = difference.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, difference.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(difference.hashCode(), differenceSame1.hashCode());
		assertEquals(difference.hashCode(), differenceSame2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(difference.hashCode(), differenceDiff1.hashCode());
		return;
	}
}
