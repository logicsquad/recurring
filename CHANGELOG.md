# Changelog

The format here is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
