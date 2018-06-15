package net.logicsquad.recurring;

import java.time.LocalDate;
import java.time.Month;

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
	 * Constructor taking months and days.
	 * 
	 * @param startMonth
	 *            first month in range
	 * @param endMonth
	 *            last month in range
	 * @param startDay
	 *            first day (in {@code startMonth})
	 * @param endDay
	 *            last day (in {@code endMonth})
	 */
	public RangeEveryYear(Month startMonth, Month endMonth, int startDay, int endDay) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = startDay;
		this.endDay = endDay;
		return;
	}

	/**
	 * Constructor taking months.
	 * 
	 * @param startMonth
	 *            first month in range
	 * @param endMonth
	 *            last month in range
	 */
	public RangeEveryYear(Month startMonth, Month endMonth) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = 0;
		this.endDay = 0;
		return;
	}

	/**
	 * Constructor taking a month.
	 * 
	 * @param month
	 *            month in range
	 */
	public RangeEveryYear(Month month) {
		this.startMonth = month;
		this.endMonth = month;
		this.startDay = 0;
		this.endDay = 0;
		return;
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
