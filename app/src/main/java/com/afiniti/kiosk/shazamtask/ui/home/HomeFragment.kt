package com.afiniti.kiosk.shazamtask.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afiniti.kiosk.shazamtask.adapter.TrackAdapter
import com.afiniti.kiosk.shazamtask.data.network.remote.util.ApiErrorHandler
import com.afiniti.kiosk.shazamtask.data.network.remote.util.ApiResponse
import com.afiniti.kiosk.shazamtask.databinding.HomeFragmentBinding
import com.afiniti.kiosk.shazamtask.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import com.afiniti.kiosk.shazamtask.model.Track


class HomeFragment : Fragment(), TrackAdapter.TrackClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var mBinding: HomeFragmentBinding
    private var tracksAdapter: TrackAdapter? = null
    private var apiErrorHandler: ApiErrorHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            apiErrorHandler = ApiErrorHandler(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = HomeFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        mViewModel.tracksResponse().observe(this, this::consumeTracksResponse)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getTracks()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun init() {
        mBinding.progressTracks.visibility = View.VISIBLE
        tracksAdapter = TrackAdapter(mutableListOf(), this)
        mBinding.recyclerPopularTracks.adapter = tracksAdapter
    }

    override fun onTrackClicked(track: Track) {
        findNavController().navigate(HomeFragmentDirections.toTrackDetailFragment(track))
    }

    private fun consumeTracksResponse(response: ApiResponse<Array<Track>>) {
        var progressVisibility = View.GONE
        when (response.status) {
            ApiResponse.Status.IN_PROCESS -> progressVisibility = View.VISIBLE
            ApiResponse.Status.ERROR -> apiErrorHandler?.handleError(response)
            ApiResponse.Status.SUCCESS -> tracksAdapter?.setDataList(response.data)
        }
        mBinding.progressTracks.visibility = progressVisibility
    }
}