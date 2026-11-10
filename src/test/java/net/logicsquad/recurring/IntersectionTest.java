package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link Intersection}.
 *
 * @author paulh
 */
public class IntersectionTest {
	private TemporalExpression dayInMonth = DayInMonth.of(DayOfWeek.MONDAY, 2);
	private TemporalExpression range = RangeEachYear.of(Month.JANUARY, Month.JUNE);

	private Intersection intersection = Intersection.of(dayInMonth, range);

	// Objects equal to intersection, and one different to it
	private Intersection intersectionSame1 = Intersection.of(DayInMonth.of(DayOfWeek.MONDAY, 2), RangeEachYear.of(Month.JANUARY, Month.JUNE));
	private Intersection intersectionSame2 = Intersection.of(dayInMonth, range);
	private Intersection intersectionDiff1 = Intersection.of(dayInMonth);

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

	@Test
	public void ofThrowsOnNullList() {
		assertThrows(NullPointerException.class, () -> Intersection.of((List<TemporalExpression>) null));
		return;
	}

	@Test
	public void ofThrowsOnNullExpression() {
		assertThrows(NullPointerException.class, () -> Intersection.of((TemporalExpression) null));
		return;
	}

	@Test
	public void ofThrowsOnNullExpressionArray() {
		assertThrows(NullPointerException.class, () -> Intersection.of((TemporalExpression[]) null));
		return;
	}

	@Test
	public void ofThrowsOnNullExpressionInList() {
		assertThrows(NullPointerException.class, () -> Intersection.of(Arrays.<TemporalExpression>asList(dayInMonth, null)));
		return;
	}

	@Test
	public void ofThrowsOnEmptyExpressions() {
		assertThrows(IllegalArgumentException.class, () -> Intersection.of(new ArrayList<>()));
		return;
	}

	@Test
	public void expressionsCannotBeModified() {
		List<TemporalExpression> expressions = new ArrayList<>();
		expressions.add(dayInMonth);
		expressions.add(range);
		Intersection modifyMe = Intersection.of(expressions);
		// Show that a date in the range matches
		assertTrue(modifyMe.includes(in_1));
		expressions.remove(dayInMonth);
		expressions.remove(range);
		// Show that a date in the range still matches
		assertTrue(modifyMe.includes(in_1));
		return;
	}

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(intersection.equals(intersection));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(intersection.equals(intersectionSame1));
		assertTrue(intersectionSame1.equals(intersection));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(intersection.equals(intersectionSame1));
		assertTrue(intersectionSame1.equals(intersectionSame2));
		assertTrue(intersection.equals(intersectionSame2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(intersection.equals(intersectionSame1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(intersection.equals(null));
		return;
	}

	/**
	 * Tests that a different sub-expression, or the same ones in a different order, make an intersection unequal, and that
	 * it doesn't equal a union of the same sub-expressions.
	 */
	@Test
	public void equalsComparesEachPart() {
		assertNotEquals(intersection, Intersection.of(dayInMonth, RangeEachYear.of(Month.JANUARY, Month.JULY)));
		assertNotEquals(intersection, Intersection.of(range, dayInMonth));
		assertNotEquals(intersection, Union.of(dayInMonth, range));
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int hashCode = intersection.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, intersection.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(intersection.hashCode(), intersectionSame1.hashCode());
		assertEquals(intersection.hashCode(), intersectionSame2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(intersection.hashCode(), intersectionDiff1.hashCode());
		return;
	}
}
