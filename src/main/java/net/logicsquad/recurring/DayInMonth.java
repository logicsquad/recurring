package net.logicsquad.recurring;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DayInMonth implements TemporalExpression {
	private int ordinal;

	private DayOfWeek day;
	public DayInMonth(DayOfWeek day, int ordinal) {
		this.day = day;
		this.ordinal = ordinal;
		return;
	}

	@Override
	public boolean includes(LocalDate date) {
		return dayMatches(date) && weekMatches(date);
	}

	private boolean dayMatches(LocalDate date) {
		boolean result = date.getDayOfWeek() == day;
		return result;
	}

	private boolean weekMatches(LocalDate date) {
		if (ordinal > 0) {
			return weekFromStartMatches(date);
		} else {
			return weekFromEndMatches(date);
		}
	}

	private boolean weekFromStartMatches(LocalDate date) {
		return weekInMonth(date.getDayOfMonth()) == ordinal;
	}

	private boolean weekFromEndMatches(LocalDate date) {
		return weekInMonth(daysLeftInMonth(date) + 1) == Math.abs(ordinal);
	}

	private int weekInMonth(int day) {
		return ((day - 1) / 7) + 1;
	}

	private int daysLeftInMonth(LocalDate date) {
		return (int) date.until(date.with(TemporalAdjusters.lastDayOfMonth()), ChronoUnit.DAYS);
	}
}
