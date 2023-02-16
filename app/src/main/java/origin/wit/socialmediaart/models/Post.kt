package origin.wit.socialmediaart.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post (var id: Long = 0,var title:String?= null, var description:String?=null,var type:String?=null, var forSale:Boolean=false, var price:Int?=null) :
    Parcelable