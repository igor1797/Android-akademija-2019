package hr.ferit.igorkuridza.taskiefragments.model

data class Task(
    var id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)