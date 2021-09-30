package com.afiniti.kiosk.shazamtask.ui.home

import android.content.Context
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.model.Chart
import com.afiniti.kiosk.shazamtask.testutil.JsonLoader
import org.json.JSONObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.afiniti.kiosk.shazamtask.testutil.MockServerRule
import com.afiniti.kiosk.shazamtask.testutil.RepositoryInitRule
import com.afiniti.kiosk.shazamtask.testutil.RxImmediateSchedulerRule
import com.afiniti.kiosk.shazamtask.TestUtil.atPosition
import com.afiniti.kiosk.shazamtask.ui.MainActivity
import org.junit.rules.RuleChain
import org.junit.rules.TestRule


@RunWith(AndroidJUnit4::class)
@Config(qualifiers = "xlarge-land", maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class HomeFragmentTest {

    private val immediateSchedulerRule = RxImmediateSchedulerRule()
    private val mockServerRule = MockServerRule()
    private val repositoryRule = RepositoryInitRule(mockServerRule)

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var chain: TestRule = RuleChain
        .outerRule(immediateSchedulerRule)
        .around(activityRule)
        .around(mockServerRule)
        .around(repositoryRule)

    private lateinit var getChartJSON: JSONObject
    private lateinit var chart: Chart
    private lateinit var context: Context
    private val LIST_ITEM_IN_TEST = 0

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        getChartJSON = JsonLoader.loadJSONFromAsset(context, "get_chart.json")

        chart = JsonLoader.toClass(getChartJSON, "success", Chart::class.java)
    }

    @Test
    fun test_isHomeFragmentVisible_onAppLaunch() {
        onView(withId(R.id.recyclerPopularTracks)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_label)).check(matches(isDisplayed()))
    }

    @Test
    fun test_homeFragmentListItem_isDetailFragmentVisible() {
        onView(withId(R.id.recyclerPopularTracks))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(LIST_ITEM_IN_TEST))
        chart.chart.forEachIndexed { index, track ->
            onView(withId(R.id.recyclerPopularTracks))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index))
                .check(matches(atPosition(index, hasDescendant(withText(track.heading.title)))))
        }
    }
}