package hr.ferit.igorkuridza.taskiefragments.ui.fragments.add

import android.text.TextUtils
import android.widget.ArrayAdapter
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.common.displayToast
import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task
import hr.ferit.igorkuridza.taskiefragments.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskiefragments.persistence.TaskPrefs
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragmentDialog
import kotlinx.android.synthetic.main.fragment_add_task_fragment_dialog.*

class AddTaskFragmentDialog : BaseFragmentDialog() {

    private var taskAddedListener: TaskAddedListener? = null
    private var currentPriority = TaskPrefs.getPriority(TaskPrefs.PREFERENCE_PRIORITY, 0)
    private val repository = TaskRoomRepository()

    override fun getLayoutResourceId() = R.layout.fragment_add_task_fragment_dialog

    override fun setUpListeners() {
        saveTaskAction.setOnClickListener { saveTask() }
    }

    override fun setUpUi() {
        context?.let {
            prioritySelector.adapter = ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,Priority.values())
            prioritySelector.setSelection(currentPriority)
        }

    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
        prioritySelector.setSelection(0)
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(newTaskTitleInput.text) || TextUtils.isEmpty(newTaskDescriptionInput.text)

    private fun savePriorityIntoPrefs(priority: Priority){
        val selection = when(priority){
            Priority.LOW -> 0
            Priority.MEDIUM -> 1
            Priority.HIGH -> 2
        }
        TaskPrefs.store(TaskPrefs.PREFERENCE_PRIORITY, selection)
    }

    private fun saveTask() {
        if(isInputEmpty()){
            context?.displayToast(R.string.emptyFields)
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority
        val task = Task(title = title,description =  description,priority =  priority)

        taskAddedListener?.onTaskAdded()
        repository.addTask(task)
        savePriorityIntoPrefs(priority)
        clearUi()
        dismiss()
    }

    fun setTaskAddedListener(taskAddedListener: TaskAddedListener){
        this.taskAddedListener = taskAddedListener
    }

    companion object{
        fun newInstance() = AddTaskFragmentDialog()
    }
}
