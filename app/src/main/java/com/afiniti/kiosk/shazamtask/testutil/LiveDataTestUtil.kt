package com.afiniti.kiosk.shazamtask.testutil

import kotlin.Throws
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtil {
    @Throws(InterruptedException::class)
    fun <T> getOrAwaitValue(liveData: LiveData<T>): T? {
        val data = arrayOfNulls<Any?>(1)
        val latch = CountDownLatch(1)
        val observer: Observer<T> = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw RuntimeException("LiveData value was never set.")
        }
        return data[0] as T?
    }
}