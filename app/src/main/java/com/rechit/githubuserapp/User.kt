package com.rechit.githubuserapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String,
    var user_name: String,
    var avatar: Int
): Parcelable
