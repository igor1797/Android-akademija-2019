package hr.ferit.igorkuridza.taskie.ui.fragments.addtask

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.common.convertCodeToMessage
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.toRoomTask
import hr.ferit.igorkuridza.taskie.model.BackendPriorityTask
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.networking.BackendFactory
import hr.ferit.igorkuridza.taskie.persistence.TaskPrefs
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import kotlinx.android.synthetic.main.fragment_dialog_new_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentDialog: DialogFragment() {

    private var taskAddedListener: TaskAddedListener? = null
    private val repository = TaskRoomRepository()

    private val interactor = BackendFactory.getTaskieInteractor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_new_task, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    fun setTaskAddedListener(listener: TaskAddedListener){
        taskAddedListener = listener
    }

    private fun initUi(){
        context?.let {
            prioritySelector.adapter = ArrayAdapter<BackendPriorityTask>(it,
                android.R.layout.simple_spinner_dropdown_item,
                BackendPriorityTask.values())
        }
        when(getLastPriority()){
            "LOW" -> prioritySelector.setSelection(0)
            "MEDIUM" -> prioritySelector.setSelection(1)
            "HIGH" -> prioritySelector.setSelection(2)
        }
    }

    private fun initListeners(){
        saveTaskAction.setOnClickListener{
            saveTask()
        }
    }

    private fun saveTask() {
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val backendPriorityTask: BackendPriorityTask = prioritySelector.selectedItem as BackendPriorityTask

        saveLastPriority()

        interactor.save(
            AddTaskRequest(title, description, backendPriorityTask.getValue()),
            addTaskCallback(title,description,backendPriorityTask.getValue())
        )
        clearUi()
        dismiss()
    }

    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
    }

    private fun isInputEmpty(): Boolean = isEmpty(newTaskTitleInput.text) || isEmpty(newTaskDescriptionInput.text)

    private fun saveLastPriority() {
        TaskPrefs.store("LAST_CHOSEN_PRIORITY", prioritySelector.selectedItem.toString())
    }

    private fun getLastPriority(): String? {
        return TaskPrefs.getString("LAST_CHOSEN_PRIORITY", "LOW")
    }

    private fun addTaskCallback(title: String, description: String, priorityTask: Int): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            handleNoInternetConnection(title,description,priorityTask)
        }
        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let { handleOkResponse(it) }
                }
            }  else
                activity?.displayToast(convertCodeToMessage(response.code()))
        }
    }

    private fun handleNoInternetConnection(title: String,description: String,priorityTask: Int){
        var taskId = "0"
        if (repository.getTasks().isNotEmpty())
            taskId = repository.getTasks().last().id +1
        val task = BackendTask(taskId,title,description,priorityTask,"", false, isCompleted = false, isSent = false)
        repository.addTask(task)
        taskAddedListener?.onTaskAdded(task)
        activity?.displayToast(getString(R.string.noInternetText))
    }

    private fun handleOkResponse(task: BackendTask) {
        repository.addTask(toRoomTask(task))
        taskAddedListener?.onTaskAdded(task)
    }

    companion object{
        fun newInstance(): AddTaskFragmentDialog {
            return AddTaskFragmentDialog()
        }
    }
}