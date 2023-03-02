package at.aau.edu.lechl.se.singlephase.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MockApiClient : ApiClient {

    override suspend fun sendRegistrationNumber(
        registrationNumber: String
    ): Result<String> = withContext(Dispatchers.IO) {
        delay(1000L) // fake loading

        return@withContext Result.success("dummy response")
    }
}