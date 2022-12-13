package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.databinding.ActivityUploadBinding
import com.example.myapplication.repository.UploadRepository
import com.example.myapplication.viewModel.MainViewModelFactory
import com.example.myapplication.viewModel.VideoUploadViewModel
import com.google.android.exoplayer2.util.Log
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UploadActivity:AppCompatActivity() {

    lateinit var activityUploadBinding: ActivityUploadBinding
    lateinit var videoUploadViewModel: VideoUploadViewModel
    val uploadService by lazy {
        RetrofitInstance.getRetrofitInstance(this).create(ApiInterface::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar = findViewById<MaterialToolbar>(R.id.appbar)
        setSupportActionBar(toolbar)
            RetrofitInstance.getRetrofitInstance(this).create(ApiInterface::class.java)
        videoUploadViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(UploadRepository(uploadService))
        ).get()
        activityUploadBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload)
        activityUploadBinding.lifecycleOwner = this
        activityUploadBinding.videoUploadViewModel = videoUploadViewModel
        videoUploadViewModel.response.observe(this){
            when(it){
                is com.example.myapplication.repository.Response.Loading -> {
                    activityUploadBinding.uploadProgress.visibility=View.VISIBLE
                }
                is com.example.myapplication.repository.Response.Success -> {
                    if(it.data.equals("success")){
                        activityUploadBinding.uploadProgress.visibility=View.GONE
                        Snackbar.make(activityUploadBinding.root, "Uploaded ${it.data}fully", Snackbar.LENGTH_SHORT).show()
                        startActivity(Intent(baseContext, MainActivity::class.java))
                    }
                }
                is com.example.myapplication.repository.Response.Error -> {
                    activityUploadBinding.uploadProgress.visibility=View.GONE
                    Snackbar.make(activityUploadBinding.root, "Something went wrong ${it.error}", Snackbar.LENGTH_SHORT).show()
                    return@observe
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        video.setOnClickListener {
            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "video/mp4"
            startActivityForResult(pickIntent, 129)
        }
        thumbnail.setOnClickListener {
            val pickIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/jpg"
            startActivityForResult(pickIntent, 128)
        }
        upload.setOnClickListener {
            uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 129) {
            data?.data?.let { uri ->
                saveVideo(uri)
            }
        }
        else if(requestCode==128){
            data?.data?.let { uri ->
                saveThumbnail(uri)
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data)
    }

    private fun saveThumbnail(uri: Uri) {
        activityUploadBinding.images.setImageURI(uri)
        videoUploadViewModel.thumbnail.value=uri
    }

    private fun saveVideo(uri: Uri) {
        videoUploadViewModel.video.value=uri
    }

    private fun uploadFile() {
        lifecycleScope.launch {
            val videoStream = contentResolver.openInputStream(videoUploadViewModel.video.value!!) ?: return@launch
            val videoRequest = RequestBody.create(MediaType.parse("video/mp4"), videoStream.readBytes()) // read all bytes using kotlin extension
            val video = MultipartBody.Part.createFormData(
                "video",
                "video.mp4",
                videoRequest
            )
            val thumbnailStream = contentResolver.openInputStream(videoUploadViewModel.thumbnail.value!!) ?: return@launch
            val  thumbnailRequest = RequestBody.create(MediaType.parse("image/jpg"), thumbnailStream.readBytes()) // read all bytes using kotlin extension
            val thumbnail = MultipartBody.Part.createFormData(
                "thumbnail",
                "thumbnail.jpg",
                thumbnailRequest
            )
            val title= RequestBody.create(
                MediaType.parse("text/plain"),
                videoUploadViewModel.title.value
            )

            val description= RequestBody.create(
                MediaType.parse("text/plain"),
                videoUploadViewModel.description.value
            )
            try {
                Log.e("OnRes", videoUploadViewModel.video.value!!.path+" "+ (videoUploadViewModel.thumbnail.value!!.path) +" "+videoUploadViewModel.title.value+" "+videoUploadViewModel.description.value)
                videoUploadViewModel.upload(video,thumbnail,title,description)
            }
            catch (e: Exception) {
                Log.e("OnRes",e.message.toString())
                Snackbar.make(activityUploadBinding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                return@launch
            }
        }
    }
}