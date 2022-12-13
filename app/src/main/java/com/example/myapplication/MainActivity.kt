package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.models.VideoDetail
import com.example.myapplication.viewModel.VideoFragmentViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sessionManager:SessionManager by lazy {
        SessionManager(baseContext)
    }

    val token:String? by lazy{
        sessionManager.fetchAuthToken()
    }

    private lateinit var video:VideoFragmentViewModel
    lateinit var navController: NavController
    private lateinit var bottomNavigationView:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = findViewById<MaterialToolbar>(R.id.appbar)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val navHost= supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        bottomNavigationView=findViewById(R.id.bottomNavaBar)
        navController=navHost.findNavController()
        bottomNavigationView.setupWithNavController(navController)
    }

    fun toVideoRecycleViewFragment(videoDetail: VideoDetail) {
        video = ViewModelProvider(this)[VideoFragmentViewModel::class.java]
        video.setVideo(videoDetail)
        navController.navigate(R.id.videoFragment)
    }

    fun hideActionBar(){
        appbar.visibility= View.GONE
    }

    fun showActionBar(){
        appbar.visibility= View.VISIBLE
    }

}