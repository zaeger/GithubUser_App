package com.zubriabuz.githubuser_s1.detail.follows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zubriabuz.githubuser_s1.adapter.UserAdapter
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse
import com.zubriabuz.githubuser_s1.databinding.FragmentFollowsBinding
import com.zubriabuz.githubuser_s1.util.ResultProcess
import com.zubriabuz.githubuser_s1.viewmodel.DetailViewModel


@Suppress("UNCHECKED_CAST")
class FollowsFragment : Fragment() {

    private var binding: FragmentFollowsBinding? = null
    private val adapter by lazy {
        UserAdapter {
        }
    }
    private val viewModel by activityViewModels<DetailViewModel>()
    var type = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter
        }
        when (type) {
            FOLLOWERS -> {
                viewModel.resultFollowersUser.observe(viewLifecycleOwner, this::manageResultFollows)

            }
            FOLLOWING -> {
                viewModel.resultFollowingUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun manageResultFollows(state: ResultProcess) {
        when (state) {
            is ResultProcess.Success<*> -> {
                adapter.setData(state.data as MutableList<UserGitResponse.ItemsItem>)
            }
            is ResultProcess.Error -> {
                Toast.makeText(
                    requireActivity(),
                    state.exception.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is ResultProcess.Loading -> {
                binding?.progressBarFL?.isVisible = state.isLoading
            }
        }

    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type: Int) = FollowsFragment()
            .apply {
                this.type = type
            }
    }
}