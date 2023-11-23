package com.example.innholandclientt.APIClient

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class innoholandClient {

    var Auth_Token = ""

fun Login(email: String, password: String) {

    // Create JSON using JSONObject
    val jsonObject = JSONObject()
    jsonObject.put("UserName", email)
    jsonObject.put("Password", password)

    // Convert JSONObject to String
    val jsonObjectString = jsonObject.toString()

    try {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://inhollandbackend.azurewebsites.net/api/Users/login")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.setRequestProperty("Content-Type", "application/json") // The format of the content we're sending to the server
            httpURLConnection.setRequestProperty("Accept", "application/json") // The format of response we want to get from the server
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true

            // Send the JSON we created
            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
            outputStreamWriter.write(jsonObjectString)
            outputStreamWriter.flush()

            // Check if the connection is successful
            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = httpURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                withContext(Dispatchers.Main) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(JsonParser.parseString(response))
                    Log.d("Pretty Printed JSON :", prettyJson)

                }
            } else {
                Log.e("HTTPURLCONNECTION_ERROR", responseCode.toString())
            }
        }
    }
    catch (e: Exception) {
        e.printStackTrace()
    }

}

    fun LoginWith(email: String, password: String): Deferred<Boolean> = GlobalScope.async(Dispatchers.IO)
    {
        // Specify the API endpoint
        val apiUrl = "https://inhollandbackend.azurewebsites.net/api/Users/login"

        // JSON request body
        val requestBody = """{"UserName": "$email", "Password": "$password"}"""

        var tmp_Token = ""
        try {
            // Create URL object
            val url = URL(apiUrl)

            // Open connection
            val connection = url.openConnection() as HttpURLConnection

            // Set request method to POST
            connection.requestMethod = "POST"

            // Enable input/output streams
            connection.doInput = true
            connection.doOutput = true

            // Set content type
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

            // Write the request body to the output stream
            val outputStream: OutputStream = BufferedOutputStream(connection.outputStream)
            val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
            outputStreamWriter.write(requestBody)
            outputStreamWriter.flush()
            outputStreamWriter.close()

            // Get the response code
            val responseCode = connection.responseCode
            println("Response Code: $responseCode")

            if (responseCode == 400)
            {
                tmp_Token = ""
                false
            }

            // Read the response (optional)
            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Response: $response")

            tmp_Token = response
            Auth_Token = tmp_Token.toString()


            // Close the connection
            connection.disconnect()

            true

        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (tmp_Token.isEmpty()) {
            false
        } else {
            Auth_Token = tmp_Token.toString()
            true
        }
    }

    fun RegisterWith(email: String, password: String): Deferred<RegistrationResult> = GlobalScope.async(Dispatchers.IO) {
        // Specify the API endpoint for registration
        val apiUrl = "https://inhollandbackend.azurewebsites.net/api/Users/register"

        // JSON request body for registration
        val requestBody = """{"UserName": "$email", "Password": "$password"}"""

        try {
            // Create URL object
            val url = URL(apiUrl)

            // Open connection
            val connection = url.openConnection() as HttpURLConnection

            // Set request method to POST
            connection.requestMethod = "POST"

            // Enable input/output streams
            connection.doInput = true
            connection.doOutput = true

            // Set content type
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

            // Write the request body to the output stream
            val outputStream: OutputStream = BufferedOutputStream(connection.outputStream)
            val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
            outputStreamWriter.write(requestBody)
            outputStreamWriter.flush()
            outputStreamWriter.close()

            // Get the response code
            val responseCode = connection.responseCode
            println("Response Code: $responseCode")

            // Read the response (optional)
            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }
            println("Response: $response")

            // Close the connection
            connection.disconnect()

            // Check the response code and create a RegistrationResult accordingly
            if (responseCode == 200) {
                RegistrationResult(true, "User registered")
            } else {
                RegistrationResult(false, "Registration failed")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            RegistrationResult(false, "Registration failed")
        }
    }

    data class RegistrationResult(val success: Boolean, val message: String)

}

