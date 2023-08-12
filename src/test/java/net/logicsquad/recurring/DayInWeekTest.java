package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
}
