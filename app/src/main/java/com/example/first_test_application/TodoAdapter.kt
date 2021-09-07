package com.example.first_test_application

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.first_test_application.databinding.ItemTodoBinding

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private lateinit var binding: ItemTodoBinding

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }
    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }
    fun deleteTodo(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        binding = ItemTodoBinding.bind(holder.itemView)
        val curTodo = todos[position]
        holder.itemView.apply{
            binding.tvTodoTitle.text = curTodo.title
            binding.cbTick.isChecked = curTodo.isChecked
            toggleStrikeThrough(binding.tvTodoTitle, curTodo.isChecked )
            binding.cbTick.setOnCheckedChangeListener({ _, isChecked ->
                toggleStrikeThrough(binding.tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            })
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}