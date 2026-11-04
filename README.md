![Build status](https://github.com/logicsquad/recurring/workflows/build/badge.svg)
[![License](https://img.shields.io/badge/License-BSD-blue.svg)](https://opensource.org/licenses/BSD-3-Clause)

Recurring
=========

What is this?
-------------
This project provides some interfaces and classes to represent
_recurring events_ in Java. It is able to represent some simple
"temporal expressions", such as:

* First Thursday of the month
* Last Wednesday of the month
* Every Monday
* Every second Monday from 14 August 2023
* Every day in June
* Every day from 17 May through 19 August
* 1 September

Most of these are agnostic to the _year_. The exception is "every second
Monday", which counts weeks from a particular date.

It also supports some simple ways of combining these expressions: union,
intersection and difference. These can be combined in arbitrarily
complex ways. For example:

* First Thursday and last Wednesday of every month from 17 May through
  19 August, except the month of June
* Second Monday of January through March

Recurring deals in dates, using `java.time.LocalDate`, so it has no
notion of times or time zones.

Getting started
---------------
`ScheduleElement`s (which link a `TemporalExpression` to some object
representing an event) can be combined into a `Schedule`, which can
then answer queries about the elements it contains. For example,
construct a `TemporalExpression` representing an event that occurs on
the first Thursday and last Wednesday of every month, and then put a
`ScheduleElement` into a `Schedule`:

    TemporalExpression firstThursday = DayInMonth.of(DayOfWeek.THURSDAY, 1);
    TemporalExpression lastWednesday = DayInMonth.of(DayOfWeek.WEDNESDAY, -1);
    ScheduleElement<String> element = ScheduleElement.of("Meeting", Union.of(firstThursday, lastWednesday));
    Schedule<String> schedule = Schedule.of(element);

An event doesn't have to be a `String`. It can be any type, and the
`Schedule` finds it using `equals()`.

Among other things, the `Schedule` can now tell us if the meeting is on
today:

    boolean today = schedule.isOccurring("Meeting", LocalDate.now());

when the meeting last occurred:

    LocalDate last = schedule.previousOccurrence("Meeting", LocalDate.now());

and all of its dates from today onwards:

    Stream<LocalDate> future = schedule.futureDates("Meeting", LocalDate.now());

That `Stream` is infinite, so limit it before collecting anything:

    List<LocalDate> nextFour = future.limit(4).toList();

The other building blocks are `DayInWeek` and `RangeEveryYear`, and
`Intersection` and `Difference` combine expressions as `Union` does.
Here's the first combined example from above:

    TemporalExpression mayToAugust = RangeEveryYear.of(MonthDay.of(Month.MAY, 17), MonthDay.of(Month.AUGUST, 19));
    TemporalExpression june = RangeEveryYear.of(Month.JUNE);
    TemporalExpression expression = Difference.of(Intersection.of(Union.of(firstThursday, lastWednesday), mayToAugust), june);

`TemporalExpression` has a single method, so a lambda can be an
expression too. That's the way to limit an expression to a particular
year, which the built-in ones can't do:

    TemporalExpression in2027 = date -> date.getYear() == 2027;
    TemporalExpression only2027 = Intersection.of(expression, in2027);

An expression like that runs out of dates, so query it with
`datesInRange()`. For now, `nextOccurrence()` and `futureDates()` will
keep looking for a date that never comes
([#16](https://github.com/logicsquad/recurring/issues/16)).

Using Recurring
---------------
You can use Recurring in your projects by including it as a
dependency:

    <dependency>
      <groupId>net.logicsquad</groupId>
      <artifactId>recurring</artifactId>
      <version>0.4</version>
    </dependency>

From 0.5, Recurring needs Java 17 or later. On Java 8 to 16, use 0.4.

It's also a module, `net.logicsquad.recurring`, so on the module path,
add `requires net.logicsquad.recurring;` to your `module-info.java`.

The [Javadoc](https://javadoc.io/doc/net.logicsquad/recurring) covers
the rest of the API.

Note that _the API may change prior to a `1.0` release,_ at which time
it will remain stable, consistent with [semantic
versioning](https://semver.org).

Contributing
------------
By all means, open issue tickets and pull requests if you have something
to contribute. To build the project and run its tests, use `mvn verify`
on Java 17 or later.

References
----------
This project implements the ideas in [Martin Fowler's "Recurring Events
for Calendars"](https://martinfowler.com/apsupp/recurring.pdf) (1997).
Where appropriate, we have made some changes to the code and ideas
presented in the original article.

* We make use of interfaces where appropriate. The original article
  hinted at interfaces, but mostly gave examples of concrete classes.
* We use `java.time.LocalDate` instead of `java.util.Date`.
* We have made some minor changes to class names. The `TE` suffix is
  gone, so `DayInMonthTE` is now `DayInMonth`, and `RangeEachYearTE` is
  now `RangeEveryYear`.

Most of this just reflects more modern Java idioms and changes in the
language and libraries.
