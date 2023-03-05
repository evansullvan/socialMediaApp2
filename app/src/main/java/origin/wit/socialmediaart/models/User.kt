package origin.wit.socialmediaart.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var id: Long = 0, val userName:String, val userEmail:String, val userPassword:String) : Parcelable
