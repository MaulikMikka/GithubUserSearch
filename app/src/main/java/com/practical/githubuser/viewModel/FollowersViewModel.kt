package com.practical.githubuser.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practical.githubuser.API.Network
import com.practical.githubuser.data.UserData
import kotlinx.coroutines.launch

class FollowersViewModel : ViewModel() {
    private val _userData = mutableStateListOf<UserData>()
     var apicalled:Boolean by mutableStateOf(false)
    var errorMessage: String by mutableStateOf("")
    val showProgress = mutableStateOf(false)
    val userDataList: List<UserData>
        get() = _userData


    fun getFollowers(username: String) {
        viewModelScope.launch {
            val apiService = Network.getInstance()
            try {
                showProgress.value=false
                val data = apiService.getFollowers(username.replace("\n", ""))
                _userData.clear()
                _userData.addAll(data)
                errorMessage = ""
                apicalled=true
            } catch (e: Exception) {
                showProgress.value=false
                apicalled=true
                _userData.clear()
                errorMessage = e.message.toString()
            }
        }
    }


}