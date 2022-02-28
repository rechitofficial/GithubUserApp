package com.rechit.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rechit.githubuserapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUser.setHasFixedSize(true)
        list.addAll(listUsers)
        showRecyclerList()
    }

    private val listUsers: ArrayList<User>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowing = resources.obtainTypedArray(R.array.following)
            val dataRepository = resources.obtainTypedArray(R.array.repository)
            val dataFollower = resources.obtainTypedArray(R.array.followers)

            val listUser = ArrayList<User>()
            for (i in dataName.indices){
                val user = User(dataName[i], dataUsername[i], dataAvatar.getResourceId(i, -1), dataLocation[i], dataCompany[i], dataFollowing.getResourceId(i,
                    -1), dataRepository.getResourceId(i, -1), dataFollower.getResourceId(i, -1))
                listUser.add(user)
            }
            return listUser
        }

    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUser.adapter = listUserAdapter

    }




}