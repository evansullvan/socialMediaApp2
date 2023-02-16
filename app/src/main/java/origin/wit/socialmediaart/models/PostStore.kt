package origin.wit.socialmediaart.models

interface PostStore {
    fun findAll(): List<Post>
    fun create(post: Post)

    fun update(post: Post)
}