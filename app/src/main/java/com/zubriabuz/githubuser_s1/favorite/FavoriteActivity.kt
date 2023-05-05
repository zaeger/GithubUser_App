package com.zubriabuz.githubuser_s1.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zubriabuz.githubuser_s1.adapter.UserAdapter
import com.zubriabuz.githubuser_s1.data.local.ConfigDB
import com.zubriabuz.githubuser_s1.databinding.ActivityFavoriteBinding
import com.zubriabuz.githubuser_s1.detail.DetailActivity
import com.zubriabuz.githubuser_s1.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private var binding: ActivityFavoriteBinding? = null
    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(ConfigDB(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding!!.rvFavorit.layoutManager = LinearLayoutManager(this)
            binding!!.rvFavorit.adapter = adapter


        viewModel.getUserFavorite().observe(this) {
            adapter.setData(it)
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

    override fun onResume() {
        super.onResume()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding!!.rvFavorit.layoutManager = LinearLayoutManager(this)
        binding!!.rvFavorit.adapter = adapter


        viewModel.getUserFavorite().observe(this) {
            adapter.setData(it)


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}