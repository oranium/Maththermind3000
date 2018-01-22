package com.example.aron.maththermind;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.Toast;
import com.example.aron.maththermind.adapter.CustomListAdapter;
import com.example.aron.maththermind.model.Items;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by breinhold on 20.12.17.
 */

public class ScoreBoard extends BaseActivity {
    private List<Items> itemsList;
    private ListView listView;
    private CustomListAdapter adapter;
    MusicThread musicThread;
    Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);
        musicThread = MainScreen.musicThread;
        SQLiteDatabase scoreDB = this.openOrCreateDatabase("scoreboard", MODE_PRIVATE, null);
        //initialize and create new adapter with layout list in score_board.xml
        itemsList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, itemsList);
        listView.setAdapter(adapter);
        btnBack = findViewById(R.id.btn_back4);

        try {
            Items items = new Items();
            items.setRank("Rank");
            items.setName("Name");
            items.setSolved("Exercises");
            items.setScore("Score");
            itemsList.add(items);
            Cursor cursor = scoreDB.rawQuery("SELECT  * FROM scores ORDER BY score DESC", null);
            cursor.moveToFirst();
            //read all rows from scoreDB and add to Array
            for (int i = 0; i < 25; i++) {
                if (!cursor.isAfterLast()) {
                    items = new Items();
                    items.setRank(Integer.toString(i + 1));
                    items.setName(cursor.getString(0));
                    items.setScore(cursor.getString(1));
                    items.setSolved(cursor.getString(2));
                    itemsList.add(items);
                    cursor.moveToNext();
                }
            }
            //to adapter: populate list with Array
            adapter.notifyDataSetChanged();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (itemsList.isEmpty()) {
            Toast.makeText(this, "No Highscores available yet. Time to play!", Toast.LENGTH_LONG).show();
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
