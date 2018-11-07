package com.learn_french.common.fulldialog.roomdatabse.utils;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.learn_french.common.fulldialog.model.app.Lesson;
import com.learn_french.common.fulldialog.roomdatabse.database.AppDatabase;
import com.learn_french.common.fulldialog.roomdatabse.entity.BookmarkedLesson;

import java.util.ArrayList;
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

    private static BookmarkedLesson addUser(final AppDatabase db, BookmarkedLesson lesson) {
        db.userDao().insertAll(lesson);
        return lesson;
    }

    private static void populateWithTestData(AppDatabase db) {
        BookmarkedLesson lesson = new BookmarkedLesson();
        lesson.setTitle("test 6");
        lesson.setEnglishTranslation("English");
        lesson.setFrenchTranslation("French");
        lesson.setTag("tags");
        addUser(db, lesson);

        List<BookmarkedLesson> lessonList = db.userDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + lessonList.size());
    }

    public static ArrayList<Lesson> getAllBookmark(AppDatabase db) {
        List<BookmarkedLesson> lessonList = db.userDao().getAll();
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (BookmarkedLesson obj : lessonList){
            Lesson lesson = new Lesson();
            lesson.setEnglishTranslation(obj.getEnglishTranslation());
            lesson.setFrenchTranslation(obj.getFrenchTranslation());
            lesson.setImageUri(obj.getImageUri());
            lesson.setTag(obj.getTag());
            lesson.setTitle(obj.getTitle());
            lessons.add(lesson);
        }
            return lessons;
    }

    public static void bookmarkALesson(AppDatabase db, Lesson lesson) {
        BookmarkedLesson bookmarkedLesson = new BookmarkedLesson();
        bookmarkedLesson.setTitle(lesson.getTitle());
        bookmarkedLesson.setEnglishTranslation(lesson.getEnglishTranslation());
        bookmarkedLesson.setFrenchTranslation(lesson.getFrenchTranslation());
        bookmarkedLesson.setTag(lesson.getTag());
        bookmarkedLesson.setImageUri(lesson.getImageUri());
        addUser(db, bookmarkedLesson);
    }
    public static void removeBookmark(AppDatabase db, String title) {
        db.userDao().deleteByName(title);
    }


        private static BookmarkedLesson getBookMarkByName(AppDatabase db, String title) {
        return db.userDao().findByName(title);
    }

    public static boolean isBookmarked(AppDatabase db, String title){
        if(null != getBookMarkByName(db, title)){
            return true;
        }
        return false;
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
