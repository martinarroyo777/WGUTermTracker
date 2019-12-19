package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import logic.Course;

@Dao
public interface CourseDAO {
    // Inserts a new entity into the course table
    @Insert
    void insert(Course course);
    // Deletes the given course from the course table
    @Delete
    void delete(Course course);
    // Updates the given course
    @Update
    void update(Course course);
    // Gets the complete list of courses ordered by when they were inserted
    @Query("SELECT * FROM course WHERE termId = :id ORDER BY id ASC")
    LiveData<List<Course>> getCourses(final int id);
}
