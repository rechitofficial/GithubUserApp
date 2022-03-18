package com.rechit.githubuserapp

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rechit.githubuserapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var list: ArrayList<User>
    private lateinit var tempList: ArrayList<User>
    private lateinit var name: ArrayList<User>
    private lateinit var listUserAdapter: ListUserAdapter

    private val listUsers: ArrayList<User>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataFollower = resources.getStringArray(R.array.followers)

            val listUser = ArrayList<User>()
            for (i in dataName.indices){
                val user = User(dataName[i], dataUsername[i], dataAvatar.getResourceId(i, -1), dataLocation[i], dataCompany[i], dataFollowing[i], dataRepository[i], dataFollower[i])
                listUser.add(user)
            }

            return listUser
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        list = arrayListOf()
        tempList = arrayListOf()
        setContentView(binding.root)
        binding.rvUser.setHasFixedSize(true)
        list.addAll(listUsers)
        showRecyclerList()
    }



    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        tempList.addAll(listUsers)
        listUserAdapter = ListUserAdapter(tempList)
        binding.rvUser.adapter = listUserAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String): Boolean {
                tempList.clear()
                val onSearchText = newText.lowercase(Locale.getDefault())
                if(onSearchText.isNotEmpty()){
                    list.forEach {
                        if(it.name.lowercase(Locale.getDefault()).contains(onSearchText)){
                            tempList.add(it)
                        }
                    }
                    listUserAdapter.notifyDataSetChanged()

                } else {
                    tempList.clear()
                    tempList.addAll(list)
                    listUserAdapter.notifyDataSetChanged()
                }
                return false
            }
        })
        return true
    }




}