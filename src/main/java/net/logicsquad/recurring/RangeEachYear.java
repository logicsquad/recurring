package net.logicsquad.recurring;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.Objects;

/**
 * Describes a contiguous range of days in any year.
 *
 * @author paulh
 */
public final class RangeEachYear implements TemporalExpression {
	/**
	 * First month of range
	 */
	private final Month startMonth;

	/**
	 * Last month of range
	 */
	private final Month endMonth;

	/**
	 * First day of range (in {@link #startMonth})
	 */
	private final int startDay;

	/**
	 * Last day of range (in {@link #endMonth})
	 */
	private final int endDay;

	/**
	 * Constructor
	 *
	 * @param startMonth
	 *            start month
	 * @param endMonth
	 *            end month
	 * @param startDay
	 *            first day (in start month) of range, or zero to include whole of
	 *            start month
	 * @param endDay
	 *            last day (in end month) of range, or zero to include whole of end
	 *            month
	 */
	private RangeEachYear(Month startMonth, Month endMonth, int startDay, int endDay) {
		Objects.requireNonNull(startMonth);
		Objects.requireNonNull(endMonth);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = startDay;
		this.endDay = endDay;
		return;
	}

	/**
	 * Returns {@code RangeEachYear} spanning from {@code start} day through
	 * {@code end} day (inclusive).
	 *
	 * @param start
	 *            start day
	 * @param end
	 *            end day
	 * @return {@code RangeEachYear}
	 * @throws NullPointerException
	 *             if either argument is {@code null}
	 */
	public static RangeEachYear of(MonthDay start, MonthDay end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		return new RangeEachYear(start.getMonth(), end.getMonth(), start.getDayOfMonth(), end.getDayOfMonth());
	}

	/**
	 * Returns {@code RangeEachYear} spanning from beginning of {@code startMonth}
	 * through end of {@code endMonth} (inclusive).
	 *
	 * @param startMonth
	 *            start month
	 * @param endMonth
	 *            end month
	 * @return {@code RangeEachYear}
	 * @throws NullPointerException
	 *             if either argument is {@code null}
	 */
	public static RangeEachYear of(Month startMonth, Month endMonth) {
		Objects.requireNonNull(startMonth);
		Objects.requireNonNull(endMonth);
		return new RangeEachYear(startMonth, endMonth, 0, 0);
	}

	/**
	 * Returns {@code RangeEachYear} spanning from beginning of {@code month}
	 * through end of {@code month} (inclusive).
	 *
	 * @param month
	 *            a month
	 * @return {@code RangeEachYear}
	 * @throws NullPointerException
	 *             if argument is {@code null}
	 */
	public static RangeEachYear of(Month month) {
		Objects.requireNonNull(month);
		return new RangeEachYear(month, month, 0, 0);
	}

	@Override
	public boolean includes(LocalDate date) {
		if (Objects.equals(startMonth, endMonth)) {
			return startMonthIncludes(date) && endMonthIncludes(date);
		} else {
			return monthsInclude(date) || startMonthIncludes(date) || endMonthIncludes(date);
		}
	}

	/**
	 * Do any of the (possibly zero) months <em>between</em> {@link #startMonth} and
	 * {@link #endMonth} (exclusive) include {@code date}?
	 *
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@code date} falls in a month <em>between</em>
	 *         {@link #startMonth} and {@link #endMonth} (exclusive), otherwise
	 *         {@code false}
	 */
	private boolean monthsInclude(LocalDate date) {
		int month = date.getMonthValue();
		return month > startMonth.getValue() && month < endMonth.getValue();
	}

	/**
	 * Does {@link #startMonth} (excluding days before {@link #startDay}, <em>if
	 * set</em>) include {@code date}?
	 *
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@link #startMonth} includes {@code date}, otherwise
	 *         {@code false}
	 */
	private boolean startMonthIncludes(LocalDate date) {
		if (date.getMonthValue() != startMonth.getValue()) {
			return false;
		} else if (startDay == 0) {
			return true;
		} else {
			return date.getDayOfMonth() >= startDay;
		}
	}

	/**
	 * Does {@link #endMonth} (excluding days after {@link #endDay}, <em>if
	 * set</em>) include {@code date}?
	 *
	 * @param date
	 *            a {@link LocalDate}
	 * @return {@code true} if {@link #endMonth} includes {@code date}, otherwise
	 *         {@code false}
	 */
	private boolean endMonthIncludes(LocalDate date) {
		if (date.getMonthValue() != endMonth.getValue()) {
			return false;
		} else if (endDay == 0) {
			return true;
		} else {
			return date.getDayOfMonth() <= endDay;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RangeEachYear)) {
			return false;
		}
		RangeEachYear other = (RangeEachYear) o;
		return endDay == other.endDay && endMonth == other.endMonth && startDay == other.startDay
				&& startMonth == other.startMonth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endDay;
		result = prime * result + ((endMonth == null) ? 0 : endMonth.hashCode());
		result = prime * result + startDay;
		result = prime * result + ((startMonth == null) ? 0 : startMonth.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[').append(this.getClass().getSimpleName()).append(": startMonth=").append(startMonth).append(" endMonth=").append(endMonth)
				.append(" startDay=").append(startDay).append(" endDay=").append(endDay).append(']');
		return sb.toString();
	}
}
