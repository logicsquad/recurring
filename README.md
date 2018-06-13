Recurring
=========

Some Java code to represent recurring events.

This will start as an implementation of the ideas in Martin Fowler's
"Recurring Events for Calendars" (1997). That paper presents only an
outline of the ideas, and we will use Java 8 features in this
implementation.

Differences
-----------

Where appropriate, we will make some changes to the code and ideas
presented in the original article.

* We will make use of interfaces where appropriate. The original article
  hinted at interfaces, but mostly gave examples of concrete classes.
* We will use `java.time.LocalDate` instead of `java.util.Date`.
* We will make some minor changes to suggested class names.

Most of this just reflects more modern Java idioms and changes in the
language and libraries.
