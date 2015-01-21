package com.example.danimal141.todos.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.danimal141.todos.R;
import com.example.danimal141.todos.db.TaskContract;
import com.example.danimal141.todos.db.TaskDBHelper;


public class MainActivity extends ActionBarActivity {

    private TaskDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            final EditText inputField = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle(R.string.message_add_task)
                    .setMessage(R.string.message_guide)
                    .setView(inputField)
                    .setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = inputField.getText().toString();
                            Log.d("MainActivity", task);

                            helper = new TaskDBHelper(MainActivity.this);
                            SQLiteDatabase db = helper.getWritableDatabase();
                            ContentValues values = new ContentValues();

                            values.clear();
                            values.put(TaskContract.Columns.TASK, task);

                            db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                        }
                    })
                    .setNegativeButton(R.string.action_cancel, null)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        helper = new TaskDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns.ID, TaskContract.Columns.TASK},
                null, null, null, null, null);
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item_task,
                cursor,
                new String[]{TaskContract.Columns.TASK},
                new int[]{R.id.task_text},
                0
        );
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(listAdapter);
    }
}
