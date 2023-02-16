package origin.wit.socialmediaart.activities



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityMainBinding
import origin.wit.socialmediaart.databinding.ActivitySearchBinding
import origin.wit.socialmediaart.databinding.PostCardBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.Post
import timber.log.Timber

private lateinit var socialmediaapp: MainApp
class SearchActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivitySearchBinding

    //private lateinit var deletePostButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById(R.id.bottomNavbar)
        //deletePostButton = findViewById(R.id.DeletePostBtn)
        socialmediaapp = application as MainApp

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
                R.id.searchbutton -> {
                   // startActivity(Intent(applicationContext, SearchActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }




        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = searchAdapter(socialmediaapp.posts)
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(origin.wit.socialmediaart.R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            origin.wit.socialmediaart.R.id.item_add -> {
                val launcherIntent = Intent(this, AddPost ::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,socialmediaapp.posts.size)
            }
        }
}


class searchAdapter constructor(private var posts: List<Post>) :
    RecyclerView.Adapter<searchAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = PostCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val post = posts[holder.adapterPosition]
        holder.bind(post)
    }

    override fun getItemCount(): Int = posts.size

    class MainHolder(private val binding : PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.postTitle.text = post.title
            binding.postdescription.text = post.description
            binding.pricedisplay.text = post.price.toString()
            binding.DeletePostBtn.setOnClickListener() {
                socialmediaapp.posts.remove(post)



            }
        }

    }
}