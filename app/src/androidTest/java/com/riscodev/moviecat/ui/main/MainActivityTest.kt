package com.riscodev.moviecat.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.tabs.TabLayout
import com.riscodev.moviecat.R
import com.riscodev.moviecat.ui.splash.SplashActivity
import com.riscodev.moviecat.utils.EspressoIdlingResource
import org.hamcrest.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    companion object {
        const val LIST_COUNT = 10
        const val INDEX_MOVIE_TAB = 0
        const val INDEX_SHOW_TAB = 1
    }

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies() {
        /*onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_MOVIE_TAB))*/
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        /*onView(withIndex(withId(R.id.rv_item), INDEX_MOVIE_TAB)).check(
            RecyclerViewItemCountAssertion(
                Matchers.greaterThanOrEqualTo(LIST_COUNT)
            )
        )
        onView(withIndex(withId(R.id.rv_item), INDEX_MOVIE_TAB)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                LIST_COUNT
            )
        )*/
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_MOVIE_TAB))
        onView(withIndex(withId(R.id.rv_item), INDEX_MOVIE_TAB)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.rv_item), INDEX_MOVIE_TAB)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )
        /*onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.image_view)).check(matches(isDisplayed()))*/
    }

    @Test
    fun shareMovie() {
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).perform(scrollTo(), click())
    }

    @Test
    fun loadShows() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_SHOW_TAB))
        onView(withId(R.id.rv_item)).check(matches(isDisplayed()))
        onView(withIndex(withId(R.id.rv_item), INDEX_SHOW_TAB)).check(
            RecyclerViewItemCountAssertion(
                Matchers.greaterThanOrEqualTo(LIST_COUNT)
            )
        )
        onView(withId(R.id.rv_item)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                LIST_COUNT
            )
        )
    }

    @Test
    fun loadDetailShow() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).perform(selectTabAtPosition(INDEX_SHOW_TAB))
        onView(withId(R.id.rv_item)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_item)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_desc)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status)).check(matches(CoreMatchers.not(withText(""))))
        onView(withId(R.id.image_view)).check(matches(isDisplayed()))
    }

    @Test
    fun shareShow() {
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).perform(scrollTo(), click())
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

    private fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
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