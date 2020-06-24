package com.orioltobar.androidklean.view.discover

import androidx.test.rule.ActivityTestRule
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.UiAssertions
import com.orioltobar.androidklean.base.MockActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class DiscoverFragmentTest : UiAssertions {

    private lateinit var discoverFragment: DiscoverFragment

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule =
        object : ActivityTestRule<MockActivity>(MockActivity::class.java, false, false) {
            override fun afterActivityLaunched() {
                super.afterActivityLaunched()
                runOnUiThread {
                    discoverFragment = DiscoverFragment()
                    activity.setFragment(discoverFragment, DiscoverFragment.TAG)
                }
            }
        }

    @Before
    fun setup() {
        activityRule.launchActivity(null)
    }

    @Test
    fun loadingAnimationIsShownByDefaultTest() {
        with(activityRule.activity) {
            checkViewIsDisplayed(R.id.discoverFragmentProgressBar)
            val text =
                resources.getText(R.string.under_construction).toString()
            checkTextIsDisplayed(text)
        }
    }
}