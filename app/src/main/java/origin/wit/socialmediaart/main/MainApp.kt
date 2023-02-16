package origin.wit.socialmediaart.main

import android.app.Application
import origin.wit.socialmediaart.models.Post
import origin.wit.socialmediaart.models.PostMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val posts = PostMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("social media started")


    }
}