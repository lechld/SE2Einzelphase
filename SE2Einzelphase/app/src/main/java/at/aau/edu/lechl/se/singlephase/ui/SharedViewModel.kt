package at.aau.edu.lechl.se.singlephase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _state: MutableLiveData<MainState> = MutableLiveData(MainState.RegistrationInput)
    val state: LiveData<MainState> = _state

    fun moveTo(state: MainState) {
        _state.postValue(state)
    }
}