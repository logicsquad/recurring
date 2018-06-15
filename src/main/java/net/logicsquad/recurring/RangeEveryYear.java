package net.logicsquad.recurring;

import java.time.LocalDate;
import java.time.Month;

/**
 * Describes a contiguous range of days in any year.
 * 
 * @author paulh
 */
public class RangeEveryYear implements TemporalExpression {
	private Month startMonth;
	private Month endMonth;
	private int startDay;
	private int endDay;

	public RangeEveryYear(Month startMonth, Month endMonth, int startDay, int endDay) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = startDay;
		this.endDay = endDay;
		return;
	}
	
	public RangeEveryYear(Month startMonth, Month endMonth) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = 0;
		this.endDay = 0;
		return;
	}
	
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

	private boolean monthsInclude(LocalDate date) {
		int month = date.getMonthValue();
		return month > startMonth.getValue() && month < endMonth.getValue();
	}

	private boolean startMonthIncludes(LocalDate date) {
		if (date.getMonthValue() != startMonth.getValue()) {
			return false;
		} else if (startDay == 0) {
			return true;
		} else {
			return date.getDayOfMonth() >= startDay;
		}
	}

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
