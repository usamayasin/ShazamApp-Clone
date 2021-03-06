package com.afiniti.kiosk.shazamtask.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.databinding.TrackDetailFragmentBinding
import com.afiniti.kiosk.shazamtask.model.Chart
import com.afiniti.kiosk.shazamtask.model.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TrackDetailFragment : Fragment() {

    private lateinit var mBinding: TrackDetailFragmentBinding
    private val navArgs: TrackDetailFragmentArgs by navArgs()
    private lateinit var track: Track

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = TrackDetailFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        track = navArgs.track
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind(track)
    }

    private fun bind(item: Track) {
        mBinding.data = item
        Glide.with(requireContext())
            .load(track?.stores?.apple?.coverarturl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            ).into(mBinding.ivTrackImage)
    }
}