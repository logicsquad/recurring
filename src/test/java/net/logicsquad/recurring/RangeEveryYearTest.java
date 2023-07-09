package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link RangeEveryYear}.
 * 
 * @author paulh
 */
public class RangeEveryYearTest {
	// Objects created using different static factory methods
	private RangeEveryYear fullRange = RangeEveryYear.of(MonthDay.of(Month.MARCH, 12), MonthDay.of(Month.JULY, 20));
	private RangeEveryYear monthsRange = RangeEveryYear.of(Month.APRIL, Month.AUGUST);
	private RangeEveryYear monthRange = RangeEveryYear.of(Month.JUNE);

	// Objects equal to corresponding object above
	private RangeEveryYear fullRangeSame1 = RangeEveryYear.of(MonthDay.of(Month.MARCH, 12), MonthDay.of(Month.JULY, 20));
	private RangeEveryYear monthsRangeSame1 = RangeEveryYear.of(Month.APRIL, Month.AUGUST);
	private RangeEveryYear monthRangeSame1 = RangeEveryYear.of(Month.JUNE);

	// Objects equal to corresponding object above
	private RangeEveryYear fullRangeSame2 = RangeEveryYear.of(MonthDay.of(Month.MARCH, 12), MonthDay.of(Month.JULY, 20));
	private RangeEveryYear monthsRangeSame2 = RangeEveryYear.of(Month.APRIL, Month.AUGUST);
	private RangeEveryYear monthRangeSame2 = RangeEveryYear.of(Month.JUNE);

	// Objects different to corresponding object above
	private RangeEveryYear fullRangeDiff1 = RangeEveryYear.of(MonthDay.of(Month.MARCH, 2), MonthDay.of(Month.DECEMBER, 25));
	private RangeEveryYear monthsRangeDiff1 = RangeEveryYear.of(Month.JANUARY, Month.MAY);
	private RangeEveryYear monthRangeDiff1 = RangeEveryYear.of(Month.SEPTEMBER);

	// Dates known to be in fullRange
	private LocalDate fullRange_in1 = LocalDate.of(2018, 3, 12);
	private LocalDate fullRange_in2 = LocalDate.of(2018, 7, 20);
	private LocalDate fullRange_in3 = LocalDate.of(2018, 6, 30);
	// Dates known to be not in fullRange
	private LocalDate fullRange_out1 = LocalDate.of(2019, 8, 1);
	private LocalDate fullRange_out2 = LocalDate.of(2020, 1, 31);
	private LocalDate fullRange_out3 = LocalDate.of(2020, 3, 11);

	// Dates known to be in monthsRange
	private LocalDate monthsRange_in1 = LocalDate.of(2018, 4, 1);
	private LocalDate monthsRange_in2 = LocalDate.of(2018, 8, 1);
	private LocalDate monthsRange_in3 = LocalDate.of(2018, 5, 11);
	// Dates known to be not in monthsRange
	private LocalDate monthsRange_out1 = LocalDate.of(2019, 3, 3);
	private LocalDate monthsRange_out2 = LocalDate.of(2020, 9, 9);

	// Dates known to be in monthRange
	private LocalDate monthRange_in1 = LocalDate.of(2018, 6, 1);
	private LocalDate monthRange_in2 = LocalDate.of(2018, 6, 30);
	private LocalDate monthRange_in3 = LocalDate.of(2020, 6, 12);
	// Dates known to be not in monthRange
	private LocalDate monthRange_out1 = LocalDate.of(2019, 12, 31);
	private LocalDate monthRange_out2 = LocalDate.of(2020, 1, 1);

	/**
	 * Tests {@code include()} for dates known to be in range.
	 */
	@Test
	public void includesReturnsTrueForDatesInFullRange() {
		assertTrue(fullRange.includes(fullRange_in1));
		assertTrue(fullRange.includes(fullRange_in2));
		assertTrue(fullRange.includes(fullRange_in3));
		return;
	}

	/**
	 * Tests {@code include()} for dates known to be not in range.
	 */
	@Test
	public void includesReturnsFalseForDatesNotInFullRange() {
		assertFalse(fullRange.includes(fullRange_out1));
		assertFalse(fullRange.includes(fullRange_out2));
		assertFalse(fullRange.includes(fullRange_out3));
		return;
	}

	/**
	 * Tests {@code include()} for dates known to be in range.
	 */
	@Test
	public void includesReturnsTrueForDatesInMonthsRange() {
		assertTrue(monthsRange.includes(monthsRange_in1));
		assertTrue(monthsRange.includes(monthsRange_in2));
		assertTrue(monthsRange.includes(monthsRange_in3));
		return;
	}

	/**
	 * Tests {@code include()} for dates known to be not in range.
	 */
	@Test
	public void includesReturnsFalseForDatesNotInMonthsRange() {
		assertFalse(monthsRange.includes(monthsRange_out1));
		assertFalse(monthsRange.includes(monthsRange_out2));
		return;
	}

