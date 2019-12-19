package logic.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * Represents a Term. Each term has Courses associated with it.
 */
@Entity(tableName = "term")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="startDate")
    private String startDate;
    @ColumnInfo(name="endDate")
    private String endDate;
    @Ignore
    private List<Course> courses;

    /**
     * Basic constructor for Term
     * @param title Title of Term
     * @param startDate Term start date
     * @param endDate Term end date
     */
    public Term(@NonNull String title, @NonNull String startDate, @NonNull String endDate){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    /**
     * Get the termId
     * @return The id for the Term
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the title of the Term
     * @return The title of the Term
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Get the start date of the Term
     * @return The start date of the Term
     */
    public String getStartDate(){
        return this.startDate;
    }

    /**
     * Get the end date of the Term
     * @return The end date of the Term
     */
    public String getEndDate(){
        return this.endDate;
    }

    /**
     * Get the Courses associated with the term
     * @return The list of Courses associated with the Term

    public List<Course> getCourses(){
    return this.courses;
    }
     */
    /**
     * Set the Term Id
     * @param id The id for the Term
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Set the title of the Term
     * @param title Title of the Term
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Set the start date of the term
     * @param start Start date for the Term
     */
    public void setStartDate(String start){
        this.startDate = start;
    }

    /**
     * Set the end date of the term
     * @param end End date for the Term
     */
    public void setEndDate(String end){
        this.endDate = end;
    }


    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Term title: ");
        res.append(this.title);
        res.append("\nStart Date: ");
        res.append(this.startDate.toString());
        res.append("\nEnd Date: ");
        res.append(this.endDate.toString());
        return res.toString();
    }

    public List<Course> getCourses(){
        return this.courses;
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }

    public boolean hasCourses(){
        return this.courses.isEmpty();
    }
}
