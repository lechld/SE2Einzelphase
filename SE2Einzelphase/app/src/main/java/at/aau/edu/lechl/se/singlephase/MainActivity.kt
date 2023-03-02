package at.aau.edu.lechl.se.singlephase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import at.aau.edu.lechl.se.singlephase.databinding.ActivityMainBinding
import at.aau.edu.lechl.se.singlephase.util.Resource
import at.aau.edu.lechl.se.singlephase.util.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

private const val REGISTRATION_NUMBER_LENGTH = 8

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(
            owner = this,
            factory = MainViewModel.Factory()
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is Resource.Failure -> {
                    setLoading(false)
                    setResult("")
                    setError(state.exception)
                }
                is Resource.Loading -> {
                    setLoading(true)
                    setResult("")
                }
                is Resource.Success -> {
                    setLoading(false)
                    setResult(state.data)
                }
            }
        }

        binding.numberInput.doAfterTextChanged { editable ->
            val isValid = editable?.toString()?.length == REGISTRATION_NUMBER_LENGTH

            binding.apply {
                numberInput.error = if (isValid) null else {
                    getString(R.string.input_invalid_error)
                }
                sendButton.isEnabled = isValid
            }
        }

        binding.sendButton.setOnClickListener {
            binding.root.hideKeyboard()
            viewModel.sendInputToServer(binding.numberInput.text?.toString() ?: "")
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.loadingFrame.isVisible = loading
    }

    private fun setError(error: Throwable) {
        val errorMessageRes = when (error) {
            is IOException -> {
                R.string.network_error
            }
            else -> {
                R.string.unknown_error
            }
        }

        Snackbar.make(binding.root, errorMessageRes, Snackbar.LENGTH_LONG).show()
    }

    private fun setResult(result: String?) {
        if (result.isNullOrEmpty()) {
            binding.response.text = getString(R.string.server_response_empty)
        } else {
            binding.response.text = getString(R.string.server_response_success, result)
        }
    }

}