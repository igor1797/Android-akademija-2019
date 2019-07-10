package hr.ferit.igorkuridza.taskie.ui.fragments.details

import android.os.Bundle
import android.view.View
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.*
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.networking.BackendFactory
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragment
import hr.ferit.igorkuridza.taskie.ui.fragments.changetask.ChangeTaskFragmentDialog
import hr.ferit.igorkuridza.taskie.ui.fragments.changetask.TaskChangedListener
import kotlinx.android.synthetic.main.fragment_task_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskDetailsFragment : BaseFragment(), TaskChangedListener {

    private val repository: TaskRepository = TaskRoomRepository()
    private val interactor = BackendFactory.getTaskieInteractor()
    private var taskID: String = NO_TASK


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_task_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
        initListeners()
    }

    override fun onTaskChanged() {
        detailsTaskTitle.text = repository.getTask(taskID).title
        detailsTaskDescription.text = repository.getTask(taskID).content
        detailsPriorityView.setBackgroundResource(convertPriority(repository.getTask(taskID).taskPriority).getColor())
    }

    private fun initListeners(){
        btnChangeTask.setOnClickListener{
            changeTask(repository.getTask(taskID))
        }
    }

    private fun changeTask(task: BackendTask) {
        val dialog = ChangeTaskFragmentDialog.newInstance()
        dialog.setTaskChangedListener(this)
        dialog.task = task
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun tryDisplayTask(id: String) {
            interactor.getTaskById(id,getTaskCallback())
    }

    private fun displayTask(task: BackendTask?) {
        if(task != null) {
            detailsTaskTitle.text = task.title
            detailsTaskDescription.text = task.content
            detailsPriorityView.setBackgroundResource(convertPriority(task.taskPriority).getColor())
        }
    }

    private fun getTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            handleOnFailure()
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> displayTask(response.body())
                }
            }
            else
                activity?.displayToast(convertCodeToMessage(response.code()))
        }
    }

    private fun handleOnFailure() {
        activity?.displayToast("No internet connection")
        displayTask(repository.getTask(taskID))
    }

    companion object {
        const val NO_TASK: String = ""

        fun newInstance(taskId: String): TaskDetailsFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
