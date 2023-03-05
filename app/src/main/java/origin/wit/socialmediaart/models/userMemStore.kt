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

}