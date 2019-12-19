package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import logic.Assessment;

@Dao
public interface AssessmentDAO {
    // Inserts a new entity into the assessment table
    @Insert
    void insert(Assessment assessment);
    // Deletes the given assessment from the assessment table
    @Delete
    void delete(Assessment assessment);
    // Updates the given assessment
    @Update
    void update(Assessment assessment);
    // Gets the complete list of assessments ordered by when they were inserted
    @Query("SELECT * FROM assessment WHERE courseId = :id ORDER BY id ASC")
    LiveData<List<Assessment>> getAssessments(final int id);
}
