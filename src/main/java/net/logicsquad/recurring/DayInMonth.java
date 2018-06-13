package net.logicsquad.recurring;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DayInMonth implements TemporalExpression {
	private int count;
	private int dayIndex;

	public DayInMonth(int dayIndex, int count) {
		this.dayIndex = dayIndex;
		this.count = count;
		return;
	}

	@Override
	public boolean includes(LocalDate date) {
		return dayMatches(date) && weekMatches(date);
	}

	private boolean dayMatches(LocalDate date) {
		boolean result = date.getDayOfWeek().getValue() == dayIndex;
		return result;
	}

	private boolean weekMatches(LocalDate date) {
		if (count > 0) {
			return weekFromStartMatches(date);
		} else {
			return weekFromEndMatches(date);
		}
	}

	private boolean weekFromStartMatches(LocalDate date) {
		return weekInMonth(date.getDayOfMonth()) == count;
	}

	private boolean weekFromEndMatches(LocalDate date) {
		return weekInMonth(daysLeftInMonth(date) + 1) == Math.abs(count);
	}

	private int weekInMonth(int day) {
		return ((day - 1) / 7) + 1;
	}

	private int daysLeftInMonth(LocalDate date) {
		return (int) date.until(date.with(TemporalAdjusters.lastDayOfMonth()), ChronoUnit.DAYS);
	}
}
