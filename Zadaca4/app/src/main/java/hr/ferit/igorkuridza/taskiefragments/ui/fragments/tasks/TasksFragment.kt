package hr.ferit.igorkuridza.taskiefragments.ui.fragments.tasks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.persistence.Repository
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragment
import hr.ferit.igorkuridza.taskiefragments.ui.adapters.TaskAdapter
import hr.ferit.igorkuridza.taskiefragments.common.EXTRA_SCREEN_TYPE
import hr.ferit.igorkuridza.taskiefragments.common.EXTRA_TASK_ID
import hr.ferit.igorkuridza.taskiefragments.common.gone
import hr.ferit.igorkuridza.taskiefragments.common.visible
import hr.ferit.igorkuridza.taskiefragments.model.Task
import hr.ferit.igorkuridza.taskiefragments.ui.activities.ContainerActivity
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.add.AddTaskFragmentDialog
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.add.TaskAddedListener
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), TaskAddedListener {
    private val repository = Repository

    private val adapter by lazy { TaskAdapter{onItemSelected(it)} }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initListeners()
        refreshTasks()
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
    }

    private fun addTask(){
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun onItemSelected(task: Task){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID,task.id)
        }
        startActivity(detailsIntent)
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun refreshTasks() {
        progress.gone()
        val data = repository.getAllTasks()
        if(data.isNotEmpty())
            noData.gone()
        else
            noData.visible()
        adapter.setData(data)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    companion object {
        fun newInstance() = TasksFragment()
    }
}
