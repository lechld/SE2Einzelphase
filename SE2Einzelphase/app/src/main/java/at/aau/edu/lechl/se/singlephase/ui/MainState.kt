package at.aau.edu.lechl.se.singlephase.ui

sealed class MainState(val index: Int) {
    object RegistrationInput : MainState(0)
    data class ServiceResult(val registrationInput: String) : MainState(1)
    data class PrimeNumber(val serviceResult: String) : MainState(2)
}