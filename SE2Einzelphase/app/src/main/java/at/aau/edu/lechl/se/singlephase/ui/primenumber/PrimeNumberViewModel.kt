package at.aau.edu.lechl.se.singlephase.ui.primenumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.aau.edu.lechl.se.singlephase.util.Resource

class PrimeNumberViewModel(
    private val primeNumberFilter: PrimeNumberFilter = PrimeNumberFilterImpl
) : ViewModel() {

    private val _result = MutableLiveData<Resource<String>>(Resource.Loading())
    val result: LiveData<Resource<String>> = _result

    fun setServiceResult(serviceResult: String) {
        val filtered = primeNumberFilter.filter(serviceResult)

        _result.postValue(Resource.Success(filtered))
    }
}