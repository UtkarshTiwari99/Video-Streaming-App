package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_upload.*

class uploadFragment : Fragment() {

    val sessionManager:SessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onResume() {
        super.onResume()
        upload.setOnClickListener {
            startActivity(Intent(context,UploadActivity::class.java))
        }
    }
}