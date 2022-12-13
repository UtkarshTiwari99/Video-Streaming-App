package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class searchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideActionBar()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showActionBar()
    }
}