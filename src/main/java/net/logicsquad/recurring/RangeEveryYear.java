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
public final class RangeEveryYear implements TemporalExpression {
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
	private RangeEveryYear(Month startMonth, Month endMonth, int startDay, int endDay) {
		Objects.requireNonNull(startMonth);
		Objects.requireNonNull(endMonth);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = startDay;
		this.endDay = endDay;
		return;
	}

	/**
	 * Returns {@code RangeEveryYear} spanning from {@code start} day through
	 * {@code end} day (inclusive).
	 * 
	 * @param start
	 *            start day
	 * @param end
	 *            end day
	 * @return {@code RangeEveryYear}
	 * @throws NullPointerException
	 *             if either argument is {@code null}
	 */
	public static RangeEveryYear of(MonthDay start, MonthDay end) {
		Objects.requireNonNull(start);
		Objects.requireNonNull(end);
		return new RangeEveryYear(start.getMonth(), end.getMonth(), start.getDayOfMonth(), end.getDayOfMonth());
	}

	/**
	 * Returns {@code RangeEveryYear} spanning from beginning of {@code startMonth}
	 * through end of {@code endMonth} (inclusive).
	 * 
	 * @param startMonth
	 *            start month
	 * @param endMonth
	 *            end month
	 * @return {@code RangeEveryYear}
	 * @throws NullPointerException
	 *             if either argument is {@code null}
	 */
	public static RangeEveryYear of(Month startMonth, Month endMonth) {
		Objects.requireNonNull(startMonth);
		Objects.requireNonNull(endMonth);
		return new RangeEveryYear(startMonth, endMonth, 0, 0);
	}

	/**
	 * Returns {@code RangeEveryYear} spanning from beginning of {@code month}
	 * through end of {@code month} (inclusive).
	 * 
	 * @param month
	 *            a month
	 * @return {@code RangeEveryYear}
	 * @throws NullPointerException
	 *             if argument is {@code null}
	 */
	public static RangeEveryYear of(Month month) {
		Objects.requireNonNull(month);
		return new RangeEveryYear(month, month, 0, 0);
	}

	@Override
	public boolean includes(LocalDate date) {
		return monthsInclude(date) || startMonthIncludes(date) || endMonthIncludes(date);
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
		if (!(o instanceof RangeEveryYear)) {
			return false;
		}
		RangeEveryYear other = (RangeEveryYear) o;
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
		sb.append("[");
		sb.append(this.getClass().getSimpleName());
		sb.append(":");
		sb.append(" startMonth=");
		sb.append(startMonth);
		sb.append(" endMonth=");
		sb.append(endMonth);
		sb.append(" startDay=");
		sb.append(startDay);
		sb.append(" endDay=");
		sb.append(endDay);
		sb.append("]");
		return sb.toString();
	}
}
