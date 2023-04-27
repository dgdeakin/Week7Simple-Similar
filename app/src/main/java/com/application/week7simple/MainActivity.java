package com.application.week7simple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.application.week7simple.databinding.ActivityMainBinding;
import com.application.week7simple.room.MyData;
import com.application.week7simple.room.MyDataDao;
import com.application.week7simple.room.MyDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MyDatabaseHelper myDatabaseHelper;
    MyDatabase myDatabase;
    MyDataDao myDataDao;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDatabaseHelper = new MyDatabaseHelper(this);

        myDatabase = Room.databaseBuilder(this, MyDatabase.class, "myroomdatabase.db").build();
        myDataDao = myDatabase.myDataDao();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("name", binding.editTextText.getText().toString());
//                db.insert("mytable", null, values);

                MyData myData = new MyData(binding.editTextText.getText().toString());

                databaseWriteExecutor.execute(()->{
                    myDataDao.insert(myData);
                });

            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("name", binding.editTextText.getText().toString());
//                db.update(
//                        "mytable",
//                        values,
//                        "id=?",
//                        new String[]{"2"});

                int id = Integer.parseInt(binding.editTextText2.getText().toString());
                String newValue = binding.editTextText.getText().toString();
                MyData myData = new MyData(newValue);
                myData.setId(id);
                databaseWriteExecutor.execute(()-> {

                    myDataDao.update(myData);
                });




            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
//                db.delete("mytable", "id=?", new String[]{"1"});
                int id = Integer.parseInt(binding.editTextText2.getText().toString());
                String newValue = binding.editTextText.getText().toString();

                MyData myData = new MyData();
                myData.setId(id);
                databaseWriteExecutor.execute(()-> {

                    myDataDao.delete(myData);
                });

            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
//
//                Cursor cursor = db.query(
//                        "mytable",    // Table to query
//                        null,   // Columns to retrieve
//                        null,    // Selection criteria
//                        null,  // Selection criteria values
//                        null,         // Group by clause
//                        null,         // Having clause
//                        null     // Sort order
//                );
//
//                while(cursor.moveToNext()){
//                    int column1Index = cursor.getColumnIndex("name");
//                    String column1Value = cursor.getString(column1Index);
//                    Log.i("Values", column1Value);
//
//
//                }

                databaseWriteExecutor.execute(()->{
                    List<MyData> dataList = myDataDao.getAllData();
                    for(MyData data:dataList){
                        Log.i("Data", data.getName());
                    }
                });


            }
        });


    }
}