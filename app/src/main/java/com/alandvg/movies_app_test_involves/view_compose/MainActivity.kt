package com.alandvg.movies_app_test_involves.view_compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.alandvg.movies_app_test_involves.R
import com.alandvg.movies_app_test_involves.view_compose.ui.theme.MoviesapptestinvolvesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesapptestinvolvesTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable("main") { MainScreen() }
                }
            }


        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(DrawerValue.Closed)
    )
    val items = listOf(
        TabItem.ListUpComingMovies,
        TabItem.ListPopularMovies,
        TabItem.ListTopRatedMovies,
        TabItem.ListFavoritedMovies,
        TabItem.ListSearchMovies
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = { Text("TopAppBar") }) },
        content = { NavHost(
            navController,
            startDestination = TabItem.ListUpComingMovies.route
        ) {
            composable(TabItem.ListUpComingMovies.route) { ListScreen(1) }
            composable(TabItem.ListPopularMovies.route) { ListScreen(2) }
            composable(TabItem.ListTopRatedMovies.route) {  ListScreen(3) }
            composable(TabItem.ListFavoritedMovies.route) { ListScreen(4) }
            composable(TabItem.ListSearchMovies.route) { ListScreen(5) }
        } },
        bottomBar = { BottomNavigation {
            val navBackStackEntry by
            navController.currentBackStackEntryAsState()
            val currentRoute =
                navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            items.forEach { tabItem ->
                BottomTab(
                    navController,
                    tabItem,
                    currentRoute == tabItem.route
                )
            }
        }},
    )

}

@Composable
private fun BottomTab(
    navController: NavHostController,
    screen: TabItem,
    selected: Boolean
) {
    RowScope.BottomNavigationItem(
        icon = { Icon(painter = painterResource(screen.icon), contentDescription = "" ) },
        label = { stringResource(id = screen.title) },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo = navController.graph.startDestination
                launchSingleTop = true
            }
        }
    )
}


@Composable
fun ListScreen(param: Int){
    Text("Teste $param")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    MoviesapptestinvolvesTheme {
        MainScreen()
    }
}


sealed class TabItem(
    val route: String,
    val title: Int,
    val icon: Int
) {
    object ListUpComingMovies : TabItem(
        "listUpComing", R.string.lbl_fragment_upcoming, R.drawable.ic_new_releases_24dp
    )
    object ListTopRatedMovies : TabItem(
        "lisTopRated", R.string.lbl_fragment_top_rated, R.drawable.ic_trending_up_24dp
    )
    object ListPopularMovies : TabItem(
        "listPopularMovies", R.string.lbl_fragment_popular, R.drawable.ic_stars_top_rating_24px
    )
    object ListFavoritedMovies : TabItem(
        "listFavoritedMovies", R.string.lbl_fragment_saved_movies, R.drawable.ic_favorite_black_24dp
    )
    object ListSearchMovies : TabItem(
        "listSearchMovies", R.string.lbl_search_movies, R.drawable.ic_search_24dp
    )
}