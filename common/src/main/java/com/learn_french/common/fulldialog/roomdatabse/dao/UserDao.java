package com.learn_french.common.fulldialog.roomdatabse.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.learn_french.common.fulldialog.roomdatabse.entity.BookmarkedLesson;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM lesson")
    List<BookmarkedLesson> getAll();

    @Query("SELECT * FROM Lesson where title LIKE  :title")
    BookmarkedLesson findByName(String title);

    @Query("SELECT COUNT(*) FROM Lesson where title LIKE  :title")
    int countByName(String title);

    @Query("DELETE FROM Lesson where title LIKE  :title")
    int deleteByName(String title);

    @Query("SELECT COUNT(*) from Lesson")
    int countUsers();

    @Insert
    void insertAll(BookmarkedLesson... lessons);

    @Delete
    void delete(BookmarkedLesson lesson);
}
