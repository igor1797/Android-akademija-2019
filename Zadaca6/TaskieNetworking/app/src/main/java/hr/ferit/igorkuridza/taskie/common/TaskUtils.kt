package hr.ferit.igorkuridza.taskie.common

import hr.ferit.igorkuridza.taskie.model.BackendTask

fun toRoomTask(task: BackendTask): BackendTask {
    return BackendTask(task.id,task.title,task.content,task.taskPriority,task.userId,task.isFavorite,task.isCompleted, isSent = true)
}