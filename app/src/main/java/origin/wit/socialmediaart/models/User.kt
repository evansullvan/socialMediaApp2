package origin.wit.socialmediaart.models

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var id: Long = 0, var firstName:String?= null, var secondName:String?= null, var userEmail:String?= null, var userPassword:String?= null) : Parcelable
