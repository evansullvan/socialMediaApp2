package origin.wit.socialmediaart.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityProfileBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.User
import timber.log.Timber


class Profile : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityProfileBinding
    lateinit var socialmediaapp: MainApp
    val user = Firebase.auth.currentUser
    private lateinit var auth: FirebaseAuth





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById(R.id.bottomNavbar)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())
        auth = Firebase.auth

        if (user != null) {
            binding.userEmailDisplay.setText(user.email)
        } else {
            // No user is signed in
        }

        binding.signoutBTN.setOnClickListener {
            val builder = AlertDialog.Builder(this@Profile)
            builder.setMessage("Are you sure you want to Sign out?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    auth.signOut()
                    startActivity(Intent(applicationContext, Signup::class.java))
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

        }






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