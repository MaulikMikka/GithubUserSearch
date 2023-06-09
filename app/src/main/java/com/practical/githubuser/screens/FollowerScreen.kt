package com.practical.githubuser.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.practical.githubuser.ContentReuse.ProgressIndicator
import com.practical.githubuser.viewModel.FollowersViewModel

val followersViewModel = FollowersViewModel()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowerScreen(
    username: String,
    navigateUserDetail: (String) -> Unit, popBackStack: () -> Unit
) {
    val showProgress by remember { followersViewModel.showProgress }
    LaunchedEffect(Unit) {
        followersViewModel.showProgress.value=true
        followersViewModel.getFollowers(username)
    }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black),
            navigationIcon = {
                IconButton(onClick = { popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Search Icon",
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

        if (showProgress) {
            ProgressIndicator(it)
        } else {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black)
                        .padding(16.dp)
                ) {

                    LazyColumn(modifier = Modifier.fillMaxSize()) {


                        items(followersViewModel.userDataList) { userdata ->

                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 8.dp, 16.dp, 8.dp)
                                        .clickable {
                                            userdata.login?.let { it1 ->
                                                navigateUserDetail(it1)
                                            }
                                        },
                                ) {

                                    Row {

                                        Card(
                                            modifier = Modifier
                                                .size(64.dp)
                                                .fillMaxWidth(),
                                            shape = CircleShape,

                                            ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(userdata.avatar_url),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxWidth(),

                                                )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.CenterVertically),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                "${userdata.login}",
                                                color = Color.White,
                                                fontSize = 20.sp,
                                                modifier = Modifier
                                                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                                                textAlign = TextAlign.Center
                                            )


                                        }


                                    }


                                }


                            }

                            Spacer(modifier = Modifier.width(16.dp))


                        }
                    }
                }

            }
        }


    }


}