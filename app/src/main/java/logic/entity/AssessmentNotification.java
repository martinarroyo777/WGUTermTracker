package logic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


/**
 * Represents a Term. Each term has Courses associated with it.
 */
@Entity(tableName = "assessment_notification")
public class AssessmentNotification implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="assessmentId")
    private int assessmentId;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="message")
    private String message;
    @ColumnInfo(name="date")
    private String date;

    public static final Creator<AssessmentNotification> CREATOR = new Creator<AssessmentNotification>() {
        @Override
        public AssessmentNotification createFromParcel(Parcel parcel) {
            return new AssessmentNotification(parcel);
        }

        @Override
        public AssessmentNotification[] newArray(int i) {
            return new AssessmentNotification[i];
        }
    };

    /**
     * Basic constructor for AssessmentNotification
     * @param assessmentId ID of Assessment the notification is for
     * @param title Title of Notification
     * @param message Notification message
     * @param date Date of notification
     */
    public AssessmentNotification(int assessmentId, @NonNull String title, @NonNull String message, @NonNull String date){
        this.assessmentId = assessmentId;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    @Ignore
    protected AssessmentNotification(Parcel in){
        id = in.readInt();
        assessmentId = in.readInt();
        title = in.readString();
        message = in.readString();
        date = in.readString();

    }

    /**
     * Get the notification Id
     * @return The id for the Notification
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the title of the Notification
     * @return The title of the Notification
     */
    public int getAssessmentId(){return this.assessmentId;}
    public String getTitle(){
        return this.title;
    }

    /**
     * Get the notification message
     * @return The notification message
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * Get the notification date
     * @return The notification date
     */
    public String getDate(){
        return this.date;
    }

    /**
     * Set the Notification Id
     * @param id The id for the notification
     */
    public void setId(int id){
        this.id = id;
    }
    public void setAssessmentId(int assessmentId){this.assessmentId=assessmentId;}
    /**
     * Set the title of the Notification
     * @param title Title of the Term
     */
    public void setTitle(String title){
        this.title = title;
    }

    public void setMessage(String message){this.message = message;};
    /**
     * Set the date of the notification
     * @param date date for the notification
     */
    public void setDate(String date){
        this.date = date;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.assessmentId);
        parcel.writeString(this.title);
        parcel.writeString(this.message);
        parcel.writeString(this.date);
    }
}
