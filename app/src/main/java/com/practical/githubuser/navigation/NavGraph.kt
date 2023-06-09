package com.practical.githubuser.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.practical.githubuser.screens.FollowerScreen
import com.practical.githubuser.screens.UserDetailScreen
import com.practical.githubuser.screens.UserProfileScreen


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoute.UserProfileScreen.path
    ) {
        addUserProfileScreen(navController, this)

        addFollowerScreen(navController, this)

        addUserDetailScreen(navController, this)


    }
}

private fun addUserProfileScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.UserProfileScreen.path) {
        UserProfileScreen( navigateFollower = { username->
            navController.navigate(NavRoute.FollowerScreen.withArgs(username))
            }
        )
    }
}

private fun addFollowerScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.FollowerScreen.withArgsFormat(NavRoute.FollowerScreen.username),  arguments = listOf(
        navArgument(NavRoute.FollowerScreen.username) {
            type = NavType.StringType
        } )){
        navBackStackEntry ->
        val args = navBackStackEntry.arguments
        FollowerScreen(
            username = args?.getString(NavRoute.FollowerScreen.username)!!,
            popBackStack = { navController.popBackStack() },
            navigateUserDetail = { username->

                navController.navigate(NavRoute.UserDetailScreen.withArgs(username))
            }
        )
    }
}


private fun addUserDetailScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = NavRoute.UserDetailScreen.withArgsFormat(NavRoute.UserDetailScreen.username),  arguments = listOf(
        navArgument(NavRoute.UserDetailScreen.username) {
            type = NavType.StringType
        } )){
            navBackStackEntry ->
        val args = navBackStackEntry.arguments
        UserDetailScreen(
            username = args?.getString(NavRoute.UserDetailScreen.username)!!,
            popBackStack = { navController.popBackStack() },

        )
    }
}









