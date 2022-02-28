package com.rechit.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rechit.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get data
        val data = intent.getParcelableExtra<User>(EXTRA_NAME) as User
        binding.tvDetailName.text = data.name
        binding.tvDetailUsername.text = data.user_name
        binding.imgDetailAvatar.setImageResource(data.avatar)
        binding.tvLocation.text = data.location
        binding.tvCompany.text = data.company
        binding.tvFollowing.inputType = data.following
        binding.tvRepository.inputType = data.repository
        binding.tvFollower.inputType = data.follower
    }
}