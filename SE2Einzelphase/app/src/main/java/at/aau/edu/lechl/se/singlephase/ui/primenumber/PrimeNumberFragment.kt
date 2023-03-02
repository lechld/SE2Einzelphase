package at.aau.edu.lechl.se.singlephase.ui.primenumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.aau.edu.lechl.se.singlephase.R
import at.aau.edu.lechl.se.singlephase.databinding.FragmentPrimeNumberBinding
import at.aau.edu.lechl.se.singlephase.ui.MainState
import at.aau.edu.lechl.se.singlephase.ui.SharedViewModel
import at.aau.edu.lechl.se.singlephase.ui.ViewModelFactory
import at.aau.edu.lechl.se.singlephase.util.Resource

class PrimeNumberFragment : Fragment() {

    private var binding: FragmentPrimeNumberBinding? = null

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity(), ViewModelFactory())[SharedViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[PrimeNumberViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPrimeNumberBinding.inflate(inflater, container, false)
        this.binding = binding

        setupUi(binding)

        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setupUi(binding: FragmentPrimeNumberBinding) {
        sharedViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state is MainState.PrimeNumber) {
                viewModel.setServiceResult(state.serviceResult)
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Failure -> {
                    // TODO: That's not implemented as not necessary for current use case
                }
                is Resource.Loading -> {
                    // TODO: That's not implemented as not necessary for current use case
                }
                is Resource.Success -> {
                    val text = if (result.data.isNullOrEmpty()) {
                        getString(R.string.no_prime_in_result)
                    } else result.data

                    binding.primeNumbersResult.text = text
                }
            }
        }

        binding.restartFlowButton.setOnClickListener {
            sharedViewModel.moveTo(MainState.RegistrationInput)
        }
    }

    companion object {
        fun instance(): PrimeNumberFragment = PrimeNumberFragment()
    }
}