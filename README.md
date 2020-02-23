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
- Dagger2.
- Retrofit.
- Glide and Lottie.
- Architecture Components: Room, Navigation, Lifecyle.
- Multi module app.
- Gradle with Kotlin DSL (IN PROGRESS)
- Unit Testing with MockK. (IN PROGRESS)
- Instrumentation Testing. (NOT IMPLEMENTED YET)

## TO-DO LIST
- Unify gradle files with Kotlin DSL.
- Unit testing is in progress.
- Instrumentation Testing.
- Lists with Paging component.
