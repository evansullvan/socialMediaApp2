package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityProfileBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.User
import timber.log.Timber


class Profile : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityProfileBinding
    lateinit var socialmediaapp: MainApp
    private var user = User()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById(R.id.bottomNavbar)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())








        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.homebutton -> {
                    Timber.i("home button pressed")
                    println("home button pressed")
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.additembutton -> {
                    Timber.i("add button pressed")
                    println("add button pressed")
                     startActivity(Intent(applicationContext, AddPost::class.java))
                    // bottomNavigationView.selectedItemId = R.id.additembutton
                     overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profilebutton -> {
                    //startActivity(Intent(applicationContext, Profile::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }


    }
}