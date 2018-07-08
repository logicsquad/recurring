package net.logicsquad.recurring;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * Describes an ordinal weekday of a month, such as "first Monday", "second
 * Tuesday" or "last Sunday".
 * 
 * @author paulh
 */
public class DayInMonth implements TemporalExpression {
	/**
	 * Ordinal position within month
	 */
	private int ordinal;

	/**
	 * Day of week
	 */
	private DayOfWeek day;

	/**
	 * Constructor taking a weekday and an ordinal number. Positive integers
	 * correspond to "first", "second" and so on. Negative integers correspond to
	 * "last", "second-to-last", and so on. {@code ordinal} must be in the range
	 * {@code [-5, 5]}, <em>excluding</em> {@code 0}. That is, the "0th {@code day}"
	 * in the month doesn't make sense, and no month spans over more than five
	 * calendar weeks.
	 * 
	 * @param day
	 *            a {@link DayOfWeek}
	 * @param ordinal
	 *            ordinal position within a month
	 * @throws NullPointerException
	 *             if {@code day} is {@code null}
	 * @throws IllegalArgumentException
	 *             if {@code ordinal} is not in {@code [-5, 5]}, <em>excluding</em>
	 *             {@code 0}
	 */
	private DayInMonth(DayOfWeek day, int ordinal) {
		Objects.requireNonNull(day);
		if (ordinal == 0 || ordinal < -5 || ordinal > 5) {
			throw new IllegalArgumentException("ordinal=" + ordinal + " is not in [-5, 5] excluding 0.");
		}
		this.day = day;
		this.ordinal = ordinal;
		return;
	}

	/**
	 * Returns {@link DayInMonth} with day of week {@code day} and ordinal
	 * {@code ordinal}. That is, the {@code ordinal}th {@code day} in the month.
	 * 
	 * @param day
	 *            day of week
	 * @param ordinal
	 *            ordinal within month
	 * @return {@link DayInMonth}
	 */
	public static DayInMonth of(DayOfWeek day, int ordinal) {
		return new DayInMonth(day, ordinal);
	}

	@Override
	public boolean includes(LocalDate date) {
		return dayMatches(date) && weekMatches(date);
	}

	/**
	 * Does {@link #day} match {@code date}'s day of week?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@code date}'s day of week matches {@link #day},
	 *         otherwise {@code false}
	 */
	private boolean dayMatches(LocalDate date) {
		boolean result = date.getDayOfWeek() == day;
		return result;
	}

	/**
	 * Is {@code date} in the {@link #ordinal}th week of its month?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@code date} is in the {@link #ordinal}th week of its
	 *         month, otherwise {@code false}
	 */
	private boolean weekMatches(LocalDate date) {
		if (ordinal > 0) {
			return weekFromStartMatches(date);
		} else {
			return weekFromEndMatches(date);
		}
	}

	/**
	 * Does {@code date}'s week from the start of the month containing {@code date}
	 * match {@link #ordinal}? That is, does {@code date} fall in the
	 * {@code ordinal}th week from the start of the month?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@code date} is in the {@link #ordinal}th week from
	 *         the start of its month, otherwise {@code false}
	 */
	private boolean weekFromStartMatches(LocalDate date) {
		return weekInMonth(date.getDayOfMonth()) == ordinal;
	}

	/**
	 * Does {@code date}'s week from the end of the month containing {@code date}
	 * match {@link #ordinal}? That is, does {@code date} fall in the
	 * {@code ordinal}th week from the end of the month?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@code date} is in the {@link #ordinal}th week from
	 *         the end of its month, otherwise {@code false}
	 */
	private boolean weekFromEndMatches(LocalDate date) {
		return weekInMonth(daysLeftInMonth(date) + 1) == Math.abs(ordinal);
	}

	/**
	 * Returns the week in the month in which {@code day} occurs. Note that this is
	 * <em>not</em> related to the (Sunday through Saturday) "calendar" week, but to
	 * 7-day blocks from the first day of the month. That is, a {@code day} of, say,
	 * 7 will always return 1, and a {@code day} of 8 will always return 2.
	 * 
	 * @param day
	 *            day of month
	 * @return corresponding week of month
	 */
	private int weekInMonth(int day) {
		return ((day - 1) / 7) + 1;
	}

	/**
	 * How many days are left in the month from {@code date}?
	 * 
	 * @param date
	 *            a {@link LocalDate}
	 * @return remaining day count
	 */
	private int daysLeftInMonth(LocalDate date) {
		return (int) date.until(date.with(TemporalAdjusters.lastDayOfMonth()), ChronoUnit.DAYS);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DayInMonth)) {
			return false;
		}
		DayInMonth other = (DayInMonth) o;
		return other.ordinal == ordinal && other.day == day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ordinal;
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(this.getClass().getSimpleName());
		sb.append(":");
		sb.append(" day=");
		sb.append(day);
		sb.append(" ordinal=");
		sb.append(ordinal);
		sb.append("]");
		return sb.toString();
	}
}
