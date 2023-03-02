package at.aau.edu.lechl.se.singlephase.api

interface ApiClient {
    suspend fun sendRegistrationNumber(registrationNumber: String): Result<String>
}