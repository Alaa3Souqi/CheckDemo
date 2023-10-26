package com.alaa.checkindemo.feature_admin.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alaa.checkindemo.R
import com.alaa.checkindemo.databinding.FragmentAdminDashboardBinding
import com.alaa.checkindemo.feature_auth.domain.model.User
import com.alaa.checkindemo.util.ScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardFragment : Fragment(R.layout.fragment_admin_dashboard) {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUsers()

        viewModel.usersLiveData.observe(viewLifecycleOwner) { state ->
            setUpList(state)
        }

        binding.ivSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(AdminDashboardFragmentDirections.actionAdminDashboardFragmentToLoginFragment())
        }
    }

    private fun setUpList(state: ScreenState<List<User>>) {
        binding.let {
            when (state) {
                is ScreenState.Error -> Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_SHORT
                )
                is ScreenState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.rvUsers.isVisible = false
                }
                is ScreenState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.rvUsers.isVisible = true
                    binding.rvUsers.adapter = UsersAdapter(state.data!!) {
                        findNavController().navigate(
                            AdminDashboardFragmentDirections.actionAdminDashboardFragmentToCheckInDetailsFragment(
                                it
                            )
                        )
                    }
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}