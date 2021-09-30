package com.afiniti.kiosk.shazamtask.testutil

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.regex.Pattern

class MockServerRule : TestWatcher() {
    private val responses: MutableMap<String, MutableList<MockResponse>> = HashMap()
    var server: MockWebServer? = null
    override fun starting(description: Description) {
        super.starting(description)
        server = MockWebServer()
        try {
            server!!.start(4040)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        server!!.url("/")
        server!!.dispatcher = object : Dispatcher() {
            override fun dispatch(recordedRequest: RecordedRequest): MockResponse {
                // iterate hashmap to find mock responses for particular api path
                for (key in responses.keys) {
                    val wildcardPath = getWildcardPath(key)
                    // now match the wildcarded path/key with recordedRequest path to find corresponding responses
                    if (Pattern.compile(wildcardPath).matcher(recordedRequest.path).find()) {
                        // if match is found then pop the first response
                        // from the list associated with this key/path
                        val mockResponse = responses[key]!!.removeAt(0)
                        // remove the key if no more responses remaining
                        if (responses[key]!!.size <= 0) responses.remove(key)
                        return mockResponse
                    }
                }
                return MockResponse().setResponseCode(404)
            }
        }
    }

    override fun finished(description: Description) {
        super.finished(description)
        responses.clear()
        try {
            server!!.shutdown()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    val serverUrl: String
        get() = server!!.url("/").toString()

    /**
     * @param apiPath API path with which you want to associate the mock response
     * @param response A mock response which will be served when the apiPath is hit
     * adds a response into a list which is associated with an apiPath
     */
    fun addMockResponse(apiPath: String, response: MockResponse) {
        if (responses.containsKey(apiPath)) {
            responses[apiPath]!!.add(response)
        } else {
            val mocks: MutableList<MockResponse> = ArrayList()
            mocks.add(response)
            responses.put(apiPath, mocks)
        }
    }

    private fun getWildcardPath(path: String): String {
        return String.format("/%s", path.replace("\\{.*?\\}".toRegex(), ".*"))
    }
}