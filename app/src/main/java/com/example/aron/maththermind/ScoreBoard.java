package com.example.aron.maththermind;

import android.content.SharedPreferences;
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
    LinearLayout llscoreBoard;
    SharedPreferences spScore;
    SharedPreferences.Editor spScoreEditor;
    private List<Items> itemsList;
    private ListView listView;
    private CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);

        itemsList = new ArrayList<>();
        SQLiteDatabase scoreDB = null;

        try{
            //create or access score database
            scoreDB = this.openOrCreateDatabase("scoreboard",MODE_PRIVATE,null);

            //table: name - score
            scoreDB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

            //select all rows from table
            Cursor cursor = scoreDB.rawQuery("SELECT * FROM scores",null);

            //if no rows: insert into table
            if(cursor!=null){
                if(cursor.getCount()<10){
                    scoreDB.execSQL("INSERT INTO scores (name,score) VALUES ('placeholder','9001');");
                    scoreDB.execSQL("INSERT INTO scores (name,score) VALUES ('haceplolder','69');");
                    scoreDB.execSQL("INSERT INTO scores (name,score) VALUES ('faceboulder','420');");
                    System.out.println(scoreDB.getPageSize());
                }
            }
        }catch(Exception e){
        }finally{
            //initialize and create new adapter with layout list in score_board.xml
            listView = (ListView) findViewById(R.id.list);
            adapter = new CustomListAdapter(this, itemsList);
            listView.setAdapter(adapter);

            Cursor cursor = scoreDB.rawQuery("SELECT * FROM scores",null);

            cursor.moveToFirst();

                //read all rows from scoreDB and add to Array
                while(!cursor.isAfterLast()){
                    Items items = new Items();

                    items.setName(cursor.getString(0));
                    items.setScore(cursor.getString(1));
                    itemsList.add(items);
                    cursor.moveToNext();
                    adapter.notifyDataSetChanged();

            }
            //to adapter: populate list with Array
            //adapter.notifyDataSetChanged();
        }


    }
}
