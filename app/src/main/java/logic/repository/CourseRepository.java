package logic.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.CourseDAO;
import logic.entity.Course;


public class CourseRepository {
    // Get the DAO
    private CourseDAO mCourseDAO;
    private LiveData<List<Course>> mAllCourses;

    public CourseRepository(Application application, int termID){
        // Get a handle to the DB and initialize member variables
        Database db = Database.getDatabase(application);
        mCourseDAO = db.courseDAO();
        //mAllCourses = mCourseDAO.getCourses(termID);
    }

    /**
     * Returns our LiveData of All Terms in the database
     * @return All Terms in our database ordered by entry
     */
    public LiveData<List<Course>> getCourses(int termID){
        this.mAllCourses = mCourseDAO.getCourses(termID);
        return this.mAllCourses;
    }
    // Insert method done asynchronously
    public void insert(Course course){
        new insertAsyncTask(mCourseDAO).execute(course);
    }
    // Delete method done asynchronously
    public void delete(Course course) {
        new deleteAsyncTask(mCourseDAO).execute(course);
    }
    public void update(Course course){
        new updateAsyncTask(mCourseDAO).execute(course);
    }


    private static class insertAsyncTask extends AsyncTask<Course,Void,Void> {
        // Create DAO as member variable
        private CourseDAO asyncCourseDAO;
        // Constructor that initializes DAO
        public insertAsyncTask(CourseDAO courseDAO){
            this.asyncCourseDAO = courseDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Course...params){
            asyncCourseDAO.insert(params[0]); // Insert the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Deletes the given term
     *
     */
    private static class deleteAsyncTask extends AsyncTask<Course,Void,Void>{
        // Create DAO as member variable
        private CourseDAO asyncCourseDAO;
        // Constructor that initializes DAO
        public deleteAsyncTask(CourseDAO courseDAO){
            this.asyncCourseDAO = courseDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Course...params){
            asyncCourseDAO.delete(params[0]); // Delete the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Updates the given term
     */
    private static class updateAsyncTask extends AsyncTask<Course,Void,Void>{
        // Create DAO as member variable
        private CourseDAO asyncCourseDAO;
        // Constructor that initializes DAO
        public updateAsyncTask(CourseDAO courseDAO){
            this.asyncCourseDAO = courseDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Course...params){
            asyncCourseDAO.update(params[0]); // update the given term using the Data Access Object
            return null;
        }
    }
}
