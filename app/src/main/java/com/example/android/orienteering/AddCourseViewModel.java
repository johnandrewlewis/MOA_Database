package com.example.android.orienteering;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.android.orienteering.database.CoursesDatabase;
import com.example.android.orienteering.database.CoursesEntry;

public class AddCourseViewModel extends ViewModel {

    private LiveData<CoursesEntry> course;

    public AddCourseViewModel(CoursesDatabase database, int courseId) {
        course = database.coursesDao().loadCourseById(courseId);
    }

    public LiveData<CoursesEntry> getCourse() {
        return course;
    }
}
