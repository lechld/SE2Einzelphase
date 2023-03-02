package at.aau.edu.lechl.se.singlephase.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import at.aau.edu.lechl.se.singlephase.ui.primenumber.PrimeNumberFragment
import at.aau.edu.lechl.se.singlephase.ui.registrationnumber.RegistrationNumberFragment
import at.aau.edu.lechl.se.singlephase.ui.serviceresult.ServiceResultFragment

class AppAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RegistrationNumberFragment.instance()
            1 -> ServiceResultFragment.instance()
            2 -> PrimeNumberFragment.instance()
            else -> throw IllegalStateException("Something went wrong here bro")
        }
    }
}