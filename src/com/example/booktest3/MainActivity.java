package com.example.booktest3;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	
	private EditText editTextName;
    private EditText editTextAdd;
    private Button btnAdd;
    private Button btnView;
    private static final String SELECT_SQL = "SELECT * FROM persons";
	private SQLiteDatabase db;
	private Cursor c;
	TextView tv1;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        createDatabase();
    	
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAdd = (EditText) findViewById(R.id.editTextAddress);
 
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        tv1=(TextView)findViewById(R.id.textViewShow);
        
        btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v == btnAdd){
		            insertIntoDB();
		        }
				
			}
		});
        btnView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openDatabase();
		        c = db.rawQuery(SELECT_SQL, null);
		        c.moveToFirst();
		        showRecords();
			}
		});
    }
    
    protected void showRecords() {
        String id = c.getString(0);
        String name = c.getString(1);
        String add = c.getString(2);
       // editTextId.setText(id);
        editTextName.setText(name);
        editTextAdd.setText(add);
        tv1.setText(name.toString());
    }
    protected void openDatabase() {
        db = openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
    }
    protected void createDatabase(){
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS persons(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR,address VARCHAR);");
    }
    
    protected void insertIntoDB(){
        String name = editTextName.getText().toString().trim();
        String add = editTextAdd.getText().toString().trim();
        if(name.equals("") || add.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        
 
        String query = "INSERT INTO persons (name,address) VALUES('"+name+"', '"+add+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
    }
    
 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
