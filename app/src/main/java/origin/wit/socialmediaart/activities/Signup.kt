package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivitySignupBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.User
import origin.wit.socialmediaart.models.userMemStore
import timber.log.Timber
import java.util.regex.Pattern


class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    lateinit var socialmediaapp: MainApp
    private var user = User()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())

        auth = Firebase.auth




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.main_yellow)
        }//changeew status bar color to yellow for the signup activity

        binding.signupBtn.setOnClickListener() {


//            user.firstName = binding.enterFirstNameSignup.text.toString()
//            user.secondName = binding.enterSecondNameSignup.text.toString()
//            user.userEmail = binding.editTextTextEmailAddressSignup.text.toString()
//            user.userPassword = binding.editTextTextPasswordSignup.text.toString()
//
//            if(user.firstName!!.isBlank() || user.secondName!!.isBlank() || user.userEmail!!.isBlank() || user.userPassword!!.isBlank()){
//                Snackbar.make(it, "please dont leave any empty fields", Snackbar.LENGTH_LONG).show()
//            }else{
//                val userStorage = userMemStore()
//                val people: HashMap<String, User> = userStorage.getAllUsers()
//if(!isValidEmailAddress(binding.editTextTextEmailAddressSignup.text.toString())){
//    Snackbar.make(it, "Email is not valid, enter a real email.", Snackbar.LENGTH_LONG).show()
//
//}
//                if (people.containsKey(binding.editTextTextEmailAddressSignup.text.toString())) {
//                    Snackbar.make(it, "Email already exists, login instead.", Snackbar.LENGTH_LONG).show()
//                }else{
//                    userStorage.addUser(User(user.id,user.firstName,user.secondName,user.userEmail,user.userPassword))
//                    //().toString())
//
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//
//                    Timber.i("user added "+ user.id+ "User name: "+user.firstName+" "+user.secondName)
//                    setResult(RESULT_OK)
//                    finish()
//
//                }
//
//
//            }
            performSignup()

        }




        binding.goToLoginPageBtn.setOnClickListener() {
            startActivity(Intent(applicationContext, Login_Activity::class.java))
        }


    }

    fun isValidEmailAddress(email: String?): Boolean {
        val ePattern =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()
    }

    private fun performSignup() {


        val userEmail = binding.editTextTextEmailAddressSignup.text.toString()
        val userpassword = binding.editTextTextPasswordSignup.text.toString()

//        if (user.firstName!!.isBlank() || user.secondName!!.isBlank() || user.userEmail!!.isBlank() || user.userPassword!!.isBlank()) {
//            Toast.makeText(this, "please dont leave any empty fields", Toast.LENGTH_SHORT).show()
//
//            if (!isValidEmailAddress(binding.editTextTextEmailAddressSignup.text.toString())) {
//                Toast.makeText(this, "Email is not valid, enter a real email.", Toast.LENGTH_LONG)
//                    .show()
//
//            }


            auth.createUserWithEmailAndPassword(userEmail, userpassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "${it.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                }
        }


    }
