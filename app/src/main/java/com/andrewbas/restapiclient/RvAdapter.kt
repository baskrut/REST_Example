package com.andrewbas.restapiclient

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RvAdapter : RecyclerView.Adapter<RvAdapter.TvViewHolder>(){

        lateinit var videoNames: List<VideoName>

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            return TvViewHolder(inflater, parent)

        }

        override fun onBindViewHolder(holder: TvViewHolder, position: Int) {

            val videoName: VideoName = videoNames[position]
            holder.bind(videoName)
        }

        fun setItems(videoName: List<VideoName>){
            videoNames = videoName
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = videoNames.size

        class TvViewHolder(inflater: LayoutInflater, parent: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)){

            var tvVideoName: TextView = itemView.findViewById(R.id.videoName)

            fun bind(videoName: VideoName){

                tvVideoName.text = videoName.videoName

            }
        }


}
