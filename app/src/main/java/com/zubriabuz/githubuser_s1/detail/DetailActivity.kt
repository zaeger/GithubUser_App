package com.zubriabuz.githubuser_s1.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zubriabuz.githubuser_s1.R
import com.zubriabuz.githubuser_s1.adapter.DetailAdapter
import com.zubriabuz.githubuser_s1.data.local.ConfigDB
import com.zubriabuz.githubuser_s1.data.model.UserDetailResponse
import com.zubriabuz.githubuser_s1.data.model.UserGitResponse
import com.zubriabuz.githubuser_s1.databinding.ActivityDetailBinding
import com.zubriabuz.githubuser_s1.detail.follows.FollowsFragment
import com.zubriabuz.githubuser_s1.util.ResultProcess
import com.zubriabuz.githubuser_s1.viewmodel.DetailViewModel

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(ConfigDB(this))
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item = intent.getParcelableExtra<UserGitResponse.ItemsItem>("item")
        val username = item?.login ?: ""



        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is ResultProcess.Success<*> -> {
                    val user = it.data as UserDetailResponse
                    binding.apply {
                        ivImageDetail.load(user.avatarUrl) {
                            transformations(CircleCropTransformation())
                        }

                        tvNameDetail.text = user.name

                        tvUsername.text = user.login
                        tvFollower.text = "${user.followers} Followers"
                        tvFollowing.text = "${user.following} Following"
                    }

                }
                is ResultProcess.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is ResultProcess.Loading -> {
                    binding.progressBarDetail.isVisible = it.isLoading
                }
            }
        }
        viewModel.getDetailUser(username)

        val fragment = mutableListOf<Fragment>(
            FollowsFragment.newInstance(FollowsFragment.FOLLOWERS),
            FollowsFragment.newInstance(FollowsFragment.FOLLOWING)

        )
        val titleFragment = mutableListOf(
            getString(R.string.followers), getString(R.string.following),
        )
        val adapter = DetailAdapter(this, fragment)
        binding.viewpager2.adapter = adapter

        TabLayoutMediator(binding.tabLout, binding.viewpager2) { tab, position ->
            tab.text = titleFragment[position]
        }.attach()

        binding.tabLout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewModel.getFollowers(username)

        viewModel.resultFavouriteSuccess.observe(this) {
            binding.btnFav.changeIconColor(R.color.red)
        }
        viewModel.resultFavouriteDelete.observe(this) {
            binding.btnFav.changeIconColor(R.color.white)
        }

        binding.btnFav.setOnClickListener {
            if (item != null) {
                viewModel.setFavoriteUser(item)
            }
        }
        viewModel.findFavoriteUser(item?.id ?: 0) {
            binding.btnFav.changeIconColor(R.color.red)

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
}