package com.afiniti.kiosk.shazamtask.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.data.network.remote.errors.APIError
import com.afiniti.kiosk.shazamtask.data.network.remote.util.ApiResponse
import com.afiniti.kiosk.shazamtask.data.network.remote.util.LiveApiResponse
import com.afiniti.kiosk.shazamtask.data.repository.Repository
import com.afiniti.kiosk.shazamtask.model.Chart
import com.afiniti.kiosk.shazamtask.model.Track
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val ctx: Context,
    private val repository: Repository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val tracksLiveData = LiveApiResponse<Array<Track>>()

    fun tracksResponse() = tracksLiveData

    fun getTracks() {
        disposables.add(repository.getTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { tracksLiveData.setValue(ApiResponse.loading()) }
            .subscribe({ result ->
                updateTracksLiveData(result)
            }, { throwable ->
                tracksLiveData.postValue(ApiResponse.error(throwable))
            })
        )
    }

    private fun updateTracksLiveData(result: Response<Chart>) {
        if (result.isSuccessful) {
            val resultBody = result.body()
            if (resultBody != null) {
                tracksLiveData.postValue(ApiResponse.success(resultBody.chart))
            } else {
                val error = APIError(result.code(), ctx.getString(R.string.error_empty_response))
                tracksLiveData.postValue(ApiResponse.error(error))
            }
        } else
            tracksLiveData.postValue(ApiResponse.error(result))
    }

}