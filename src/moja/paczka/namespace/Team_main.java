package moja.paczka.namespace;
import java.util.ArrayList;
import java.util.List;

import customTheme.Utils;
import MyUtility.Teams_Db;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author karlo Molendowski
 * Class activity display menu for team play
 * user can choose Team create new one or get a quick game without choosing any team or player
 * or login to web and download players etc.
 */
public class Team_main extends Activity{

	/**/
	private OnClickListener btn_play_listener;
	private Spinner spiner;
	private Button button_play,button_create,button_quick,/*button_login,*/button_back;
	private Teams_Db teams_database;
	private int team_id;
	
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try{
    		Utils.setThemeToActivity(this);
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.team_play_main);
    		teams_database = new Teams_Db(getApplicationContext());
    		init();
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
    /* (non-Javadoc)
   	 * @see android.app.Activity#finish()
   	 */
   	@Override
   	public void finish() {
   		teams_database.close();
   		super.finish();
   	}
 	/* (non-Javadoc)
   	 * @see android.app.Activity#onPause()
   	 */
   	@Override
   	protected void onPause() {
   		teams_database.close();
   		super.onPause();
   	}
   	/* (non-Javadoc)
   	 * @see android.app.Activity#onResume()
   	 */
   	@Override
   	protected void onResume() {
   		//players_database.open();
   		
   		super.onResume();
   	}
   	/* (non-Javadoc)
   	 * @see android.app.Activity#onStop()
   	 */
   	@Override
   	protected void onStop() {
   		// TODO Auto-generated method stub
   		teams_database.close();
   		super.onStop();
   	}

	public void loadTeams(){
    	 // Spinner Drop down elements
        List<String> names = teams_database.getAllTeams();
        if(names.size()!= 0)
        	names.add(0, "Select a Team");
        else{
        	names =new ArrayList<String>();
        	names.add("Empty database! Create new Team!");
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spiner.setAdapter(dataAdapter);
        teams_database.close();
    }
	/*
	 * Initialize setting up buttons and listeners
	 */
	public void init(){
		
		button_quick = (Button) findViewById(R.id.quick_team_play_button);
		spiner = (Spinner) findViewById(R.id.spinner_team_choosing);
		button_play = (Button) findViewById(R.id.play_team_button);
		button_create = (Button) findViewById(R.id.create_team_button);
		button_back = (Button) findViewById(R.id.team_main_back);
		team_id=0;
		
		loadTeams();
		/*
		 * setting up buttons listeners
		 */
		button_quick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),Oneplayer.class);
                startActivity(myIntent);
            }
        });
		button_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),Create_team.class);
                startActivityForResult(myIntent, 0);
            }
        });
		
    	btn_play_listener = new Button.OnClickListener() {
    		 public void onClick(View v) {
    			 Intent myIntent = new Intent(getApplicationContext(), One_player_list.class);			//tworzenie intencji 
    	         myIntent.putExtra(getString(R.string.player_id),team_id);
    	         startActivity(myIntent);	
    		 }
    	};
    	
    	button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    	
    	button_play.setOnClickListener(btn_play_listener);
    	button_play.setClickable(false);
    	
    	spiner.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	        // your code here
    	    	if(position!=0){
    	    		button_play.setClickable(true);
    	    		button_play.setEnabled(true);
    	    		team_id=(position-1);
    	    	}
    	    	else{
    	    		button_play.setClickable(false); 
    	    		button_play.setEnabled(false);
    	    	}
    	    }

    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here
    	    	
    	    }

    	});
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode!=RESULT_CANCELED){
			loadTeams();
		}
	}

   	
   	
   	
   	
}


	
    

   	
   	
   	