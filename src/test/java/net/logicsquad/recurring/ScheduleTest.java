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

	private static final String KNOWN_EVENT = "Some event";
	private static final String UNKNOWN_EVENT = "Some other event";

	private ScheduleElement element = ScheduleElement.of(KNOWN_EVENT, intersection);

	private LocalDate in_1 = LocalDate.of(2018, 1, 8);
	private LocalDate in_2 = LocalDate.of(2018, 2, 12);
	private LocalDate in_3 = LocalDate.of(2018, 3, 12);

	private LocalDate out_1 = LocalDate.of(2018, 7, 9);
	private LocalDate out_2 = LocalDate.of(2018, 8, 13);
	private LocalDate out_3 = LocalDate.of(2018, 9, 10);

	private Schedule schedule = Schedule.of(element);

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
		assertTrue(schedule.isOccurring(KNOWN_EVENT, in_1));
		assertTrue(schedule.isOccurring(KNOWN_EVENT, in_2));
		assertTrue(schedule.isOccurring(KNOWN_EVENT, in_3));
		return;
	}

	@Test
	public void excludesExpectedDatesForUnknownEvent() {
		assertFalse(schedule.isOccurring(KNOWN_EVENT, out_1));
		assertFalse(schedule.isOccurring(KNOWN_EVENT, out_2));
		assertFalse(schedule.isOccurring(KNOWN_EVENT, out_3));
		return;
	}

	@Test
	public void excludesAllDatesForUnknownEvent() {
		assertFalse(schedule.isOccurring(UNKNOWN_EVENT, in_1));
		assertFalse(schedule.isOccurring(UNKNOWN_EVENT, in_2));
		assertFalse(schedule.isOccurring(UNKNOWN_EVENT, in_3));
		assertFalse(schedule.isOccurring(UNKNOWN_EVENT, out_1));
		assertFalse(schedule.isOccurring(UNKNOWN_EVENT, out_2));
		assertFalse(schedule.isOccurring(UNKNOWN_EVENT, out_3));
		return;
	}

	@Test
	public void datesInRangeReturnsExpectedDatesForYear() {
		List<LocalDate> result = schedule.datesInRange(KNOWN_EVENT, LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31));
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
		assertEquals(LocalDate.of(2018, 1, 8), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2017, 6, 30)));
		assertEquals(LocalDate.of(2018, 1, 8), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2017, 9, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2017, 12, 20)));
		assertEquals(LocalDate.of(2018, 1, 8), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2018, 1, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2018, 1, 8)));
		assertEquals(LocalDate.of(2018, 2, 12), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2018, 1, 9)));
		assertEquals(LocalDate.of(2018, 3, 12), schedule.nextOccurrence(KNOWN_EVENT, LocalDate.of(2018, 2, 13)));
		return;
	}

	@Test
	public void futureDatesProducesExpectedResult() {
		List<LocalDate> futureDates = schedule.futureDates(KNOWN_EVENT, LocalDate.of(2016, 9, 1)).limit(10)
				.collect(Collectors.toList());
		assertEquals(expectedFutureDates, futureDates);
		return;
	}

	@Test
	public void previousOccurrenceReturnsExpectedResults() {
		assertEquals(LocalDate.of(2017, 6, 12), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2017, 6, 30)));
		assertEquals(LocalDate.of(2017, 6, 12), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2017, 9, 1)));
		assertEquals(LocalDate.of(2017, 6, 12), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2017, 12, 20)));
		assertEquals(LocalDate.of(2017, 6, 12), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2018, 1, 1)));
		assertEquals(LocalDate.of(2018, 1, 8), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2018, 1, 8)));
		assertEquals(LocalDate.of(2018, 1, 8), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2018, 1, 9)));
		assertEquals(LocalDate.of(2018, 2, 12), schedule.previousOccurrence(KNOWN_EVENT, LocalDate.of(2018, 2, 13)));
		return;
	}

	@Test
	public void pastDatesProducesExpectedResult() {
		List<LocalDate> expectedPastDates = new ArrayList<>(expectedFutureDates);
		Collections.reverse(expectedPastDates);
		List<LocalDate> pastDates = schedule.pastDates(KNOWN_EVENT, LocalDate.of(2018, 5, 1)).limit(10)
				.collect(Collectors.toList());
		assertEquals(expectedPastDates, pastDates);
		return;
	}

	@Test
	public void elementsCannotBeModified() {
		List<ScheduleElement> elements = new ArrayList<>();
		elements.add(element);
		Schedule modifyMe = Schedule.of(elements);
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT, in_1));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT, in_2));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT, in_3));
		// Remove element--should have no effect
		elements.remove(element);
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT, in_1));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT, in_2));
		assertTrue(modifyMe.isOccurring(KNOWN_EVENT, in_3));
		return;
	}

	// Can't create a Schedule from null
	@Test
	public void ofThrowsOnNullList() {
		assertThrows(NullPointerException.class, () -> Schedule.of((List<ScheduleElement>) null));
		return;
	}

	// We can create an empty Schedule
	@Test
	public void ofAllowsEmptyList() {
		Schedule schedule = Schedule.of(new ArrayList<>());
		// Just test some operations
		assertFalse(schedule.isOccurring(KNOWN_EVENT, in_1));
		assertFalse(schedule.isOccurring(KNOWN_EVENT, in_2));
		assertFalse(schedule.isOccurring(KNOWN_EVENT, in_3));
		assertTrue(schedule.datesInRange(KNOWN_EVENT, LocalDate.of(2017, 1, 1), LocalDate.of(2019, 12, 31)).isEmpty());
		return;
	}

	// Can't add null to list of ScheduleElements
	@Test
	public void ofThrowsOnNullElement() {
		assertThrows(NullPointerException.class, () -> Schedule.of(element, null));
		return;
	}
}
