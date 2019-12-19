package view.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import logic.entity.Course;
import logic.repository.CourseRepository;


public class CourseViewModel extends AndroidViewModel {
    // Get a reference to our repository
    private CourseRepository mCourseRepository;
    // Get our LiveData for caching
    private LiveData<List<Course>> mAllCourses;
    // Hold the term ID
    private int termID;


    // Constructor - initialize member variables
    public CourseViewModel(Application application){
        super(application);
        mCourseRepository = new CourseRepository(application, termID);
        //mAllCourses = mCourseRepository.getCourses();
    }
    // Create getter for data cache
    public LiveData<List<Course>> getAllCourses(int termID){
        this.mAllCourses = mCourseRepository.getCourses(termID);
        return this.mAllCourses;
    }
    // create method to insert term via repository
    public void insert(Course course){
        mCourseRepository.insert(course);
    }
    // return the term at the given position in our list
    public Course get(int pos){
        return mAllCourses.getValue().get(pos);
    }
    // create method to delete term via repository
    public void delete(Course course){
        mCourseRepository.delete(course);
    }

    // update term
    public void update(Course course){
        mCourseRepository.update(course);
    }
}
