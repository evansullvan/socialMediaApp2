package origin.wit.socialmediaart.activities


import android.annotation.SuppressLint
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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.material.bottomnavigation.BottomNavigationView
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityMainBinding
import origin.wit.socialmediaart.databinding.PostCardBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.Post
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

private lateinit var socialmediaapp: MainApp

class MainActivity : AppCompatActivity(), PostListener {
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityMainBinding

    private lateinit var refreshPostButton : Button
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById(R.id.bottomNavbar)

        refreshPostButton = findViewById(R.id.refreshBtn)

        socialmediaapp = application as MainApp
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        // binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts)
        //binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts.findAll())
        binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts.findAll(), this)

        binding.recyclerView.setHasFixedSize(true)


        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homebutton -> {
                    Timber.i("home button pressed")
                    println("home button pressed")
                    // startActivity(Intent(applicationContext, MainActivity::class.java))
                    // bottomNavigationView.selectedItemId = R.id.homebutton
                    //overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.additembutton -> {
                    Timber.i("add button pressed")
                    println("add button pressed")

                    startActivity(Intent(applicationContext, AddPost::class.java))
                    //item.setIcon(R.drawable.profile__1_)
                    overridePendingTransition(0, 0)

                    return@setOnNavigationItemSelectedListener true
                }
//                R.id.searchbutton -> {
//                    startActivity(Intent(applicationContext, SearchActivity::class.java))
//                    overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        //refresh when delete is pressed
        refreshPostButton.setOnClickListener {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(origin.wit.socialmediaart.R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            origin.wit.socialmediaart.R.id.item_add -> {
                val launcherIntent = Intent(this, AddPost::class.java)
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
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(
                    0,
                    socialmediaapp.posts.findAll().size
                )
            }
        }

    val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(
                    0,
                    socialmediaapp.posts.findAll().size
                )
            }
        }

    override fun onPostClick(post: Post) {
        val launcherIntent = Intent(this, AddPost::class.java)
        launcherIntent.putExtra("post_edit", post)
        getClickResult.launch(launcherIntent)
    }







}

interface PostListener {
    fun onPostClick(post: Post)
}

class PostAdapter constructor(private var posts: List<Post>,private val listener:PostListener) :
    RecyclerView.Adapter<PostAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = PostCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val post = posts[holder.adapterPosition]
        holder.bind(post,listener)
    }

    override fun getItemCount(): Int = posts.size

    class MainHolder(private val binding: PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post,listener: PostListener) {
            binding.postTitle.text = post.title
            binding.postdescription.text = post.description
            binding.pricedisplay.text = post.price.toString()

            //using instagrams timestamp
            binding.TimeStampView.text = TimeAgo.using(post.timestamp)

            //delete button
            binding.DeletePostBtn.setOnClickListener() {
                socialmediaapp.posts.remove(post)


            }
            binding.root.setOnClickListener { listener.onPostClick(post) }
        }

    }
}