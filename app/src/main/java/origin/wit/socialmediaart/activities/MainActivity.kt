package origin.wit.socialmediaart.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import origin.wit.socialmediaart.R
import timber.log.Timber
import timber.log.Timber.i
import origin.wit.socialmediaart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonClick = findViewById<Button>(R.id.gotoCreateScreen)
        buttonClick.setOnClickListener {
            val intent = Intent(this, AddPost::class.java)
            startActivity(intent)
        }
    }
}