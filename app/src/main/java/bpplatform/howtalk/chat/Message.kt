package bpplatform.howtalk.chat

data class Message(
    val message: String?,
    var sendId: String?
){
    constructor():this("","")
}

