package net.logicsquad.recurring;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Describes a day in a week.
 *
 * @author paulh
 * @see <a href="https://github.com/logicsquad/recurring/issues/4">Issue #4</a>
 * @since 0.3
 */
public class DayInWeek implements TemporalExpression {
	/**
	 * Day of week
	 */
	private final DayOfWeek dayOfWeek;

	/**
	 * Constructor taking day of week
	 * 
	 * @param dayOfWeek day of week
	 */
	private DayInWeek(DayOfWeek dayOfWeek) {
		Objects.requireNonNull(dayOfWeek);
		this.dayOfWeek = dayOfWeek;
		return;
	}

	/**
	 * Returns a {@code DayInWeek} matching every {@code dayOfWeek}.
	 * 
	 * @param dayOfWeek day of week
	 * @return new object
	 */
	public static DayInWeek of(DayOfWeek dayOfWeek) {
		return new DayInWeek(dayOfWeek);
	}

	@Override
	public boolean includes(LocalDate date) {
		return Objects.equals(dayOfWeek, date.getDayOfWeek());
	}
}
