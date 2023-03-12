package origin.wit.socialmediaart.models




class userMemStore {


    var users: HashMap<String, User> = HashMap()

    fun addUser(user:User) {
//        user.id = getId()
//        users[user.userEmail] = user

    //users.put(user.userEmail,user)
    }

    fun remove(user: User) {
        users.remove(user.userEmail)
    }

     fun getAllUsers(): HashMap<String,User> {
        return users
    }

    fun getUser(useremail:String): User {
        var userholder:User = User()

       for(user in users){
           if(user.key.equals(useremail)){
               userholder = user.value
           }
       }
        return userholder
    }

}

private operator fun <K, V> HashMap<K, V>.set(userEmail: K?, value: V) {

}