	/**
	 * Tests {@code include()} for dates known to be in range.
	 */
	@Test
	public void includesReturnsTrueForDatesInMonthRange() {
		assertTrue(monthRange.includes(monthRange_in1));
		assertTrue(monthRange.includes(monthRange_in2));
		assertTrue(monthRange.includes(monthRange_in3));
		return;
	}

	/**
	 * Tests {@code include()} for dates known to be not in range.
	 */
	@Test
	public void includesReturnsFalseForDatesNotInMonthRange() {
		assertFalse(monthRange.includes(monthRange_out1));
		assertFalse(monthRange.includes(monthRange_out2));
		return;
	}

	/**
	 * Tests reflexivity of {@code equals()}.
	 */
	@Test
	public void equalsIsReflexive() {
		assertTrue(fullRange.equals(fullRange));
		assertTrue(monthsRange.equals(monthsRange));
		assertTrue(monthRange.equals(monthRange));
		return;
	}

	/**
	 * Tests symmetry of {@code equals()}.
	 */
	@Test
	public void equalsIsSymmetric() {
		assertTrue(fullRange.equals(fullRangeSame1));
		assertTrue(fullRangeSame1.equals(fullRange));
		assertTrue(monthsRange.equals(monthsRangeSame1));
		assertTrue(monthsRangeSame1.equals(monthsRange));
		assertTrue(monthRange.equals(monthRangeSame1));
		assertTrue(monthRangeSame1.equals(monthRange));
		return;
	}

	/**
	 * Tests transitivity of {@code equals()}.
	 */
	@Test
	public void equalsIsTransitive() {
		assertTrue(fullRange.equals(fullRangeSame1));
		assertTrue(fullRangeSame1.equals(fullRangeSame2));
		assertTrue(fullRange.equals(fullRangeSame2));
		assertTrue(monthsRange.equals(monthsRangeSame1));
		assertTrue(monthsRangeSame1.equals(monthsRangeSame2));
		assertTrue(monthsRange.equals(monthsRangeSame2));
		assertTrue(monthRange.equals(monthRangeSame1));
		assertTrue(monthRangeSame1.equals(monthRangeSame2));
		assertTrue(monthRange.equals(monthRangeSame2));
		return;
	}

	/**
	 * Tests consistency of {@code equals()}.
	 */
	@Test
	public void equalsIsConsistent() {
		for (int i = 0; i < 1000; i++) {
			assertTrue(fullRange.equals(fullRangeSame1));
			assertTrue(monthsRange.equals(monthsRangeSame1));
			assertTrue(monthRange.equals(monthRangeSame1));
		}
		return;
	}

	/**
	 * Tests null-handling of {@code equals()}.
	 */
	@Test
	public void equalsHandlesNull() {
		assertFalse(fullRange.equals(null));
		assertFalse(monthsRange.equals(null));
		assertFalse(monthRange.equals(null));
		return;
	}

	/**
	 * Tests consistency of {@code hashCode()}.
	 */
	@Test
	public void hashCodeIsConsistent() {
		int hashCode = fullRange.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, fullRange.hashCode());
		}
		hashCode = monthsRange.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, monthsRange.hashCode());
		}
		hashCode = monthRange.hashCode();
		for (int i = 0; i < 1000; i++) {
			assertEquals(hashCode, monthRange.hashCode());
		}
		return;
	}

	/**
	 * Tests equality of {@code hashCode()}.
	 */
	@Test
	public void equalObjectsHaveEqualHashCodes() {
		assertEquals(fullRange.hashCode(), fullRangeSame1.hashCode());
		assertEquals(fullRange.hashCode(), fullRangeSame2.hashCode());
		assertEquals(monthsRange.hashCode(), monthsRangeSame1.hashCode());
		assertEquals(monthsRange.hashCode(), monthsRangeSame2.hashCode());
		assertEquals(monthRange.hashCode(), monthRangeSame1.hashCode());
		assertEquals(monthRange.hashCode(), monthRangeSame2.hashCode());
		return;
	}

	// This is not required by the contract, but is desirable
	/**
	 * Tests non-equality of {@code hashCode()}.
	 */
	@Test
	public void unequalObjectsHaveUnequalHashCodes() {
		assertNotEquals(fullRange.hashCode(), fullRangeDiff1.hashCode());
		assertNotEquals(monthsRange.hashCode(), monthsRangeDiff1.hashCode());
		assertNotEquals(monthRange.hashCode(), monthRangeDiff1.hashCode());
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullMonth() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of(null));
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullFirstMonth() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of(null, Month.APRIL));
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullSecondMonth() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of(Month.DECEMBER, null));
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullBothMonths() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of((Month) null, (Month) null));
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullFirstMonthDay() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of(null, MonthDay.of(Month.JULY, 1)));
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullSecondMonthDay() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of(MonthDay.of(Month.JUNE, 12), null));
		return;
	}

	/**
	 * Arguments to {@code of()} cannot be {@code null}.
	 */
	@Test
	public void ofThrowsOnNullBothMonthDays() {
		assertThrows(NullPointerException.class, () -> RangeEveryYear.of((MonthDay) null, (MonthDay) null));
		return;
	}
}
