package origin.wit.socialmediaart.activities



import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityAddPostBinding
import origin.wit.socialmediaart.databinding.ActivityMainBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.Post
import timber.log.Timber
import timber.log.Timber.i


class AddPost : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var socialmediaapp: MainApp
    private var post = Post()
    private lateinit var binding: ActivityAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
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
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.about -> {
//                    startActivity(Intent(applicationContext, AboutActivity::class.java))
//                    overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        Timber.i("Placemark Activity started...")
        //val pricebutton = binding.sellable.isChecked


        binding.sellableswitch.setOnClickListener{
            if (binding.sellableswitch.isChecked){
                post.forSale = true
                binding.priceField.isVisible = true
            }else{
                post.forSale = false
                binding.priceField.isVisible = false

            }
        }

        binding.postButton.setOnClickListener() {
            Timber.i("add Button Pressed: ")

            post.title = binding.postTitle.text.toString()
            post.description = binding.postDesc.text.toString()

            post.price = binding.priceField.text.toString().toInt()


            if(title.isNotEmpty() && post.description!!.isNotEmpty()) {
                socialmediaapp.posts.add(post.copy())
                Timber.i("added post: ")
               for (i in socialmediaapp.posts.indices)
                { i("post[$i]:${this.socialmediaapp.posts[i]}") }
                setResult(RESULT_OK)
                finish()
            } else{
                Snackbar.make(it,"please dont leave empty fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }




}