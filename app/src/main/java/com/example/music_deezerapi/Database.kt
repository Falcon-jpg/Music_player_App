package com.example.music_deezerapi

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf
import kotlin.coroutines.coroutineContext

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "LikedSongs"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "SongsTable"
        private const val COLUMN_TITLE = "SongName"
        private const val COLUMN_IMAGE = "CoverImage"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FAV_STATUS = "FavStatus"
        private const val COLUMN_ARTIST = "artist"
        private const val COLUMN_LINK = "link"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_IMAGE TEXT, $COLUMN_LINK TEXT,$COLUMN_ARTIST TEXT, UNIQUE ($COLUMN_ARTIST, $COLUMN_TITLE))"

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertSong(song: LikedSong, context: Context) {
        val db = writableDatabase
        val artist = song.artist
        val name = song.name

        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE = ? AND $COLUMN_ARTIST = ?"
        val cursor = db.rawQuery(query, arrayOf(name, artist))

        if (cursor.moveToFirst()) {
            /*Toast.makeText(context, "Song already exists", Toast.LENGTH_SHORT).show()*/
        } else {
            val values = ContentValues().apply {
                put(COLUMN_TITLE, song.name)
                put(COLUMN_IMAGE, song.image)
                put(COLUMN_ARTIST, song.artist)
                put(COLUMN_LINK,song.link)
            }
            db.insert(TABLE_NAME, null, values)
            /*Toast.makeText(context, "Song inserted successfully", Toast.LENGTH_SHORT).show()*/
        }
        cursor.close()
        db.close()
    }

    fun deleteSong(song: LikedSong) {
        val db = writableDatabase
        val name = song.name
        val artist = song.artist
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE =? AND $COLUMN_ARTIST =?"
        val cursor = db.rawQuery(query, arrayOf(name, artist))
        if (cursor.moveToFirst()) {
            db.delete(TABLE_NAME, "$COLUMN_TITLE=? AND $COLUMN_ARTIST=?", arrayOf(name, artist))
        }
        cursor.close()
        db.close()
    }

    fun getSongs() : List<LikedSong>{
        val songList = mutableListOf<LikedSong>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        //cursor iterates through rows of the table ( storing each row information)
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            val artist = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTIST))
            val link = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK))

            val song = LikedSong(name,image,artist,link)
            songList.add(song)
        }
        cursor.close()
        db.close()
        return songList
    }




}