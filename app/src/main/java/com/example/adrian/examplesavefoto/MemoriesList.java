package com.example.adrian.examplesavefoto;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.adrian.examplesavefoto.adapter.MemoryAdapter;
import com.example.adrian.examplesavefoto.adapter.MemoryData;

import java.util.ArrayList;
import java.util.List;


public class MemoriesList extends ActionBarActivity {
    ContentResolver contentResolver;
    List<MemoryData> memoryList;

    GridView gridview;
    MemoryAdapter memoryAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        getMemories();
        memoryAdapter = new MemoryAdapter(this, memoryList);
        // Set the Adapter for the GridView
        gridview.setAdapter(memoryAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories_list);
        gridview = (GridView) findViewById(R.id.gridView);
    }

    private void getMemories() {
        this.contentResolver = getContentResolver();

        Cursor coursesListCursor = this.contentResolver.query(
                MemoriesProvider.CONTENT_URI,
                new String[]{MemoriesProvider.MEMORY_IMAGE, MemoriesProvider.MEMORY_AUDIO, MemoriesProvider.MEMORY_VIDEO},
                null,
                null,
                null);
        fromCursor(coursesListCursor);
    }

    private void fromCursor(Cursor cursor) {
        memoryList = new ArrayList<MemoryData>();
        if (cursor.moveToFirst()) {
            do {


                String imagePath = cursor.getString(
                        cursor.getColumnIndex(MemoriesProvider.MEMORY_IMAGE)
                );
                String audioPath = cursor.getString(
                        cursor.getColumnIndex(MemoriesProvider.MEMORY_AUDIO)
                );
                String videoPath = cursor.getString(
                        cursor.getColumnIndex(MemoriesProvider.MEMORY_VIDEO)
                );


                memoryList.add(
                        new MemoryData(
                                "Title",
                                "Text",
                                audioPath,
                                videoPath,
                                imagePath,
                                2.02,
                                43.02));
                Log.i("BBDD","FIN");

            } while (cursor.moveToNext());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memories_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.addMemory:
                Intent i = new Intent(MemoriesList.this,
                        MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
