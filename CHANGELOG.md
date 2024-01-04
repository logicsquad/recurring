# Changelog

The format here is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
