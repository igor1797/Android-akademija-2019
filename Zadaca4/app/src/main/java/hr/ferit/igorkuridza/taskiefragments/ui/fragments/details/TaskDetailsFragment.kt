package hr.ferit.igorkuridza.taskiefragments.ui.fragments.details

import android.os.Bundle
import android.view.View
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.common.EXTRA_TASK_ID
import hr.ferit.igorkuridza.taskiefragments.common.displayToast
import hr.ferit.igorkuridza.taskiefragments.model.Task
import hr.ferit.igorkuridza.taskiefragments.persistence.Repository
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment : BaseFragment() {

    private val repository = Repository
    private var taskID = NO_TASK

    override fun getLayoutResourceId() = R.layout.fragment_task_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(EXTRA_TASK_ID)?.let { taskID = it }
        tryDisplayTask(taskID)
    }

    private fun tryDisplayTask(taskId: Int) {
        try {
            val task = repository.get(taskId)
            displayTask(task)
        } catch (e: NoSuchElementException){
            context?.displayToast(R.string.text_noTaskFound)
        }
    }

    private fun displayTask(task: Task) {
        detailsTaskTitle.text = task.title
        detailsTaskDescription.text = task.description
        detailsPriorityView.setBackgroundResource(task.priority.getColor())
    }

    companion object{
        const val NO_TASK = -1

        fun newInstance(taskId: Int): TaskDetailsFragment{
            val bundle = Bundle().apply { putInt(EXTRA_TASK_ID, taskId) }
            return TaskDetailsFragment().apply { arguments = bundle }
        }
    }
}
