package origin.wit.socialmediaart.models

import android.os.Parcelable
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

import java.util.*


@Parcelize
data class Post (@DocumentId val Id: String?=null,var title:String = "", var description:String = "", var type:String = "", var forSale:Boolean=false, var price:Int = 0, var user:@RawValue User?=null, var timestamp: Long = System.currentTimeMillis()) :
    Parcelable


