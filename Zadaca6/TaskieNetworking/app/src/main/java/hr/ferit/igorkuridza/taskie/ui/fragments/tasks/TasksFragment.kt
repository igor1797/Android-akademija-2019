package hr.ferit.igorkuridza.taskie.ui.fragments.tasks

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.*
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.model.response.DeleteTaskResponse
import hr.ferit.igorkuridza.taskie.model.response.GetTasksResponse
import hr.ferit.igorkuridza.taskie.networking.BackendFactory
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.ui.activities.ContainerActivity
import hr.ferit.igorkuridza.taskie.ui.adapters.TaskAdapter
import hr.ferit.igorkuridza.taskie.ui.fragments.addtask.AddTaskFragmentDialog
import hr.ferit.igorkuridza.taskie.ui.fragments.addtask.TaskAddedListener
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragment:
    BaseFragment(), TaskAddedListener
{
    private val repository: TaskRepository = TaskRoomRepository()
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }
    private val interactor = BackendFactory.getTaskieInteractor()


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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu,inflater)
        inflater.inflate(R.menu.menu_task,menu)
    }

    override fun onTaskAdded(task: BackendTask) {
        adapter.addData(task)
        noData.gone()
    }

    private fun clearAllTasks(){
        val data = repository.getTasks()
        for (task in data) {
            interactor.delete(task.id, deleteTaskCallback(task.id))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clearAllTasksMenuItem ->{
                if(repository.getTasks().isNotEmpty())
                    showClearAllAlertDialog()
                else
                    context?.displayToast(getString(R.string.nothingToDelete_text))
            }
            R.id.sortByPriorityMenuItem -> {
                if(repository.getTasks().isNotEmpty())
                    adapter.sortDataByPriority()
                else
                    context?.displayToast(getString(R.string.nothingToSort_text))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initUi() {
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

    private fun refresh() {
        swipeRefresh.isRefreshing = true
        getAllTasks()
        saveOfflineTasks()
        swipeRefresh.isRefreshing = false
    }

    private fun onItemSelected(task: BackendTask){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID,task.id)
        }
        startActivity(detailsIntent)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun handleOkResponseDeleteTask(id: String){
        val task = adapter.getTask(id)
        if (task != null){
            adapter.deleteTask(task)
            repository.deleteTask(task)
            adapter.notifyDataSetChanged()
            checkList()
        }
    }

    private fun deleteTaskCallback(id: String): Callback<DeleteTaskResponse> = object  :
        Callback<DeleteTaskResponse> {
        override fun onFailure(call: Call<DeleteTaskResponse>, t: Throwable) {
            progress.gone()
            activity?.displayToast(getString(R.string.noInternetText))
        }

        override fun onResponse(call: Call<DeleteTaskResponse>, response: Response<DeleteTaskResponse>) {
            progress.gone()
            noData.gone()
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponseDeleteTask(id)
                }
            }else
                activity?.displayToast(convertCodeToMessage(response.code()))
        }
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

    private fun deleteTask(task: BackendTask){
        interactor.delete(task.id, deleteTaskCallback(task.id))
    }

    private fun getAllTasks() {
        interactor.getTasks(getTaskieCallback())
    }


    private fun getTaskieCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            handleNoInternetConnectionResponse()
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            progress.gone()
            noData.gone()
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()!!.notes?.let {handleOkResponseGetTaskie(it)  }
                }
            }else
                activity?.displayToast(convertCodeToMessage(response.code()))
        }
    }


    private fun handleNoInternetConnectionResponse(){
        adapter.setData(repository.getTasks().toMutableList())
        checkList()
        progress.gone()
        activity?.displayToast(getString(R.string.noInternetText))
    }

    private fun handleOkResponseGetTaskie(response: MutableList<BackendTask>) {
        for (backendTask in response) {
                repository.addTask(toRoomTask(backendTask))
        }
        adapter.setData(response)
        checkList()
        progress.gone()
    }

    private fun saveOfflineTasks(){
        repository.getTasks().forEach { if(it.isSent == false){
            interactor.save(AddTaskRequest(it.title, it.content,it.taskPriority),addOfflineTaskCallback(it.id))
        }}
    }

    private fun addOfflineTaskCallback(id: String): Callback<BackendTask> = object : Callback<BackendTask>{
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            handleNoInternetConnectionResponse()
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if(response.isSuccessful){
                when(response.code()){
                    RESPONSE_OK -> response.body()?.let {
                        handleOkResponseSaveOfflineTask(id,it)
                    }
                }
            }else
                activity?.displayToast(convertCodeToMessage(response.code()))
        }
    }

    private fun handleOkResponseSaveOfflineTask(id: String,task: BackendTask) {
        repository.getTask(id).let {
            repository.deleteTask(it)
        }
        repository.addTask(task)
        checkList()
    }

    private fun showDeleteOneItemAlertDialog(task: BackendTask){
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.alertDialogTitleDeleteOneItem))
            .setIcon(R.drawable.ic_about)
            .setPositiveButton(getString(R.string.positiveAlertButtonText)) { _, _ ->
                deleteTask(task)
                adapter.notifyDataSetChanged() }
            .setNegativeButton(getString(R.string.negativeAlertButtonText)){ _, _ -> getAllTasks()}
            .show()
    }

    private fun showClearAllAlertDialog() {
        AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.clear_all_tasks))
            .setIcon(R.drawable.ic_about)
            .setPositiveButton(getString(R.string.positiveAlertButtonText)) { _, _ ->
                clearAllTasks()
                adapter.notifyDataSetChanged() }
            .setNegativeButton(getString(R.string.negativeAlertButtonText)){ _, _ -> getAllTasks()}
            .show()
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
