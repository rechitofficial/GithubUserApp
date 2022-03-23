package com.rechit.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Following (
    var name: String = "",
    var username: String = "",
    var avatar: String = "",
    var location: String = "",
    var company: String = "",
    var following: String = "",
    var repository: String = "",
    var follower: String = ""
): Parcelable
