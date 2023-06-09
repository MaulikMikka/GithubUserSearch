package com.practical.githubuser.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practical.githubuser.API.Network
import com.practical.githubuser.data.UserData
import com.practical.githubuser.util.SearchWidgetState
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {


    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    val showProgress = mutableStateOf(false)

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    private val _userData = mutableStateListOf<UserData>()
    var errorMessage: String by mutableStateOf("")
    val userDataList: List<UserData>
        get() = _userData


    fun getUserData(username: String) {
       showProgress.value=true
        viewModelScope.launch {
            val apiService = Network.getInstance()
            try {
                val data = apiService.getUserData(username.replace("\n", ""))
                showProgress.value=false
                _userData.clear()
                _userData.add(data)
                errorMessage = ""
            } catch (e: Exception) {
                showProgress.value=false
                _userData.clear()
                errorMessage = e.message.toString()
            }
        }
    }




}