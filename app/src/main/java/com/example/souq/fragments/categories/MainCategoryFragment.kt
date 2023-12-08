package com.example.souq.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souq.R
import com.example.souq.adapters.BestDealsAdapter
import com.example.souq.adapters.BestProductsAdapter
import com.example.souq.adapters.SpecialProductsAdapter
import com.example.souq.databinding.FragmentMainCategoryBinding
import com.example.souq.util.Resource
import com.example.souq.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private val TAG = "MainCategoryFragment"

@AndroidEntryPoint
class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {
    lateinit var biniding: FragmentMainCategoryBinding
    lateinit var specialProductAdapter: SpecialProductsAdapter
    lateinit var bestDealsAdapter: BestDealsAdapter
    lateinit var bestProductsAdapter: BestProductsAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        biniding = FragmentMainCategoryBinding.inflate(inflater)
        return biniding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpecialProductsRv()
        setupBestDealsRv()
        setupBestProductsRv()

        lifecycleScope.launchWhenStarted {
            viewModel.specialProduct.collectLatest {
                when (it) {
                    is Resource.Loading -> ShowLoading()
                    is Resource.Success -> {
                        specialProductAdapter.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }


                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestProduct.collectLatest {
                when (it) {
                    is Resource.Loading -> biniding.bestProductsProgressBar.visibility =
                        View.VISIBLE

                    is Resource.Success -> {
                        bestProductsAdapter.differ.submitList(it.data)
                        biniding.bestProductsProgressBar.visibility = View.GONE


                    }

                    is Resource.Error -> {
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        biniding.bestProductsProgressBar.visibility = View.GONE
                    }


                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> ShowLoading()
                    is Resource.Success -> {
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }


                    else -> Unit
                }
            }
        }
        biniding.nestedScrollMainCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY) {
                viewModel.fetchBestProduct()
            }
        })
    }

    private fun setupBestProductsRv() {
        bestProductsAdapter = BestProductsAdapter()
        biniding.rvBestProducts.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestProductsAdapter
        }

    }

    private fun setupSpecialProductsRv() {
        specialProductAdapter = SpecialProductsAdapter()
        biniding.rvSpecialProduct.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = specialProductAdapter
        }
    }

    private fun setupBestDealsRv() {
        bestDealsAdapter = BestDealsAdapter()
        biniding.rvBestDeals.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bestDealsAdapter
        }
    }

    private fun hideLoading() {
        biniding.mainCategoryProgressBar.visibility = View.GONE
    }

    private fun ShowLoading() {
        biniding.mainCategoryProgressBar.visibility = View.VISIBLE
    }
}
