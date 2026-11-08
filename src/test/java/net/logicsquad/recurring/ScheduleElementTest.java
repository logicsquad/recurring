package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link ScheduleElement}.
 *
 * @author paulh
 */
public class ScheduleElementTest {
	private TemporalExpression dayInMonth = DayInMonth.of(DayOfWeek.MONDAY, 2);
	private TemporalExpression range = RangeEachYear.of(Month.JANUARY, Month.JUNE);

	private Intersection intersection = Intersection.of(dayInMonth, range);

	private static final String EVENT_1 = "Some event";
	private static final Integer EVENT_2 = Integer.valueOf(12);

	private ScheduleElement<String> stringElement = ScheduleElement.of(EVENT_1, intersection);
	private ScheduleElement<Integer> integerElement = ScheduleElement.of(EVENT_2, intersection);

	// Objects equal to stringElement, and one different to it
	private ScheduleElement<String> stringElementSame1 = ScheduleElement.of("Some event",
			Intersection.of(DayInMonth.of(DayOfWeek.MONDAY, 2), RangeEachYear.of(Month.JANUARY, Month.JUNE)));
	private ScheduleElement<String> stringElementSame2 = ScheduleElement.of(EVENT_1, intersection);
	private ScheduleElement<String> stringElementDiff1 = ScheduleElement.of("Some other event", intersection);

	private LocalDate in_1 = LocalDate.of(2018, 1, 8);
	private LocalDate in_2 = LocalDate.of(2018, 2, 12);
	private LocalDate in_3 = LocalDate.of(2018, 3, 12);

	private LocalDate out_1 = LocalDate.of(2018, 7, 9);
	private LocalDate out_2 = LocalDate.of(2018, 8, 13);
	private LocalDate out_3 = LocalDate.of(2018, 9, 10);

	@Test
	public void includesExpectedDates() {
		assertTrue(stringElement.isOccurring(in_1));
		assertTrue(stringElement.isOccurring(in_2));
		assertTrue(stringElement.isOccurring(in_3));

		assertTrue(integerElement.isOccurring(in_1));
		assertTrue(integerElement.isOccurring(in_2));
		assertTrue(integerElement.isOccurring(in_3));
		return;
	}

	@Test
	public void excludesExpectedDates() {
		assertFalse(stringElement.isOccurring(out_1));
		assertFalse(stringElement.isOccurring(out_2));
		assertFalse(stringElement.isOccurring(out_3));

		assertFalse(integerElement.isOccurring(out_1));
		assertFalse(integerElement.isOccurring(out_2));
		assertFalse(integerElement.isOccurring(out_3));
		return;
	}

	/**
	 * {@code event} argument cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullEvent() {
		assertThrows(NullPointerException.class, () -> ScheduleElement.of(null, intersection));
		return;
	}

	/**
	 * {@code expression} argument cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullExpression() {
		assertThrows(NullPointerException.class, () -> ScheduleElement.of(EVENT_1, null));
		return;
	}

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(stringElement.equals(stringElement));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(stringElement.equals(stringElementSame1));
		assertTrue(stringElementSame1.equals(stringElement));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(stringElement.equals(stringElementSame1));
		assertTrue(stringElementSame1.equals(stringElementSame2));
		assertTrue(stringElement.equals(stringElementSame2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(stringElement.equals(stringElementSame1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(stringElement.equals(null));
		return;
	}

	/**
	 * Tests that a different event or expression makes an element unequal.
	 */
	@Test
	public void equalsComparesEachPart() {
		assertNotEquals(stringElement, stringElementDiff1);
		assertNotEquals(stringElement, ScheduleElement.of(EVENT_1, range));
		assertNotEquals(stringElement, integerElement);
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int hashCode = stringElement.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, stringElement.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(stringElement.hashCode(), stringElementSame1.hashCode());
		assertEquals(stringElement.hashCode(), stringElementSame2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(stringElement.hashCode(), stringElementDiff1.hashCode());
		return;
	}
}
