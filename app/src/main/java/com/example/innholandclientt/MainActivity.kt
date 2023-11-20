package com.example.innholandclientt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innholandclientt.APIClient.innoholandClient
import com.example.innholandclientt.ui.theme.InnholandClienttTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InnholandClienttTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting("Welcome to Newsreader app, please login")

                        // StyledEmailField with email value
                        StyledEmailField()

                        // StyledPasswordField with password value
                        StyledPasswordField()
                        // Smaller Button
                        FilledButtonExample {
                            // Pass email and password to the login function
                            LoginBtnClicked()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun StyledEmailField() {
    val email by remember {
        mutableStateOf(TextFieldValue()) }
    TextField(
        value = email.value,
        onValueChange = { email.value = it },
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
    var password by remember {
        mutableStateOf(TextFieldValue()) }
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

fun LoginBtnClicked() {
    // Use email and password for login logic
    val tmp_InnoholandAPI = innoholandClient()
    tmp_InnoholandAPI.LoginWith()
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InnholandClienttTheme {
        Greeting("Android")
    }
}