package at.aau.edu.lechl.se.singlephase

import androidx.lifecycle.*
import at.aau.edu.lechl.se.singlephase.api.ApiClient
import at.aau.edu.lechl.se.singlephase.api.MockApiClient
import at.aau.edu.lechl.se.singlephase.util.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val apiClient: ApiClient,
) : ViewModel() {

    private val _state = MutableLiveData<Resource<String>>()
    val state: LiveData<Resource<String>> = _state

    fun sendInputToServer(input: String) {
        _state.postValue(Resource.Loading())

        viewModelScope.launch {
            val apiResult = apiClient.sendRegistrationNumber(input)

            apiResult.fold(
                onSuccess = {
                    _state.postValue(Resource.Success(it))
                }, onFailure = {
                    _state.postValue(Resource.Failure(it))
                })
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST") // Can safely ignore that warning, we check isAssignableFrom
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(MockApiClient()) as T
            } else throw IllegalStateException("Can't init that viewModel with provided Factory!")
        }
    }
}