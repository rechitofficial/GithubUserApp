package com.rechit.githubuserapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rechit.githubuserapp.databinding.ActivityMainBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel: ViewModel(){
    private val listUserNonMutable = ArrayList<User>()
    private val listUsersMutable = MutableLiveData<ArrayList<User>>()

    fun getListUsers() : LiveData<ArrayList<User>> {
        return listUsersMutable
    }

    fun getDataFromGithubApi(context: Context){
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token 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")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)
                try{
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDetailData(usernameLogin, context)
                    }

                }catch (e: Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when(statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun findUserData(usernameLogin: String, context: Context){
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token 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")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/search/users?q=${usernameLogin}"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)
                try {
                    listUserNonMutable.clear()
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        getDetailData(username, context)
                    }

                } catch (e: java.lang.Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when(statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getDetailData(usernameLogin: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token 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")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/${usernameLogin}"
        httpClient.get(urlClient, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)
                try{
                    val jsonObject = JSONObject(result)
                    val data = User()
                    data.user_name = jsonObject.getString("login")
                    data.name = jsonObject.getString("name")
                    data.avatar = jsonObject.getString("avatar_url")
                    data.company = jsonObject.getString("company")
                    data.location = jsonObject.getString("location")
                    data.repository = jsonObject.getString("public_repos")
                    data.follower = jsonObject.getString("followers")
                    data.following = jsonObject.getString("following")
                    listUserNonMutable.add(data)
                    listUsersMutable.postValue(listUserNonMutable)

                } catch (e: Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}