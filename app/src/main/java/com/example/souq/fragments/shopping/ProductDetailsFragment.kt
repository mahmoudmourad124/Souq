package com.example.souq.fragments.shopping

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souq.R
import com.example.souq.adapters.ColorsAdapter
import com.example.souq.adapters.SizesAdapter
import com.example.souq.adapters.ViewPager2ImagesAdapter
import com.example.souq.data.CartProduct
import com.example.souq.data.Product
import com.example.souq.databinding.FragmentProductDetailsBinding
import com.example.souq.util.Resource
import com.example.souq.util.hideBottomNavigation
import com.example.souq.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    lateinit var binding: FragmentProductDetailsBinding
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewpagerAdapter by lazy { ViewPager2ImagesAdapter() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }
    private var selectedColor: Int? = null
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigation()
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product

        setupSizesrv()
        setupColosrv()
        setupViewPager()

        setupPrices(product)

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizesAdapter.onItemCLick = {
            selectedSize = it
        }
        colorsAdapter.onItemCLick = {
            selectedColor = it

        }
        binding.buttonAddTOCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.buttonAddTOCart.startAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> binding.buttonAddTOCart.startAnimation()
                    is Resource.Success -> {
                        binding.buttonAddTOCart.revertAnimation()
                        binding.buttonAddTOCart.setBackgroundColor(resources.getColor(R.color.black))

                    }

                    is Resource.Unspecified -> Unit
                }

            }
        }

        //ايه المشكله لو عرفناها معfun
        viewpagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }
    }

    private fun setupPrices(product: Product) {
        binding.apply {

            if (product.colors.isNullOrEmpty())
                tvProductColors.visibility = View.INVISIBLE
            if (product.sizes.isNullOrEmpty())

                tvProductSizes.visibility = View.INVISIBLE
            tvProductName.text = product.name

            product.offerPercentage?.let {
                val remainingPricePercentage = 1f - it
                val priceAfterOffer = remainingPricePercentage * product.price
                tvProductNewPrizce.text = "$ ${String.format("%.1f", priceAfterOffer)}"
                tvProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            }

            tvProductOldPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description

        }
    }

    private fun setupViewPager() {
        binding.apply {
            viewPagerProductImages.adapter = viewpagerAdapter
        }
    }

    private fun setupColosrv() {
        binding.rvColors.apply {
            adapter = colorsAdapter
            //why
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun setupSizesrv() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }
}