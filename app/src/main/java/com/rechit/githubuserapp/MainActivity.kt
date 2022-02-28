package com.rechit.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rechit.githubuserapp.databinding.ActivityMainBinding

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
            val listUser = ArrayList<User>()
            for (i in dataName.indices){
                val user = User(dataName[i], dataUsername[i], dataAvatar.getResourceId(i, -1))
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