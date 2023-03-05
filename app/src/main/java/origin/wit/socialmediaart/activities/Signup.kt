package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityAddPostBinding
import origin.wit.socialmediaart.databinding.ActivitySignupBinding
import origin.wit.socialmediaart.main.MainApp
import timber.log.Timber


class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    lateinit var socialmediaapp: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.main_yellow)
        }

        binding.signupBtn.setOnClickListener() {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        binding.goToLoginPageBtn.setOnClickListener(){
            startActivity(Intent(applicationContext, Login_Activity::class.java))
        }
    }
}