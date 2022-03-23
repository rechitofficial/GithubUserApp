package com.rechit.githubuserapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rechit.githubuserapp.view.fragment.FollowerFragment
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowerViewModel : ViewModel() {
    private val listFollowerNonMutable = ArrayList<Follower>()
    private val listFollowerMutable = MutableLiveData<ArrayList<Follower>>()

    fun getListFollower() : LiveData<ArrayList<Follower>> {
        return listFollowerMutable
    }

    fun getDataFromGithubApi(context: Context, id: String){
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token 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")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/${id}/followers"

        httpClient.get(urlClient, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(FollowerFragment.TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for ( i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDetailData(usernameLogin, context)
                    }
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
                    401 -> "$statusCode: Bad Request"
                    403 -> "$statusCode: Forbidden"
                    404 -> "$statusCode: Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDetailData(usernameLogin: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", "token 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")
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$usernameLogin"
        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val data = Follower()
                    data.username = jsonObject.getString("login")
                    data.name = jsonObject.getString("name")
                    data.avatar = jsonObject.getString("avatar_url")
                    data.company = jsonObject.getString("company")
                    data.location = jsonObject.getString("location")
                    data.repository = jsonObject.getString("public_repos")
                    data.follower = jsonObject.getString("followers")
                    data.following = jsonObject.getString("following")
                    listFollowerNonMutable.add(data)
                    listFollowerMutable.postValue(listFollowerNonMutable)

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
                    401 -> "$statusCode: Bad Request"
                    403 -> "$statusCode: Forbidden"
                    404 -> "$statusCode: Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })

    }


}