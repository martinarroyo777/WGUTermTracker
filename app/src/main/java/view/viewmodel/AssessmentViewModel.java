package view.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import logic.entity.Assessment;
import logic.repository.AssessmentRepository;


public class AssessmentViewModel extends AndroidViewModel {
    // Get a reference to our repository
    private AssessmentRepository mAssessmentRepository;
    // Get our LiveData for caching
    private LiveData<List<Assessment>> mAllAssessments;
    // Hold the term ID
    private int courseID;

    // Constructor - initialize member variables
    public AssessmentViewModel(Application application){
        super(application);
        mAssessmentRepository = new AssessmentRepository(application, courseID);
        //mAllCourses = mCourseRepository.getCourses();
    }
    // Create getter for data cache
    public LiveData<List<Assessment>> getAllAssessments(int courseID){
        this.mAllAssessments = mAssessmentRepository.getAssessments(courseID);
        return this.mAllAssessments;
    }
    // create method to insert term via repository
    public void insert(Assessment assessment){
        mAssessmentRepository.insert(assessment);
    }
    // return the term at the given position in our list
    public Assessment get(int pos){
        return mAllAssessments.getValue().get(pos);
    }
    // create method to delete term via repository
    public void delete(Assessment assessment){
        mAssessmentRepository.delete(assessment);
    }
    // update term
    public void update(Assessment assessment){
        mAssessmentRepository.update(assessment);
    }
}
