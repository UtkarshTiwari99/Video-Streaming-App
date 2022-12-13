package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.api.Constants
import com.example.myapplication.models.VideoDetail
import kotlinx.android.synthetic.main.customvideoview.view.*

class CustomVideoAdapter : RecyclerView.Adapter<CustomVideoAdapter.VideoViewHolder>() {

    private val BASEURL: String = Constants.BASEURL

    var videos = mutableListOf<VideoDetail>()

    fun setVideoList(movies: List<VideoDetail>) {
        this.videos = movies.toMutableList()
        notifyDataSetChanged()
    }

    inner class VideoViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
            val image=itemView.thumbnail
        val title =itemView.title
        val channelName=itemView.channelName
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return VideoViewHolder(layoutInflater.inflate(R.layout.customvideoview,parent,false))
    }

    override fun getItemCount()= videos.size


    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        if (holder.itemView.context != null) {
            Glide.with(holder.itemView.context)
                .load("${BASEURL}/thumbnail/"+videos[position].thumbnailUrl)
                .into(holder.image)
        }
        holder.title.text= videos[position].title
        holder.channelName.text=videos[position].channelName
        holder.itemView.setOnClickListener{
            (holder.itemView.context as MainActivity).toVideoRecycleViewFragment(videos[position])}
    }


    fun notifyDataChange(progressBar:ProgressBar,videoRecyclerView: RecyclerView){
        progressBar.visibility=View.VISIBLE
        videoRecyclerView.visibility=View.GONE
        notifyDataSetChanged()
        progressBar.visibility=View.GONE
        videoRecyclerView.visibility=View.VISIBLE
    }

}