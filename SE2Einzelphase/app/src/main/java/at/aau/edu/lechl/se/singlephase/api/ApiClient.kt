package at.aau.edu.lechl.se.singlephase.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.ObjectOutputStream
import java.net.Socket

interface ApiClient {
    suspend fun sendRegistrationNumber(registrationNumber: String): Result<String>
}

private const val HOST_NAME = "se2-isys.aau.at"
private const val PORT = 53212

class ApiClientImpl : ApiClient {

    override suspend fun sendRegistrationNumber(
        registrationNumber: String
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            Socket(HOST_NAME, PORT).use { socket ->
                val output = ObjectOutputStream(socket.getOutputStream())
                val input = BufferedInputStream(socket.getInputStream())

                output.writeUTF(registrationNumber)
                output.flush()

                val response = input.bufferedReader().readLine()

                Result.success(response)
            }
        } catch (error: Throwable) {
            Result.failure(error)
        }
    }
}