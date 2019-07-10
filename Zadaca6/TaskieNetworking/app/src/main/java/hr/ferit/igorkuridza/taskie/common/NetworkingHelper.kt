package hr.ferit.igorkuridza.taskie.common

fun convertCodeToMessage(code: Int): String {
    return when(code){
        RESPONSE_BAD_REQUEST -> "Bad request!"
        RESPONSE_NOT_FOUND -> "Task not found!"
        UNAUTHORIZED_ACCESS -> "Unauthorized access!"
        SERVER_ERROR -> "Server error!"
        SERVICE_UNAVAILABLE -> "Service unavailable!"
        else -> "Unknown error!"
    }
}