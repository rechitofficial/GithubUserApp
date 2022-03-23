package com.rechit.githubuserapp

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rechit.githubuserapp.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var list: ArrayList<User>
    private lateinit var tempList: ArrayList<User>

    private val viewModel: MainViewModel by viewModels()
    //private lateinit var name: ArrayList<User>

    private lateinit var listUserAdapter: ListUserAdapter
//
//    private val listUsers: ArrayList<User>
//        get() {
//            val dataName = resources.getStringArray(R.array.name)
//            val dataUsername = resources.getStringArray(R.array.username)
//            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
//            val dataLocation = resources.getStringArray(R.array.location)
//            val dataCompany = resources.getStringArray(R.array.company)
//            val dataFollowing = resources.getStringArray(R.array.following)
//            val dataRepository = resources.getStringArray(R.array.repository)
//            val dataFollower = resources.getStringArray(R.array.followers)
//
//            val listUser = ArrayList<User>()
//            for (i in dataName.indices) {
//                val user = User(
//                    dataName[i],
//                    dataUsername[i],
//                    dataAvatar.getResourceId(i, -1),
//                    dataLocation[i],
//                    dataCompany[i],
//                    dataFollowing[i],
//                    dataRepository[i],
//                    dataFollower[i]
//                )
//                listUser.add(user)
//            }
//
//            return listUser
//        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = arrayListOf()
        tempList = arrayListOf()
        listUserAdapter = ListUserAdapter(list)

        binding.rvUser.setHasFixedSize(true)
        //list.addAll(listUsers)
        viewConfig()
        runGetDataFromGithub()
        configMainViewModel(listUserAdapter)

//        showRecyclerList()

    }

    private fun configMainViewModel(adapter: ListUserAdapter) {
        viewModel.getListUsers().observe(this, androidx.lifecycle.Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

    }

    private fun runGetDataFromGithub() {
        viewModel.getDataFromGithubApi(applicationContext)
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun viewConfig() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)

        listUserAdapter.notifyDataSetChanged()
        binding.rvUser.adapter = listUserAdapter
    }


    //
//    private fun getListUsers() {
//        binding.progressBar.visibility = View.VISIBLE
//        val client = AsyncHttpClient()
//        client.addHeader("Authorization", "token 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")
//        client.addHeader("User-agent", "request")
//        val url = "https://api.github.com/users"
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray?
//            ) {
//                val result = responseBody?.let { String(it) }
//                Log.d(TAG, result.toString())
//                try {
//                    val jsonArray = JSONArray(result)
//
//                    for (i in 0 until jsonArray.length()) {
//                        val jsonObject = jsonArray.getJSONObject(i)
//                        val username = jsonObject.getString("login")
//                        getUserDetailFromUsername(username)
//                    }
//                } catch (e: Exception){
//                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
//                    e.printStackTrace()
//                }
//                binding.progressBar.visibility = View.INVISIBLE
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray?,
//                error: Throwable?
//            ) {
//                binding.progressBar.visibility = View.INVISIBLE
//                val errorMessage = when (statusCode) {
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error?.message}"
//                }
//                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//
//    private fun getUserDetailFromUsername(username: String) {
//        binding.progressBar.visibility = View.VISIBLE
//        val client = AsyncHttpClient()
//        client.addHeader("Authorization", "token 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")
//        client.addHeader("User-agent", "request")
//        val url = "https://api.github.com/user?q=${username}"
//
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray
//            ) {
//                val result = String(responseBody)
//                Log.d(TAG, result)
//                try {
//                    val jsonObject = JSONObject(result)
//                    val data = User()
//                    data.name = jsonObject.getString("login")
//                    data.avatar = jsonObject.getString("avatar_url")
//                    data.company = jsonObject.getString("company")
//                    data.location = jsonObject.getString("location")
//                    data.repository = jsonObject.getString("public_repos")
//                    data.follower = jsonObject.getString("followers")
//                    data.following = jsonObject.getString("following")
//
//                } catch (e: java.lang.Exception){
//                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
//                    e.printStackTrace()
//                }
//                binding.progressBar.visibility = View.INVISIBLE
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray?,
//                error: Throwable?
//            ) {
//                binding.progressBar.visibility = View.INVISIBLE
//                val errorMessage = when (statusCode) {
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error?.message}"
//                }
//                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//
//
//    private fun showRecyclerList() {
//        binding.rvUser.layoutManager = LinearLayoutManager(this)
//        tempList.addAll(listUsers)
//        listUserAdapter = ListUserAdapter(tempList)
//        listUserAdapter.setData(listUsers)
//        binding.rvUser.adapter = listUserAdapter
//
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.option_menu, menu)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView =
//            menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = resources.getString(R.string.search_hint)
//        searchView.setOnQueryTextListener(object :
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
//                searchView.clearFocus()
//                return true
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onQueryTextChange(newText: String): Boolean {
//                tempList.clear()
//                val onSearchText = newText.lowercase(Locale.getDefault())
//                if (onSearchText.isNotEmpty()) {
//                    list.forEach {
//                        if (it.name.lowercase(Locale.getDefault()).contains(onSearchText)) {
//                            tempList.add(it)
//                        }
//                    }
//                    listUserAdapter.notifyDataSetChanged()
//
//                } else {
//                    tempList.clear()
//                    tempList.addAll(list)
//                    listUserAdapter.notifyDataSetChanged()
//                }
//                return false
//            }
//        })
//        return true
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchView =
            menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    list.clear()
                    viewConfig()
                    viewModel.findUserData(query, applicationContext)
                    binding.progressBar.visibility = View.VISIBLE
                    configMainViewModel(listUserAdapter)
                } else {
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true

    }


}