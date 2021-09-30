package com.afiniti.kiosk.shazamtask.ui.home

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afiniti.kiosk.shazamtask.data.network.remote.util.ApiResponse
import com.afiniti.kiosk.shazamtask.model.Chart
import com.afiniti.kiosk.shazamtask.testutil.*
import com.afiniti.kiosk.shazamtask.testutil.JsonLoader.loadJSONFromAsset
import com.afiniti.kiosk.shazamtask.testutil.JsonLoader.toClass
import com.afiniti.kiosk.shazamtask.utils.AppConstants
import com.afiniti.kiosk.shazamtask.utils.stringUtil.StringProviderImpl
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class HomeViewModelTest{

    private val immediateSchedulerRule = RxImmediateSchedulerRule()
    private val mockServerRule = MockServerRule()
    private val repositoryRule = RepositoryInitRule(mockServerRule)

    @get:Rule
    var chain: TestRule = RuleChain
        .outerRule(immediateSchedulerRule)
        .around(mockServerRule)
        .around(repositoryRule)

    private lateinit var context: Context
    private lateinit var getChartJSON: JSONObject
    private lateinit var chart:Chart
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var stringProviderImpl:StringProviderImpl
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        stringProviderImpl = StringProviderImpl(context)

        getChartJSON = loadJSONFromAsset(context, "get_chart.json")
        chart = toClass(getChartJSON, "success", Chart::class.java)

        homeViewModel = HomeViewModel(stringProviderImpl,repositoryRule.repository)
    }

    @Test
    fun `test getTracks() returns list of tracks`() {
        // prepare mock response
        mockServerRule.addMockResponse(AppConstants.Network.CHART_ENDPOINT,
            MockResponse().setResponseCode(200).setBody(JsonLoader.toString(getChartJSON, "success")))

        // get chart
        homeViewModel.getTracks()

        // validate the chart response
        val response = LiveDataTestUtil.getOrAwaitValue(homeViewModel.tracksResponse())
        assertEquals(ApiResponse.Status.SUCCESS, response?.status)
    }

    @Test
    fun `test getTracks() returns time out exception`() {
        // prepare mock response
        mockServerRule.addMockResponse(AppConstants.Network.CHART_ENDPOINT,
            MockResponse()
                .setResponseCode(200)
                .setBodyDelay(2, TimeUnit.SECONDS)
                .setBody(JsonLoader.toString(getChartJSON, "success")))

        // get chart
        homeViewModel.getTracks()

        // validate the chart response
        val response = LiveDataTestUtil.getOrAwaitValue(homeViewModel.tracksResponse())
        Assert.assertEquals(ApiResponse.Status.ERROR, response?.status)
        Assert.assertEquals(ApiResponse.ErrorType.THROWABLE, response?.errorType)
    }

}