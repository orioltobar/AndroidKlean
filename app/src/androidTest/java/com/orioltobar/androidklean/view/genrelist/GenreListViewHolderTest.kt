package com.orioltobar.androidklean.view.genrelist

import android.widget.FrameLayout
import androidx.test.rule.ActivityTestRule
import com.orioltobar.androidklean.UiAssertions
import com.orioltobar.androidklean.base.MockActivity
import com.orioltobar.domain.models.movie.MovieGenreDetailModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GenreListViewHolderTest : UiAssertions {

    init {
        MockKAnnotations.init(this, relaxed = true)
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var holder: GenreListViewHolder

    @MockK
    private lateinit var movieGenreDetailModelMock: MovieGenreDetailModel

    @get:Rule
    val activityRule =
        object : ActivityTestRule<MockActivity>(MockActivity::class.java, false, false) {
            override fun afterActivityLaunched() {
                super.afterActivityLaunched()
                runOnUiThread {
                    holder =
                        GenreListViewHolder(activity.findViewById<FrameLayout>(android.R.id.content))
                    activity.setView(holder.itemView)
                }
            }
        }

    @Before
    fun setup() {
        activityRule.launchActivity(null)

        every { movieGenreDetailModelMock.id } returns 12
        every { movieGenreDetailModelMock.name } returns "Action"
    }

    @Test
    fun setViewHolderInformation() {
        activityRule.runOnUiThread {
            holder.update(movieGenreDetailModelMock)
        }

        checkTextIsDisplayed(movieGenreDetailModelMock.name)
    }
}