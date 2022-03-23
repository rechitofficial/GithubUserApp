package com.rechit.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.rechit.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIl = "extra_detail"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //get data
//        val data = intent.getParcelableExtra<User>(EXTRA_NAME) as User
//        binding.tvDetailName.text = data.name
//        binding.tvDetailUsername.text = data.user_name
//        binding.imgDetailAvatar.setImageResource(data.avatar)
//        binding.tvLocation.text = data.location
//        binding.tvCompany.text = data.company
//        binding.tvFollowing.text = data.following
//        binding.tvRepository.text = data.repository
//        binding.tvFollower.text = data.follower
        //setup section pager for binding viewpager and tablayout
        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        setData()


    }

    private fun setData() {
        val data = intent.getParcelableExtra<User>(EXTRA_DETAIl) as User
        Glide.with(this)
            .load(data.avatar)
            .apply(RequestOptions())
            .into(binding.imgDetailAvatar)
        binding.tvDetailName.text = data.name
        binding.tvDetailUsername.text = data.user_name
        binding.tvCompany.text = data.company
        binding.tvLocation.text = data.location
        binding.tvFollowing.text = data.following
        binding.tvFollower.text = data.follower
        binding.tvRepository.text = data.repository
    }
}