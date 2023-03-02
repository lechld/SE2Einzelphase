package at.aau.edu.lechl.se.singlephase.ui.serviceresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.aau.edu.lechl.se.singlephase.R
import at.aau.edu.lechl.se.singlephase.databinding.FragmentServiceResultBinding
import at.aau.edu.lechl.se.singlephase.ui.MainState
import at.aau.edu.lechl.se.singlephase.ui.SharedViewModel
import at.aau.edu.lechl.se.singlephase.ui.ViewModelFactory
import at.aau.edu.lechl.se.singlephase.util.Resource
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class ServiceResultFragment : Fragment() {

    private var binding: FragmentServiceResultBinding? = null

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity(), ViewModelFactory())[SharedViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[ServiceResultViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentServiceResultBinding.inflate(inflater, container, false)
        this.binding = binding

        setupUi(binding)

        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setupUi(binding: FragmentServiceResultBinding) {
        sharedViewModel.state.observe(viewLifecycleOwner) { state ->
            if (state is MainState.ServiceResult) {
                val input = state.registrationInput

                viewModel.setInput(input)
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { loadResult ->
            when (loadResult) {
                is Resource.Failure -> {
                    binding.progressBar.isVisible = false

                    val errorRes = if (loadResult.exception is IOException) {
                        R.string.network_error
                    } else R.string.unknown_error

                    Snackbar.make(binding.root, errorRes, Snackbar.LENGTH_LONG).show()

                    binding.serverResponseTitle.isVisible = true
                    binding.serverResponseText.isVisible = true
                    binding.serverResponseText.text = getString(errorRes)
                    binding.convertPrimeButton.isVisible = false
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.serverResponseTitle.isVisible = false
                    binding.serverResponseText.isVisible = false
                    binding.convertPrimeButton.isVisible = false
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    binding.serverResponseTitle.isVisible = true
                    binding.serverResponseText.isVisible = true
                    binding.serverResponseText.text = loadResult.data
                    binding.convertPrimeButton.isVisible = true
                }
            }
        }

        binding.convertPrimeButton.setOnClickListener {
            val result = viewModel.result.value?.data
                ?: throw IllegalStateException("Result not persisted inside memory!")

            sharedViewModel.moveTo(MainState.PrimeNumber(result))
        }
    }

    companion object {
        fun instance(): ServiceResultFragment = ServiceResultFragment()
    }
}