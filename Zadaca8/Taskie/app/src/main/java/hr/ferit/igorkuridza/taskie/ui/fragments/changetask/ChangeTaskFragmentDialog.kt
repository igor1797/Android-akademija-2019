package hr.ferit.igorkuridza.taskie.ui.fragments.changetask

import android.text.TextUtils
import android.widget.EditText
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.convertCodeToMessage
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.priorityFactory
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.model.response.EditTaskResponse
import hr.ferit.igorkuridza.taskie.presentation.ChangeTaskFragmentDialogPresenter
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragmentDialog
import kotlinx.android.synthetic.main.fragment_dialog_change_task.*
import org.koin.android.ext.android.inject

class ChangeTaskFragmentDialog: BaseFragmentDialog(), ChangeTaskContract.View{

    private val presenter by inject<ChangeTaskContract.Presenter>()
    private var taskChangedListener : TaskChangedListener? = null
    private var priority: Int = 0
    lateinit var task: BackendTask
    lateinit var title: EditText
    lateinit var description: EditText

    override fun getLayoutResourceId() = R.layout.fragment_dialog_change_task

    fun setTaskChangedListener (listener: TaskChangedListener){
        taskChangedListener = listener
    }

    override fun initUI() {
        presenter.setView(this)
        title = view!!.findViewById(R.id.newTaskTitleChange)  //ne znam zasto, ali dok ovo nisam napravio bacalo mi je null pointer gresku
        description = view!!.findViewById(R.id.newTaskDescriptionChange)
        title.setText(task.title)
        description.setText(task.content)
        val selection = task.taskPriority - 1
        prioritySelectorChange.setSelection(selection)
    }

    override fun initListeners() {
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

        presenter.onEditTask(task.id, title, content, priority)
        dismiss()
    }

    override fun onNoInternetConnection() {
        activity?.displayToast(R.string.noInternetText)
    }

    override fun onChangeTaskSuccessful(editTaskResponse: EditTaskResponse) {
        activity?.displayToast(editTaskResponse.message)
        taskChangedListener?.onTaskChanged()
        dismiss()
    }

    override fun onSomethingWentWrong(code: Int) {
        activity?.displayToast(convertCodeToMessage(code))
    }

    private fun isInputEmpty(): Boolean =
        TextUtils.isEmpty(title.text) || TextUtils.isEmpty(description.text)

    companion object {
        fun newInstance() = ChangeTaskFragmentDialog()
    }
}
