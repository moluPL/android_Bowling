package moja.paczka.namespace;

import java.util.List;

import customTheme.Utils;
import MyUtility.Leagues_DB;
import MyUtility.Match_DB;
import MyUtility.MyMatch;
import MyUtility.SearchData;
import MyUtility.Tournament_DB;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditMatch extends Activity {
	
	private static final int DATE_DIALOG_ID = 999;
	protected TextView txtType,txTdate,txTtotal,txTavg,txTgameNo,tmp;
	protected LinearLayout linGames;
	protected SearchData search;
	protected Context context;
	protected Spinner spinLg,spinTour;
	protected Button btn_date,btn_showG,btn_changeTypeToLg,btn_changeTypeToTour,
	btn_changeTypeTotRain,btn_edit_games,btn_save,btn_Cancel;
	protected int lgId,tmpLgId,tmpTourId,tourId,playerId,total,gameNumbrs,matchId;
	protected float avg;
	protected String typeS,dateS;
	protected Match_DB dbMatch;
	protected Fragment_gamesList fg1;
	private FragmentManager fgmng;
	private FragmentTransaction fragTran;
	protected Bundle args;
	private int year;
	private int month;
	private int day;
	
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_match);
		context = getApplicationContext();
		search = new SearchData(context);
		getExtra();
		getData();
		init();
		
	}
	
	private void getData() {
		if(matchId == -1){
			Toast.makeText(context, getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
    		finish();
		}
		//search = new SearchData(context);
		MyMatch match = search.getMatchFromId(matchId);
		playerId = Integer.parseInt(match.getMyMatch_PLAYER_ID());
		typeS = match.getMyMatch_TYPE();
		lgId = Integer.parseInt(match.getMyMatch_LGID());
		tourId = Integer.parseInt(match.getMyMatch_TOURID());
		dateS = match.getMyMatch_DATE();
		total = Integer.parseInt(match.getMyMatch_TOTAL());
		avg = Float.parseFloat(match.getMyMatch_AVG());
		gameNumbrs = Integer.parseInt(match.getMyMatch_NMBR_GAMES());
	}

	private void getExtra() {
		Bundle extras = null;
		matchId=-1;
		if(getIntent().hasExtra(getString(R.string.AllInfoMatches)))
    		extras = getIntent().getExtras();
    	else{
    		Toast.makeText(context, getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
    		finish();
    	}
		if (extras == null){
			Toast.makeText(context, getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
    		finish();
		}
		else{
			matchId = Integer.parseInt(extras.getString(getString(R.string.AllInfoMatches)));
		}
		
	}
	
	
	public void init(){
		btn_edit_games = (Button) findViewById(R.id.Edit_match_EditGames);
		btn_showG = (Button) findViewById(R.id.edit_match_showGamesBtn);
		btn_date = (Button) findViewById(R.id.Edit_match_date);
		btn_changeTypeTotRain = (Button) findViewById(R.id.Edit_Match_changeToTrain);
		btn_changeTypeToLg = (Button) findViewById(R.id.edit_Match_changeToLG);
		btn_changeTypeToTour = (Button) findViewById(R.id.Edit_Match_ChangeToTour);
		btn_Cancel = (Button) findViewById(R.id.edit_Match_button_cancel);
		btn_save = (Button) findViewById(R.id.edit_Match_button_save);
		spinLg = (Spinner) findViewById(R.id.edit_MatchSpinner_typeLg);
		spinTour = (Spinner) findViewById(R.id.edit_MatchSpinner_typeTour);
		linGames = (LinearLayout) findViewById(R.id.Edit_match_GamesList);
		//search = new SearchData(context);
		loadLeague(spinLg,btn_changeTypeToLg);
		loadTour(spinTour, btn_changeTypeToTour);
		spinLg.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		tmpLgId = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		btn_changeTypeToLg.setEnabled(true);
    	    	}
    	    	else
    	    		btn_changeTypeToLg.setEnabled(false);
    	    }
			public void onNothingSelected(AdapterView<?> parent) {
			}
    	});
		spinTour.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		tmpTourId = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		btn_changeTypeToTour.setEnabled(true);
    	    	}
    	    	else
    	    		btn_changeTypeToTour.setEnabled(false);
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
		setupData();
		loadGames();
		OnClickListener onClkLis = new OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				if(v == btn_date){
					showDialog(DATE_DIALOG_ID);
				}else if(v==btn_changeTypeToLg){
					typeS=getString(R.string.type_league);
						lgId=tmpLgId;
						tourId=-1;
						setupData();
				}else if(v==btn_changeTypeToTour){
					typeS=getString(R.string.type_tour);
						tourId=tmpTourId;
						lgId=-1;
						setupData();
				}else if(v==btn_changeTypeTotRain){
						typeS=getString(R.string.type_train);
						lgId=-1;
						tourId=-1;
						setupData();
				}else if(v==btn_Cancel){
					setResult(RESULT_CANCELED);
					finish();
				}
				else if(v==btn_edit_games){
					Intent myIntent = new Intent(getApplicationContext(), EditGames.class);			//tworzenie intencji 
	   		 		myIntent.putExtra(getString(R.string.player_id),playerId);
	   		 		myIntent.putExtra(getString(R.string.Match_info_mid),matchId);
	   		 		startActivityForResult(myIntent, 0);
	   		 		
				}
				else if(v==btn_showG){
					if(v.getTag()==getString(R.string.p1))
					{
						
						linGames.setVisibility(View.GONE);
						linGames.setEnabled(false);
						btn_showG.setText(getString(R.string.edit_match_show_games));
						btn_showG.setTag(getString(R.string.p0));
					}
					else{
						if(!linGames.isEnabled()){
							linGames.setVisibility(View.VISIBLE);
							linGames.setEnabled(true);
							btn_showG.setTag(getString(R.string.p1));
							btn_showG.setText(getString(R.string.edit_match_hide_games));
						}
						else
						{
							linGames.setVisibility(View.VISIBLE);
							linGames.setEnabled(true);
							btn_showG.setTag(getString(R.string.p1));
							btn_showG.setText(getString(R.string.edit_match_hide_games));	
							if(fragTran!=null && !(fragTran.isEmpty()) )
							{
								fragTran.remove(fg1);
							}
								
								 fgmng = getFragmentManager();
								 fragTran = fgmng.beginTransaction();
								 fg1 = new Fragment_gamesList();
								 args=new Bundle();
							     args.putInt(getString(R.string.player_id),playerId);
							     args.putInt(getString(R.string.Match_info_mid),matchId);
							     fg1.setArguments(args);
								 fragTran.add(R.id.Edit_match_GamesList,fg1);
								 fragTran.commit();
						}
					}
						 
				}else if(v==btn_save){
					dbMatch = new Match_DB(context);
					dbMatch.updateMatch(matchId, playerId, typeS, lgId, tourId, dateS, false,-1, total, avg, gameNumbrs);
					setResult(RESULT_OK);
					finish();
				}
			}
		};
		btn_showG.setOnClickListener(onClkLis);
		btn_showG.setTag(getString(R.string.p0));
		btn_Cancel.setOnClickListener(onClkLis);
		btn_save.setOnClickListener(onClkLis);
		btn_changeTypeToLg.setOnClickListener(onClkLis);
		btn_changeTypeToTour.setOnClickListener(onClkLis);
		btn_changeTypeTotRain.setOnClickListener(onClkLis);
		btn_date.setOnClickListener(onClkLis);
		btn_edit_games.setOnClickListener(onClkLis);
	}
	
	 
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() 
	{
		
		

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) 
		{
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
		dateS = new StringBuilder().append(year).append("-").append(month + 1)
		   .append("-").append(day)
		   .append(" ").toString();
		 
		// set selected date into textview
		txTdate.setText(getString(R.string.Match_info_Date)+dateS);
		
		// set selected date into datepicker also
		//dpResult.init(year, month, day, null);
		
		}
	};

		
		
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
	/**
	 * 
	 */
	private void loadGames() {
		// TODO Auto-generated method stub
		
	}


	private void setupData() {
		txTavg = (TextView) findViewById(R.id.Edit_Match_averageTxt);
		txtType = (TextView) findViewById(R.id.edit_match_typeTxt);
		txTdate = (TextView) findViewById(R.id.edit_match_dateTxt);
		txTgameNo = (TextView) findViewById(R.id.edit_match_noGamesTxt);
		txTtotal = (TextView) findViewById(R.id.edit_match_totalTxt);
		txTavg.setText(getString(R.string.Match_info_avg)+avg);
		txtType.setText(getString(R.string.Match_info_type)+typeS);
		txTdate.setText(getString(R.string.Match_info_Date)+dateS);
		txTgameNo.setText(getString(R.string.Match_info_gamesNo)+gameNumbrs);
		txTtotal.setText(getString(R.string.Match_info_total)+total);
		String[] partsDate = dateS.split("-");
		year = Integer.parseInt(partsDate[0].trim());
		month = Integer.parseInt(partsDate[1].trim())-1;
		day = Integer.parseInt(partsDate[2].trim());
		
	}
	 	/**
	 	 * load Leagues<br>
	 	 * from database<br>
	 	 * @param spinner_league
	 	 * @param button_league
	 	 */
    	public void loadLeague(Spinner spinner_league,Button button_league){
        	//Database handler
    		Leagues_DB db;
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
    	/**
    	 * 
    	 * @param spinner_tour
    	 * @param button_tournament
    	 */
    	public void loadTour(Spinner spinner_tour,Button button_tournament){
        	//Database handler
    		Tournament_DB tour_db;
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
		 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
		 */
		@Override
		protected void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			
			super.onActivityResult(requestCode, resultCode, data);
			if(resultCode!= RESULT_CANCELED){
				getData();
				init();
			}
		}

}
