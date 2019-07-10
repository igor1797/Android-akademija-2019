package hr.ferit.igorkuridza.taskie.ui.fragments.changetask

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.common.convertCodeToMessage
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.priorityFactory
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.model.request.EditTaskRequest
import hr.ferit.igorkuridza.taskie.model.response.EditTaskResponse
import hr.ferit.igorkuridza.taskie.networking.BackendFactory
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import kotlinx.android.synthetic.main.fragment_dialog_change_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeTaskFragmentDialog: DialogFragment(){

    private val interactor = BackendFactory.getTaskieInteractor()

    private var repository: TaskRepository = TaskRoomRepository()
    private var taskChangedListener : TaskChangedListener? = null
    lateinit var task: BackendTask

    private var priority: Int = 0

    lateinit var title: EditText
    lateinit var description: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_change_task, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.newTaskTitleChange)  //ne znam zasto, ali dok ovo nisam napravio bacalo mi je null pointer gresku
        description = view.findViewById(R.id.newTaskDescriptionChange)

        initListeners()
        initUI()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun setTaskChangedListener (listener: TaskChangedListener){
        taskChangedListener = listener
    }

    private fun initUI() {
        title.setText(task.title)
        description.setText(task.content)
        val selection = task.taskPriority - 1
        prioritySelectorChange.setSelection(selection)
    }

    private fun initListeners() {
        saveTaskChanges.setOnClickListener {
            saveTaskChanges()
        }
    }

    private fun saveTaskChanges(){
        if (isInputEmpty()){
            context?.displayToast(getString(R.string.emptyFields))
            return
        }

        val title = title.text.toString()
        val content = description.text.toString()
        this.priority = prioritySelectorChange.priorityFactory().getValue()

        interactor.editTask(EditTaskRequest(task.id, title, content, priority), editTaskCallback())

        dismiss()
    }

    private fun editTaskCallback(): Callback<EditTaskResponse> = object : Callback<EditTaskResponse> {
        override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
            handleNoInternetConnectionResponse()
        }

        override fun onResponse(call: Call<EditTaskResponse>, response: Response<EditTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let {
                        handleOkResponse(it)
                    }
                }
            } else
                activity?.displayToast(convertCodeToMessage(response.code()))
        }
    }

    private fun handleNoInternetConnectionResponse() {
        activity?.displayToast("No internet connection")
    }

    private fun handleOkResponse(editTaskResponse: EditTaskResponse) {
        repository.updateTask(
            title.text.toString(),
            description.text.toString(),
            priority,
            task.id)
        activity?.displayToast(editTaskResponse.message)
        taskChangedListener?.onTaskChanged()
        dismiss()
    }

    private fun isInputEmpty(): Boolean =
        TextUtils.isEmpty(title.text) || TextUtils.isEmpty(description.text)

    companion object {
        fun newInstance() = ChangeTaskFragmentDialog()
    }
}
