package view.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import logic.entity.AssessmentNotification;
import logic.repository.AssessmentNotificationRepository;


public class NotificationViewModel extends AndroidViewModel {
    // Get a reference to our repository
    private AssessmentNotificationRepository mNotificationRepository;
    // Get our LiveData for caching
    private LiveData<List<AssessmentNotification>> mAllNotifications;
    // Hold the assessment ID
    private int assessmentId;


    // Constructor - initialize member variables
    public NotificationViewModel(Application application){
        super(application);
        mNotificationRepository = new AssessmentNotificationRepository(application, assessmentId);
        //mAllNotifications = mNotificationRepository.getNotifications(assessmentId);
    }
    // Create getter for data cache
    public LiveData<List<AssessmentNotification>> getAllNotifications(int assessmentId){
        this.mAllNotifications = mNotificationRepository.getNotifications(assessmentId);
        return this.mAllNotifications;
    }
    // create method to insert term via repository
    public void insert(AssessmentNotification notification){
        mNotificationRepository.insert(notification);
    }
    // return the term at the given position in our list
    public AssessmentNotification get(int pos){
        return mAllNotifications.getValue().get(pos);
    }
    // create method to delete term via repository
    public void delete(AssessmentNotification notification){
        mNotificationRepository.delete(notification);
    }
    // update term
    public void update(AssessmentNotification notification){
        mNotificationRepository.update(notification);
    }
}
