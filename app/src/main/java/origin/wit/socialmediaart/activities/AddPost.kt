package origin.wit.socialmediaart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityAddPostBinding
import origin.wit.socialmediaart.databinding.ActivityMainBinding
import timber.log.Timber

class AddPost : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        Timber.i("Placemark Activity started...")

        binding..setOnClickListener() {
            Timber.i("add Button Pressed")
        }
    }
}