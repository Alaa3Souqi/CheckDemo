package com.alaa.checkindemo.feature_auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alaa.checkindemo.R
import com.alaa.checkindemo.databinding.FragmentSplashBinding
import com.alaa.checkindemo.util.UserAuthDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentUser()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            observeNavigationPath()
        }
    }

    private fun observeNavigationPath() {

       viewModel.navigationPath.observe(viewLifecycleOwner) {

            when (it) {
                UserAuthDestination.Login -> {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }

                UserAuthDestination.AdminDashboard -> {
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAdminDashboardFragment())
                }

                UserAuthDestination.Employee ->
                    findNavController().navigate(SplashFragmentDirections.actionSplashScreenFragmentToCheckInFragment())
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}