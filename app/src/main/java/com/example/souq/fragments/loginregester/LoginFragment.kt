package com.example.souq.fragments.loginregester

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.souq.R
import com.example.souq.activities.ShoppingActivity
import com.example.souq.databinding.FragmentLoginBinding
import com.example.souq.util.Resource
import com.example.souq.dialog.setupBottomSheetDialog
import com.example.souq.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navigation
        binding.souqDontHaveanyacount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.apply {
            binding.buttonLoginLogin.setOnClickListener {
                val email = editTextEmail.text.toString().trim()
                val passowrd = editTextPassword.text.toString()
                viewModel.login(email, passowrd)
            }
        }
        binding.forgetPassword.setOnClickListener {
            setupBottomSheetDialog { email ->
                viewModel.resetPassword(email)

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when (it) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        Snackbar.make(requireView(),"Reset link was sent to your email",Snackbar.LENGTH_LONG).show()
                    }

                    is Resource.Error -> {

                        Snackbar.make(requireView(),"Error:${it.message}",Snackbar.LENGTH_LONG).show()


                    }

                    else -> Unit
                }


            }
        }


        lifecycleScope.launchWhenStarted {

            viewModel.login.collect {
                when (it) {
                    is Resource.Loading -> {
                        Log.i("Morad1stuvxyz", "loading")
                        binding.buttonLoginLogin.startAnimation()

                    }

                    is Resource.Success -> {
                        Log.i("Morad1stuvxyz", "success")
                        binding.buttonLoginLogin.revertAnimation()
                        val intent = Intent(context, ShoppingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)


                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        binding.buttonLoginLogin.revertAnimation()
                        Log.i("Morad1stuvxyz", "error")

                    }

                    else -> Unit
                }

            }
        }
    }

}