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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.darkNavy_accent)
        }//changes status bar color to navy for login page

        binding.LoginBtn.setOnClickListener() {

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


}