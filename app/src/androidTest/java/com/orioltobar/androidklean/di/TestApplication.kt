package com.orioltobar.androidklean.di

import androidx.test.core.app.ActivityScenario
import com.orioltobar.androidklean.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestApplication {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun runAppTest() {
        ActivityScenario.launch(MainActivity::class.java)
    }
}
