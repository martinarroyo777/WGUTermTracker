package database.join;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import logic.entity.Course;
import logic.entity.Term;

public class TermWithCourses {
    @Embedded public Term term;
    @Relation(
            parentColumn = "id",
            entityColumn = "termId",
            entity = Course.class
    )
    public List<Course> courses;

    public Term getTerm(){
        return this.term;
    }

    public List<Course> getCourses(){
        return this.courses;
    }
}
