package com.rechit.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var user_name: String,
    var avatar: Int,
    var location: String,
    var company: String,
    var following: String,
    var repository: String,
    var follower: String
): Parcelable
