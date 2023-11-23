package com.example.innholandclientt


import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.innholandclientt.ui.theme.InnholandClienttTheme

@Composable
fun RegisterScreen() {
    InnholandClienttTheme {
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

                WelcomeSTR()

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
                RegisterButton {
                    // Pass email and password to the login function
                    RegisterBtnClicked(email, password)
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
        }
    }
}
@Composable
fun LoginScreen() {
    InnholandClienttTheme {
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

                WelcomeSTR()

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
                LoginButton {
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
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    RegisterScreen()
//}
//
//@Composable
//fun FavScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFBB86FC))
//            .wrapContentSize(Alignment.Center)
//    ) {
//        Text(
//            text = "Fav View",
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 25.sp
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun FavScreenPreview() {
//    FavScreen()
//}