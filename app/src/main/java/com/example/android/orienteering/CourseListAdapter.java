package com.example.android.orienteering;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.orienteering.database.CoursesEntry;
import com.example.android.todolist.R;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This CourseListAdapter creates and binds ViewHolders, that hold the marker_id and description of a course,
 * to a RecyclerView to efficiently display data.
 * Clicking on individual list item will take user to new detail activity
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.TaskViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<CoursesEntry> mCourseEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the CourseListAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public CourseListAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.course_list_layout, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        CoursesEntry coursesEntry = mCourseEntries.get(position);
        int marker_id = coursesEntry.getMarker_id();
        String description = coursesEntry.getDescription();

        //Set values
        holder.markerIdView.setText(String.valueOf(marker_id)+". ");
        holder.descriptionView.setText(description);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCourseEntries == null) {
            return 0;
        }
        return mCourseEntries.size();
    }

    public List<CoursesEntry> getTasks() {
        return mCourseEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setCourses(List<CoursesEntry> coursesEntries) {
        mCourseEntries = coursesEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the marker_id and description TextViews
        TextView markerIdView, descriptionView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            markerIdView = itemView.findViewById(R.id.marker_id);
            descriptionView = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mCourseEntries.get(getAbsoluteAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}