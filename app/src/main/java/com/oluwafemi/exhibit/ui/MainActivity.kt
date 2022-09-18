package com.oluwafemi.exhibit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.oluwafemi.exhibit.data.Exhibit
import com.oluwafemi.exhibit.databinding.ActivityMainBinding
import com.oluwafemi.exhibit.ui.adapter.ClickListener
import com.oluwafemi.exhibit.ui.adapter.ExhibitAdapter
import com.oluwafemi.exhibit.ui.viewmodel.MainViewModel
import com.oluwafemi.exhibit.ui.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ClickListener {

    private inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
        crossinline bindingInflater: (LayoutInflater) -> T
    ) =
        lazy(LazyThreadSafetyMode.NONE) {
            bindingInflater.invoke(layoutInflater)
        }

    private val viewModel: MainViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var exhibitAdapter: ExhibitAdapter

    private lateinit var snackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setObservers()

        exhibitAdapter = ExhibitAdapter(this, this)
        binding.exhibitRecycler.apply {
            adapter = exhibitAdapter
        }
    }

    private fun setObservers() {
        snackBar =
            Snackbar.make(binding.root, "Something went wrong.", Snackbar.LENGTH_INDEFINITE).apply {
                setAction("RETRY") {
                    viewModel.fetchExhibits()
                }
            }

        viewModel.dataStatus.observe(this) {
            if (it == Status.SUCCESS) {
                binding.progressBar.visibility = View.GONE
                if (snackBar.isShown) snackBar.dismiss()
                viewModel.resetStatus()
            } else if (it == Status.ERROR) {
                binding.progressBar.visibility = View.GONE
                snackBar.show()
                viewModel.resetStatus()
            } else {
                if (it == Status.LOADING) {
                    binding.progressBar.visibility = View.VISIBLE
                    if (snackBar.isShown) snackBar.dismiss()
                }
            }
        }

        viewModel.exhibit.observe(this)
        {
            exhibitAdapter.submitList(it)
        }
    }

    override fun onExhibitClick(exhibit: Exhibit) {
        val progressDialog = ImageSlide()
        val bundle = Bundle()
        bundle.putStringArrayList("Exhibit", exhibit.images)
        progressDialog.arguments = bundle
        progressDialog.show(this.supportFragmentManager, "Image Slide Dialog")
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchExhibits()
    }
}