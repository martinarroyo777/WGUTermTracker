package logic.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

import database.Database;
import database.dao.CourseNoteDAO;
import logic.entity.CourseNote;


public class NoteRepository {
    // Get the DAO
    private CourseNoteDAO mNoteDAO;
    private LiveData<List<CourseNote>> mAllNotes;

    public NoteRepository(Application application, int termID){
        // Get a handle to the DB and initialize member variables
        Database db = Database.getDatabase(application);
        mNoteDAO = db.courseNoteDAO();
    }

    /**
     * Returns all Notes associated with courseID
     * @return All Notes based on courseID, ordered by courseID
     */
    public LiveData<List<CourseNote>> getNotes(int courseID){
        this.mAllNotes = mNoteDAO.getCourseNotes(courseID);
        return this.mAllNotes;
    }
    // Insert method done asynchronously
    public void insert(CourseNote note){
        new insertAsyncTask(mNoteDAO).execute(note);
    }
    // Delete method done asynchronously
    public void delete(CourseNote note) {
        new deleteAsyncTask(mNoteDAO).execute(note);
    }
    public void update(CourseNote note){
        new updateAsyncTask(mNoteDAO).execute(note);
    }


    private static class insertAsyncTask extends AsyncTask<CourseNote,Void,Void> {
        // Create DAO as member variable
        private CourseNoteDAO asyncNoteDAO;
        // Constructor that initializes DAO
        public insertAsyncTask(CourseNoteDAO noteDAO){
            this.asyncNoteDAO = noteDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(CourseNote...params){
            asyncNoteDAO.insert(params[0]); // Insert the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Deletes the given term
     *
     */
    private static class deleteAsyncTask extends AsyncTask<CourseNote,Void,Void>{
        // Create DAO as member variable
        private CourseNoteDAO asyncNoteDAO;
        // Constructor that initializes DAO
        public deleteAsyncTask(CourseNoteDAO noteDAO){
            this.asyncNoteDAO = noteDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(CourseNote...params){
            asyncNoteDAO.delete(params[0]); // Delete the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Updates the given term
     */
    private static class updateAsyncTask extends AsyncTask<CourseNote,Void,Void>{
        // Create DAO as member variable
        private CourseNoteDAO asyncNoteDAO;
        // Constructor that initializes DAO
        public updateAsyncTask(CourseNoteDAO noteDAO){
            this.asyncNoteDAO = noteDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(CourseNote...params){
            asyncNoteDAO.update(params[0]); // update the given term using the Data Access Object
            return null;
        }
    }
}
