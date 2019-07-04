package hr.ferit.igorkuridza.taskiefragments.ui.fragments.change

import android.os.Bundle
import android.text.TextUtils
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.common.EXTRA_TASK_ID
import hr.ferit.igorkuridza.taskiefragments.common.displayToast
import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task
import hr.ferit.igorkuridza.taskiefragments.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragmentDialog
import kotlinx.android.synthetic.main.fragment_change_task_fragment_dialog.*

class ChangeTaskFragmentDialog : BaseFragmentDialog() {

    private val repository = TaskRoomRepository()
    private var taskChangedListener : TaskChangedListener? = null
    private var taskId = NO_TASK

    override fun getLayoutResourceId() = R.layout.fragment_change_task_fragment_dialog

    override fun setUpUi() {
        arguments?.getInt(EXTRA_TASK_ID)?.let { taskId = it }

        val task = repository.getTaskById(taskId)

        taskTitleChange.append(task.title)
        taskDescriptionChange.append(task.description)

        val selection = when(task.priority){
            Priority.LOW -> 0
            Priority.MEDIUM -> 1
            Priority.HIGH -> 2
        }
        prioritySelectorChange.setSelection(selection)
    }

    override fun setUpListeners() {
        btnSaveTaskChanges.setOnClickListener {
            saveTaskChanges()
            dismiss()
        }
    }

    private fun saveTaskChanges(){
            if (isInputEmpty()){
                context?.displayToast(R.string.emptyFields)
                return
            }

            val title = taskTitleChange.text.toString()
            val description = taskDescriptionChange.text.toString()
            val priority =  getPrioritySelected()

            val changedTask = Task (title = title, description = description, priority = priority)
            taskChangedListener!!.onTaskChanged(changedTask)
            repository.updateTask(newTitle = title,newDescription = description, newPriority = priority, taskDbId = taskId)
    }

    private fun getPrioritySelected(): Priority{
        return when(prioritySelectorChange.selectedItemPosition){
            0 -> Priority.LOW
            1 -> Priority.MEDIUM
            2 -> Priority.HIGH
            else -> Priority.LOW
        }
    }

    fun setTaskChangedListener (listener: TaskChangedListener){
        taskChangedListener = listener
    }

    private fun isInputEmpty(): Boolean =
        TextUtils.isEmpty(taskTitleChange.text) || TextUtils.isEmpty(taskDescriptionChange.text)

    companion object{
        const val NO_TASK = -1

        fun newInstance(taskId: Int): ChangeTaskFragmentDialog{
            val bundle = Bundle().apply { putInt(EXTRA_TASK_ID,taskId) }
            return ChangeTaskFragmentDialog().apply { arguments = bundle }
        }
    }
}
