package net.logicsquad.recurring;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BasicSchedule implements Schedule {
	private List<ScheduleElement> elements = new ArrayList<>();

	public void addElement(ScheduleElement element) {
		elements.add(element);
		return;
	}

	@Override
	public boolean isOccurring(String event, LocalDate date) {
		for (ScheduleElement e : elements) {
			if (e.event().equals(event) && e.isOccurring(date)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<LocalDate> dates(String event, LocalDate start, LocalDate end) {
		List<LocalDate> result = new ArrayList<>();
		LocalDate cursor = start;
		while (cursor.equals(end) || cursor.isBefore(end)) {
			if (isOccurring(event, cursor)) {
				result.add(cursor);
			}
			cursor = cursor.plusDays(1);
		}
		return result;
	}

	@Override
	public LocalDate nextOccurrence(String event, LocalDate date) {
		LocalDate cursor = date;
		while (!isOccurring(event, cursor)) {
			cursor = cursor.plusDays(1);
		}
		return cursor;
	}
}
