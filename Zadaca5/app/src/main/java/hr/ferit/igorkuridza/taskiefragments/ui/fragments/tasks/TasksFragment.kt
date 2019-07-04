package hr.ferit.igorkuridza.taskiefragments.ui.fragments.tasks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.common.*
import hr.ferit.igorkuridza.taskiefragments.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragment
import hr.ferit.igorkuridza.taskiefragments.ui.adapters.TaskAdapter
import hr.ferit.igorkuridza.taskiefragments.model.Task
import hr.ferit.igorkuridza.taskiefragments.ui.activities.ContainerActivity
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.add.AddTaskFragmentDialog
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.add.TaskAddedListener
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.change.TaskChangedListener
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), TaskAddedListener, TaskChangedListener {
    private val repository = TaskRoomRepository()

    private val adapter by lazy { TaskAdapter{onItemSelected(it)} }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initListeners()
        onItemSwipedLeft()
        refreshTasks()
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        refreshTasks()
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tasks_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_deleteAll -> {
                if(repository.getTasks().isNotEmpty())
                    showDeleteAllAlertDialog()
                else
                    activity?.displayToast(R.string.toastTextNothingToDelete)
            }
            R.id.item_sort -> {
                if(repository.getTasks().isNotEmpty())
                    sortByPriority()
                else
                    activity?.displayToast(R.string.toastTextNothingToSort)
            }
        }
        return super.onOptionsItemSelected(item)
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
        swipeRefresh.setOnRefreshListener{ refresh()}
        addTask.setOnClickListener { addTask() }
    }

    private fun refreshTasks() {
        progress.gone()
        val data = repository.getTasks()
        if(data.isNotEmpty())
            noData.gone()
        else
            noData.visible()
        adapter.setData(data)
    }

    private fun onItemSwipedLeft(){
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDeleteItemAlertDialog(adapter.getTaskByPosition(viewHolder.adapterPosition))
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun deleteTask(task: Task){
        repository.deleteTask(task)
        refreshTasks()
    }

    private fun deleteAll(){
        repository.clearAllTasks()
        refreshTasks()
    }

    private fun refresh(){
        swipeRefresh.isRefreshing = true
        refreshTasks()
        swipeRefresh.isRefreshing = false
    }

    private fun sortByPriority(){
        adapter.sortByPriority()
    }

    private fun showDeleteItemAlertDialog(task: Task){
        showAlertDialog(activity,getString(R.string.alertDialogTextDelete),R.drawable.ic_about,{deleteTask(task)},{refreshTasks()})
    }

    private fun showDeleteAllAlertDialog(){
        showAlertDialog(activity,getString(R.string.deleteAllAlertDialogText), R.drawable.ic_about,{deleteAll()},{null})
    }

    override fun onTaskAdded() {
        refreshTasks()
    }

    override fun onTaskChanged(task: Task) {
        refreshTasks()
    }

    companion object {
        fun newInstance() = TasksFragment()
    }
}
