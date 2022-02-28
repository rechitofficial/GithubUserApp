package com.rechit.githubuserapp

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var user_name: String,
    var avatar: Int,
    var location: String,
    var company: String,
    var following: Int,
    var repository: Int,
    var follower: Int
): Parcelable
