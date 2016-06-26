package com.os.operando.realm.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.os.operando.realm.sample.BindingHolder;
import com.os.operando.realm.sample.R;
import com.os.operando.realm.sample.databinding.RowTaskItemBinding;
import com.os.operando.realm.sample.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<BindingHolder<RowTaskItemBinding>> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public BindingHolder<RowTaskItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingHolder<>(parent.getContext(), parent, R.layout.row_task_item);
    }

    @Override
    public void onBindViewHolder(BindingHolder<RowTaskItemBinding> holder, int position) {
        Task task = tasks.get(position);
        holder.binding.title.setText(task.title);
        holder.binding.comment.setText(task.comment);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
