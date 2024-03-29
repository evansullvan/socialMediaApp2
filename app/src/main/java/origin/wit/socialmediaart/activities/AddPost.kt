package origin.wit.socialmediaart.activities



import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.w3c.dom.Document
import origin.wit.socialmediaart.R
import origin.wit.socialmediaart.databinding.ActivityAddPostBinding
import origin.wit.socialmediaart.databinding.ActivityMainBinding
import origin.wit.socialmediaart.main.MainApp
import origin.wit.socialmediaart.models.Post
import origin.wit.socialmediaart.models.User

import timber.log.Timber
import timber.log.Timber.i


class AddPost : AppCompatActivity() {
    //lateinit var bottomNavigationView: BottomNavigationView

    lateinit var socialmediaapp: MainApp
    private var post = Post()
    private lateinit var binding: ActivityAddPostBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var firestoreDB: FirebaseFirestore
    var signedInUser: User?=null
    val PICK_PHOTO_CODE = 1234
    var photoUri: Uri? = null
    var TAG = "addPost"


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)

        setContentView(binding.root)
        //bottomNavigationView = findViewById(R.id.bottomNavbar)
        // bottomNavigationView = binding.bottomNavbar
        socialmediaapp = application as MainApp
        Timber.plant(Timber.DebugTree())
        auth = Firebase.auth
        val currentUser = Firebase.auth.currentUser
        storageReference = FirebaseStorage.getInstance().reference
        firestoreDB = FirebaseFirestore.getInstance()


        val DocumentID = intent.getStringExtra("DOCUMENTID")

        val postRef = firestoreDB.collection("posts").document(DocumentID.toString())


        binding.topTextView.text = "Create new post"
        binding.postButton.text = "Post"

        val userId = FirebaseAuth.getInstance().currentUser?.uid as String
        firestoreDB.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    signedInUser = userSnapshot.toObject(User::class.java)
                    if (signedInUser != null) {
                        i("User with ID $userId is: " + signedInUser.toString())
                        // do something with the signed-in user object
                    } else {
                        Timber.e("Unable to retrieve signed-in user object")
                    }
                } else {
                    Timber.e("User with ID $userId does not exist")
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
//                    // startActivity(Intent(applicationContext, AddPost::class.java))
//                    // bottomNavigationView.selectedItemId = R.id.additembutton
//                    // overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                R.id.profilebutton -> {
//                    i("signed in user: " + signedInUser)
//                    startActivity(Intent(applicationContext, Profile::class.java))
//                    overridePendingTransition(0, 0)
//                    return@setOnNavigationItemSelectedListener true
//                }
//                else -> return@setOnNavigationItemSelectedListener false
//            }
//        }


                Timber.i("post Activity started...")
                //val pricebutton = binding.sellable.isChecked

                //drop down array
                val artForms = arrayOf("Painting", "Drawing", "Sculpture", "Printmaking", "Photography", "Film", "Architecture", "Design", "Textiles", "Ceramics")

                //val spinner = findViewById<Spinner>(R.id.arttypeSpinner)
                val spinner = binding.arttypeSpinner
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, artForms)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedValue = parent?.getItemAtPosition(position).toString()
                        post.type = selectedValue
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        post.type = null.toString()
                    }
                }

                binding.priceField.isVisible = false
                binding.sellableswitch.setOnClickListener{
                    if (binding.sellableswitch.isChecked){
                        post.forSale = true
                        binding.priceField.isVisible = true

                    }else{
                        post.forSale = false
                        binding.priceField.isVisible = false

                    }
                }




                //edit post
                if (intent.hasExtra("post_edit")) {
                    post = intent.extras?.getParcelable("post_edit")!!
                    binding.postTitle.setText(post.title)
                    binding.postDesc.setText(post.description)




                    //            post.price?.let { binding.priceField.setText(it) }
                    if(post.forSale){
                        binding.sellableswitch.setChecked(true)
                        // binding.priceField.setText(post.price)
                    }else{
                        binding.sellableswitch.setChecked(false)
                    }
                    binding.arttypeSpinner.setSelection(adapter.getPosition(post.type.toString()))
                    binding.topTextView.text = "Update post"
                    binding.postButton.text = "Update"
                    binding.documentID.text = DocumentID
                    post.timestamp = post.timestamp

                    if(post.price!! >0){
                        binding.priceField.isVisible = true
                        // binding.priceField.setText(post.price)
                    }else{
                        binding.priceField.isVisible = false
                        // binding.priceField.setText(0)
                    }
//            binding.postButton.setOnClickListener() {
//                post.title = binding.postTitle.text.toString()
//                post.id = post.id
//                post.forSale = binding.sellableswitch.toString().toBoolean()
//                post.description = binding.postDesc.text.toString()
//                post.type = binding.arttypeSpinner.toString()
//                post.price = binding.priceField.text.toString().toInt()
////                if(binding.sellableswitch.isChecked){
////                    post.price = binding.priceField.text.toString().toInt()
////                }else {
////                    post.price = 0
////                }
//                socialmediaapp.posts.update(Post(post.id,post.title,post.description,post.type,post.forSale,post.price))
//                socialmediaapp.posts.posts.forEach {
//                    Timber.i(it.toString())
//                }
//            }
                }

                binding.imagePickerBtn.setOnClickListener {
                    val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
                    imagePickerIntent.type = "image/*"
                    if(imagePickerIntent.resolveActivity(packageManager)!=null){
                        startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)


                    }
                }




                binding.postButton.setOnClickListener() {
                    Timber.i("add Button Pressed: ")

//            if (binding.postTitle.text.toString().isEmpty() == true) {
//                Snackbar.make(it, "please dont leave the title empty", Snackbar.LENGTH_LONG).show()
//            }
//            if (binding.postDesc.text.toString().isEmpty() == true) {
//                Snackbar.make(it, "please dont leave the description empty", Snackbar.LENGTH_LONG)
//                    .show()
//            }
//            if (binding.priceField.text.toString().isEmpty() == true || post.price!! < 0) {
//                Snackbar.make(it, "please dont leave the price empty", Snackbar.LENGTH_LONG).show()
//            } else {


                    post.title = binding.postTitle.text.toString()
                    post.description = binding.postDesc.text.toString()



                    post.timestamp = post.timestamp
                    if (binding.sellableswitch.isChecked) {
                        post.price = binding.priceField.text.toString().toInt()

                    } else {
                        post.price = 0
                    }
                    //post.userEmail = auth.currentUser?.email


                    if (title.isNotEmpty() && post.description!!.isNotEmpty()) {
                        if (binding.postButton.text == "UPDATE") {
                            i("using update method")

                            val updatedPost = mapOf(
                                "title" to post.title,
                                "description" to post.description,
                                "type" to post.type,
                                "forSale" to post.forSale,
                                "price" to post.price
                            )
//                        Post(DocumentID,post.title, post.description, post.type, post.forSale, post.price)
                            postRef.update(updatedPost)
                            startActivity(Intent(applicationContext, MainActivity::class.java))

                            // socialmediaapp.posts.update(Post( post.title, post.description, post.type, post.forSale, post.price))
                            //socialmediaapp.posts.update(post.copy())
                        } else {
//                    i("using create method")
//                    // socialmediaapp.posts.create(post.copy())
//                    val newPost = Post(
//                        post.Id,
//                        post.imageUrl,
//                        post.title,
//                        post.description,
//                        post.type,
//                        post.forSale,
//                        post.price,
//                        signedInUser
//                    )
//                    firestoreDB.collection("posts").add(newPost)
//                        .addOnSuccessListener { postCreateTask ->
//
//                            Snackbar.make(it, "Success", Snackbar.LENGTH_LONG).show()
////                            val documentId = postCreateTask.id
////                            // Store the document ID in the Post object
////                            newPost.documentId = documentId
//                            val profileIntent = Intent(applicationContext, Profile::class.java)
//                            profileIntent.putExtra(EXTRA_USEREMAIL, signedInUser?.userEmail)
//                            //startActivity(Intent(applicationContext, MainActivity::class.java))
//                            setResult(RESULT_OK)
//                            finish()
//
//                        }
//                        .addOnFailureListener { postFailTaask ->
//                            Snackbar.make(it, "Couldn't upload Post", Snackbar.LENGTH_LONG).show()
//                            Timber.i("added unsuccessfully")
//                        }
//
//                    Timber.i("added post: " + post.type + " " + post.title)
                            val photoUploadUri = photoUri as Uri
                            val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo")
                            photoReference.putFile(photoUploadUri)
                                .continueWithTask{ photoUploadTask ->
                                    photoReference.downloadUrl
                                }.continueWithTask { downloadUrlTask ->
                                    val newPost = Post(
                                        post.Id,
                                        downloadUrlTask.result.toString(),
                                        post.title,
                                        post.description,
                                        post.type,
                                        post.forSale,
                                        post.price,
                                        signedInUser


                                    )
                                    firestoreDB.collection("posts").add(newPost)


                                }.addOnCompleteListener { postCreationTask ->
                                    if(!postCreationTask.isSuccessful){
                                        Log.e(TAG, "error",postCreationTask.exception)
                                        Toast.makeText(this,"failed to create post",Toast.LENGTH_SHORT).show()
                                    }

                                    startActivity(Intent(applicationContext, MainActivity::class.java))
                                    finish()
                                }
                        }


                    } else {
                        Snackbar.make(it, "please dont leave empty fields", Snackbar.LENGTH_LONG).show()
                    }
                    //  }
                }
            }
//        val photoUloadUri = photoUri as Uri
//       val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")
//        photoReference.putFile(photoUloadUri)
//            .continueWithTask{ photoUploadTask ->
//                    photoReference.downloadUrl
//            }.continueWithTask { downloadUrlTask ->
//                val newPost = Post(
//                    post.Id,
//                    downloadUrlTask.result.toString(),
//                    post.title,
//                    post.description,
//                    post.type,
//                    post.forSale,
//                    post.price,
//                    signedInUser
//
//                )
//                firestoreDB.collection("posts").add(newPost)
//
//
//            }.addOnCompleteListener { postCreationTask ->
//                if(!postCreationTask.isSuccessful){
//                    Log.e(TAG, "error",postCreationTask.exception)
//                    Toast.makeText(this,"failed to create post",Toast.LENGTH_SHORT).show()
//                }
//
//                startActivity(Intent(applicationContext, MainActivity::class.java))
//                finish()
//            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_PHOTO_CODE ){
            if(resultCode == Activity.RESULT_OK){
                photoUri = data?.data
                binding.imageView3.setImageURI(photoUri)
            }else{
                Toast.makeText(this,"Image picker closed",Toast.LENGTH_SHORT).show()
            }
        }
    }




}


