package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())
        auth = Firebase.auth

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.darkNavy_accent)
        }//changes status bar color to navy for login page

        binding.LoginBtn.setOnClickListener() {
//            user.userEmail = binding.enterUserEmailLogin.text.toString()
//            user.userPassword = binding.enterUserPasswordLogin.text.toString()
//
//
//            val userStorage = userMemStore()
//            val people: HashMap<String, User> = userStorage.getAllUsers()
//            val searchedUser:User = userStorage.getUser(binding.enterUserEmailLogin.text.toString())
//            if(binding.enterUserEmailLogin.text.toString().isNullOrBlank() || binding.enterUserPasswordLogin.text.toString().isNullOrBlank()){
//                Snackbar.make(it, "please dont leave empty fields", Snackbar.LENGTH_LONG).show()
//            }else {
//                if (people.containsKey(binding.enterUserEmailLogin.text.toString()) && binding.enterUserPasswordLogin.text.toString()
//                        .equals(searchedUser.userPassword)
//                ) {
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                } else {
//                    Snackbar.make(it, "Email or password doesnt match", Snackbar.LENGTH_LONG).show()
//                }
//            }
            login()
        }




    }

    private fun login(){
        val userEmail = binding.enterUserEmailLogin.text.toString()
            val userPassword = binding.enterUserPasswordLogin.text.toString()

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occured ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}