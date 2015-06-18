package moja.paczka.namespace;


import java.util.List;

import customTheme.Utils;
import MyUtility.Db_versions;
import MyUtility.Patterns_DB;
import MyUtility.Throws;
import MyUtility.Xml_throw_to_DB;
import MyUtility.Xml_to_DB;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 *	@author: Karol Molendowski
 *	@version 1.0
 * 	2012/2013
 * 	all rights reserved
 * 	This app count your points in ten-pin bowling 
 */

/*Main window*/
 public class PierwszyActivity extends Activity {
	
	 

	 // buttons from main menu
	 protected Button one_playerBtn;
	 protected Button team_playBtn;
	 protected Button quick_playBtn;
	 protected Button statisticBtn;
	 protected Button backupBtn;
	 protected Button loginBTn;
	 protected Button donwnload_btn;
	 protected Button setting_btn;
	 protected Button edit_btn;
	 protected Button about_btn;
	 protected Context thisContext; 
	 protected Db_versions db_v;
	 protected Xml_to_DB xmlDB;
	 protected Patterns_DB pattDb;
	 protected Throws throwDb;
	 
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    // try catch block returns better error reporting
    try{
    	SharedPreferences prefs = getPreferences(MODE_PRIVATE); 
    	String size = prefs.getString("size", null);
    	String theme = prefs.getString("theme", null);
    	if (size != null && theme !=null) 
    	{
    		Utils.THEME=theme;
    		Utils.settingChanged=true;
    		Utils.SIZE=size;
    	}
    	if(!Utils.settingChanged)
    	{
    		Utils.THEME="Default_theme";
    		Utils.settingChanged=true;
    		Utils.SIZE="DEFAULT";
    	}
    	 Utils.setThemeToActivity(this);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       // thisContext = getApplicationContext();
       
        new MakeDB().execute();
        
        setupView();
        addBtnListeners();
        
                
    }catch(Exception e){
    	Log.e("ERROR",e.toString());
    	e.printStackTrace();
    }
       
    }
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	/**setup buttons  */
	private void setupView(){
		
		one_playerBtn= (Button) findViewById(R.id.one_player_button);
		team_playBtn= (Button) findViewById(R.id.team_play_button);
		quick_playBtn = (Button) findViewById(R.id.quick_play_button);
		statisticBtn= (Button) findViewById(R.id.statistic_button);
		loginBTn= (Button) findViewById(R.id.login_button);
		donwnload_btn = (Button) findViewById(R.id.downloading_button);
		backupBtn = (Button) findViewById(R.id.backup_button);
		setting_btn = (Button) findViewById(R.id.settings_button);
		edit_btn = (Button) findViewById(R.id.mainEdit_button);
		about_btn = (Button) findViewById(R.id.main_About);
	}
	private void addBtnListeners(){
		thisContext = this.getBaseContext();
		
		
		quick_playBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(thisContext,Oneplayer.class);
                startActivity(myIntent);
            }
        });
		
		one_playerBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	
                Intent myIntent = new Intent(v.getContext(),One_player_main.class);
                startActivity(myIntent);
            }
        });
		
		edit_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	
                Intent myIntent = new Intent(v.getContext(),Select_edit.class);
                startActivity(myIntent);
            }
        });
		
		team_playBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(),Team_main.class);
                startActivity(myIntent);
            }
        });
		
		statisticBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),allInfo.class);
                startActivity(myIntent);
            }
        });
		
		loginBTn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(getApplicationContext(), One_player_main.class);
                startActivityForResult(myIntent, 1);
                
            }
        });
		
		donwnload_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),DownloadData.class);
                startActivity(myIntent);
            }
        });
		
		backupBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(getApplicationContext(),BackupRestore.class);
                startActivity(myIntent);
            }
        });
		setting_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(getApplicationContext(),Settings.class);
                startActivity(myIntent);
            }
        });
		about_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	//Intent myIntent = new Intent(getApplicationContext(),TEST.class);
                //startActivity(myIntent);
            }
        });
        

	}
	private class MakeDB extends AsyncTask<Integer,Integer,Integer>
	{

		private ProgressDialog prog;
		private Boolean work=false;
		protected void onPreExecute() {
			
			 thisContext = getApplicationContext();
			   prog = new ProgressDialog(PierwszyActivity.this);
	          prog.setTitle("Adding to DataBase.\n It might take a minute.");
			  prog.setMessage("Please wait...."); 
			  prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			  prog.setCancelable(false);
			  prog.setProgressPercentFormat(null);
	          prog.show();  
		   }
		
		protected void onPostExecute(Integer result) {
			prog.dismiss();
			if(work)
				Toast.makeText(getApplicationContext(), "Done! Welcome!",Toast.LENGTH_LONG).show();
			
		}
		
		protected void onProgressUpdate(Integer... values) {
			prog.setProgress(values[0]);
			

		
			
		}
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		protected Integer doInBackground(Integer... params) {
			db_v = new Db_versions(getBaseContext());
			db_v.open();
			db_v.close();
			
			pattDb = new Patterns_DB(thisContext);
	        
			if(pattDb.getNumberOfRecords()==0)
	        {
	        	work=true;
	        	xmlDB = new Xml_to_DB(thisContext);
	        	xmlDB.parsePatt(false);
	        	xmlDB.add_toDB_local(pattDb);
	        }
	        throwDb = new Throws(thisContext);
	        
	        if(throwDb.getNumberOfrecords()==0)
	        {
	        	work=true;
	        	Xml_throw_to_DB xttDb = new Xml_throw_to_DB(thisContext);
	        	List<String> listaStr = xttDb.AddThrowsFromXML();
	        	int max = listaStr.size();
	        	prog.setMax(max);
	        	int i=0;
	        	for(String s : listaStr){
	        		throwDb.addThrow(s);
	        		i++;
	        		publishProgress((int) (( (i+1) / (float) max) * 100),i,100);
	        		
	        	}
	        }
			
			//Log.d("Debug","Make DB");
			return 1;
		}
		
	}
    
}
 