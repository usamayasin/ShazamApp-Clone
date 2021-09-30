package com.afiniti.kiosk.shazamtask.ui.detail

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.model.Chart
import com.afiniti.kiosk.shazamtask.testutil.JsonLoader
import com.afiniti.kiosk.shazamtask.ui.MainActivity
import org.json.JSONObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(qualifiers = "xlarge-land", maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class TrackDetailFragmentTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var scenario: FragmentScenario<TrackDetailFragment>
    private lateinit var getChartJSON: JSONObject
    private lateinit var chart: Chart
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        getChartJSON = JsonLoader.loadJSONFromAsset(context, "get_chart.json")
        chart = JsonLoader.toClass(getChartJSON, "success", Chart::class.java)
    }

    @Test
    fun test_isDetailFragmentVisible_onAppLaunch() {

        val fragmentArgs = Bundle()
        fragmentArgs.putParcelable("track",  chart.chart[0])

        scenario = launchFragmentInContainer(initialState = Lifecycle.State.RESUMED,
            fragmentArgs = fragmentArgs
        )
        scenario.onFragment {
            Espresso.onView(ViewMatchers.withId(R.id.tv_track_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            Espresso.onView(ViewMatchers.withId(R.id.iv_track_image))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            Espresso.onView(ViewMatchers.withId(R.id.tv_track_artist))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            Espresso.onView(ViewMatchers.withId(R.id.btn_share))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }
}