package com.example.dell.myapplication;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import static android.R.attr.onClick;
import static com.example.dell.myapplication.R.id.content;

public class MainActivity extends ListActivity implements /*View.OnClickListener,*/AdapterView.OnItemLongClickListener {
     private Button btn;
     private EditText content;
     private MySql mySql;
     private SQLiteDatabase dbRead,dbWriter;
     private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.sure);
        content = (EditText) findViewById(R.id.content);
        mySql = new MySql(this);
        //实现数据库的读写
        dbRead = mySql.getReadableDatabase();
        dbWriter = mySql.getWritableDatabase();

        adapter = new SimpleCursorAdapter(this,R.layout.call,null,new String[] {MySql.CONTENT},
                new int[]{R.id.content});
        setListAdapter(adapter);
        getListView().setOnItemLongClickListener(this);

        //添加数据
       // ContentValues cv = new ContentValues();
        //cv.put("content","我喜欢ll");
       // dbWriter.insert(MySql.TABLE_NAME,null,cv);
        select();

       btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("content",content.getText().toString());
                //添加数据
                dbWriter.insert(MySql.TABLE_NAME,null,cv);
               // dbRead.update("content",cv,"content=?",null);
                select();
            }
        });
    }

    //查询数据
    public void select(){
        Cursor cursor = dbRead.query(MySql.TABLE_NAME,null,null,null,null,null,null);
        //刷新UI
        adapter.changeCursor(cursor);
    }


     //@Override
    //点击添加方法
     /*public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure:{
                //deleteDatabase("user");
                ContentValues cv = new ContentValues();
                cv.put("content","我喜欢13");
                //添加数据
                dbWriter.insert(MySql.TABLE_NAME,null,cv);
                //dbRead.update("content",cv,"content=?",new String[]{String.valueOf(content)});
                select();
                break;}
        }

    }*/


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
