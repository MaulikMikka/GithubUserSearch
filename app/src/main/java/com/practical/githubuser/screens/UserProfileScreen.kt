package com.practical.githubuser.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practical.githubuser.ContentReuse
import com.practical.githubuser.ContentReuse.ProgressIndicator
import com.practical.githubuser.R
import com.practical.githubuser.util.SearchWidgetState
import com.practical.githubuser.viewModel.UserProfileViewModel

var username = ""
val userProfileViewModel = UserProfileViewModel()


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navigateFollower: (String) -> Unit
) {
    val searchWidgetState by userProfileViewModel.searchWidgetState
    val searchTextState by userProfileViewModel.searchTextState
    val showProgress by remember { userProfileViewModel.showProgress }



    Scaffold(topBar = {
        MainAppBar(searchWidgetState = searchWidgetState,
            searchTextState = searchTextState,
            onTextChange = {
                userProfileViewModel.showProgress.value = true
                userProfileViewModel.updateSearchTextState(newValue = it)
                userProfileViewModel.getUserData(it)
            },
            onCloseClicked = {
                userProfileViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
            },
            onSearchClicked = {

                Log.d("Searched Text", it)
            },
            onSearchTriggered = {
                userProfileViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
            })


    }) {


        if (userProfileViewModel.errorMessage.isEmpty() && userProfileViewModel.searchWidgetState.value != SearchWidgetState.CLOSED) {
            Box {
                if (showProgress) {
                    ProgressIndicator(it)
                } else {
                    ContentReuse.MainContent(
                        navigateFollower, userProfileViewModel.userDataList, it
                    )
                }


            }
        } else if (userProfileViewModel.searchWidgetState.value == SearchWidgetState.CLOSED) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(
                        color = Color.Black.copy(alpha = 0.9f),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.githubicon),
                    contentDescription = "",
                    modifier = Modifier.size(96.dp),
                    alignment = Alignment.Center

                )

            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), Alignment.Center
            ) {

                Text(
                    stringResource(R.string.not_found),
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(50.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center

                )
            }
        }


    }


}


@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }

        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}


@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(backgroundColor = Color.Black, title = {
        Text(
            text = stringResource(R.string.github_username), color = Color.White

        )
    }, actions = {
        IconButton(onClick = { onSearchClicked() }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_icon),
                tint = Color.White
            )
        }
    })
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.Black
    ) {
        TextField(modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
            onTextChange(it)
        }, placeholder = {
            androidx.compose.material.Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = stringResource(R.string.search_here),
                color = Color.White
            )
        }, textStyle = TextStyle(
            fontSize = MaterialTheme.typography.subtitle1.fontSize, color = Color.White
        ), singleLine = true, leadingIcon = {
            IconButton(modifier = Modifier.alpha(ContentAlpha.medium), onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = Color.White
                )
            }
        }, trailingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    onTextChange("")
                } else {
                    onCloseClicked()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = Color.White
                )
            }
        }, keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ), keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(text)
        }), colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
        )
        )
    }


}


