package hr.ferit.igorkuridza.taskie.ui.fragments.details

import android.os.Bundle
import android.view.View
import hr.ferit.igorkuridza.taskie.presentation.TaskDetailsFragmentPresenter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.*
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragment
import hr.ferit.igorkuridza.taskie.ui.fragments.changetask.ChangeTaskFragmentDialog
import hr.ferit.igorkuridza.taskie.ui.fragments.changetask.TaskChangedListener
import kotlinx.android.synthetic.main.fragment_task_details.*
import org.koin.android.ext.android.inject

class TaskDetailsFragment : BaseFragment(), TaskChangedListener, TaskDetailsContract.View {

    private val presenter by inject<TaskDetailsContract.Presenter>()
    private var taskID: String = NO_TASK

    override fun getLayoutResourceId() = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.setView(this)
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        initListeners()
    }

    override fun onTaskChanged() {
        detailsTaskTitle.text = presenter.onGetTaskById(taskID).title
        detailsTaskDescription.text = presenter.onGetTaskById(taskID).content
        detailsPriorityView.setBackgroundResource(convertPriority(presenter.onGetTaskById(taskID).taskPriority).getColor())
    }

    private fun initListeners(){
        btnChangeTask.setOnClickListener{
            changeTask(presenter.onGetTaskById(taskID))
        }
    }

    private fun changeTask(task: BackendTask) {
        val dialog = ChangeTaskFragmentDialog.newInstance()
        dialog.setTaskChangedListener(this)
        dialog.task = task
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun tryDisplayTask(id: String) {
            presenter.onTryDisplayTask(id)
    }

    private fun displayTask(task: BackendTask?) {
        if(task != null) {
            detailsTaskTitle.text = task.title
            detailsTaskDescription.text = task.content
            detailsPriorityView.setBackgroundResource(convertPriority(task.taskPriority).getColor())
        }
    }

    override fun onNoInternetConnection() {
        activity?.displayToast(R.string.noInternetText)
        displayTask(presenter.onGetTaskById(taskID))
    }

    override fun onGetTaskSuccessful(task: BackendTask?) {
        displayTask(task)
    }

    override fun onSomethingWentWrong(code: Int) {
        activity?.displayToast(convertCodeToMessage(code))
    }

    companion object {
        const val NO_TASK: String = ""

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
