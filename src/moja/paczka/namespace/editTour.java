package moja.paczka.namespace;

import java.util.List;
import java.util.regex.Pattern;

import customTheme.Utils;
import MyUtility.Patterns;
import MyUtility.Patterns_DB;
import MyUtility.Tournament_DB;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class editTour extends Activity {

	private EditText name,home;
	private static final String NAME_PATTERN = "^[A-Z_a-z0-9]{3,25}$";
	private Pattern patt;
	private Button btn_patterns,btn_patterns2,btn_save,btn_cancel;
	private Patterns_DB databasePattern;
	private Tournament_DB databasetournament;
	private TextView txt_select_patt,txt_select_patt2;
	private String vname,vhome,pattR_id,pattL_id;
	private CheckBox check,checkSame; 
	private Spinner spinner_tours;
	private int id1;
	//private final static String unknownPatt = "-1";
	/* (non-Javadoc)
	 * @see android.app.Activity#onedit(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour);
		init();
	}
	public void init(){
		pattR_id="1";
		pattL_id="1";
		name = (EditText) findViewById(R.id.edit_tournament_text_name);
		home = (EditText) findViewById(R.id.edit_tournament_text_home);
		txt_select_patt = (TextView) findViewById(R.id.edit_tournament_patternselectTXT);
		btn_patterns = (Button) findViewById(R.id.edit_tournament_btn_select_pattern);
		txt_select_patt2 = (TextView) findViewById(R.id.edit_tournament_patternselectTXT2);
		btn_patterns2 = (Button) findViewById(R.id.edit_tournament_btn_select_pattern2);
		btn_save = (Button) findViewById(R.id.edit_tournament_button_save);
		btn_cancel  = (Button) findViewById(R.id.edit_tournament_button_cancel); 
		check = (CheckBox) findViewById(R.id.edit_tournament_checkBoxUnknow);
		checkSame = (CheckBox) findViewById(R.id.edit_tournament_checkSame);
		spinner_tours = (Spinner) findViewById(R.id.edit_Tour_spinner_);
		loadTours();
		spinner_tours.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if(position > 0){
    	    		String tmp = parentView.getSelectedItem().toString();
    	    		id1 = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
    	    		fill(id1);
    	    	}
    	    	else
    	    		disenable(false);
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
		checkSame.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

			if (buttonView.isChecked()) { 
			//checked
				if(txt_select_patt2.isClickable())
					txt_select_patt2.setClickable(false);
				txt_select_patt2.setText(getString(R.string.checkBoxPatternsSame));
				txt_select_patt2.setError(null);
				btn_patterns2.setEnabled(false);
				pattL_id = pattR_id;
			} 
			else{
			//not checked
				setPatterns(pattR_id,true);
				setPatterns(pattL_id,false);
			} 

			}

	    });
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 

			if (buttonView.isChecked()) { 
			//checked
				if(txt_select_patt.isClickable())
					txt_select_patt.setClickable(false);
				txt_select_patt.setText(getString(R.string.checkUnknown));
				txt_select_patt.setError(null);
				btn_patterns.setEnabled(false);
				txt_select_patt2.setText(getString(R.string.checkUnknown));
				txt_select_patt2.setError(null);
				btn_patterns2.setEnabled(false);
			} 
			else{
			//not checked
				setPatterns(pattR_id,true);
				setPatterns(pattL_id,false);
			} 

			}

	    });
		
		btn_save.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				if(getValue())
				{
					databasetournament = new Tournament_DB(getApplicationContext());
					if(!check.isChecked()){
						if(databasetournament.UpdateTour(id1,vname, vhome, pattR_id,pattL_id)){
							setResult(1);
							Toast.makeText(getApplicationContext(), "Added to Database",Toast.LENGTH_LONG).show();
							finish();
						}
						else
							Toast.makeText(getApplicationContext(), "Error can't add to DB",Toast.LENGTH_LONG).show();
					}
					else{
						if(databasetournament.UpdateTour(id1,vname, vhome, "-1","-1")){
							setResult(1);
							Toast.makeText(getApplicationContext(), "Added to Database",Toast.LENGTH_LONG).show();
							finish();
						}
						else
							Toast.makeText(getApplicationContext(), "Error can't add to DB",Toast.LENGTH_LONG).show();	
					}
				}
					
				
			}
		});
		
		btn_cancel.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				setResult(0);
				Toast.makeText(getApplicationContext(), "Adding canceled!",Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		//selecting patterns
		btn_patterns.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(getApplicationContext(), Selecting_pattern.class);
				startActivityForResult(myIntent, 1);
			}
		});
		btn_patterns2.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				Intent myIntent = new Intent(getApplicationContext(), Selecting_pattern.class);
				startActivityForResult(myIntent, 2);
			}
		});
		
		
		//set texst about selected(default) pattern
		setPatterns(pattR_id,true);
		setPatterns(pattL_id,false);
		
	}
	
	protected void disenable(boolean b) {
		btn_patterns.setEnabled(b);
		btn_patterns2.setEnabled(b);
		btn_save.setEnabled(b);
		check.setEnabled(b);
		checkSame.setEnabled(b);
		home.setEnabled(b);
		name.setEnabled(b);
		home.setEnabled(b);
	}
	protected void fill(int id12) {
		databasetournament = new Tournament_DB(getApplicationContext());
		String[][] tours = databasetournament.getLeagueFromId(id12);
		if(tours!=null){
			disenable(true);
			name.setText(tours[1][1]);
			home.setText(tours[1][2]);
			pattL_id = tours[1][3];
			pattR_id = tours[1][3];
			setPatterns(pattL_id, false);
			setPatterns(pattR_id, true);
		}
		
	}
	private void loadTours() {
		//Database handler
    	databasetournament = new Tournament_DB(getApplicationContext());
    	
    	 // Spinner Drop down elements
        List<String> names = databasetournament.getAllNames();
        if(names.size()>0)
        {
        	names.add(0,getString(R.string.select_tour));
        }
        else
        {
        	names.add(0,getString(R.string.error_no_data));
        }
 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner_tours.setAdapter(dataAdapter);
        databasetournament.close();
		
	}
	/*
	 * Get value from edittext
	 */
	public boolean getValue()
	{
		String tmp;
		patt = Pattern.compile(NAME_PATTERN);
		tmp = name.getText().toString();
		if(patt.matcher(tmp).matches()){
			vname = tmp;
		}
		else{
			name.setError(getString(R.string.error_nameTxt));
			return false;
		}
		
		tmp = home.getText().toString();
		if(tmp.length()!=0){
			if(patt.matcher(tmp).matches()){
				vhome=tmp;
			}
			else{
				home.setError(getString(R.string.error_homeTXt));
				return false;
			}
		}
		return true;
	}
	
	/*
	 * sett up text with selected pattern
	 */
	public void setPatterns(String id,boolean right){
		databasePattern = new Patterns_DB(getApplicationContext());
		Patterns patt = databasePattern.getPattId(Integer.parseInt(id));	//get pattern from database where pattern _id = id
		if(patt !=null)	//if there is a data from a database
		{		
			if(right){
				if(txt_select_patt.isClickable())
					txt_select_patt.setClickable(false);
				txt_select_patt.setText(patt.getName());
				txt_select_patt.setError(null);
				btn_patterns.setEnabled(true);
			}
			else{
				if(txt_select_patt2.isClickable())
					txt_select_patt2.setClickable(false);
				txt_select_patt2.setText(patt.getName());
				txt_select_patt2.setError(null);
				btn_patterns2.setEnabled(true);
			}
		}//Else where there is no data drom DB
		else{
			btn_patterns.setEnabled(false);
			txt_select_patt.setText(getString(R.string.download));
			txt_select_patt.setError(getString(R.string.download));
			btn_patterns2.setEnabled(false);
			txt_select_patt2.setText(getString(R.string.download));
			txt_select_patt2.setError(getString(R.string.download));
			checkSame.setEnabled(false);
			
			txt_select_patt.setClickable(true);
			OnClickListener downlistener = new OnClickListener() 
			{
				
				public void onClick(View v) {
					Intent myIntent = new Intent(getApplicationContext(), DownloadData.class);
					startActivityForResult(myIntent, 0);
					
				}
			};
			txt_select_patt.setOnClickListener(downlistener);
			txt_select_patt2.setOnClickListener(downlistener);
		}		
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0 && resultCode == 1){
			setPatterns(pattR_id,true);
			setPatterns(pattL_id,false);
		}
		else if(requestCode == 1 && resultCode != -1){
			
			pattR_id = Integer.toString(resultCode);
			setPatterns(pattR_id,true);
			if(checkSame.isChecked()){
				pattL_id = pattR_id;
			}
			
		}
		else if(requestCode == 2 && resultCode != -1){
			
			pattL_id = Integer.toString(resultCode);
			setPatterns(pattL_id,false);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	

}
