package logic;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an assessment
 *
 * Assessments must be assigned to a Course
 */
@Entity(tableName = "assessment")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "courseId")
    private int courseId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "dueDate")
    private String dueDate;

    /**
     * Builds a standard Assessment without a notification. A course must already exist for this assessment to be assigned to using courseId.
     * @param courseId
     * @param title
     * @param type
     * @param dueDate
     */
    public Assessment(int courseId, String title, String type, String dueDate){
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
    }


    /**
     * Returns the unique id for the Assessment
     * @return AssessmentId
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the courseId for the associated course
     * @return courseId
     */
    public int getCourseId(){
        return this.courseId;
    }

    /**
     * Returns the Assessment title
     * @return Assessment title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Returns the Assessment type (Performance or Objective)
     * @return Assessment type
     */
    public String getType(){
        return this.type;
    }

    /**
     * Returns the Assessment due date
     * @return Assessment due date
     */
    public String getDueDate(){
        return this.dueDate;
    }

    /**
     * Sets the id for the Assessment
     * @param id
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Sets the courseId for the Assessment
     * @param courseId
     */
    public void setCourseId(int courseId){
        this.courseId = courseId;
    }

    /**
     * Sets the title of the Assessment
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets the assessment type. Can only be Performance or Objective.
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Sets the due date for the assessment
     * @param dueDate
     */
    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }
}
