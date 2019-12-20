package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import database.join.TermWithCourses;
import logic.entity.Term;

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
    @Transaction
    @Query("SELECT term.id,term.title,term.startDate,term.endDate FROM term JOIN course ON term.id=course.termId")
    LiveData<List<TermWithCourses>> getTermsWithCourses();

}
