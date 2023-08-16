package bpplatform.howtalk.chat

data class User(
    val name: String,
    val email: String,
    val uId: String,
    var timestamp: String?
){
    constructor(): this("", "", "", timestamp="")
    constructor(name: String, email: String, uId: String) : this()
}


