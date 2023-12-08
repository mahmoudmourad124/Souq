package com.example.souq.fragments.loginregester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.souq.R
import com.example.souq.databinding.FragmentAcountOptionsBinding
import dagger.hilt.android.AndroidEntryPoint

class AcountOptionsFragment:Fragment(R.layout.fragment_acount_options) {
    lateinit var binidng:FragmentAcountOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binidng=FragmentAcountOptionsBinding.inflate(inflater)
        return binidng.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binidng.buttonLoginOption.setOnClickListener {
            findNavController().navigate(R.id.action_acountOptionsFragment_to_loginFragment)
        }
        binidng.buttonRegisterOption.setOnClickListener {
            findNavController().navigate(R.id.action_acountOptionsFragment_to_registerFragment)
        }
    }
}