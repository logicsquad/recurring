# Changelog

The format here is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.5] - 2026-11-08
### Added
- Recurring is now a named module, `net.logicsquad.recurring`. On the
  module path, 0.4 was an automatic module called `recurring`, so
  update any `requires` that names it. #15

- `TemporalExpression` is now marked `@FunctionalInterface`, so a
  lambda will always work as an expression. #19

- The JAR now includes the licence, as `META-INF/LICENSE`. #9

### Changed
- Recurring now needs Java 17 or later, and is tested on Java 17, 21
  and 25. On Java 8 to 16, use 0.4. #13

- Renamed `RangeEveryYear` to `RangeEachYear`, to match
  `RangeEachYearTE` in Fowler's article. Code that uses the class will
  need updating. #20

- The README now covers `DayInWeek`, combining expressions, lambdas
  and the module, and says that `futureDates()` returns an infinite
  `Stream`. #19

- Release tags are now named `release/x.y` rather than `version/x.y`,
  so links to the old tags no longer work. #17

- Releases are now published through the Central Publisher Portal,
  since OSSRH has shut down. #11

- Updated JUnit to 6.1.3, and pinned every Maven plugin at its latest
  version. #14

- CI now builds pull requests, using current action versions, and
  Dependabot keeps the POM and the workflows up to date. #12

### Removed
- Removed the Maven site and reporting configuration, including PMD,
  which the project didn't use. #10

### Fixed
- GitHub now detects the licence as BSD-3-Clause, rather than
  "Other". #9

- The POM's SCM URLs now work. The connection used `git://`, which
  GitHub no longer serves, and the developer connection had the wrong
  host.

## [0.4] - 2024-01-04
### Added
- `Schedule` and `ScheduleElement` are now generic types, varying on
  the type of `event` they accept. This allows use of types that might
  be more suited to specific applications than `String`. #8

## [0.3] - 2023-08-13
### Added
- `DayInWeek` now provides for expressions such as "every Monday",
  "every Tuesday and Thursday", and even "every second Friday". #4

### Fixed
- Fixed broken handling of start and end in same month for
  `RangeEveryYear`. This also fixes single day expressions, such as
  "every 1 September". #5

- Updated Surefire version so that JUnit 5 tests are actually run
  during the build. #6

## [0.2] - 2023-07-09
### Changed
- Various improvements to encapsulation, including: making fields
  final where appropriate; making defensive copies of list arguments;
  reducing visibility where appropriate.

- Migrated project from Travis CI to GitHub Actions (and moved from
  `paulhoadley` to `logicsquad` in the process).

- Updated to JUnit 5.

- Prevented adding `null` `ScheduleElement`s to a `Schedule`.

## [0.1] - 2018-07-08
- Initial release.
