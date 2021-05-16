package com.riscodev.moviecat.ui.main

import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.tabs.TabLayout
import com.riscodev.moviecat.R
import com.riscodev.moviecat.ui.splash.SplashActivity
import com.riscodev.moviecat.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    companion object {
        const val LIST_COUNT = 10
        const val INDEX_MOVIE_TAB = 0
        const val INDEX_SHOW_TAB = 1
        const val DELAY_TIME = 1000L
    }

    @Before
    fun setUp() {
        ActivityScenario.launch(SplashActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_MOVIE_TAB))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(
            RecyclerViewItemCountAssertion(Matchers.greaterThan(0))
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(LIST_COUNT)
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_MOVIE_TAB))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(
            RecyclerViewItemCountAssertion(Matchers.greaterThan(0))
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.tv_title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_movie)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_genres_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres_movie)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_score_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score_movie)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_date_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_movie)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_desc_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc_movie)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_status_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status_movie)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.image_view_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun shareMovie() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_MOVIE_TAB))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(
            RecyclerViewItemCountAssertion(Matchers.greaterThan(0))
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.btn_share_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share_movie)).perform(scrollTo(), click())
    }

    @Test
    fun loadShows() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_SHOW_TAB))
        SystemClock.sleep(DELAY_TIME)
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_show)).check(
            RecyclerViewItemCountAssertion(Matchers.greaterThan(0))
        )
        onView(withId(R.id.rv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(LIST_COUNT)
        )
    }

    @Test
    fun loadDetailShow() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_SHOW_TAB))
        SystemClock.sleep(DELAY_TIME)
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_show)).check(
            RecyclerViewItemCountAssertion(Matchers.greaterThan(0))
        )
        onView(withId(R.id.rv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.tv_title_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_show)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_genres_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres_show)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_score_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score_show)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_date_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_show)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_desc_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc_show)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_status_show)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status_show)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.image_view_show)).check(matches(isDisplayed()))
    }

    @Test
    fun shareShow() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_SHOW_TAB))
        SystemClock.sleep(DELAY_TIME)
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_show)).check(
            RecyclerViewItemCountAssertion(Matchers.greaterThan(0))
        )
        onView(withId(R.id.rv_show)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.btn_share_show)).perform(scrollTo())
        onView(withId(R.id.btn_share_show)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share_show)).perform(click())
    }

    @Test
    fun loadFavoriteMovies() {
        onView(withId(R.id.bottomNavigation)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.menu_favorite)).perform(click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_MOVIE_TAB))
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(LIST_COUNT)
        )
    }

    @Test
    fun loadFavoriteShows() {
        onView(withId(R.id.bottomNavigation)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.menu_favorite)).perform(click())
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_SHOW_TAB))
        SystemClock.sleep(DELAY_TIME)
        onView(withId(R.id.rv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_show)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(LIST_COUNT)
        )
    }

    @Test
    fun showVersionApp() {
        onView(withId(R.id.bottomNavigation)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.menu_setting)).perform(click())
        onView(withId(R.id.version_app)).check(matches(isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = CoreMatchers.allOf(
                isDisplayed(),
                isAssignableFrom(TabLayout::class.java)
            )

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    class RecyclerViewItemCountAssertion(private val matcher: Matcher<Int>) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, matcher)
        }
    }
}