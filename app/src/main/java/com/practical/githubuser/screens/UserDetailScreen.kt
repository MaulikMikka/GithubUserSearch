package com.practical.githubuser.screens

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.practical.githubuser.ContentReuse
import com.practical.githubuser.R
import com.practical.githubuser.viewModel.UserDetailViewModel

val vm = UserDetailViewModel()
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(username: String, popBackStack: () -> Unit) {

    LaunchedEffect(Unit) {
        vm.getUserData(username)
    }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
            navigationIcon = {
                IconButton(onClick = {
                    popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = Color.White
                    )
                }


            },

            title = {
                Text(
                    text = username, color = Color.White
                )
            },
        )


    }) {
        if (vm.errorMessage.isEmpty()) {
            ContentReuse.MainContent(null,vm.userDataList,it)

        }
    }

}