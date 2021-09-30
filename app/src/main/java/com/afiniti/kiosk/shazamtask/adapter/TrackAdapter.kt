package com.afiniti.kiosk.shazamtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.databinding.TrackItemLayoutBinding
import com.afiniti.kiosk.shazamtask.model.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

class TrackAdapter(
    listModels: List<Track>,
    private val listener: TrackClickListener
) :
    RecyclerView.Adapter<TrackAdapter.ImagesViewHolder>() {

    interface TrackClickListener {
        fun onTrackClicked(track: Track)
    }

    var listModels: List<Track>

    init {
        this.listModels = listModels as ArrayList<Track>
    }

    fun setDataList(listModels: Array<Track>) {
        this.listModels = listModels.toList() as ArrayList<Track>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesViewHolder {
        val trackItemLayoutBinding = TrackItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ImagesViewHolder(trackItemLayoutBinding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val data: Track = listModels[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    inner class ImagesViewHolder(
        private val itemViewDataBinding: TrackItemLayoutBinding) :
        RecyclerView.ViewHolder(itemViewDataBinding.root){

        fun bind(item: Track) {
            itemViewDataBinding.data = item
            Glide.with(itemViewDataBinding.root.context)
                .load(item.images.default)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                ).into(itemViewDataBinding.ivTrackImage)

            itemViewDataBinding.root.setOnClickListener{
                listener.onTrackClicked(item)
            }
        }
    }
}