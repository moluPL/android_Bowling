package moja.paczka.namespace;

import java.util.List;

import customTheme.Utils;
import MyUtility.Leagues_DB;
import MyUtility.Players_Db;
import MyUtility.Tournament_DB;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class allInfo extends Activity {

	
	
	private OnClickListener btn_all_charts_info,btn_league_Charts_listener,btn_tour_charts_listener;
	private int league_id,tour_id;
	private	Spinner spinner_league,spinner_tour,spinner_player;
	private Button button_all_info , button_all_charts,button_league_charts , button_league_info,
	button_tournament_charts , button_tournament_info;
	private Leagues_DB db;
	private Tournament_DB tour_db;
	private int player_id;
	protected Context context;
	
	public allInfo() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_chart_to_show);
		context = getApplicationContext();
		init();
		
	}
	public void init(){
		spinner_league = (Spinner) findViewById(R.id.SelectChart_spinner_choose_lg);
		
    	spinner_tour =  (Spinner) findViewById(R.id.SelectChart_spinner_choose_tour);
    	spinner_player =  (Spinner) findViewById(R.id.SelectChart_spinner_choose_player);
    	
		//button_training = (Button) findViewById(R.id.training_button);
		button_all_charts = (Button) findViewById(R.id.SelectChart_allInfo_chart_btn);
		button_all_info = (Button) findViewById(R.id.SelectChart_allInfo_btn);
		button_league_charts = (Button) findViewById(R.id.SelectChart_charts_Lg_btn); 
		button_league_info = (Button) findViewById(R.id.SelectChart_allInfo_league_btn);
		button_tournament_charts = (Button) findViewById(R.id.SelectChart_charts_tour_btn); 
		button_tournament_info = (Button) findViewById(R.id.SelectChart_allInfo_tour_btn);
		loadPlayers();
		loadLeague();
		loadTour();
		/*
		 *	SPINNERS 
		 */
		spinner_player.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	        // your code here
    	    	if(position > 0){
    	    		button_all_charts.setError(null);
    	    		button_all_charts.setEnabled(true);
    	    		button_all_charts.setClickable(true);
    	    		button_all_info.setError(null);
    	    		button_all_info.setEnabled(true);
    	    		button_all_info.setClickable(true);
    	    		String tmp = parentView.getSelectedItem().toString(); 
    	    		player_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		if(league_id>=0)
    	    			disableBtn(true, 1);
    	    		if(tour_id>=0)
    	    			disableBtn(true, 2);
    	    			
    	    		
    	    	}
    	    	else if(position == 0){
    	    		player_id=-1;
    	    		disableBtn(false,0);
    	    		button_all_charts.setError(getString(R.string.error_select));
    	    		button_all_info.setError(getString(R.string.error_select));
    	    	}
    	    }

    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here
    	    	
    	    }

    	});
		spinner_league.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0 ){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		league_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		if(player_id!=-1){
    	    			button_league_charts.setEnabled(true);
    	    			button_league_info.setEnabled(true);
    	    			button_league_charts.setError(null);;
    	    			button_league_info.setError(null);;
    	    			button_league_charts.setClickable(true);
    	    			button_league_info.setClickable(true);
    	    		}
    	    	}
    	    	else{
    	    		league_id=-1;
    	    		button_league_charts.setEnabled(false);
    	    		button_league_info.setEnabled(false);
    	    	}
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
		
		spinner_tour.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0 ){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		tour_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		if(player_id!=-1){
    	    			button_tournament_charts.setEnabled(true);
    	    			button_tournament_info.setEnabled(true);
    	    			button_tournament_charts.setClickable(true);
    	    			button_tournament_info.setClickable(true);
    	    			button_tournament_charts.setError(null);
    	    			button_tournament_info.setError(null);
    	    		}
    	    	}
    	    	else{
    	    		tour_id=-1;
    	    		button_tournament_charts.setEnabled(false);
    	    		button_tournament_info.setEnabled(false);
    	    	}
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
    	
		/*
    	btn_training_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		Intent myIntent = new Intent(getApplicationContext(), Oneplayer.class);			//tworzenie intencji 
   		 		myIntent.putExtra(getString(R.string.player_id),player_id);
   		 		startActivityForResult(myIntent, 0);
   		 	}
    	};
    	*/
		/* listener for all info and ccharts*/
		btn_all_charts_info = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		if(player_id >= 0)
   		 		{
   		 			Intent myIntent = new Intent(getApplicationContext(), Graph_all_game.class);			//tworzenie intencji 
   		 			myIntent.putExtra(getString(R.string.player_id),player_id);
   		 			if(v.getId()==R.id.SelectChart_allInfo_chart_btn)
   		 				myIntent.putExtra(getString(R.string.charts),true);
   		 			else
   		 				myIntent.putExtra(getString(R.string.charts),false);
   		 			startActivity(myIntent);
   		 		}
   		 		else{
   		 			button_all_charts.setError(getString(R.string.error_select));
   		 			button_all_info.setError(getString(R.string.error_select));
   		 		}
   		 	}
    	};
    	button_all_charts.setOnClickListener(btn_all_charts_info);
    	button_all_info.setOnClickListener(btn_all_charts_info);
    	
    	
    	button_league_charts.setOnClickListener(btn_league_Charts_listener);
    	button_league_info.setOnClickListener(btn_league_Charts_listener);
		/* listener for lg charts and info*/
    	btn_league_Charts_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		if(league_id >= 0)
   		 		{
   		 			Intent myIntent = new Intent(getApplicationContext(), Graph_all_game.class);			//tworzenie intencji 
   		 			myIntent.putExtra(getString(R.string.player_id),player_id);
   		 			myIntent.putExtra(getString(R.string.league_id),league_id);
   		 			if(v.getId()==R.id.SelectChart_charts_Lg_btn)
   		 				myIntent.putExtra(getString(R.string.charts),true);
   		 			else
   		 				myIntent.putExtra(getString(R.string.charts),false);
   		 			startActivity(myIntent);
   		 		}
   		 		else{
   		 			button_league_charts.setError(getString(R.string.error_select));
   		 		}
   		 	}
    	};
    	button_league_charts.setOnClickListener(btn_league_Charts_listener);
    	button_league_info.setOnClickListener(btn_league_Charts_listener);
    	
    	/* listener for tour charts and info*/
    	btn_tour_charts_listener = new Button.OnClickListener() {
   		 	public void onClick(View v) {
   		 		if(tour_id >= 0)
   		 		{
   		 			Intent myIntent = new Intent(getApplicationContext(), Graph_all_game.class);			//tworzenie intencji 
   		 			myIntent.putExtra(getString(R.string.player_id),player_id);
   		 			myIntent.putExtra(getString(R.string.tournament_id),tour_id);
   		 			if(v.getId()==button_tournament_charts.getId())
   		 			{
   		 				myIntent.putExtra(getString(R.string.charts),true);
   		 			}
   		 			else
   		 			{
   		 				myIntent.putExtra(getString(R.string.info),true);
   		 			}
   		 			startActivity(myIntent);
   		 		}
   		 		else{
   		 			button_tournament_charts.setError(getString(R.string.error_select));
   		 		}
   		 	}
    	};
    	button_tournament_charts.setOnClickListener(btn_tour_charts_listener);
    	button_tournament_info.setOnClickListener(btn_tour_charts_listener);
    	
    	
    	
    	
	}
	private void loadTour() {
		//Database handler
    	tour_db = new Tournament_DB(getApplicationContext());
    	
    	 // Spinner Drop down elements
        List<String> names = tour_db.getAllNames();
        if(names.size()>0)
        {
        	names.add(0, getString(R.string.select_tour));
        	button_tournament_charts.setError(null);
        	button_tournament_info.setError(null);
        }
        else
        {
        	names.add(0,getString(R.string.error_no_data));
        	button_tournament_charts.setError(getString(R.string.error_no_data));
        	button_tournament_charts.setEnabled(false);
        	button_tournament_info.setError(getString(R.string.error_no_data));
        	button_tournament_info.setEnabled(false);
        	
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

	private void loadPlayers() {
		 
		Players_Db players_database = new Players_Db(getApplicationContext());
		// Spinner Drop down elements
        List<String> names = players_database.getAllNames();
        if(names.size() > 0)
        {
        	names.add(0, getString(R.string.one_player_choose));
        }
        else{
        	names.add(getString(R.string.error_no_data));
        	button_all_charts.setClickable(false);
        	button_all_info.setClickable(false);
        	button_all_info.setError(getString(R.string.error_no_data));
        	button_all_charts.setError(getString(R.string.error_no_data));
        	
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner_player.setAdapter(dataAdapter);
        players_database.close();
		
	}

	public void disableBtn(Boolean state,int spin){
		if(!state){
			button_all_charts.setClickable(state);
			button_all_info.setClickable(state);
			button_league_charts.setClickable(state);
			button_league_info.setClickable(state);
			button_tournament_charts.setClickable(state);
			button_tournament_info.setClickable(state);
			button_all_charts.setEnabled(state);
			button_all_info.setEnabled(state);
			button_league_charts.setEnabled(state);
			button_league_info.setEnabled(state);
			button_tournament_charts.setEnabled(state);
			button_tournament_info.setEnabled(state);
		}
		else if(spin==1){
			button_league_charts.setClickable(state);
			button_league_info.setClickable(state);
			button_league_charts.setEnabled(state);
			button_league_info.setEnabled(state);
		}
		else if(spin==2){
			button_tournament_charts.setEnabled(state);
			button_tournament_info.setEnabled(state);
			button_tournament_charts.setClickable(state);
			button_tournament_info.setClickable(state);
		}
	}
	public void loadLeague()
	{
		//Database handler
		db = new Leagues_DB(getApplicationContext());
		
		// Spinner Drop down elements
		List<String> names = db.getAllNames();
		if(names.size()>0)
		{
			names.add(0, "Select a League");
			button_league_charts.setError(null);
			button_league_info.setError(null);
		}
		else
		{
			names.add(0,getString(R.string.error_no_data));
			button_league_charts.setError(getString(R.string.error_no_data));
			button_league_info.setError(getString(R.string.error_no_data));
			button_league_charts.setEnabled(false);
			button_league_info.setEnabled(false);
			
		}
		
    // 	Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_spinner_item, names);

    // 	Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // 	attaching data adapter to spinner
		spinner_league.setAdapter(dataAdapter);
    	db.close();
	}
		
}


