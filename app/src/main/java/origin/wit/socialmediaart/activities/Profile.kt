package origin.wit.socialmediaart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityProfileBinding
import origin.wit.socialmediaart.databinding.ActivitySignupBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.User
import timber.log.Timber



class Profile : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding
    lateinit var socialmediaapp: MainApp
    private var user = User()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())


      

    }
}