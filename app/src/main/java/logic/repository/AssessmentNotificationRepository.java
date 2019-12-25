package logic.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import database.Database;
import database.dao.AssessmentNotificationDAO;
import logic.entity.AssessmentNotification;


public class AssessmentNotificationRepository {
    // Get the DAO
    private AssessmentNotificationDAO mNotificationDAO;
    private LiveData<List<AssessmentNotification>> mAllNotifications;

    public AssessmentNotificationRepository(Application application, int assessmentId){
        // Get a handle to the DB and initialize member variables
        Database db = Database.getDatabase(application);
        mNotificationDAO = db.notificationDAO();
    }

    /**
     * Returns all Notes associated with courseID
     * @return All Notes based on courseID, ordered by courseID
     */
    public LiveData<List<AssessmentNotification>> getNotifications(int assessmentID){
        this.mAllNotifications = mNotificationDAO.getNotifications(assessmentID);
        return this.mAllNotifications;
    }
    // Insert method done asynchronously
    public void insert(AssessmentNotification notification){
        new insertAsyncTask(mNotificationDAO).execute(notification);
    }
    // Delete method done asynchronously
    public void delete(AssessmentNotification notification) {
        new deleteAsyncTask(mNotificationDAO).execute(notification);
    }
    public void update(AssessmentNotification notification){
        new updateAsyncTask(mNotificationDAO).execute(notification);
    }


    private static class insertAsyncTask extends AsyncTask<AssessmentNotification,Void,Void> {
        // Create DAO as member variable
        private AssessmentNotificationDAO asyncNotificationDAO;
        // Constructor that initializes DAO
        public insertAsyncTask(AssessmentNotificationDAO notificationDAO){
            this.asyncNotificationDAO = notificationDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(AssessmentNotification...params){
            asyncNotificationDAO.insert(params[0]); // Insert the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Deletes the given term
     *
     */
    private static class deleteAsyncTask extends AsyncTask<AssessmentNotification,Void,Void>{
        // Create DAO as member variable
        private AssessmentNotificationDAO asyncNotificationDAO;
        // Constructor that initializes DAO
        public deleteAsyncTask(AssessmentNotificationDAO notificationDAO){
            this.asyncNotificationDAO = notificationDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(AssessmentNotification...params){
            asyncNotificationDAO.delete(params[0]); // Delete the given term using the Data Access Object
            return null;
        }
    }

    /**
     * Updates the given term
     */
    private static class updateAsyncTask extends AsyncTask<AssessmentNotification,Void,Void>{
        // Create DAO as member variable
        private AssessmentNotificationDAO asyncNotificationDAO;
        // Constructor that initializes DAO
        public updateAsyncTask(AssessmentNotificationDAO notificationDAO){
            this.asyncNotificationDAO = notificationDAO;
        }
        // Override doInBackground for AsyncTask
        @Override
        protected Void doInBackground(AssessmentNotification...params){
            asyncNotificationDAO.update(params[0]); // update the given term using the Data Access Object
            return null;
        }
    }
}
