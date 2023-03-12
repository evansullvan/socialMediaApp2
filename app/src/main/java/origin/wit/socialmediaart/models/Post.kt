package origin.wit.socialmediaart.models

import android.os.Parcelable
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.auth.FirebaseUser
import kotlinx.parcelize.Parcelize
import java.util.*


data class Post (var title:String = "", var description:String = "",var type:String = "", var forSale:Boolean=false, var price:Int = 0,var user:User?=null, var timestamp: Long = System.currentTimeMillis())


