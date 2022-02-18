package com.example.lillydooassignment.components.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.Joke
import com.example.lillydooassignment.R
import com.example.lillydooassignment.components.home.ContentState
import com.example.lillydooassignment.components.home.ErrorState
import com.example.lillydooassignment.components.home.LoadingState
import com.example.lillydooassignment.components.home.RefreshState
import com.example.lillydooassignment.components.home.adapter.JokesRecyclerViewAdapter
import com.example.lillydooassignment.components.home.viewmodel.HomeViewModel
import com.example.lillydooassignment.databinding.FragmentHomeBinding
import com.example.lillydooassignment.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val mViewModel by viewModels<HomeViewModel>()

    private lateinit var mAdapter: JokesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        bindViewModel()
        bindListeners()
    }

    private fun bindListeners() {
        with(binding.swipeRefresh) {
            setOnRefreshListener {
                mViewModel.refreshJokes()
            }
        }
    }

    private fun navigateToDetail(joke: Joke) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToJokeDetailDialog(joke))
    }

    private fun setUpAdapter() {
        with(binding.recyclerViewJokes) {
            adapter = JokesRecyclerViewAdapter(this@HomeFragment::navigateToDetail).also { mAdapter = it }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun bindViewModel() {
        with(mViewModel) {

            jokes.observe(viewLifecycleOwner) { jokes ->
                mAdapter.updateList(jokes)
            }

            uiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    ContentState -> {
                        binding.progressBar.makeGone()
                        binding.swipeRefresh.makeRefreshGone()
                    }
                    is ErrorState -> {
                        binding.progressBar.makeGone()
                        binding.swipeRefresh.makeRefreshGone()
                        showToast(state.message)
                    }
                    LoadingState -> {
                        binding.progressBar.makeVisible()
                    }
                    RefreshState -> {
                        binding.swipeRefresh.makeRefreshVisible()
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