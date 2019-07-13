package hr.ferit.igorkuridza.taskie.ui.fragments.addtask

import android.text.TextUtils.isEmpty
import android.widget.ArrayAdapter
import hr.ferit.igorkuridza.taskie.presentation.AddTaskFragmentPresenter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.convertCodeToMessage
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.model.BackendPriorityTask
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.persistence.TaskPrefs
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragmentDialog
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import org.koin.android.ext.android.inject

class AddTaskFragmentDialog: BaseFragmentDialog(), AddTaskContract.View {

    private val presenter by inject<AddTaskContract.Presenter>()
    private var taskAddedListener: TaskAddedListener? = null

    override fun getLayoutResourceId() = R.layout.fragment_dialog_new_task

    fun setTaskAddedListener(listener: TaskAddedListener){
        taskAddedListener = listener
    }

    override fun initUI(){
        presenter.setView(this)
        context?.let {
            prioritySelector.adapter = ArrayAdapter(it,
                android.R.layout.simple_spinner_dropdown_item,
                BackendPriorityTask.values())
        }
        when(getLastPriority()){
            "LOW" -> prioritySelector.setSelection(0)
            "MEDIUM" -> prioritySelector.setSelection(1)
            "HIGH" -> prioritySelector.setSelection(2)
        }
    }

    override fun initListeners(){
        saveTaskAction.setOnClickListener{
            saveTask()
        }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(R.string.emptyFields)
            return
        }
        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val backendPriorityTask: BackendPriorityTask = prioritySelector.selectedItem as BackendPriorityTask
        saveLastPriority()
        presenter.onTaskAdded(title, description, backendPriorityTask.getValue())
        clearUi()
        dismiss()
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
    }

    private fun isInputEmpty(): Boolean = isEmpty(newTaskTitleInput.text) || isEmpty(newTaskDescriptionInput.text)

    private fun saveLastPriority() = TaskPrefs.store("LAST_CHOSEN_PRIORITY", prioritySelector.selectedItem.toString())

    private fun getLastPriority(): String? = TaskPrefs.getString("LAST_CHOSEN_PRIORITY", "LOW")

    override fun onNoInternetConnection(task: BackendTask){
        taskAddedListener?.onTaskAdded(task)
        activity?.displayToast(R.string.noInternetText)
    }

    override fun onAddTaskSuccessful(task: BackendTask) {
        taskAddedListener?.onTaskAdded(task)
    }

    override fun onSomethingWentWrong(code: Int){
        activity?.displayToast(convertCodeToMessage(code))
    }

    companion object{
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }
}