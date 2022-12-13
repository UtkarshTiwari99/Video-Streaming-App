package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.api.Constants
import com.example.myapplication.viewModel.VideoFragmentViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_video.*


class videoFragment : Fragment() {

    private val BASEURL: String = Constants.BASEURL

    private val video by lazy {
        ViewModelProvider(requireActivity())[VideoFragmentViewModel::class.java].video.value
    }

    var player: ExoPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        (activity as MainActivity).appbar.visibility=View.GONE
        player = ExoPlayer.Builder(requireContext()).build()
        player_view.player = player
        val mediaItem: MediaItem =
            MediaItem.fromUri(Uri.parse("${BASEURL}/video/${video?.url}"))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
        videoTitle.text= video?.title
        channelName.text=video?.channelName
    }

    override fun onPause() {
        (activity as MainActivity).showActionBar()
        player?.pause()
        player=null
        super.onPause()
    }

}