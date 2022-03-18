package com.rechit.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Follower (
    var name: String,
    var username: String,
    var avatar: Int,
    var location: String,
    var company: String,
    var following: String,
    var repository: String,
    var follower: String
): Parcelable