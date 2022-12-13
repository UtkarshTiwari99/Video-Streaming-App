package com.example.myapplication

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.models.VideoDetail
import com.example.myapplication.repository.Response
import com.example.myapplication.repository.VideoRepository
import com.example.myapplication.viewModel.MainViewModelFactory
import com.example.myapplication.viewModel.VideoViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){

    private var AUTHTOKEN:String?=""
    private lateinit var adapter:CustomVideoAdapter
    private lateinit var data:LiveData<List<VideoDetail>>
    private lateinit var  videoViewModel:VideoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loadToken()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun loadToken(){
        AUTHTOKEN=(activity as MainActivity).token
    }

    override fun onStart() {
        super.onStart()
        val videoService= RetrofitInstance.getRetrofitInstance(requireActivity()).create(ApiInterface::class.java)
        videoViewModel= ViewModelProvider(requireActivity(),MainViewModelFactory(VideoRepository(videoService))).get()
        adapter=CustomVideoAdapter()
        videoRecycleView.adapter = adapter
        videoViewModel.listOfVideos.observe(requireActivity()) {
            when(it){
                is Response.Loading -> {
                    progress_bar.visibility=View.VISIBLE
                }
                is Response.Success -> {
                    progress_bar.visibility=View.GONE
                    videoRecycleView.visibility=View.VISIBLE
                    Log.d("GokuSon", "onRes: ${it.data}")
                    it.data?.let { it1 -> adapter.setVideoList(it1) }
                }
                is Response.Error -> {
                    Log.d("GokuSonError", "onRes: ${it.error}")
                }
            }
        }
        videoViewModel.getAllVideos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoRecycleView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        videoRecycleView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
    }

}