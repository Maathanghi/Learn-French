package com.learn_french.common.fulldialog.roomdatabse.utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.learn_french.common.fulldialog.roomdatabse.database.AppDatabase;
import com.learn_french.common.fulldialog.roomdatabse.entity.Lesson;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static int getTotal(final AppDatabase db){
        return db.userDao().countUsers();
    }

    public static int getTotal(final AppDatabase db, String title){
        return db.userDao().countByName(title);
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Lesson addUser(final AppDatabase db, Lesson lesson) {
        db.userDao().insertAll(lesson);
        return lesson;
    }

    private static void populateWithTestData(AppDatabase db) {
        Lesson lesson = new Lesson();
        lesson.setTitle("test 6");
        lesson.setEnglishTranslation("English");
        lesson.setFrenchTranslation("French");
        lesson.setTag("tags");
        addUser(db, lesson);

        List<Lesson> lessonList = db.userDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + lessonList.size());
    }

    private static void bookmarALesson(AppDatabase db, Lesson lesson) {
        addUser(db, lesson);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
