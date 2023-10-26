package com.alaa.checkindemo.feature_admin.presentation.check_in_details

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alaa.checkindemo.R
import com.alaa.checkindemo.databinding.FragmentCheckInDetailsBinding
import com.alaa.checkindemo.feature_admin.presentation.AdminViewModel
import com.alaa.checkindemo.util.ScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckInDetailsFragment : Fragment(R.layout.fragment_check_in_details) {

    private var _binding: FragmentCheckInDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckInDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserByCheckInLogById()

        viewModel.userById.observe(viewLifecycleOwner) { state ->
            binding.apply {
                when (state) {
                    is ScreenState.Error -> {
                        progressBar.isVisible = false
                    }
                    is ScreenState.Loading -> {
                        progressBar.isVisible = true
                        rvCheckInDetails.isVisible = false
                    }
                    is ScreenState.Success -> {
                        Log.d(TAG, "onViewCreated: ${state.data?.checkIn}")
                        rvCheckInDetails.adapter = CheckInDetailsAdapter(state.data?.checkIn!!)
                        progressBar.isVisible = false
                        rvCheckInDetails.isVisible = true

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