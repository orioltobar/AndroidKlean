# [WorkInProgress] Movie Klean Playground

Sample Android App to access to TheMovieDatabase API.

The app is organized in modules following CLEAN architecture/SOLID principles:
```
app -> presentation -> domain/commons <- data <- networkdatasource/diskdatasource
```

## Code Features

- Kotlin.
- MVVM pattern.
- LiveData with Coroutines.
- Dagger Hilt.
- Retrofit.
- Moshi.
- Glide and Lottie.
- Architecture Components: Room, Navigation, Lifecyle.
- Multi module app.
- Gradle with Kotlin DSL (IN PROGRESS)
- Unit Testing with JUnit4, MockK, Robolectric.
- Instrumentation Testing.

## TO-DO LIST
- Unify gradle files with Kotlin DSL.
- Lists with Paging component.
- Implement better error handling (ATM ErrorModel contains a simple string).
- Implement Timber.
