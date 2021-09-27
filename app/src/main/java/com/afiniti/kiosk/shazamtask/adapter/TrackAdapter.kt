package com.afiniti.kiosk.shazamtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.databinding.TrackItemLayoutBinding
import com.afiniti.kiosk.shazamtask.model.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

class TrackAdapter(
    private var context: Context,
    listModels: List<Track>,
    private val listener: TrackClickListener
) :
    RecyclerView.Adapter<TrackAdapter.ImagesViewHolder>() {

    interface TrackClickListener {
        fun onTrackClicked(Track: Track)
    }

    var listModels: List<Track>

    init {
        this.listModels = listModels as ArrayList<Track>
    }

    fun setDataList(listModels: Array<Track>) {
        this.listModels = listModels.toList() as ArrayList<Track>
        notifyDataSetChanged()
    }

    fun getDataListSize():Int {
        return this.listModels.size
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesViewHolder {
        val trackItemLayoutBinding = TrackItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )

        return ImagesViewHolder(trackItemLayoutBinding, listModels, context)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val data: Track = listModels[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    inner class ImagesViewHolder(
        private val itemViewDataBinding: TrackItemLayoutBinding,
        imagesList: List<Track>,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(itemViewDataBinding.root), View.OnClickListener {

        var dataList: List<Track> = imagesList

        override fun onClick(v: View) {
            if (adapterPosition < dataList.size) listener.onTrackClicked(dataList[adapterPosition])
        }

        init {
            try {
                itemViewDataBinding.root.setOnClickListener(this)
            } catch (e: Exception) {
                Toast.makeText(context, "Error in View Holder " + e.message, Toast.LENGTH_LONG)
                    .show()
            }
        }

        fun bind(item: Track) {
            itemViewDataBinding.data = item
            Glide.with(itemViewDataBinding.root.context)
                .load(item.images.default)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                ).into(itemViewDataBinding.ivTrackImage)
        }
    }
}