package moja.paczka.namespace;

import java.util.Calendar;
import java.util.regex.Pattern;

import customTheme.Utils;
import MyUtility.Players_Db;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Create_player extends Activity{

	/**Params*/
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
	private static final String NAME_PATTERN = "^[a-z_A-Z]{3,15}$";
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creating_player);
		init();
	}
	protected void init(){
		calendar = Calendar.getInstance();
		current_year=calendar.get(Calendar.YEAR);
		
		save = (Button) findViewById(R.id.create_league_button_save);
		cancel = (Button) findViewById(R.id.create_league_button_cancel);
		fname = (EditText) findViewById(R.id.create_text_name);
		fsurname = (EditText) findViewById(R.id.create_text_surname);
		fhome =	(EditText) findViewById(R.id.create_text_hometown);
		femail = (EditText) findViewById(R.id.create_text_email);
		fage = (EditText) findViewById(R.id.create_text_age);
		save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				getValue();
				
				
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),getString(R.string.cancel), Toast.LENGTH_SHORT).show();
				finishes(false);
				
			}
		});
	}
	
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
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		if(radioGroup.getId()==R.id.radio0)
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
	private void save(){
		player_database= new Players_Db(getApplicationContext());
		if(player_database.new_player(name,surname,email,hand,home,year_birth)){
			Toast.makeText(getApplicationContext(), getString(R.string.playerAddedtoDB), Toast.LENGTH_SHORT).show();
			finishes(true);
		}
		else
			Toast.makeText(getApplicationContext(), getString(R.string.createPlayerErrorCantAdd), Toast.LENGTH_LONG).show();
	}
	
	public void finishes(boolean saved) {
		if(saved){
			setResult(RESULT_OK);
		}
		else
			  setResult(RESULT_CANCELED);
		super.finish();
	}
	
	

}
