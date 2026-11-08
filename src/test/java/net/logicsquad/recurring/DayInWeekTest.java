package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link DayInWeek}.
 * 
 * @author paulh
 * @see <a href="https://github.com/logicsquad/recurring/issues/4">Issue #4</a>
 * @since 0.3
 */
public class DayInWeekTest {
	private static final LocalDate SAT = LocalDate.of(2023, 8, 12);

	private static final LocalDate SUN = LocalDate.of(2023, 8, 13);

	private static final LocalDate MON = LocalDate.of(2023, 8, 14);

	private static final LocalDate THU = LocalDate.of(2023, 8, 17);

	// Objects created using each static factory method
	private DayInWeek everyMonday = DayInWeek.of(DayOfWeek.MONDAY);
	private DayInWeek fortnightly = DayInWeek.of(2, MON);

	// Objects equal to corresponding object above
	private DayInWeek everyMondaySame1 = DayInWeek.of(DayOfWeek.MONDAY);
	private DayInWeek fortnightlySame1 = DayInWeek.of(2, MON);

	// Objects equal to corresponding object above
	private DayInWeek everyMondaySame2 = DayInWeek.of(DayOfWeek.MONDAY);
	private DayInWeek fortnightlySame2 = DayInWeek.of(2, MON);

	// Objects different to corresponding object above
	private DayInWeek everyMondayDiff1 = DayInWeek.of(DayOfWeek.TUESDAY);
	private DayInWeek fortnightlyDiff1 = DayInWeek.of(3, MON);

	@Test
	public void ofThrowsOnNullDayOfWeek() {
		assertThrows(NullPointerException.class, () -> DayInWeek.of(null));
		return;
	}

	@Test
	public void includesReturnsTrueOnMatch() {
		DayInWeek sat = DayInWeek.of(DayOfWeek.SATURDAY);
		assertTrue(sat.includes(SAT));
		DayInWeek sun = DayInWeek.of(DayOfWeek.SUNDAY);
		assertTrue(sun.includes(SUN));
		return;
	}

	@Test
	public void includesReturnsFalseOnNonMatch() {
		DayInWeek sat = DayInWeek.of(DayOfWeek.SATURDAY);
		assertFalse(sat.includes(SUN));
		DayInWeek sun = DayInWeek.of(DayOfWeek.SUNDAY);
		assertFalse(sun.includes(SAT));
		return;
	}

	@Test
	public void canCombineDaysInWeek() {
		TemporalExpression everyMonAndThu = Union.of(DayInWeek.of(DayOfWeek.MONDAY), DayInWeek.of(DayOfWeek.THURSDAY));
		assertTrue(everyMonAndThu.includes(MON));
		assertTrue(everyMonAndThu.includes(THU));
		assertTrue(everyMonAndThu.includes(MON.minusWeeks(1)));
		assertTrue(everyMonAndThu.includes(THU.minusWeeks(1)));
		assertTrue(everyMonAndThu.includes(MON.plusWeeks(1)));
		assertTrue(everyMonAndThu.includes(THU.plusWeeks(0)));
		assertFalse(everyMonAndThu.includes(SAT));
		assertFalse(everyMonAndThu.includes(SUN));
		return;
	}

	@Test
	public void ofThrowsOnNonPositiveOrdinal() {
		assertThrows(IllegalArgumentException.class, () -> DayInWeek.of(0, SAT));
		assertThrows(IllegalArgumentException.class, () -> DayInWeek.of(-1, SAT));
		assertThrows(IllegalArgumentException.class, () -> DayInWeek.of(-10, SAT));
		return;
	}

	@Test
	public void ofThrowsOnNullReferenceDate() {
		assertThrows(NullPointerException.class, () -> DayInWeek.of(1, null));
		return;
	}

