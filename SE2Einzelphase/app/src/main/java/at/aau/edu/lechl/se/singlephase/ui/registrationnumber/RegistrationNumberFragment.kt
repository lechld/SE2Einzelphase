package at.aau.edu.lechl.se.singlephase.ui.registrationnumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.aau.edu.lechl.se.singlephase.R
import at.aau.edu.lechl.se.singlephase.databinding.FragmentRegistrationNumberBinding
import at.aau.edu.lechl.se.singlephase.ui.MainState
import at.aau.edu.lechl.se.singlephase.ui.SharedViewModel
import at.aau.edu.lechl.se.singlephase.ui.ViewModelFactory

private const val INPUT_LENGTH = 8

class RegistrationNumberFragment : Fragment() {

    private var binding: FragmentRegistrationNumberBinding? = null

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity(), ViewModelFactory())[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegistrationNumberBinding.inflate(inflater, container, false)
        this.binding = binding

        setupUi(binding)

        return binding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setupUi(binding: FragmentRegistrationNumberBinding) {
        binding.numberInput.doAfterTextChanged { editable ->
            val isValid = editable?.length == INPUT_LENGTH

            binding.numberInput.error = if (isValid) null else {
                getString(R.string.input_invalid_error)
            }

            binding.sendButton.isEnabled = isValid
        }

        binding.sendButton.setOnClickListener {
            sharedViewModel.moveTo(
                MainState.ServiceResult(
                    binding.numberInput.text?.toString() ?: ""
                )
            )
        }
    }

    companion object {
        fun instance(): RegistrationNumberFragment = RegistrationNumberFragment()
    }
}