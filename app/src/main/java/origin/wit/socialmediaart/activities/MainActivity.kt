package origin.wit.socialmediaart.activities


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityMainBinding
import origin.wit.socialmediaart.databinding.PostCardBinding
import origin.wit.socialmediaart.databinding.ProfilePostCardBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.Post
import origin.wit.socialmediaart.models.User
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "MainActivity"
const val EXTRA_USEREMAIL = "EXTRA_USEREMAIL"
private lateinit var socialmediaapp: MainApp



class MainActivity : AppCompatActivity(), PostListener {
    // lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDb:FirebaseFirestore
    private lateinit var posts:MutableList<Post>
    private lateinit var adapter: PostAdapter
    val currentUser = Firebase.auth.currentUser
    var signedInUser: User?=null
//var filterButton = null
var postList1 = listOf<Post>()



    @SuppressLint("NotifyDataSetChanged", "LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottomNavigationView = binding.bottomNavbar

//        binding.toolbar.title = title
//        setSupportActionBar(binding.toolbar)


        var filterButton = binding.filter
        firebaseDb = FirebaseFirestore.getInstance()
        posts = mutableListOf<Post>()
        adapter = PostAdapter(this,posts,this)



        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        firebaseDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Timber.i("user is : " + signedInUser.toString())
            }.addOnFailureListener{
                Timber.i("failed, cannot get uid")
            }


//        var postsReference = firebaseDb.collection("posts")
//            .limit(20)
//            .orderBy("timestamp",Query.Direction.DESCENDING)
//        val username = intent.getStringExtra(EXTRA_USEREMAIL)
//        if(username != null){
//            postsReference = postsReference.whereEqualTo("user.userEmail",username)

       // }

        //initially displaying the posts



        //refreshPostButton = findViewById(R.id.refreshBtn)


        socialmediaapp = application as MainApp
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        // binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts)
        //binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts.findAll())
        //binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts.findAll(), this)

        binding.recyclerView.setHasFixedSize(true)

        binding.addItem.setOnClickListener {
            startActivity(Intent(applicationContext, AddPost::class.java))
        }
//initially display posts
        var postsReference = firebaseDb.collection("posts")
            .limit(20)
            .orderBy("timestamp", Query.Direction.DESCENDING)
        postsReference.addSnapshotListener { snapshot, exception ->
            if(exception != null || snapshot == null){
                Log.e(TAG, "error when querying data",exception)
                return@addSnapshotListener

            }
            val postList1 = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList1)
            adapter.notifyDataSetChanged()

            for(post in postList1){
                Log.e(TAG,"post  ${post}")
            }
        }

        //display the posts
        fun updatePostsList(query: Query) {
            query.addSnapshotListener { snapshot, exception ->
                if (exception != null || snapshot == null) {
                    Log.e(TAG, "error when querying data", exception)
                    return@addSnapshotListener
                }
                val postList = snapshot.toObjects(Post::class.java)
                posts.clear()
                posts.addAll(postList)
                adapter.notifyDataSetChanged()

                for (post in postList) {
                    Log.e(TAG, "post  ${post}")
                }
            }
        }



        filterButton.setOnClickListener {

            val popup = PopupMenu(this, filterButton)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.filter_menu, popup.menu)

            popup.setOnMenuItemClickListener { item: MenuItem ->
                // Handle item selection
                when (item.itemId) {
                    R.id.any -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        updatePostsList(postsReference)

                        true
                    }
                    R.id.painting -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Painting")
                        updatePostsList(postsReference)

                        true
                    }
                    R.id.drawing -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Drawing")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.sculpture -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Sculpture")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.printmaking -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Printmaking")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.photo -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Photography")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.film -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Film")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.architecture -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Architecture")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.design -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Design")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.textiles -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Textiles")
                        updatePostsList(postsReference)
                        true
                    }
                    R.id.ceramics -> {
                        var postsReference = firebaseDb.collection("posts")
                            .limit(20)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                        postsReference = postsReference.whereEqualTo("type", "Ceramics")
                        updatePostsList(postsReference)
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }



//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.homebutton -> {
//                    Timber.i("home button pressed")
//                    println("home button pressed")
//                    // startActivity(Intent(applicationContext, MainActivity::class.java))
//                    // bottomNavigationView.selectedItemId = R.id.homebutton
//                    //overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.additembutton -> {
//                    Timber.i("add button pressed")
//                    println("add button pressed")
//
//                    startActivity(Intent(applicationContext, AddPost::class.java))
//                    //item.setIcon(R.drawable.profile__1_)
//                    overridePendingTransition(0, 0)
//
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.profilebutton -> {
//                    startActivity(Intent(applicationContext, Profile::class.java))
//                    overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                else -> return@setOnNavigationItemSelectedListener false
//            }
//        }


        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(origin.wit.socialmediaart.R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            origin.wit.socialmediaart.R.id.profile -> {
                val launcherIntent = Intent(this, Profile ::class.java)
                launcherIntent.putExtra(EXTRA_USEREMAIL, signedInUser?.userEmail)
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
        launcherIntent.putExtra("DOCUMENTID",post.Id)
        getClickResult.launch(launcherIntent)
    }







}

interface PostListener {
    fun onPostClick(post: Post)
}

//class PostAdapter constructor(private var posts: List<Post>,private val listener:PostListener) :
//    RecyclerView.Adapter<PostAdapter.MainHolder>() {

class PostAdapter constructor(val context: Context, val posts:List<Post>,private val listener:PostListener)
    : RecyclerView.Adapter<PostAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = PostCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        // val post = posts[holder.adapterPosition]
        holder.bind(posts[position],listener)
    }

    override fun getItemCount(): Int = posts.size

    inner class MainHolder(private val binding: PostCardBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(post: Post, listener: PostListener) {

            binding.postCreator.text = post.user?.userEmail
            binding.postdescription.text = post.description
            binding.pricedisplay.text = post.price.toString()
            binding.postTitle.text = post.title
            binding.postKey.text = post.Id
            Glide.with(context).load(post.imageUrl).into(binding.imageView)

            //using instagrams timestamp
            binding.TimeStampView.text = TimeAgo.using(post.timestamp)

            if(context.javaClass == MainActivity::class.java){
                binding.DeletePostBtn.visibility = View.GONE
            }
            else if (context.javaClass == Profile::class.java) {
                binding.DeletePostBtn.visibility = View.VISIBLE
            }

            //delete button
            binding.DeletePostBtn.setOnClickListener() {
                val postKey = binding.postKey.text.toString()
                val postRef = FirebaseFirestore.getInstance().collection("posts").document(
                    postKey)
                postRef.delete()



            }
            binding.root.setOnClickListener { listener.onPostClick(post) }




        }

    }
}