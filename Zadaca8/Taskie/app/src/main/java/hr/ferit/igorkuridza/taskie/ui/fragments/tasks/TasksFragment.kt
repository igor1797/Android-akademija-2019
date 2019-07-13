package hr.ferit.igorkuridza.taskie.ui.fragments.tasks

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.igorkuridza.taskie.presentation.TasksFragmentPresenter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.*
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.ui.activities.container.ContainerActivity
import hr.ferit.igorkuridza.taskie.ui.adapters.TaskAdapter
import hr.ferit.igorkuridza.taskie.ui.fragments.addtask.AddTaskFragmentDialog
import hr.ferit.igorkuridza.taskie.ui.fragments.addtask.TaskAddedListener
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import org.koin.android.ext.android.inject

class TasksFragment: BaseFragment(), TaskAddedListener, TasksContract.View{

    private val presenter by inject<TasksContract.Presenter>()

    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initListeners()
        onSwipeLeftDeleteItem()
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        saveOfflineTasks()
        getAllTasks()
    }

    private fun initUi() {
        presenter.setView(this)
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
        saveOfflineTasks()
        getAllTasks()
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
        swipeRefresh.setOnRefreshListener { refresh() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu,inflater)
        inflater.inflate(R.menu.menu_task,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clearAllTasksMenuItem ->{
                if(adapter.getTasks().isNotEmpty())
                    showClearAllAlertDialog()
                else
                    context?.displayToast(R.string.nothingToDelete_text)
            }
            R.id.sortByPriorityMenuItem -> {
                if(adapter.getTasks().isNotEmpty())
                    adapter.sortDataByPriority()
                else
                    context?.displayToast(R.string.nothingToSort_text)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAllTasks() = presenter.onGetAllTasks()

    private fun saveOfflineTasks() = presenter.onSaveOfflineTasks()

    private fun deleteTask(task: BackendTask) = presenter.onDeleteTask(task)

    private fun clearAllTasks() = presenter.onClearAllTasks()

    override fun onNoInternetConnection(tasks: MutableList<BackendTask>) {
        adapter.setData(tasks)
        checkList()
        progress.gone()
        activity?.displayToast(R.string.noInternetText)
    }

    override fun onSomethingWentWrong(code: Int) {
        activity?.displayToast(convertCodeToMessage(code))
    }

    override fun onDeleteTaskSuccessful(id: String) {
        val task = adapter.getTask(id)
        if (task != null){
            adapter.deleteTask(task)
            adapter.notifyDataSetChanged()
            checkList()
        }
    }

    override fun onGetTasksSuccessful(tasks: MutableList<BackendTask>) {
        adapter.setData(tasks)
        checkList()
        progress.gone()
    }

    override fun onSaveOfflineTaskSuccessful() {
        checkList()
    }

    override fun onTaskAdded(task: BackendTask) {
        adapter.addData(task)
        noData.gone()
    }

    private fun refresh() {
        swipeRefresh.isRefreshing = true
        saveOfflineTasks()
        getAllTasks()
        swipeRefresh.isRefreshing = false
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun onItemSelected(task: BackendTask){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID,task.id)
        }
        startActivity(detailsIntent)
    }

    private fun onSwipeLeftDeleteItem() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                val task = adapter.getTaskByPosition(viewHolder.adapterPosition)
                showDeleteOneItemAlertDialog(task)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)
    }

    private fun showDeleteOneItemAlertDialog(task: BackendTask){
        showDialog(
            activity,
            getString(R.string.alertDialogTitleDeleteOneItem),
            {deleteTask(task);adapter.notifyDataSetChanged()},
            {getAllTasks()}
        )
    }

    private fun showClearAllAlertDialog() {
        showDialog(
            activity,
            getString(R.string.clear_all_tasks)
        ) { clearAllTasks();adapter.notifyDataSetChanged()}
    }

    private fun checkList() {
        if (adapter.getSize()==0) {
            noData.visible()
        } else {
            noData.gone()
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}
