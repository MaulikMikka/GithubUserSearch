package com.practical.githubuser.navigation

sealed class NavRoute(val path: String) {

    object UserProfileScreen: NavRoute("UserProfileScreen")

    object UserDetailScreen: NavRoute("UserDetailScreen"){
        val username = "username"
    }

    object FollowerScreen: NavRoute("FollowerScreen") {
        val username = "username"
    }

    object Search: NavRoute("search") {
        val query = "query"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}


