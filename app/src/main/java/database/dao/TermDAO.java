package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import logic.Course;
import logic.Term;

@Dao
public interface TermDAO {
    // Inserts a new entity/term into the term_table
    @Insert
    void insert(Term term);
    // Deletes the given term from the term_table
    @Delete
    void delete(Term term);
    // Updates the given term
    @Update
    void update(Term term);
    // Gets the complete list of terms ordered by when they were inserted
    @Query("SELECT * FROM term ORDER BY id ASC")
    LiveData<List<Term>> getTerms();
    // gets list of courses by given term id
    @Query("SELECT * FROM course WHERE termId=:termId")
    public List<Course> getCourses(int termId);
}
