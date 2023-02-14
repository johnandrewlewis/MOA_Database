package com.example.android.orienteering;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.android.orienteering.database.CoursesDatabase;

public class AddCourseViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final CoursesDatabase mDb;
    private final int mCourseId;

    public AddCourseViewModelFactory(CoursesDatabase database, int courseId) {
        mDb = database;
        mCourseId = courseId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddCourseViewModel(mDb, mCourseId);
    }
}
