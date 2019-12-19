package logic;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Represents a Course. All Course instances must have a term to which they are associated.
 */
@Entity(tableName = "course", foreignKeys = @ForeignKey(entity = Term.class,parentColumns = "id",childColumns = "termId", onDelete = ForeignKey.CASCADE))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "termId")
    private int termId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "startDate")
    private String startDate;
    @ColumnInfo(name = "endDate")
    private String endDate;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name="mentor_name")
    private String mentor_name;
    @ColumnInfo(name="mentor_email")
    private String mentor_email;
    @ColumnInfo(name="mentor_phone")
    private String mentor_phone;


    /**
     * Basic constructor for Course
     * @param termId
     * @param title
     * @param mentor_name
     * @param mentor_email
     * @param mentor_phone
     * @param startDate
     * @param endDate
     * @param status
     */
    public Course(int termId, String title, String mentor_name, String mentor_email,
                  String mentor_phone, String startDate, String endDate,
                  String status){

        this.termId = termId;
        this.title = title;
        this.mentor_name = mentor_name;
        this.mentor_email = mentor_email;
        this.mentor_phone = mentor_phone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;

    }

    public Course(){};

    /**
     * Get the course id
     * @return courseId
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the id for the associated Term
     * @return termId
     */
    public int getTermId(){
        return this.termId;
    }

    /**
     * Get course title
     * @return The title of the course
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Get Course start date
     * @return Start date for course
     */
    public String getStartDate(){
        return this.startDate;
    }

    /**
     * Get the Course Anticipated End Date
     * @return End Date for the course
     */
    public String getEndDate(){
        return this.endDate;
    }

    /**
     * Get the status of the course. Status can be one of the following:<br>
     *     In Progress, Completed, Dropped, Plan to Take
     * @return Status of the course
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * Get the course mentor assigned to the Course
     * @return CourseMentor assigned to the course
     */
    public String getMentor_name(){return this.mentor_name;}
    public String getMentor_email(){return this.mentor_email;}
    public String getMentor_phone(){return this.mentor_phone;}

    /**
     * Set the Course Id
     * @param id The course id
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Set the term Id for the course
     * @param termId The id of the Term to which this Course is associated
     */
    public void setTermId(int termId){
        this.termId=termId;
    }

    /**
     * Set Course title
     * @param title The title of the Course
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     * Assign the Course Mentor to the Course*/
    public void setMentor_name(String mentor_name){
        this.mentor_name = mentor_name;
    }
    public void setMentor_email(String mentor_email){
        this.mentor_email = mentor_email;
    }
    public void setMentor_phone(String mentor_phone){
        this.mentor_phone = mentor_phone;
    }
    /**
     * Set the Course start date
     * @param startDate Start date of the Course
     */
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    /**
     * Set the anticipated Course end date
     * @param endDate End date of the Course
     */
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    /**
     * Set the status of the Course. Status can only be one of the following:<br>
     *     In Progress, Completed, Dropped, Plan to Take
     * @param status The status of the Course
     */
    public void setStatus(String status){
        this.status=status;
    }


    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Course Title: ");
        res.append(this.title);
        res.append("\nCourse Start: ");
        res.append(this.startDate);
        res.append("\nCourse End: ");
        res.append(this.endDate);
        res.append("\nCourse Status: ");
        res.append(this.status);
        res.append("\nCourse Mentor: " );
        res.append(this.mentor_name);
        res.append("\n");
        res.append(this.mentor_email);
        res.append("\n");
        res.append(this.mentor_phone);
        return res.toString();

    }
}
