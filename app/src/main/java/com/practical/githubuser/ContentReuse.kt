package com.practical.githubuser

import android.graphics.Paint.Style
import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.practical.githubuser.data.UserData

object ContentReuse {

    @Composable
    fun MainContent(
        navigateFollower: ((String) -> Unit)?,
        userData: List<UserData>,
        paddingValues: PaddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.Black.copy(alpha = 0.9f))
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(userData) { userdata ->
                    Column {
                        Row(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(0.dp, 0.dp, 16.dp, 0.dp), Alignment.Center
                                ) {

                                    Row {

                                        Card(
                                            modifier = Modifier
                                                .size(96.dp)
                                                .fillMaxWidth(),
                                            shape = CircleShape,

                                            ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(userdata.avatar_url),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxWidth(),
                                                alignment = Alignment.Center

                                            )
                                        }

                                        Text(
                                            "${userdata.followers} \n ${stringResource(R.string.followers)}",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .clickable {
                                                    if (userdata.followers.toInt() > 0) {
                                                        userdata.login?.let { it1 ->
                                                            navigateFollower?.let {
                                                                it(
                                                                    it1.replace(
                                                                        "\n",
                                                                        ""
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                },
                                            textAlign = TextAlign.Center

                                        )
                                        Text(
                                            "${userdata.following} \n ${stringResource(R.string.following)},",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )


                                    }


                                }

                                Text(
                                    "${userdata.login}",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally)
                                )

                                userdata.bio?.let {
                                    Text(
                                        "${userdata.bio}", color = Color.White,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxWidth()
                                            .align(Alignment.CenterHorizontally),

                                        )
                                }


                            }

                            Spacer(modifier = Modifier.width(16.dp))

                        }
                        Divider()
                    }
                }
            }
        }
    }

    @Composable
    fun ProgressIndicator(paddingValues: PaddingValues) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.Black.copy(alpha = 0.9f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 56.dp),
                color = Color.Yellow
            )
        }
    }


}