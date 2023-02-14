package com.example.android.orienteering;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.example.android.orienteering.database.CoursesDatabase;
import com.example.android.orienteering.database.CoursesEntry;
import com.example.android.todolist.R;


public class CourseDetailActivity extends AppCompatActivity {

    // Extra for the course ID to be received in the intent
    public static final String EXTRA_COURSE_ID = "extraTaskId";
    // Extra for the course ID to be received after rotation
    public static final String INSTANCE_COURSE_ID = "instanceTaskId";

    // Constant for default course id to be used when not in update mode
    private static final int DEFAULT_COURSE_ID = -1;
    // Constant for logging
    private static final String TAG = CourseDetailActivity.class.getSimpleName();
    // Fields for views
    private TextView mMarkerIdTextview, mDescriptionTextView;
    private ImageView mPictureImageView;

    private int mCourseId = DEFAULT_COURSE_ID;

    // Member variable for the Database
    private CoursesDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        initViews();

        mDb = CoursesDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_COURSE_ID)) {
            mCourseId = savedInstanceState.getInt(INSTANCE_COURSE_ID, DEFAULT_COURSE_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_COURSE_ID)) {

            if (mCourseId == DEFAULT_COURSE_ID) {
                // populate the UI
                mCourseId = intent.getIntExtra(EXTRA_COURSE_ID, DEFAULT_COURSE_ID);

                // Declare a AddCourseViewModelFactory using mDb and mTaskId
                AddCourseViewModelFactory factory = new AddCourseViewModelFactory(mDb, mCourseId);
                // Declare a AddCourseViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddCourseViewModel
                final AddCourseViewModel viewModel
                        = ViewModelProviders.of((FragmentActivity) this, (ViewModelProvider.Factory) factory).get(AddCourseViewModel.class);

                // Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getCourse().observe(this, new Observer<CoursesEntry>() {
                    @Override
                    public void onChanged(@Nullable CoursesEntry coursesEntry) {
                        viewModel.getCourse().removeObserver(this);
                        populateUI(coursesEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_COURSE_ID, mCourseId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mMarkerIdTextview = findViewById(R.id.markerIdDetail);
        mDescriptionTextView = findViewById(R.id.descriptionDetail);
        mPictureImageView = findViewById(R.id.pictureDetail);
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param course the CoursesEntry to populate the UI
     */
    private void populateUI(CoursesEntry course) {
        if (course == null) {
            return;
        }
        mMarkerIdTextview.setText(String.valueOf(course.getMarker_id()));
        mDescriptionTextView.setText(course.getDescription());
        mPictureImageView.setImageResource(course.getPicture());
        Log.d(TAG, "Populating the User Interface in Update mode");

    }
}
