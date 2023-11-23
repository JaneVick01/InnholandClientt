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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
            }
        }
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
        label = { Text("Email") },
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
        label = { Text("Password") },
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
fun FilledButtonExample(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text("Login")
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
    fun LoginScreen() {
        InnholandClienttTheme {
            val navController = rememberNavController()
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // Declare variables to hold email and password values
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Greeting("Welcome to Newsreader app, please login")

                    // StyledEmailField with email value
                    StyledEmailField(email) { updatedEmail ->
                        email = updatedEmail
                    }

                    // StyledPasswordField with password value
                    StyledPasswordField(password) { updatedPassword ->
                        password = updatedPassword
                    }

                    // Debugging Text to display email and password TODO: delete
                    Text("Email: $email, Password: $password", color = Color.Gray)

                    // Smaller Button
                    FilledButtonExample {
                        // Pass email and password to the login function
                        LoginBtnClicked(email, password)
                    }

                    // Clickable text to navigate to the registration screen
//                    Text("Haven't registered yet?")
//                    Spacer(modifier = Modifier.height(16.dp))
//                    ClickableText(
//                        text = AnnotatedString("Click here to Register"),
//                        onClick = {
//                            // Handle click on the clickable text
//                            navController.navigate(NavigationItem.Register.route)
//                        })


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
        }
    }