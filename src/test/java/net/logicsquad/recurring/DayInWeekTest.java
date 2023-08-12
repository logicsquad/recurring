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
	public void ofThrowsOnNull() {
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
}
