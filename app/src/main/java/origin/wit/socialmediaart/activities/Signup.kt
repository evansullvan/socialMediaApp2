package origin.wit.socialmediaart.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
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





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.main_yellow)
        }//changeew status bar color to yellow for the signup activity

        binding.signupBtn.setOnClickListener() {


            user.firstName = binding.enterFirstNameSignup.text.toString()
            user.secondName = binding.enterSecondNameSignup.text.toString()
            user.userEmail = binding.editTextTextEmailAddressSignup.text.toString()
            user.userPassword = binding.editTextTextPasswordSignup.text.toString()

            if(user.firstName!!.isBlank() || user.secondName!!.isBlank() || user.userEmail!!.isBlank() || user.userPassword!!.isBlank()){
                Snackbar.make(it, "please dont leave any empty fields", Snackbar.LENGTH_LONG).show()
            }else{
                val userStorage = userMemStore()
                val people: HashMap<String, User> = userStorage.getAllUsers()
if(!isValidEmailAddress(binding.editTextTextEmailAddressSignup.text.toString())){
    Snackbar.make(it, "Email is not valid, enter a real email.", Snackbar.LENGTH_LONG).show()

}
                if (people.containsKey(binding.editTextTextEmailAddressSignup.text.toString())) {
                    Snackbar.make(it, "Email already exists, login instead.", Snackbar.LENGTH_LONG).show()
                }else{
                    userStorage.addUser(User(user.id,user.firstName,user.secondName,user.userEmail,user.userPassword))
                    //().toString())

                    startActivity(Intent(applicationContext, MainActivity::class.java))

                    Timber.i("user added "+ user.id+ "User name: "+user.firstName+" "+user.secondName)
                    setResult(RESULT_OK)
                    finish()

                }


            }

        }

        binding.goToLoginPageBtn.setOnClickListener(){
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
}