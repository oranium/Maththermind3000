package com.example.aron.maththermind;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.aron.maththermind.adapter.CustomListAdapter;
import com.example.aron.maththermind.model.Items;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by breinhold on 20.12.17.
 */

public class ScoreBoard extends AppCompatActivity {
    private List<Items> itemsList;
    private ListView listView;
    private CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);

        SQLiteDatabase scoreDB = this.openOrCreateDatabase("scoreboard",MODE_PRIVATE,null);
        String[] columns = {"name","score"};
        //initialize and create new adapter with layout list in score_board.xml
        itemsList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, itemsList);
        listView.setAdapter(adapter);

        try {
            Cursor cursor = scoreDB.rawQuery("SELECT  * FROM scores ORDER BY score DESC", null);
            cursor.moveToFirst();
            //read all rows from scoreDB and add to Array
            for (int i = 0; i < 10; i++) {
                if (!cursor.isAfterLast()) {
                    Items items = new Items();
                    items.setName(cursor.getString(0));
                    items.setScore(cursor.getString(1));
                    itemsList.add(items);
                    cursor.moveToNext();
                }
            }
            adapter.notifyDataSetChanged();
        }catch(SQLiteException e) {
            e.printStackTrace();
        }
        //to adapter: populate list with Array
    }

}

    /*private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("/data/data/com.example.aron.maththermind/databases/scoreboard.db3", null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException   e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }*/




