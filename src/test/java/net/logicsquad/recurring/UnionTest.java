package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link Union}.
 *
 * @author paulh
 */
public class UnionTest {
	RangeEachYear range = RangeEachYear.of(Month.JUNE, Month.SEPTEMBER);
	DayInMonth day = DayInMonth.of(DayOfWeek.SUNDAY, 1);

	Union union = Union.of(range, day);

	// Objects equal to union, and one different to it
	Union unionSame1 = Union.of(RangeEachYear.of(Month.JUNE, Month.SEPTEMBER), DayInMonth.of(DayOfWeek.SUNDAY, 1));
	Union unionSame2 = Union.of(range, day);
	Union unionDiff1 = Union.of(range);

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

	@Test
	public void ofThrowsOnNullList() {
		assertThrows(NullPointerException.class, () -> Union.of((List<TemporalExpression>) null));
		return;
	}

	@Test
	public void ofThrowsOnNullExpression() {
		assertThrows(NullPointerException.class, () -> Union.of((TemporalExpression) null));
		return;
	}

	@Test
	public void ofThrowsOnNullExpressionArray() {
		assertThrows(NullPointerException.class, () -> Union.of((TemporalExpression[]) null));
		return;
	}

	@Test
	public void ofThrowsOnEmptyList() {
		assertThrows(IllegalArgumentException.class, () -> Union.of(new ArrayList<>()));
		return;
	}

	@Test
	public void expressionsCannotBeModified() {
		List<TemporalExpression> expressions = new ArrayList<>();
		expressions.add(range);
		expressions.add(day);
		Union modifyMe = Union.of(expressions);
		// Show that a date in the range matches
		assertTrue(modifyMe.includes(LocalDate.of(2018, 6, 1)));
		expressions.remove(range);
		// Show that a date in the range still matches
		assertTrue(modifyMe.includes(LocalDate.of(2018, 6, 1)));
		return;
	}

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(union.equals(union));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(union.equals(unionSame1));
		assertTrue(unionSame1.equals(union));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(union.equals(unionSame1));
		assertTrue(unionSame1.equals(unionSame2));
		assertTrue(union.equals(unionSame2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(union.equals(unionSame1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(union.equals(null));
		return;
	}

	/**
	 * Tests that a different sub-expression, or the same ones in a different order, make a union unequal, and that it doesn't
	 * equal an intersection of the same sub-expressions.
	 */
	@Test
	public void equalsComparesEachPart() {
		assertNotEquals(union, Union.of(range, DayInMonth.of(DayOfWeek.SUNDAY, 2)));
		assertNotEquals(union, Union.of(day, range));
		assertNotEquals(union, Intersection.of(range, day));
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int hashCode = union.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, union.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(union.hashCode(), unionSame1.hashCode());
		assertEquals(union.hashCode(), unionSame2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(union.hashCode(), unionDiff1.hashCode());
		return;
	}
}
