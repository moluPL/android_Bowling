package moja.paczka.namespace;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import customTheme.Utils;
import MyUtility.MultiSpinner_Players;
import MyUtility.Players_Db;
import MyUtility.Teams_Db;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Create_team extends Activity{

	/**Params*/
	protected Players_Db players_database;
	protected String name,email,home,club;
	protected int no_players;
	protected ArrayAdapter<String> data_no_play;
	protected MultiSpinner_Players multi_spinner;
	private EditText fname,fclub,femail,fhome;
	private Pattern patt;
	private List<String> chosen_palyers;
	private Button save,cancel,create_palyer;
	private static final String NAME_PATTERN = "^[A-Z_a-z]{3,15}$";
	private static final String CLUB_PATTERN = "^[a-zA-z0-9]{3,15}$";
	private TextView players;
	private Teams_Db teams_database;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creating_team);
		init();
	}
	protected void init(){
		
		save = (Button) findViewById(R.id.create_league_button_save);
		cancel = (Button) findViewById(R.id.create_league_button_cancel);
		fname = (EditText) findViewById(R.id.create_text_name);
		fclub = (EditText) findViewById(R.id.create_text_club);
		fhome =	(EditText) findViewById(R.id.create_text_home);
		femail = (EditText) findViewById(R.id.create_text_email);
		players= (TextView) findViewById(R.id.selected_players);
		create_palyer = (Button) findViewById(R.id.button_create_player);
		players_database = new Players_Db(getApplicationContext());
		multi_spinner = (MultiSpinner_Players) findViewById(R.id.spinner_blah);
		List<String> players = loadPlayers();
		if(players != null)
			multi_spinner.setItems(players);
		else
		{
			multi_spinner.setEnabled(false);
		}
		
		save.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v){
				getValue();
			}
		});
		cancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finishes(false);
			}
		});
		create_palyer.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(),Create_player.class);
                startActivityForResult(myIntent, 0);
			}
		});
		
		
	}
	
	/*
	 * This method extracts players from database to spinner to choose some
	 */
	public List<String> loadPlayers(){
   	 // Spinner Drop down elements
		List<String> names = players_database.getAllNames();
		if( names.size() == 0)
	       	return null;
	    players_database.close();
	    return names;
   }
	
	protected void getValue(){
		String tmp;
		boolean[] data_ok = new boolean[5];
		Arrays.fill(data_ok, false);
		patt = Pattern.compile(NAME_PATTERN);
		//checking email
		tmp=femail.getText().toString();
		if(tmp.length()!=0)
		{
			if(android.util.Patterns.EMAIL_ADDRESS.matcher(tmp).matches())
			{
				email=tmp;
				data_ok[0]=true;
			}
			else
			{
				femail.setError("Enter a proper e-mail address");
				data_ok[0]=false;
			}
		}
		else
		{
			data_ok[0]=true;
			email = "none";
		}
		//checking name
		tmp=fname.getText().toString();
		if(patt.matcher(tmp).matches()){
			name=tmp;
			data_ok[1]=true;
		}
		else{
			fname.setError("Enter a proper name(min 3 char. max 15)");
			data_ok[1]=false;
		}
		//checking home
		tmp=fhome.getText().toString();
		if(tmp.length()!=0){
			if(patt.matcher(tmp).matches()){
				home=tmp;
				data_ok[3]=true;
			}
			else{
				fhome.setError("Enter a proper hometown(min 3 char. max 15) or leave it blank");
				data_ok[3]=false;
			}
		}
		else{
			data_ok[3]=true;
			home = "none";
		}
		//checking club name
		tmp=fclub.getText().toString();
		patt = Pattern.compile(CLUB_PATTERN);
		if(tmp.length()!=0){
			if(patt.matcher(tmp).matches()){
				club=tmp;
				data_ok[4]=true;
			}
			else{
				fclub.setError("Enter a proper Club(min 3 char. max 15 only letters and digits) or leave it blank");
				data_ok[4]=false;
			}
		}
		else{
			data_ok[4]=true;
			club = "none";
		}
		//checking if user choose players
		chosen_palyers=multi_spinner.getValues();
		if(chosen_palyers.size()>0)
		{
			data_ok[2]=true;
		}
		else
		{	data_ok[2]=false;
			players.setText("Select Players!");
			players.setError("Select Players!");
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
		
		teams_database= new Teams_Db(getApplicationContext());
		if(teams_database.newTeam(name,email,home,club,chosen_palyers)){
			Toast.makeText(getApplicationContext(), "Team added to the database.", Toast.LENGTH_SHORT).show();
			finishes(true);
		}
		else
			Toast.makeText(getApplicationContext(), "Error!\nCan't add Player!", Toast.LENGTH_LONG).show();
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
