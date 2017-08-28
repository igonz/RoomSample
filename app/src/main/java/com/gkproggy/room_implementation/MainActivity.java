package com.gkproggy.room_implementation;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.gkproggy.room_implementation.databinding.DialogNewUserBinding;

import java.util.List;

public class MainActivity extends LifecycleActivity {

    RecyclerView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userList = (RecyclerView) findViewById(R.id.user_list);
        userList.setLayoutManager(new LinearLayoutManager(this));
        userList.setAdapter(new UserAdpater());

        new AsyncTask<Void, Void, LiveData<List<User>>>() {
            @Override
            protected LiveData<List<User>> doInBackground(Void... voids) {
                return AppDatabase.getAppDatabase(MainActivity.this).userDao().getAll();
            }

            @Override
            protected void onPostExecute(LiveData<List<User>> users) {
                users.observe(MainActivity.this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(@Nullable List<User> users) {
                        System.out.println("SIZE: " + users.size());
                        getUserAdapter().clear();
                        getUserAdapter().addAllUser(users);
                    }
                });
            }
        }.execute();

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogNewUserBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(MainActivity.this), R.layout.dialog_new_user, null, false);
                inflate.setUser(new User());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(inflate.getRoot());
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                AppDatabase.getAppDatabase(MainActivity.this).userDao().insertAll(inflate.getUser());
                                return null;
                            }
                        }.execute();


                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private UserAdpater getUserAdapter() {
        return (UserAdpater) userList.getAdapter();
    }
}
