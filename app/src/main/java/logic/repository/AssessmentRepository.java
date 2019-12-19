package logic.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.AssessmentDAO;
import logic.entity.Assessment;

public class AssessmentRepository {
    // Get the DAO
    private AssessmentDAO mAssessmentDAO;
    private LiveData<List<Assessment>> mAllAssessments;

    public AssessmentRepository(Application application, int termID){
        // Get a handle to the DB and initialize member variables
        Database db = Database.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
    }

    /**
     * Returns our LiveData of All Terms in the database
     * @return All Terms in our database ordered by entry
     */
    public LiveData<List<Assessment>> getAssessments(int courseID){
        this.mAllAssessments = mAssessmentDAO.getAssessments(courseID);
        return this.mAllAssessments;
    }
    // Insert method done asynchronously
    public void insert(Assessment assessment){
        new insertAsyncTask(mAssessmentDAO).execute(assessment);
    }
    // Delete method done asynchronously
    public void delete(Assessment assessment) {
        new deleteAsyncTask(mAssessmentDAO).execute(assessment);
    }
    public void update(Assessment assessment){
        new updateAsyncTask(mAssessmentDAO).execute(assessment);
    }


    private static class insertAsyncTask extends AsyncTask<Assessment,Void,Void> {
        // Create DAO as member variable
        private AssessmentDAO asyncAssessmentDAO;
        // Constructor that initializes DAO
        public insertAsyncTask(AssessmentDAO asyncAssessmentDAO){
            this.asyncAssessmentDAO = asyncAssessmentDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Assessment...params){
            asyncAssessmentDAO.insert(params[0]); // Insert the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Deletes the given term
     */
    private static class deleteAsyncTask extends AsyncTask<Assessment,Void,Void>{
        // Create DAO as member variable
        private AssessmentDAO asyncAssessmentDAO;
        // Constructor that initializes DAO
        public deleteAsyncTask(AssessmentDAO asyncAssessmentDAO){
            this.asyncAssessmentDAO = asyncAssessmentDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Assessment...params){
            asyncAssessmentDAO.delete(params[0]); // Delete the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Updates the given term
     */
    private static class updateAsyncTask extends AsyncTask<Assessment,Void,Void>{
        // Create DAO as member variable
        private AssessmentDAO asyncAssessmentDAO;
        // Constructor that initializes DAO
        public updateAsyncTask(AssessmentDAO asyncAssessmentDAO){
            this.asyncAssessmentDAO = asyncAssessmentDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(Assessment...params){
            asyncAssessmentDAO.update(params[0]); // update the given term using the Data Access Object
            return null;
        }
    }
}
