package com.example.souq.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souq.R
import com.example.souq.adapters.ColorsAdapter
import com.example.souq.adapters.SizesAdapter
import com.example.souq.adapters.ViewPager2ImagesAdapter
import com.example.souq.databinding.ColorRvItemBinding
import com.example.souq.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewpagerAdapter by lazy { ViewPager2ImagesAdapter() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product
        setupSizesrv()
        setupColosrv()
        setupViewPager()
        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description

        }
        //ايه المشكله لو عرفناها معfun
        viewpagerAdapter.differ.submitList(product.images)
        product.colors?.let {colorsAdapter.differ.submitList(it)}
        product.sizes?.let {sizesAdapter.differ.submitList(it)}
    }

    private fun setupViewPager() {
        binding.apply {
            viewPagerProductImages.adapter = viewpagerAdapter
        }
    }

    private fun setupColosrv() {
        binding.rvSize.apply {
            adapter = colorsAdapter
            //why
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSizesrv() {
        binding.rvColor.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }
}