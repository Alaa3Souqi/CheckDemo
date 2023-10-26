package com.alaa.checkindemo.feature_auth.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alaa.checkindemo.R
import com.alaa.checkindemo.databinding.FragmentLoginBinding
import com.alaa.checkindemo.util.ScreenState
import com.alaa.checkindemo.util.UserAuthDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text?.toString()?.trim() ?: ""
                val password = etPassword.text?.toString()?.trim() ?: ""

                viewModel.signInWithEmailAndPassword(email, password)
            }
        }

        viewModel.userLoginStatus.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ScreenState.Loading -> {
                }
                is ScreenState.Success -> {
                    navigateToDestination(state.data!!)
                }
                is ScreenState.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToDestination(destination: UserAuthDestination) {

        when (destination) {
            UserAuthDestination.Login -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCheckInFragment())
            }

            UserAuthDestination.AdminDashboard -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAdminDashboardFragment())
            }

            UserAuthDestination.Employee ->
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCheckInFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}