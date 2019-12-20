package logic.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.TermDAO;
import database.join.TermWithCourses;
import logic.entity.Term;


public class TermRepository {
    // Get the DAO
    private TermDAO mTermDAO;
    private LiveData<List<Term>> mAllTerms;
    private LiveData<List<TermWithCourses>> mTermsWithCourses;

    public TermRepository(Application application){
        // Get a handle to the DB and initialize member variables
        Database db = Database.getDatabase(application);
        mTermDAO = db.termDAO();
        mAllTerms = mTermDAO.getTerms();
        mTermsWithCourses = mTermDAO.getTermsWithCourses();
    }

    /**
     * Returns our LiveData of All Terms in the database
     * @return All Terms in our database ordered by entry
     */
    public LiveData<List<Term>> getAllTerms(){
        return mAllTerms;
    }
    public LiveData<List<TermWithCourses>> getCoursesByTerm(){return mTermsWithCourses;}

    // Insert method done asynchronously
    public void insert(Term term){
        new insertAsyncTask(mTermDAO).execute(term);
    }
    // Delete method done asynchronously
    public void delete(Term term) {
        new deleteAsyncTask(mTermDAO).execute(term);
    }
    public void update(Term term){
        new updateAsyncTask(mTermDAO).execute(term);
    }


    private static class insertAsyncTask extends AsyncTask<Term,Void,Void>{
        // Create DAO as member variable
        private TermDAO asyncTermDAO;
        // Constructor that initializes DAO
        public insertAsyncTask(TermDAO termDAO){
            this.asyncTermDAO = termDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Term...params){
            asyncTermDAO.insert(params[0]); // Insert the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Deletes the given term
     *
     */
    private static class deleteAsyncTask extends AsyncTask<Term,Void,Void>{
        // Create DAO as member variable
        private TermDAO asyncTermDAO;
        // Constructor that initializes DAO
        public deleteAsyncTask(TermDAO termDAO){
            this.asyncTermDAO = termDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Term...params){
            asyncTermDAO.delete(params[0]); // Delete the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Updates the given term
     */
    private static class updateAsyncTask extends AsyncTask<Term,Void,Void>{
        // Create DAO as member variable
        private TermDAO asyncTermDAO;
        // Constructor that initializes DAO
        public updateAsyncTask(TermDAO termDAO){
            this.asyncTermDAO = termDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Term...params){
            asyncTermDAO.update(params[0]); // update the given term using the Data Access Object
            return null;
        }
    }



}
