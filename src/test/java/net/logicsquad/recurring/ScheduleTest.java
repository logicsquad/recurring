package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ScheduleTest {
	private TemporalExpression dayInMonth = DayInMonth.of(DayOfWeek.MONDAY, 2);
	private TemporalExpression range = RangeEveryYear.of(Month.JANUARY, Month.JUNE);

	private Intersection intersection = Intersection.of(dayInMonth, range);

	private static final String KNOWN_EVENT_1 = "Some event";
	private static final String UNKNOWN_EVENT_1 = "Some other event";

	private static final Integer KNOWN_EVENT_2 = Integer.valueOf(12);
	private static final Integer UNKNOWN_EVENT_2 = Integer.valueOf(765);
	
	private ScheduleElement<String> stringElement = ScheduleElement.of(KNOWN_EVENT_1, intersection);
	private ScheduleElement<Integer> integerElement = ScheduleElement.of(KNOWN_EVENT_2, intersection);

	private LocalDate in_1 = LocalDate.of(2018, 1, 8);
	private LocalDate in_2 = LocalDate.of(2018, 2, 12);
	private LocalDate in_3 = LocalDate.of(2018, 3, 12);

	private LocalDate out_1 = LocalDate.of(2018, 7, 9);
	private LocalDate out_2 = LocalDate.of(2018, 8, 13);
	private LocalDate out_3 = LocalDate.of(2018, 9, 10);

	private Schedule<String> stringSchedule = Schedule.of(stringElement);
	private Schedule<Integer> integerSchedule = Schedule.of(integerElement);

	// List of first 10 elements of stream from 2016-09-01: 2nd Monday, Jan -> Jun
	// or, reversed, stream from 2018-05-01.
	private List<LocalDate> expectedFutureDates = new ArrayList<>(Arrays.asList(
			LocalDate.of(2017, 1, 9),
			LocalDate.of(2017, 2, 13),
			LocalDate.of(2017, 3, 13),
			LocalDate.of(2017, 4, 10),
			LocalDate.of(2017, 5, 8),
			LocalDate.of(2017, 6, 12),
			LocalDate.of(2018, 1, 8),
			LocalDate.of(2018, 2, 12),
			LocalDate.of(2018, 3, 12),
			LocalDate.of(2018, 4, 9)
			));

	@Test
	public void includesExpectedDatesForKnownEvent() {
		assertTrue(stringSchedule.isOccurring(KNOWN_EVENT_1, in_1));
		assertTrue(stringSchedule.isOccurring(KNOWN_EVENT_1, in_2));
		assertTrue(stringSchedule.isOccurring(KNOWN_EVENT_1, in_3));

		assertTrue(integerSchedule.isOccurring(KNOWN_EVENT_2, in_1));
		assertTrue(integerSchedule.isOccurring(KNOWN_EVENT_2, in_2));
		assertTrue(integerSchedule.isOccurring(KNOWN_EVENT_2, in_3));
		return;
	}

	@Test
	public void excludesExpectedDatesForUnknownEvent() {
		assertFalse(stringSchedule.isOccurring(KNOWN_EVENT_1, out_1));
		assertFalse(stringSchedule.isOccurring(KNOWN_EVENT_1, out_2));
		assertFalse(stringSchedule.isOccurring(KNOWN_EVENT_1, out_3));

		assertFalse(integerSchedule.isOccurring(KNOWN_EVENT_2, out_1));
		assertFalse(integerSchedule.isOccurring(KNOWN_EVENT_2, out_2));
		assertFalse(integerSchedule.isOccurring(KNOWN_EVENT_2, out_3));
		return;
	}

	@Test
	public void excludesAllDatesForUnknownEvent() {
		assertFalse(stringSchedule.isOccurring(UNKNOWN_EVENT_1, in_1));
		assertFalse(stringSchedule.isOccurring(UNKNOWN_EVENT_1, in_2));
		assertFalse(stringSchedule.isOccurring(UNKNOWN_EVENT_1, in_3));
		assertFalse(stringSchedule.isOccurring(UNKNOWN_EVENT_1, out_1));
		assertFalse(stringSchedule.isOccurring(UNKNOWN_EVENT_1, out_2));
		assertFalse(stringSchedule.isOccurring(UNKNOWN_EVENT_1, out_3));

		assertFalse(integerSchedule.isOccurring(UNKNOWN_EVENT_2, in_1));
		assertFalse(integerSchedule.isOccurring(UNKNOWN_EVENT_2, in_2));
		assertFalse(integerSchedule.isOccurring(UNKNOWN_EVENT_2, in_3));
		assertFalse(integerSchedule.isOccurring(UNKNOWN_EVENT_2, out_1));
		assertFalse(integerSchedule.isOccurring(UNKNOWN_EVENT_2, out_2));
		assertFalse(integerSchedule.isOccurring(UNKNOWN_EVENT_2, out_3));
		return;
	}

	@Test
	public void datesInRangeReturnsExpectedDatesForYear() {
		List<LocalDate> result = stringSchedule.datesInRange(KNOWN_EVENT_1, LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		assertEquals(6, result.size());
		assertTrue(result.contains(LocalDate.of(2018, 1, 8)));
		assertTrue(result.contains(LocalDate.of(2018, 2, 12)));
		assertTrue(result.contains(LocalDate.of(2018, 3, 12)));
		assertTrue(result.contains(LocalDate.of(2018, 4, 9)));
		assertTrue(result.contains(LocalDate.of(2018, 5, 14)));
		assertTrue(result.contains(LocalDate.of(2018, 6, 11)));

		result = integerSchedule.datesInRange(KNOWN_EVENT_2, LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
		assertEquals(6, result.size());
		assertTrue(result.contains(LocalDate.of(2018, 1, 8)));
		assertTrue(result.contains(LocalDate.of(2018, 2, 12)));
		assertTrue(result.contains(LocalDate.of(2018, 3, 12)));
		assertTrue(result.contains(LocalDate.of(2018, 4, 9)));
		assertTrue(result.contains(LocalDate.of(2018, 5, 14)));
		assertTrue(result.contains(LocalDate.of(2018, 6, 11)));
		return;
	}

	@Test
	public void nextOccurrenceReturnsExpectedResults() {
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2017, 6, 30)));
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2017, 9, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2017, 12, 20)));
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 1, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 1, 8)));
		assertEquals(LocalDate.of(2018, 2, 12), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 1, 9)));
		assertEquals(LocalDate.of(2018, 3, 12), stringSchedule.nextOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 2, 13)));

		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2017, 6, 30)));
		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2017, 9, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2017, 12, 20)));
		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 1, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 1, 8)));
		assertEquals(LocalDate.of(2018, 2, 12), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 1, 9)));
		assertEquals(LocalDate.of(2018, 3, 12), integerSchedule.nextOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 2, 13)));
		return;
	}

	@Test
	public void futureDatesProducesExpectedResult() {
		List<LocalDate> futureDates = stringSchedule.futureDates(KNOWN_EVENT_1, LocalDate.of(2016, 9, 1)).limit(10).collect(Collectors.toList());
		assertEquals(expectedFutureDates, futureDates);

		futureDates = integerSchedule.futureDates(KNOWN_EVENT_2, LocalDate.of(2016, 9, 1)).limit(10).collect(Collectors.toList());
		assertEquals(expectedFutureDates, futureDates);
		return;
	}

	@Test
	public void previousOccurrenceReturnsExpectedResults() {
		assertEquals(LocalDate.of(2017, 6, 12), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2017, 6, 30)));
		assertEquals(LocalDate.of(2017, 6, 12), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2017, 9, 1)));
		assertEquals(LocalDate.of(2017, 6, 12), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2017, 12, 20)));
		assertEquals(LocalDate.of(2017, 6, 12), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 1, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 1, 8)));
		assertEquals(LocalDate.of(2018, 1, 8), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 1, 9)));
		assertEquals(LocalDate.of(2018, 2, 12), stringSchedule.previousOccurrence(KNOWN_EVENT_1, LocalDate.of(2018, 2, 13)));

		assertEquals(LocalDate.of(2017, 6, 12), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2017, 6, 30)));
		assertEquals(LocalDate.of(2017, 6, 12), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2017, 9, 1)));
		assertEquals(LocalDate.of(2017, 6, 12), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2017, 12, 20)));
		assertEquals(LocalDate.of(2017, 6, 12), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 1, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 1, 8)));
		assertEquals(LocalDate.of(2018, 1, 8), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 1, 9)));
		assertEquals(LocalDate.of(2018, 2, 12), integerSchedule.previousOccurrence(KNOWN_EVENT_2, LocalDate.of(2018, 2, 13)));
		return;
	}

	@Test
	public void pastDatesProducesExpectedResult() {
		List<LocalDate> expectedPastDates = new ArrayList<>(expectedFutureDates);
		Collections.reverse(expectedPastDates);
		List<LocalDate> pastDates = stringSchedule.pastDates(KNOWN_EVENT_1, LocalDate.of(2018, 5, 1)).limit(10).collect(Collectors.toList());
		assertEquals(expectedPastDates, pastDates);

		pastDates = integerSchedule.pastDates(KNOWN_EVENT_2, LocalDate.of(2018, 5, 1)).limit(10).collect(Collectors.toList());
		assertEquals(expectedPastDates, pastDates);
		return;
	}

	@Test
	public void elementsCannotBeModified() {
		List<ScheduleElement<String>> elements = new ArrayList<>();
		elements.add(stringElement);
		Schedule<String> modifyMe = Schedule.of(elements);
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT_1, in_1));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT_1, in_2));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT_1, in_3));
		// Remove element--should have no effect
		elements.remove(stringElement);
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT_1, in_1));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT_1, in_2));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT_1, in_3));
		return;
	}

	// Can't create a Schedule from null
	@Test
	public void ofThrowsOnNullList() {
		assertThrows(NullPointerException.class, () -> Schedule.of((List<ScheduleElement<String>>) null));
		return;
	}

	// We can create an empty Schedule
	@Test
	public void ofAllowsEmptyList() {
		Schedule<String> schedule = Schedule.of(new ArrayList<>());
		// Just test some operations
		assertFalse(schedule.isOccurring(KNOWN_EVENT_1, in_1));
		assertFalse(schedule.isOccurring(KNOWN_EVENT_1, in_2));
		assertFalse(schedule.isOccurring(KNOWN_EVENT_1, in_3));
		assertTrue(schedule.datesInRange(KNOWN_EVENT_1, LocalDate.of(2017, 1, 1), LocalDate.of(2019, 12, 31)).isEmpty());
		return;
	}

	// Can't add null to list of ScheduleElements
	@Test
	public void ofThrowsOnNullElement() {
		assertThrows(NullPointerException.class, () -> Schedule.of(stringElement, null));
		return;
	}
}
