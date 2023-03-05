package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityLoginBinding
import origin.wit.socialmediaart.databinding.ActivitySignupBinding
import origin.wit.socialmediaart.main.MainApp
import timber.log.Timber

class Login_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var socialmediaapp: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.darkNavy_accent)
        }//changes status bar color to navy for login page

        binding.LoginBtn.setOnClickListener() {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }


    }
}