	@Test
	public void dayInWeekWithOrdinalIncludesReturnsTrueOnMatch() {
		// Every second Monday from MON
		DayInWeek everySecondMonday = DayInWeek.of(2, MON);
		assertTrue(everySecondMonday.includes(MON));
		assertTrue(everySecondMonday.includes(MON.plusWeeks(2)));
		assertTrue(everySecondMonday.includes(MON.plusWeeks(4)));
		assertTrue(everySecondMonday.includes(MON.plusWeeks(12)));
		assertTrue(everySecondMonday.includes(MON.minusWeeks(2)));
		assertTrue(everySecondMonday.includes(MON.minusWeeks(4)));
		assertTrue(everySecondMonday.includes(MON.minusWeeks(12)));
		return;
	}

	@Test
	public void dayInWeekWithOrdinalIncludesReturnsFalseOnNonMatch() {
		// Every second Monday from MON
		DayInWeek everySecondMonday = DayInWeek.of(2, MON);
		assertFalse(everySecondMonday.includes(SAT));
		assertFalse(everySecondMonday.includes(SUN));
		assertFalse(everySecondMonday.includes(THU));
		assertFalse(everySecondMonday.includes(MON.plusWeeks(1)));
		assertFalse(everySecondMonday.includes(MON.plusWeeks(3)));
		assertFalse(everySecondMonday.includes(MON.plusWeeks(11)));
		assertFalse(everySecondMonday.includes(MON.minusWeeks(1)));
		assertFalse(everySecondMonday.includes(MON.minusWeeks(3)));
		assertFalse(everySecondMonday.includes(MON.minusWeeks(11)));
		return;
	}

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(everyMonday.equals(everyMonday));
		assertTrue(fortnightly.equals(fortnightly));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(everyMonday.equals(everyMondaySame1));
		assertTrue(everyMondaySame1.equals(everyMonday));
		assertTrue(fortnightly.equals(fortnightlySame1));
		assertTrue(fortnightlySame1.equals(fortnightly));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(everyMonday.equals(everyMondaySame1));
		assertTrue(everyMondaySame1.equals(everyMondaySame2));
		assertTrue(everyMonday.equals(everyMondaySame2));
		assertTrue(fortnightly.equals(fortnightlySame1));
		assertTrue(fortnightlySame1.equals(fortnightlySame2));
		assertTrue(fortnightly.equals(fortnightlySame2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(everyMonday.equals(everyMondaySame1));
			assertTrue(fortnightly.equals(fortnightlySame1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(everyMonday.equals(null));
		assertFalse(fortnightly.equals(null));
		return;
	}

	/**
	 * Tests that objects differing in only one field are unequal.
	 */
	@Test
	public void equalsComparesEachField() {
		assertNotEquals(everyMonday, everyMondayDiff1);
		assertNotEquals(fortnightly, fortnightlyDiff1);
		assertNotEquals(fortnightly, DayInWeek.of(2, MON.plusWeeks(1)));
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int everyMondayHashCode = everyMonday.hashCode();
		int fortnightlyHashCode = fortnightly.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(everyMondayHashCode, everyMonday.hashCode());
			assertEquals(fortnightlyHashCode, fortnightly.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(everyMonday.hashCode(), everyMondaySame1.hashCode());
		assertEquals(everyMonday.hashCode(), everyMondaySame2.hashCode());
		assertEquals(fortnightly.hashCode(), fortnightlySame1.hashCode());
		assertEquals(fortnightly.hashCode(), fortnightlySame2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(everyMonday.hashCode(), everyMondayDiff1.hashCode());
		assertNotEquals(fortnightly.hashCode(), fortnightlyDiff1.hashCode());
		return;
	}

	/**
	 * Equality compares how expressions are built, not the dates they match.
	 */
	@Test
	public void equalityComparesStructureNotDates() {
		assertNotEquals(everyMonday, DayInWeek.of(1, MON));
		assertNotEquals(fortnightly, DayInWeek.of(2, MON.plusWeeks(2)));
		return;
	}
}
