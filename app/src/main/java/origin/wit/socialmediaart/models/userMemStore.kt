package origin.wit.socialmediaart.models




class userMemStore {


    var users: HashMap<String, User> = HashMap()

    fun addUser(user:User) {
        user.id = getId()
        users[user.userEmail] = user //users.put(user.userEmail,user)
    }

    fun remove(user: User) {
        users.remove(user.userEmail)
    }

     fun getAllUsers(): HashMap<String,User> {
        return users
    }

}

private operator fun <K, V> HashMap<K, V>.set(userEmail: K?, value: V) {

}
