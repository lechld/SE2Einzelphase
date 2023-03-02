package at.aau.edu.lechl.se.singlephase.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import at.aau.edu.lechl.se.singlephase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val sharedViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[SharedViewModel::class.java]
    }

    private val adapter by lazy {
        AppAdapter(supportFragmentManager, lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater).also { binding ->
            setContentView(binding.root)
            binding.pager.adapter = adapter

            sharedViewModel.state.observe(this) {
                binding.pager.currentItem = it.index
            }
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentItem = binding.pager.currentItem

                if (currentItem == 0) {
                    finish()
                } else {
                    binding.pager.currentItem = currentItem - 1
                }
            }
        })
    }
}