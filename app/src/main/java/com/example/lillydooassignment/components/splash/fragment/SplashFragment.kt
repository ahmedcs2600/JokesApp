package com.example.lillydooassignment.components.splash.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lillydooassignment.R
import com.example.lillydooassignment.components.splash.viewmodel.SplashViewModel
import com.example.lillydooassignment.databinding.FragmentSplashBinding
import com.example.lillydooassignment.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!

    private val mViewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setUpNavigation()
    }

    private fun setUpNavigation() {
        lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }

    private fun bindViewModel() {
        with(mViewModel) {
            visitCount.observe(viewLifecycleOwner, EventObserver {
                binding.appOpenDetails.text = getString(R.string.app_visit_count, it.toString())
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}