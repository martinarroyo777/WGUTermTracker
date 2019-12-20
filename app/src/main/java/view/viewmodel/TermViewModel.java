package view.viewmodel;
/**
 * View Model for our Term UI updates
 */

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import database.join.TermWithCourses;
import logic.entity.Term;
import logic.repository.TermRepository;


public class TermViewModel extends AndroidViewModel {
    // Get a reference to our repository
    private TermRepository mTermRepository;
    // Get our LiveData for caching
    private LiveData<List<Term>> mAllTerms;
    // List of courses by term
    private LiveData<List<TermWithCourses>> mTermsWithCourses;
    // Constructor - initialize member variables
    public TermViewModel(Application application){
        super(application);
        mTermRepository = new TermRepository(application);
        mAllTerms = mTermRepository.getAllTerms();
        mTermsWithCourses = mTermRepository.getCoursesByTerm();
    }
// Create getter for data cache
    public LiveData<List<Term>> getAllTerms(){
        return this.mAllTerms;
    }
// create method to insert term via repository
    public void insert(Term term){
        mTermRepository.insert(term);
    }
    // return the term at the given position in our list
    public Term get(int pos){
        return mAllTerms.getValue().get(pos);
    }
    // create method to delete term via repository
    public void delete(Term term){
        mTermRepository.delete(term);
    }
    // update term
    public void update(Term term){
        mTermRepository.update(term);
    }
    // Getter for terms with courses
    public LiveData<List<TermWithCourses>> getTermsWithCourses(){
        return this.mTermsWithCourses;
    }


}
