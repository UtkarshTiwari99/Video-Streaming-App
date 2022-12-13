package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.LogInRepository
import com.example.myapplication.repository.SignUpRepository
import com.example.myapplication.repository.UploadRepository
import com.example.myapplication.repository.VideoRepository

class MainViewModelFactory<T>(private val repository:T):ViewModelProvider.Factory{
    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        return when(repository){
            is UploadRepository -> VideoUploadViewModel(repository) as V
            is VideoRepository -> VideoViewModel(repository) as V
            is LogInRepository -> LogInViewModel(repository) as V
            is SignUpRepository -> SignUpViewModel(repository) as V
            else -> LogInViewModel(repository as LogInRepository) as V
        }
    }
}