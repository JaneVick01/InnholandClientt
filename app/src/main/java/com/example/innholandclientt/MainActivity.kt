package com.example.innholandclientt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.innholandclientt.APIClient.innoholandClient
import com.example.innholandclientt.ui.theme.InnholandClienttTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()

            }
        }
    }

@Composable
fun WelcomeSTR() {
    Text(stringResource(R.string.welcome))
}

@Composable
fun RegisterSTR() {
    Text(stringResource(R.string.register))
}

@Composable
fun LoginSTR() {
    Text(stringResource(R.string.login))
}

@Composable
fun PasswordSTR() {
    Text(stringResource(R.string.password))
}

@Composable
fun EmailSTR() {
    Text(stringResource(R.string.email))
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Login,
        NavigationItem.Register // Add the new item
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_700),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
//        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController)},
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
        backgroundColor = colorResource(R.color.teal_700) // Set background color to avoid the white flashing when you switch between screens
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun StyledEmailField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { EmailSTR()},
        maxLines = 1,
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun StyledPasswordField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = {PasswordSTR()},
        maxLines = 1,
        textStyle = TextStyle(color = Color.Black),
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}



fun LoginBtnClicked(email: String, password: String) {
    // Use email and password for login logic
    val tmp_InnoholandAPI = innoholandClient()
    tmp_InnoholandAPI.LoginWith(email, password)
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
    LoginSTR()
    }
}

@Composable
fun RegisterButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
    RegisterSTR()
    }
}

fun RegisterBtnClicked(email: String, password: String) {
    val tmp_InnoholandAPI = innoholandClient()

    runBlocking {
        val result = tmp_InnoholandAPI.RegisterWith(email, password)

        val response = result.await()

        println("Received response: $response")
    }

}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Login.route) {
        composable(NavigationItem.Login.route) {
            LoginScreen()
        }
        composable(NavigationItem.Register.route) {
            RegisterScreen()
        }
    }
}


@Composable
fun ShowMessageBox(message: String) {
    var showDialog by remember { mutableStateOf(true) }

    // AlertDialog to show the message box
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Message Box") },
            text = { Text(message) },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Handle the button click (if needed)
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

//    @Composable
//    fun TopBar() {
//        TopAppBar(
//            title = { Text(text = stringR)}
//            backgroundColor = colorResource(id = R.color.purple_700),
//            contentColor = Color.White
//        )
//    }

    //    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        InnholandClienttTheme {
            Greeting("Android")
        }
    }


//            @Composable
//            fun RegisterScreen() {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(color = Color.Gray),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Welcome to the Register Screen!", fontSize = 20.sp, color = Color.White)
//                }
//            }


//            @Composable
//            fun RegisterScreenPreview() {
//                RegisterScreen()
//            }

}
