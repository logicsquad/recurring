![](https://github.com/logicsquad/recurring/workflows/build/badge.svg)
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
* Every day in June
* Every day from 17 May through 19 August
* 1 September

The expressions are agnostic to the _year_.

It also supports some simple ways of combining these expressions: union,
intersection and difference. These can be combined in arbitrarily
complex ways. For example:

* First Thursday and last Wednesday of every month from 17 May through
19 August, except the month of June
* Second Monday of January through March

Getting started
---------------
`ScheduleElement`s (which link a `TemporalExpression` to a `String`
token representing an event) can be combined into a `Schedule`, which
can then answer queries about the elements it contains. For example,
construct a `TemporalExpression` representing an event that occurs on
the first Thursday and last Wednesday of every month, and then put a
`ScheduleElement` into a `Schedule`:

    TemporalExpression firstThursday = DayInMonth.of(DayOfWeek.THURSDAY, 1);
    TemporalExpression lastWednesday = DayInMonth.of(DayOfWeek.WEDNESDAY, -1);
    ScheduleElement element = ScheduleElement.of("Meeting", Union.of(firstThursday, lastWednesday));
    Schedule schedule = Schedule.of(element);

Among other things, the `Schedule` can now tell us if the meeting is on
today:

    boolean today = schedule.isOccurring("Meeting", LocalDate.now());

when the meeting last occurred:

    LocalDate last = schedule.previousOccurrence("Meeting", LocalDate.now());

and all future dates of the meeting:

	Stream<LocalDate> future = schedule.futureDates("Meeting", LocalDate.now());

Using Recurring
---------------
You can use Recurring in your projects by including it as a
dependency:

    <dependency>
      <groupId>net.logicsquad</groupId>
      <artifactId>recurring</artifactId>
      <version>0.3</version>
    </dependency>

Note that _the API may change prior to a `1.0` release,_ at which time
it will remain stable, consistent with [semantic
versioning](https://semver.org).

Contributing
------------
By all means, open issue tickets and pull requests if you have something
to contribute.

References
----------
This project implements the ideas in [Martin Fowler's "Recurring Events
for Calendars"](https://martinfowler.com/apsupp/recurring.pdf) (1997).
Where appropriate, we have made some changes to the code and ideas
presented in the original article.

* We will make use of interfaces where appropriate. The original article
  hinted at interfaces, but mostly gave examples of concrete classes.
* We will use `java.time.LocalDate` instead of `java.util.Date`.
* We will make some minor changes to suggested class names.

Most of this just reflects more modern Java idioms and changes in the
language and libraries.
