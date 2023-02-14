package com.example.android.orienteering;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.orienteering.database.CoursesDatabase;
import com.example.android.orienteering.database.CoursesEntry;
import com.example.android.todolist.R;

import java.util.List;


public class MainActivity extends AppCompatActivity implements CourseListAdapter.ItemClickListener {

    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private CourseListAdapter mAdapter;
    private CoursesDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the RecyclerView to its corresponding view
        mRecyclerView = findViewById(R.id.recyclerViewTasks);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new CourseListAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        mDb = CoursesDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getCourses().observe(this, new Observer<List<CoursesEntry>>() {
            @Override
            public void onChanged(@Nullable List<CoursesEntry> coursesEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setCourses(coursesEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch CourseDetailActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
        intent.putExtra(CourseDetailActivity.EXTRA_COURSE_ID, itemId);
        startActivity(intent);
    }
}
