package origin.wit.socialmediaart.models

import android.os.Parcelable
import com.github.marlonlom.utilities.timeago.TimeAgo
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Post (var id: Long = 0,var title:String?= null, var description:String?=null,var type:String?=null, var forSale:Boolean=false, var price:Int?=null, var timestamp: Long = System.currentTimeMillis(),var postUser:String?=null) :
    Parcelable


