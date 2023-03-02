package at.aau.edu.lechl.se.singlephase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.aau.edu.lechl.se.singlephase.ui.primenumber.PrimeNumberViewModel
import at.aau.edu.lechl.se.singlephase.ui.serviceresult.ServiceResultViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST") // Can safely ignore that warning, we check isAssignableFrom
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SharedViewModel::class.java) -> SharedViewModel() as T
            modelClass.isAssignableFrom(PrimeNumberViewModel::class.java) -> PrimeNumberViewModel() as T
            modelClass.isAssignableFrom(ServiceResultViewModel::class.java) -> ServiceResultViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class. Maybe forgot to register it in ViewModelFactory?")
        }
    }
}