package at.aau.edu.lechl.se.singlephase.ui.serviceresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.aau.edu.lechl.se.singlephase.api.ApiClient
import at.aau.edu.lechl.se.singlephase.api.ApiClientImpl
import at.aau.edu.lechl.se.singlephase.util.Resource
import kotlinx.coroutines.launch

class ServiceResultViewModel(
    private val apiClient: ApiClient = ApiClientImpl()
) : ViewModel() {

    private val _result = MutableLiveData<Resource<String>>(Resource.Loading())
    val result: LiveData<Resource<String>> = _result

    fun setInput(input: String) {
        viewModelScope.launch {
            apiClient.sendRegistrationNumber(input).fold(
                onSuccess = {
                    _result.postValue(Resource.Success(it))
                }, onFailure = {
                    _result.postValue(Resource.Failure(it))
                }
            )
        }
    }
}