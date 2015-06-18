package moja.paczka.namespace;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import customTheme.Utils;
import MyUtility.Players_Db;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class EditPlayer extends Activity {

	
	
	
	 
	/**
	 * Params
	 */
	protected Players_Db player_database;
	protected String name,surname,email,home;
	protected boolean hand;
	protected int year_birth,current_year;
	private EditText fname,fsurname,femail,fhome,fage;
	//private Matcher matcher;
	private Pattern patt;
	private RadioGroup radioGroup;
	private Button save,cancel;
	private Calendar calendar;
	private OnClickListener onClickLis;
	private int id1;
	private Spinner spin;
	RadioButton rh01,rh02;
	private static final String NAME_PATTERN = "^[a-z_A-Z]{3,15}$";
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_player);
		init();
		disenable(false);
	}
	

	protected void init(){
		calendar = Calendar.getInstance();
		current_year=calendar.get(Calendar.YEAR);
		
		save = (Button) findViewById(R.id.edit_player_button_save);
		cancel = (Button) findViewById(R.id.edit_player_button_cancel);
		fname = (EditText) findViewById(R.id.edit_player_text_name);
		fsurname = (EditText) findViewById(R.id.edit_player_text_surname);
		fhome =	(EditText) findViewById(R.id.edit_player_text_hometown);
		femail = (EditText) findViewById(R.id.edit_player_text_email);
		fage = (EditText) findViewById(R.id.edit_player_text_age);
		spin = (Spinner) findViewById(R.id.edit_player_spinner_players);
		radioGroup = (RadioGroup) findViewById(R.id.edit_player_radioGroup1);
		rh01 = (RadioButton) findViewById(R.id.edit_player_radio0);
		rh02 = (RadioButton) findViewById(R.id.edit_player_radio1);
		loadPlayers();
		
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {
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
    	
		onClickLis = new OnClickListener(){
			public void onClick(View v) {
				if(v == save){
					getValue();
				}else if(v==cancel){
					Toast.makeText(getApplicationContext(),getString(R.string.cancel), Toast.LENGTH_SHORT).show();
					finishes(false);
				}
			}
		};
		
		save.setOnClickListener(onClickLis);
		cancel.setOnClickListener(onClickLis);
	}
	/**
	 * 
	 * @param pl_id
	 */
	public void fill(int pl_id){
		try{
			player_database = new Players_Db(getApplicationContext());
			String[][] player = player_database.getPlayerFromId(pl_id);
			if(player!=null){
				disenable(true);
				fname.setText(player[1][1]);
				fsurname.setText(player[1][2]);
				femail.setText(player[1][3]);
				Boolean bol = Boolean.parseBoolean(player[1][4]);
				rh01.setChecked(bol);
				rh02.setChecked(!bol);
				if(!player[1][5].equalsIgnoreCase("0"))
					fhome.setText(player[1][5]);
				else
					fhome.setText("");
				if(!player[1][6].equalsIgnoreCase("0"))
					fage.setText(player[1][6]);
				else
					fage.setText("");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			player_database.close();
		}
		
		
		
	}
	/**
	 * 
	 * @param fil
	 */
	public void disenable(boolean fil){
		save.setEnabled(fil);
		fname.setEnabled(fil);
		fsurname.setEnabled(fil);
		fhome.setEnabled(fil);
		femail.setEnabled(fil);
		fage.setEnabled(fil);
	}
	/**
	 * 
	 */
	protected void getValue(){
		String tmp;
		boolean[] data_ok = new boolean[5];
		patt = Pattern.compile(NAME_PATTERN);
		
		tmp=femail.getText().toString();
		if(android.util.Patterns.EMAIL_ADDRESS.matcher(tmp).matches()){
			email=tmp;
			data_ok[0]=true;
		}
		else{
			femail.setError(getString(R.string.createPlayerErrorEmail));
			data_ok[0]=false;
		}
		tmp=fname.getText().toString();
		if(patt.matcher(tmp).matches()){
			name=tmp;
			data_ok[1]=true;
		}
		else{
			fname.setError(getString(R.string.createPlayerErrorName));
			data_ok[1]=false;
		}
		tmp=fsurname.getText().toString();
		if(patt.matcher(tmp).matches()){
			surname=tmp;
			data_ok[2]=true;
		}
		else{
			fsurname.setError(getString(R.string.createPlayerErrorSurName));
			data_ok[2]=false;
		}
		
		if(radioGroup.getId()==rh01.getId())
			hand=true;
		else
			hand=false;
		
		tmp=fhome.getText().toString();
		if(tmp.length()!=0){
			if(patt.matcher(tmp).matches()){
				home=tmp;
				data_ok[3]=true;
			}
			else{
				fhome.setError(getString(R.string.createPlayerErrorhome));
				data_ok[3]=false;
			}
		}
		else{
			data_ok[3]=true;
			home = "none";
		}
		tmp=fage.getText().toString();
		if(tmp.length()!=0){
			try{
				int year=Integer.parseInt(tmp);
				if(1900<year && year<current_year){
					year_birth=year;
					data_ok[4]=true;
				}
				else{
					fage.setError(getString(R.string.createPlayerErrorYear)+current_year);
					data_ok[4]=false;
				}
			}catch(NumberFormatException e){
				Log.e("Error parsnig int", e.toString());
				fage.setError(getString(R.string.createPlayerErrorYear)+current_year);
				data_ok[4]=false;
			}
		}
		else{
			year_birth=current_year;
			data_ok[4]=true;
		}
		if(areAllTrue(data_ok))
			save();
	}
	public static boolean areAllTrue(boolean[] array)
	{
	    for(boolean b : array) if(!b) return false;
	    return true;
	}
	/**
	 * 
	 */
	private void save(){
		player_database = new Players_Db(getApplicationContext());
		if(player_database.updatePlayer(id1,name,surname,email,hand,home,year_birth)){
			Toast.makeText(getApplicationContext(), getString(R.string.playerAddedtoDB), Toast.LENGTH_SHORT).show();
			finishes(true);
		}
		else
			Toast.makeText(getApplicationContext(), getString(R.string.createPlayerErrorCantAdd), Toast.LENGTH_LONG).show();
	}
	//TODO zrobic jakas madrze wyswietlanie komunikatu
	public void finishes(boolean saved) {
		if(saved){
			setResult(RESULT_OK);
		}
		else
			  setResult(RESULT_CANCELED);
		super.finish();
	}
	/**
	 * 
	 */
	public void loadPlayers(){
		try{
			player_database = new Players_Db(getApplicationContext());
			// Spinner Drop down elements
			List<String> names = player_database.getAllNames();
			
			if(names.size() > 0)
			{
				names.add(0, getString(R.string.one_player_choose));
			}
			else{
				names.add(getString(R.string.error_no_data));
				
			}
			// Creating adapter for spinner
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
               R.layout.spinner_item, names);

			// 	Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(R.layout.spinner_item);
			
			// 	attaching data adapter to spinner
			spin.setAdapter(dataAdapter);
		}catch(Exception e){
			Log.e(getString(R.string.Debug_error), e.toString());
		}finally{
			player_database.close();
		}
   }
	
	
	

}
