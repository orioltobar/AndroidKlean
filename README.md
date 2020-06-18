# [WorkInProgress] Movie Klean Playground

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/aa18f20faa4f45ecb4058a8ddb877fff)](https://app.codacy.com/manual/orioltobar/AndroidKlean?utm_source=github.com&utm_medium=referral&utm_content=orioltobar/AndroidKlean&utm_campaign=Badge_Grade_Dashboard)

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
- Moshi
- Glide and Lottie.
- Architecture Components: Room, Navigation, Lifecyle.
- Multi module app.
- Gradle with Kotlin DSL (IN PROGRESS)
- Unit Testing with JUnit4, MockK, Robolectric.
- Instrumentation Testing with Dagger.

## TO-DO LIST
- Unify gradle files with Kotlin DSL.
- Lists with Paging component.
- Implement better error handling (ATM ErrorModel contains a simple string).
- Implement Timber.
