package database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import logic.entity.AssessmentNotification;


@Dao
public interface AssessmentNotificationDAO {
    // Inserts a new entity into the Note table
    @Insert
    void insert(AssessmentNotification notification);
    // Deletes the given note from the Note table
    @Delete
    void delete(AssessmentNotification notification);
    // Updates the given note
    @Update
    void update(AssessmentNotification notification);
    // Gets list of notes ordered by when they were inserted for given course id
    @Query("SELECT * FROM assessment_notification WHERE assessmentId = :id ORDER BY id ASC")
    LiveData<List<AssessmentNotification>> getNotifications(final int id);
}
