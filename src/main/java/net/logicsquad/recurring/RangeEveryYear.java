package net.logicsquad.recurring;

import java.time.LocalDate;

public class RangeEveryYear implements TemporalExpression {
	private int startMonth;
	private int endMonth;
	private int startDay;
	private int endDay;

	public RangeEveryYear(int startMonth, int endMonth, int startDay, int endDay) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = startDay;
		this.endDay = endDay;
		return;
	}
	
	public RangeEveryYear(int startMonth, int endMonth) {
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = 0;
		this.endDay = 0;
		return;
	}
	
	public RangeEveryYear(int month) {
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
		return month > startMonth && month < endMonth;
	}

	private boolean startMonthIncludes(LocalDate date) {
		if (date.getMonthValue() != startMonth) {
			return false;
		} else if (startDay == 0) {
			return true;
		} else {
			return date.getDayOfMonth() >= startDay;
		}
	}

	private boolean endMonthIncludes(LocalDate date) {
		if (date.getMonthValue() != endMonth) {
			return false;
		} else if (endDay == 0) {
			return true;
		} else {
			return date.getDayOfMonth() <= endDay;
		}
	}
}
