package moja.paczka.namespace;

import java.util.List;

import customTheme.Utils;
import MyUtility.Leagues_DB;
import MyUtility.Tournament_DB;
import android.app.Activity;
import android.content.Intent;
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
 * @author Karol Molendowski
 * @version 1.0
 * 
 */
public class One_player_list extends Activity {
	
	/** variables*/
	@SuppressWarnings("unused")
	private OnClickListener btn_training_listener,btn_league_listener,btn_tour_listener,btn_create_league_listener,btn_create_tour_listener;
	private int league_id,tour_id;
	private	Spinner spinner_league,spinner_tour;
	private Button button_training,button_league,button_tournament,button_create_lg,button_create_tour;
	private Leagues_DB db;
	private Tournament_DB tour_db;
	private int player_id;
	private Bundle extras;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try{ 
    		Utils.setThemeToActivity(this);
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.one_player_list);
    		init();
    	}catch(Exception e){
    		Log.e("Error OPG list OnCreate",e.toString());
    		e.printStackTrace();
    		}
    	
    }
    /*
     * initialize layout and params
     */
    public void init(){
    	spinner_league = (Spinner) findViewById(R.id.spinner_league);
    	spinner_tour =  (Spinner) findViewById(R.id.spinner_tour);
		button_training = (Button) findViewById(R.id.training_button);
		button_tournament = (Button) findViewById(R.id.play_tournamnet);
		button_league = (Button) findViewById(R.id.play_league);
		button_create_lg = (Button) findViewById(R.id.btn_league_create);
		button_create_tour = (Button) findViewById(R.id.btn_create_tour);
		player_id=getId();
		loadLeague();
		loadTour();
		
		spinner_league.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		league_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ).trim() );
    	    		button_league.setEnabled(true);
    	    	}
    	    	else
    	    		button_league.setEnabled(false);
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
		
		spinner_tour.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		tour_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ).trim() );
    	    		button_tournament.setEnabled(true);
    	    	}
    	    	else
    	    		button_tournament.setEnabled(false);
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
    	
    	btn_training_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		Intent myIntent = new Intent(getApplicationContext(), Oneplayer.class);			//tworzenie intencji 
   		 		myIntent.putExtra(getString(R.string.player_id),player_id);
   		 		startActivityForResult(myIntent, 0);
   		 	}
    	};
    	btn_league_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		if(league_id >= 0)
   		 		{
   		 			Intent myIntent = new Intent(getApplicationContext(), Oneplayer.class);			//tworzenie intencji 
   		 			myIntent.putExtra(getString(R.string.player_id),player_id);
   		 			myIntent.putExtra(getString(R.string.league_id),league_id);
   		 			startActivityForResult(myIntent, 0);
   		 		}
   		 		else{
   		 			button_league.setError(getString(R.string.error_select));
   		 		}
   		 	}
    	};
    	
    	btn_tour_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		if(tour_id >= 0)
   		 		{
   		 			Intent myIntent = new Intent(getApplicationContext(), Oneplayer.class);			//tworzenie intencji 
   		 			myIntent.putExtra(getString(R.string.player_id),player_id);
   		 			myIntent.putExtra(getString(R.string.tournament_id),tour_id);
   		 			startActivityForResult(myIntent, 1);
   		 		}
   		 		else{
   		 			button_tournament.setError(getString(R.string.error_select));
   		 		}
   		 	}
    	};
    	
    	button_training.setOnClickListener(btn_training_listener);
    	button_league.setOnClickListener(btn_league_listener);
    	
    	
    	btn_create_league_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		Intent myIntent = new Intent(getApplicationContext(), Create_league.class);			//tworzenie intencji 
   		 		startActivityForResult(myIntent,0);	
   		 	}
    	};
    	button_create_lg.setOnClickListener(btn_create_league_listener);
    	
    	btn_create_tour_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		Intent myIntent = new Intent(getApplicationContext(), Create_tournament.class);			//tworzenie intencji 
   		 		startActivityForResult(myIntent,1);	
   		 	}
    	};
    	button_create_tour.setOnClickListener(btn_create_tour_listener);
    	
    	btn_league_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		Intent myIntent = new Intent(getApplicationContext(), Oneplayer.class);			//tworzenie intencji 
   		 		myIntent.putExtra(getString(R.string.player_id),player_id);
   		 		if(league_id!=0)
   		 			myIntent.putExtra(getString(R.string.league_id),league_id);
   		 		startActivityForResult(myIntent, 0);	
   		 	}
    	};
    	button_league.setOnClickListener(btn_league_listener);
    	
    	findViewById(R.id.one_player_list_back_btn).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==0 && resultCode == 1){//back from creating league
    		loadLeague();
    	}
    	else if(requestCode==1 && resultCode == 1){
    		loadTour();
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    /*
     * Get id of player from upper instance
     */
    public int getId(){
    	if(getIntent().hasExtra(getString(R.string.player_id)))
    		extras = getIntent().getExtras();
    	else
    		return -1;
		if (extras == null){
		    return -1;
		}
		else{
			int temp = extras.getInt(getString(R.string.player_id),-1);
			return temp;
		}
			
    }
    
    /*
     * load Leagues
     * from database
     */
    	public void loadLeague(){
        	//Database handler
        	db = new Leagues_DB(getApplicationContext());
        	
        	 // Spinner Drop down elements
            List<String> names = db.getAllNames();
            if(names.size()>0)
            {
            	names.add(0, "Select a League");
            	button_league.setError(null);
            }
            else
            {
            	names.add(0,getString(R.string.error_no_data));
            	button_league.setError(getString(R.string.error_no_data));
            	button_league.setEnabled(false);
            	
            }
     
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, names);
     
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     
            // attaching data adapter to spinner
            spinner_league.setAdapter(dataAdapter);
            db.close();
    	}
    	
    	/*
         * load TOURNAMENTS
         * from database
         */
        	public void loadTour(){
            	//Database handler
            	tour_db = new Tournament_DB(getApplicationContext());
            	
            	 // Spinner Drop down elements
                List<String> names = tour_db.getAllNames();
                if(names.size()>0)
                {
                	names.add(0, "Select Tournament");
                	button_tournament.setError(null);
                }
                else
                {
                	names.add(0,getString(R.string.error_no_data));
                	button_tournament.setError(getString(R.string.error_no_data));
                	button_tournament.setEnabled(false);
                	
                }
         
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, names);
         
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         
                // attaching data adapter to spinner
                spinner_tour.setAdapter(dataAdapter);
                tour_db.close();
        	}
        
        
    
    /* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
    
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		db.close();
		super.finish();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		db.close();
		super.onPause();
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//db.open();
		super.onResume();
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		db.close();
		super.onStop();
	}


}
