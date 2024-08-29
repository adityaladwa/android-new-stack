- Create layered architecture
  per [Google recommended architecture](https://developer.android.com/topic/architecture/recommendations)
    - Expose public api via `api`, `impl`
      modules [Link](https://speakerdeck.com/vrallev/android-at-scale-at-square)
    - Create a `data` module for data layer
        - use coroutine flows for communication b/w layers
    - Create a `feature` module for each feature
        - Use android-view-models
        - Use jetpack-compose
        - UI tests using [Maestro UI testing](https://github.com/mobile-dev-inc/maestro)
        - Unit tests using [JUnit](https://junit.org/junit5/)
            - Use fakes over mocks, create fakes using json files
- Use hilt for dependency injection
- Know what to test
  - Unit test ViewModels, including Flows.
  - Unit test data layer entities. That is, repositories and data sources.
  - UI navigation tests that are useful as regression tests in CI.


Problems to solve
- api, impl modules