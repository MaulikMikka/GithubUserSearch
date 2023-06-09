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

class UserDetailViewModel : ViewModel() {
    private val _userData = mutableStateListOf<UserData>()
    val userDataList: List<UserData>
        get() = _userData
    var errorMessage: String by mutableStateOf("")
    fun getUserData(username: String) {
        viewModelScope.launch {
            val apiService = Network.getInstance()
            try {
                val data = apiService.getUserData(username.replace("\n", ""))
                _userData.clear()
                _userData.add(data)
                errorMessage = ""
            } catch (e: Exception) {
                _userData.clear()
                errorMessage = e.message.toString()
            }
        }
    }
}