package logic;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a single CourseNote. CourseNotes are associated with Courses.
 */
@Entity(tableName = "course_note")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "body")
    private String body;

    /**
     * Complete constructor for Note object
     * @param courseId The id of the associated course
     * @param title The title of the note
     * @param body The contents of the note
     */
    public CourseNote(int courseId, String title, String body){
        this.courseId = courseId;
        this.title = title;
        this.body = body;
    }

    /**
     * Gets the unique courseId
     * @return courseId
     */
    public int getId(){
        return this.id;
    }

    /**
     * Gets the id of the associated Course
     * @return courseId
     */
    public int getCourseId(){
        return this.courseId;
    }

    /**
     * Gets the title of the note
     * @return title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Gets the contents of the note
     * @return body
     */
    public String getBody(){
        return this.body;
    }

    /**
     * Sets the id of the Note
     * @param id unique id for Note
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the course id for the associated Course
     * @param courseId ID of associated Course
     */
    public void setCourseId(int courseId){
        this.courseId = courseId;
    }

    /**
     * Sets the title for the Note
     * @param title Note title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets the contents of the Note body
     * @param body contents of Note body
     */
    public void setBody(String body){
        this.body = body;
    }
}
