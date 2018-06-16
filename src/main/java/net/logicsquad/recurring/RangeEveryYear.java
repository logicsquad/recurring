package net.logicsquad.recurring;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

/**
 * Describes a contiguous range of days in any year.
 * 
 * @author paulh
 */
public class RangeEveryYear implements TemporalExpression {
	/**
	 * First month of range
	 */
	private Month startMonth;

	/**
	 * Last month of range
	 */
	private Month endMonth;

	/**
	 * First day of range (in {@link #startMonth})
	 */
	private int startDay;

	/**
	 * Last day of range (in {@link #endMonth})
	 */
	private int endDay;

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
	 */
	public static RangeEveryYear of(MonthDay start, MonthDay end) {
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
	 */
	public static RangeEveryYear of(Month startMonth, Month endMonth) {
		return new RangeEveryYear(startMonth, endMonth, 0, 0);
	}

	/**
	 * Returns {@code RangeEveryYear} spanning from beginning of {@code month}
	 * through end of {@code month} (inclusive).
	 * 
	 * @param month
	 *            a month
	 * @return {@code RangeEveryYear}
	 */
	public static RangeEveryYear of(Month month) {
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
}
