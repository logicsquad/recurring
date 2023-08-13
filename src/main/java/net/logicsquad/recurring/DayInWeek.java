package net.logicsquad.recurring;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * <p>
 * Describes a day in a week. There are two options:
 * </p>
 *
 * <ol>
 * <li>{@link #of(DayOfWeek)} creates an expression matching <em>every</em> {@code dayOfWeek}; and</li>
 * <li>{@link #of(int, LocalDate)} creates an expression matching the day of {@code referenceDate} in every {@code ordinal} weeks.</li>
 * </ol>
 *
 * <p>
 * For example, to match "every Monday", use {@code of(DayOfWeek.MONDAY)}. To match "every second Monday from 2023-08-14", use
 * {@code of(2, LocalDate.of(2023, 8, 14)}.
 * </p>
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
	 * Ordinal describing every "nth" week
	 */
	private final int ordinal;

	/**
	 * Reference date if we're not using <em>every</em> {@code dayOfWeek}
	 */
	private final LocalDate referenceDate;

	/**
	 * Constructor taking either a {@code dayOfWeek} or an {@code ordinal} and a {@code referenceDate}. If {@code dayOfWeek} is set, this object
	 * matches that day in <em>every</em> week. Otherwise, it matches the day of {@code referenceDate} every {@code ordinal} weeks, starting
	 * from {@code referenceDate}.
	 * 
	 * @param dayOfWeek     day of week to match
	 * @param ordinal       weeks from {@code referenceDate} to match
	 * @param referenceDate a reference date
	 */
	private DayInWeek(DayOfWeek dayOfWeek, int ordinal, LocalDate referenceDate) {
		this.dayOfWeek = dayOfWeek;
		this.ordinal = ordinal;
		this.referenceDate = referenceDate;
		return;
	}

	/**
	 * Returns a {@code DayInWeek} matching every {@code dayOfWeek}.
	 * 
	 * @param dayOfWeek day of week
	 * @return new object
	 */
	public static DayInWeek of(DayOfWeek dayOfWeek) {
		Objects.requireNonNull(dayOfWeek);
		return new DayInWeek(dayOfWeek, 0, null);
	}

	/**
	 * Returns a {@code DayInWeek} matching the day of {@code referenceDate} every {@code ordinal} weeks (in either direction) from
	 * {@code referenceDate} (and including {@code referenceDate}).
	 * 
	 * @param ordinal       weeks from {@code referenceDate} to match
	 * @param referenceDate a reference date
	 * @return new object
	 */
	public static DayInWeek of(int ordinal, LocalDate referenceDate) {
		Objects.requireNonNull(referenceDate);
		if (ordinal < 1) {
			throw new IllegalArgumentException("");
		}
		return new DayInWeek(null, ordinal, referenceDate);
	}

	@Override
	public boolean includes(LocalDate date) {
		if (dayOfWeek != null) {
			return Objects.equals(dayOfWeek, date.getDayOfWeek());
		} else {
			return Objects.equals(referenceDate.getDayOfWeek(), date.getDayOfWeek()) && (ChronoUnit.WEEKS.between(referenceDate, date) % ordinal == 0);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(dayOfWeek, ordinal, referenceDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DayInWeek)) {
			return false;
		}
		DayInWeek other = (DayInWeek) obj;
		return dayOfWeek == other.dayOfWeek && ordinal == other.ordinal && Objects.equals(referenceDate, other.referenceDate);
	}
}
