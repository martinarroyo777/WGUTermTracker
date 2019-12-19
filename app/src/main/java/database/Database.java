package database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import logic.Assessment;
import logic.Course;
import logic.CourseNote;
import logic.Term;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class, CourseNote.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;
    public static Database getDatabase(final Context context){
        // If the database hasn't been instantiated already, do so
        if (INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),Database.class,"wgutermtracker_database").addCallback(sDataBaseCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    // Create a database callback method to populate the database when we start the app
    private static RoomDatabase.Callback sDataBaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        PopulateDbAsync(Database db) {
        }

        @Override
        protected Void doInBackground(final Void... params) {

            return null;
        }
    }
}
