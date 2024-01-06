package com.example.souq.fragments.loginregester

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.souq.R
import com.example.souq.activities.ShoppingActivity
import com.example.souq.databinding.FragmentIntroductionBinding
import com.example.souq.viewmodel.IntroductionViewModel
import com.example.souq.viewmodel.IntroductionViewModel.Companion.ACCOUNT_OPTION_FRAGMENT
import com.example.souq.viewmodel.IntroductionViewModel.Companion.SHOPPING_ACTIVITY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class introductionFragment : Fragment(R.layout.fragment_introduction) {
    private lateinit var binding: FragmentIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroductionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collect {
                when (it) {
                    SHOPPING_ACTIVITY -> {
                        val intent = Intent(context, ShoppingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                    ACCOUNT_OPTION_FRAGMENT -> {
                      //  findNavController().navigate(R.id.action_introductionFragment_to_acountOptionsFragment)
                        findNavController().navigate(it)
                    }
                    else -> Unit
                }
            }
        }

        binding.StartButton.setOnClickListener {
            viewModel.startButtonClick()
            findNavController().navigate(R.id.action_introductionFragment_to_acountOptionsFragment)
        }
    }
}