package moja.paczka.namespace;
import java.util.List;

import customTheme.Utils;
import MyUtility.Players_Db;
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
 * Class activity display menu for one player
 * user can choose player create new one or get a quick game without choosing any player
 * or login to web and download players etc.
 */

public class One_player_main extends Activity {

	/*Params*/
	private OnClickListener btn_play_listener;
	private Spinner spiner;
	protected Button button_play,button_create,button_quick,button_login,button_back;
	private Players_Db players_database;
	private int player_id;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try{
    		Utils.setThemeToActivity(this);
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.one_player_main);
    		players_database = new Players_Db(getApplicationContext());
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
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
    
    /* (non-Javadoc)
   	 * @see android.app.Activity#finish()
   	 */
       
   	@Override
   	public void finish() {
   		// TODO Auto-generated method stub
   		players_database.close();
   		super.finish();
   	}

   	/* (non-Javadoc)
   	 * @see android.app.Activity#onPause()
   	 */
   	@Override
   	protected void onPause() {
   		// TODO Auto-generated method stub
   		players_database.close();
   		super.onPause();
   	}
   	/* (non-Javadoc)
   	 * @see android.app.Activity#onResume()
   	 */
   	@Override
   	protected void onResume() {
   		// TODO Auto-generated method stub
   		//players_database.open();
   		
   		super.onResume();
   	}
   	
   	
   	
   	/* (non-Javadoc)
   	 * @see android.app.Activity#onStop()
   	 */
   	@Override
   	protected void onStop() {
   		// TODO Auto-generated method stub
   		players_database.close();
   		super.onStop();
   	}

	public void loadPlayers(){
    	 // Spinner Drop down elements
        List<String> names = players_database.getAllNames();
        if(names.size() > 0)
        {
        	names.add(0, getString(R.string.one_player_choose));
        }
        else{
        	names.add(getString(R.string.error_no_data));
        	button_play.setClickable(false);
        	button_play.setError(getString(R.string.error_no_data));
        	
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spiner.setAdapter(dataAdapter);
        players_database.close();
    }
	public void init(){
		
		button_quick = (Button) findViewById(R.id.quick_play_button);
		spiner = (Spinner) findViewById(R.id.spinner);
		button_play = (Button) findViewById(R.id.opg_play_button);
		button_create = (Button) findViewById(R.id.create_player_button);
		button_back = (Button) findViewById(R.id.one_player_main_back);
		player_id=0;

		loadPlayers();
		/*
		 * setting up buttons listeners
		 */
		button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
		button_quick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),Oneplayer.class);
                startActivity(myIntent);
            }
        });
		button_create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(),Create_player.class);
                startActivityForResult(myIntent, 0);
            }
        });
		
    	btn_play_listener = new Button.OnClickListener() {
    		 public void onClick(View v) {
    			 Intent myIntent = new Intent(getApplicationContext(), One_player_list.class);			//tworzenie intencji 
    	         myIntent.putExtra(getString(R.string.player_id),player_id);
    	         startActivity(myIntent);	
    		 }
    	};
    	
    	button_play.setOnClickListener(btn_play_listener);
    	button_play.setClickable(false);
    	
    	spiner.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	        // your code here
    	    	if(position != 0){
    	    		button_play.setError(null);
    	    		button_play.setEnabled(true);
    	    		button_play.setClickable(true);
    	    		String tmp = parentView.getSelectedItem().toString(); 
    	    		player_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		//Log.d("Debug", "id = "+player_id);
    	    	}
    	    	else if(position == 0){
    	    		button_play.setEnabled(false);
    	    		button_play.setClickable(false);
    	    		button_play.setError(getString(R.string.error_select));
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
			loadPlayers();
		}
	}
	
}
