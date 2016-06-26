package com.os.operando.realm.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.os.operando.realm.sample.adapter.TaskAdapter;
import com.os.operando.realm.sample.databinding.ActivityMainBinding;
import com.os.operando.realm.sample.model.Task;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        Realm realm = Realm.getDefaultInstance();
        // RealmResultsはaddとかを実装しないでException投げるようになってるのでArrayListでラップする
        taskList = new ArrayList<>(realm.where(Task.class).findAll());
        adapter = new TaskAdapter(taskList);
        binding.taskList.setLayoutManager(new LinearLayoutManager(this));
        binding.taskList.setAdapter(adapter);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Task task = new Task();
                task.id = nextId();
                task.title = binding.title.getText().toString();
                task.comment = binding.comment.getText().toString();

                Realm realm = Realm.getDefaultInstance();
                try {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            taskList.add(realm.copyToRealmOrUpdate(task));
                            adapter.notifyDataSetChanged();
                        }
                    });
                } finally {
                    realm.close();
                }
            }
        });
    }

    private long nextId() {
        Realm realm = Realm.getDefaultInstance();
        Number number = realm.where(Task.class).max("id");
        Log.d("tas", "number : " + (number == null ? 1 : number));
        if (number == null) {
            return 1;
        }
        return number.longValue() + 1;
    }
}
