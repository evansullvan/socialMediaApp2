package origin.wit.socialmediaart.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityProfileBinding
import origin.wit.socialmediaart.databinding.PostCardBinding
import origin.wit.socialmediaart.databinding.ProfilePostCardBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.Post
import origin.wit.socialmediaart.models.User
import timber.log.Timber

private const val TAG = "ProfileActivity"

class Profile : AppCompatActivity(),PostListener {

    //lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var binding: ActivityProfileBinding
    lateinit var socialmediaapp: MainApp
    val currentUser = Firebase.auth.currentUser
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDb:FirebaseFirestore
    private lateinit var posts:MutableList<Post>
    private lateinit var adapter: PostAdapter
    var signedInUser: User?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  bottomNavigationView = findViewById(R.id.bottomNavbar)
        //bottomNavigationView = binding.bottomNavbar
        socialmediaapp = application as MainApp
//        val layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.layoutManager = layoutManager
//      //  binding.recyclerView.adapter = PostAdapter(socialmediaapp.posts.findPostWithEmail(auth.currentUser?.email.toString()), this)
//        binding.recyclerView.adapter = PostAdapter(this,socialmediaapp.posts.findAll())

       // binding.recyclerView.setHasFixedSize(true)
        firebaseDb = FirebaseFirestore.getInstance()
        posts = mutableListOf()
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

        var postsReference = firebaseDb.collection("posts")
            .limit(20)
            .orderBy("timestamp", Query.Direction.DESCENDING)
        val useremail = intent.getStringExtra(EXTRA_USEREMAIL)
                //val useremail = signedInUser?.userEmail
        if(useremail != null) {
            postsReference = postsReference.whereEqualTo("user.userEmail", useremail)
        }

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

        Timber.plant(Timber.DebugTree())
        auth = Firebase.auth

        if (currentUser != null) {
            binding.userEmailDisplay.setText(currentUser.email)
        } else {
            // No user is signed in
        }

        binding.signoutBTN.setOnClickListener {
            val builder = AlertDialog.Builder(this@Profile)
            builder.setMessage("Are you sure you want to Sign out?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    auth.signOut()
                    startActivity(Intent(applicationContext, Signup::class.java))
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

        }

        binding.addItem.setOnClickListener {
            startActivity(Intent(applicationContext, AddPost::class.java))
        }






//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//
//            when (item.itemId) {
//                R.id.homebutton -> {
//                    Timber.i("home button pressed")
//                    println("home button pressed")
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.additembutton -> {
//                    Timber.i("add button pressed")
//                    println("add button pressed")
//                     startActivity(Intent(applicationContext, AddPost::class.java))
//                    // bottomNavigationView.selectedItemId = R.id.additembutton
//                     overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.profilebutton -> {
//                    //startActivity(Intent(applicationContext, Profile::class.java))
//                    overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                else -> return@setOnNavigationItemSelectedListener false
//            }
//        }




    }

    override fun onPostClick(post: Post) {

    }

//
}

