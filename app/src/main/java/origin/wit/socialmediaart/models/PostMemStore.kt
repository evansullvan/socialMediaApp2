package origin.wit.socialmediaart.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PostMemStore:PostStore {
    val posts = ArrayList<Post>()

    override fun findAll(): List<Post> {
        return posts
    }

    fun findPostWithEmail(email:String): List<Post>{
//        var postholder:Post = Post()
//        val emailPostsarray = ArrayList<Post>()
//
//        for(post in posts){
//            if(post.userEmail.equals(email)){
//                emailPostsarray.add(post)
//            }
//        }
//        return emailPostsarray
        return ArrayList<Post>()
    }

    override fun create(post: Post) {
//        post.id = getId()
//        posts.add(0,post)

    }

    override fun update(post: Post) {
//        var foundPost: Post? = posts.find { p -> p.id == post.id }
//        if (foundPost != null) {
//            foundPost.title = post.title
//            foundPost.description = post.description
//            foundPost.price = post.price
//            foundPost.forSale = post.forSale
//            foundPost.type = post.type
//
//            logAll()
//        }
    }

    fun remove(post: Post) {
        posts.remove(post)
    }

    private fun logAll() {
        posts.forEach { i("$it") }
    }
}