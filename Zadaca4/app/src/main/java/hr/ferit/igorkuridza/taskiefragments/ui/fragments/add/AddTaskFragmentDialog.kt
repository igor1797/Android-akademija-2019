package hr.ferit.igorkuridza.taskiefragments.ui.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.common.displayToast
import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.persistence.Repository
import kotlinx.android.synthetic.main.fragment_add_task_fragment_dialog.*

class AddTaskFragmentDialog : DialogFragment() {

    private var taskAddedListener: TaskAddedListener? = null
    private val repository = Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task_fragment_dialog,container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        setUpListeners()
    }
    private fun clearUi() {
        newTaskTitleInput.text.clear()
        newTaskDescriptionInput.text.clear()
        prioritySelector.setSelection(0)
    }

    private fun isInputEmpty(): Boolean = TextUtils.isEmpty(newTaskTitleInput.text) || TextUtils.isEmpty(newTaskDescriptionInput.text)

    private fun saveTask() {
        if(isInputEmpty()){
            context?.displayToast(R.string.emptyFields)
            return
        }

        val title = newTaskTitleInput.text.toString()
        val description = newTaskDescriptionInput.text.toString()
        val priority = prioritySelector.selectedItem as Priority
        val task = repository.save(title, description, priority)

        clearUi()

        taskAddedListener?.onTaskAdded(task)
        dismiss()
    }

    private fun setUpListeners() {
        saveTaskAction.setOnClickListener { saveTask() }
    }

    private fun setUpUi() {
        context?.let {
            prioritySelector.adapter = ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,Priority.values())
            prioritySelector.setSelection(0)
        }
    }

    fun setTaskAddedListener(taskAddedListener: TaskAddedListener){
        this.taskAddedListener = taskAddedListener
    }

    companion object{
        fun newInstance() = AddTaskFragmentDialog()
    }
}
