package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import logic.entity.CourseNote;

@Dao
public interface CourseNoteDAO {
    // Inserts a new entity into the Note table
    @Insert
    void insert(CourseNote note);
    // Deletes the given note from the Note table
    @Delete
    void delete(CourseNote note);
    // Updates the given note
    @Update
    void update(CourseNote note);
    // Gets list of notes ordered by when they were inserted for given course id
    @Query("SELECT * FROM course_note WHERE courseId = :id ORDER BY id ASC")
    LiveData<List<CourseNote>> getCourseNotes(final int id);
}
