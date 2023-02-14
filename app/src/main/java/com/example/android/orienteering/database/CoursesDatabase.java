package com.example.android.orienteering.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.android.todolist.R;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {CoursesEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class CoursesDatabase extends RoomDatabase {

    public abstract CoursesDao coursesDao();

    private static final String LOG_TAG = CoursesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Orienteering_Courses";
    private static CoursesDatabase sInstance;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CoursesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CoursesDatabase.class, CoursesDatabase.DATABASE_NAME)
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    /**
     * Override the onCreate method to populate the database.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                CoursesDao dao = sInstance.coursesDao();

                Date date = new Date();

                CoursesEntry newEntry = new CoursesEntry(1, "Ormeau Tennis Centre front door",
                        54.589946051431504, -5.915203594731079, R.drawable.marker_one_ormeau, date);
                dao.insert(newEntry);

            });
        }
    };
}
