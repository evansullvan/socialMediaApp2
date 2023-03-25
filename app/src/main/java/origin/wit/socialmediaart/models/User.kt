package origin.wit.socialmediaart.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize


data class User(val uid:String ="", var userEmail:String = "", var userPassword:String = "")
