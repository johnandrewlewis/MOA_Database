package com.example.android.orienteering;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.android.orienteering.database.CoursesDatabase;
import com.example.android.orienteering.database.CoursesEntry;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<CoursesEntry>> courses;

    public MainViewModel(Application application) {
        super(application);
        CoursesDatabase database = CoursesDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the course from the DataBase");
        courses = database.coursesDao().loadOrmeau();
    }

    public LiveData<List<CoursesEntry>> getCourses() {

        return courses;
    }
}
