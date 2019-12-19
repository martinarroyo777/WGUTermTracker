package view.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import logic.entity.CourseNote;
import logic.repository.NoteRepository;



public class NoteViewModel extends AndroidViewModel {
    // Get a reference to our repository
    private NoteRepository mNoteRepository;
    // Get our LiveData for caching
    private LiveData<List<CourseNote>> mAllNotes;
    // Hold the course ID
    private int courseID;


    // Constructor - initialize member variables
    public NoteViewModel(Application application){
        super(application);
        mNoteRepository = new NoteRepository(application, courseID);
        //mAllCourses = mCourseRepository.getCourses();
    }
    // Create getter for data cache
    public LiveData<List<CourseNote>> getAllNotes(int courseID){
        this.mAllNotes = mNoteRepository.getNotes(courseID);
        return this.mAllNotes;
    }
    // create method to insert term via repository
    public void insert(CourseNote note){
        mNoteRepository.insert(note);
    }
    // return the term at the given position in our list
    public CourseNote get(int pos){
        return mAllNotes.getValue().get(pos);
    }
    // create method to delete term via repository
    public void delete(CourseNote note){
        mNoteRepository.delete(note);
    }
    // update term
    public void update(CourseNote note){
        mNoteRepository.update(note);
    }
}
