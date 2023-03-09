package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityLoginBinding
import origin.wit.socialmediaart.databinding.ActivitySignupBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.User
import origin.wit.socialmediaart.models.userMemStore
import timber.log.Timber

class Login_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var socialmediaapp: MainApp

    private var user = User()
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
            user.userEmail = binding.enterUserEmailLogin.text.toString()
            user.userPassword = binding.enterUserPasswordLogin.text.toString()


            val userStorage = userMemStore()
            val people: HashMap<String, User> = userStorage.getAllUsers()
            val searchedUser:User = userStorage.getUser(binding.enterUserEmailLogin.text.toString())
            if(binding.enterUserEmailLogin.text.toString().isNullOrBlank() || binding.enterUserPasswordLogin.text.toString().isNullOrBlank()){
                Snackbar.make(it, "please dont leave empty fields", Snackbar.LENGTH_LONG).show()
            }else {
                if (people.containsKey(binding.enterUserEmailLogin.text.toString()) && binding.enterUserPasswordLogin.text.toString()
                        .equals(searchedUser.userPassword)
                ) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Snackbar.make(it, "Email or password doesnt match", Snackbar.LENGTH_LONG).show()
                }
            }
        }


    }
}