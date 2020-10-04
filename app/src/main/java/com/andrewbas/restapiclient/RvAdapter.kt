package com.andrewbas.restapiclient

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter : RecyclerView.Adapter<RvAdapter.TvViewHolder>(){

        lateinit var videoItems: List<VideoItem>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return TvViewHolder(inflater, parent)

        }

        override fun onBindViewHolder(holder: TvViewHolder, position: Int) {

            val videoItem: VideoItem = videoItems[position]
            holder.bind(videoItem)
        }

        fun setItems(videoItem: List<VideoItem>){
            videoItems = videoItem
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = videoItems.size

        class TvViewHolder(inflater: LayoutInflater, parent: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)){

            var tvVideoName: TextView = itemView.findViewById(R.id.videoName)
            var tvVideoDuration: TextView = itemView.findViewById(R.id.videoDuration)

            fun bind(videoItem: VideoItem){

                tvVideoName.text = videoItem.videoName
                tvVideoDuration.text = videoItem.videoDuration

            }
        }


}
