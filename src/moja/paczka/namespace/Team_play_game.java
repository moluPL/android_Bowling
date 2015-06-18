package moja.paczka.namespace;


import android.app.Activity;
/*
import java.text.SimpleDateFormat;
import java.util.Date;
import customTheme.Utils;
import MyUtility.Match_DB;
import MyUtility.MyGame;
import MyUtility.Players_Db;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
*/

/**
 * @author: Karol Molendowski
 * 2012
 * all rights reserved
 * This count team players max score in ten-pin bowling game
 * at starts shows perfect game by touching the frame you can change the score
 */

/*Main window*/
 public class Team_play_game  extends Activity {
	
	 /*
	 private static final int BAD_ID = -1;
	// private static final int DEFAULT_MAX = 50;
	// private static final int DEFAULT_MIN = 1;
	 protected Button[][] tv;										//Buttons with game frames
	 protected TextView[] txHd;									// text with score max
	 protected MyGame[] mojaGra;									//Array instances of game class
	 protected TableRow[] tr;										//
	 protected int gameNumbers;
	 protected int allMaxScore;
	 protected Button resetBtn,save,add;
	 protected EditText hmg;
	 protected TextView tx;
	 protected Bundle extras;
	 private boolean[][] touched;
	 private String player,type;
	 private Players_Db player_database;
	 private Match_DB match_db;
	 private int league_id,tour_id,player_id;
	 //private final static Color TOUCHED_END = Color.argb(alpha, red, green, blue);

    *//** Called when the activity is first created. *//*
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try{
    		Utils.setThemeToActivity(this);
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.oneplayergame);
    		hmg = (EditText) findViewById(R.id.Ethmg);
    		getData();
    		player_database = new Players_Db(getApplicationContext());
    		match_db = new Match_DB(getApplicationContext());    		
    		player = player_database.getNameFromId(player_id);
    		
    	}catch(Exception e){
    		Log.e("ERROR",e.toString());
    		e.printStackTrace();
    	}
    }
    
     * Get extra data from parent Activity
     
    public void getData(){	
		Intent thisIntent = getIntent();
		Bundle extras = thisIntent.getExtras();
		
		if(thisIntent.hasExtra(getString(R.string.tournament_id))){
			tour_id = extras.getInt(getString(R.string.tournament_id));
			type = getString(R.string.type_tour);
		}
		else{
			tour_id = BAD_ID;
			type = getString(R.string.type_train);
		}
		
		if(thisIntent.hasExtra(getString(R.string.player_id)))
			player_id = extras.getInt( getString(R.string.player_id) );
		else
			player_id = BAD_ID;
		
		if(thisIntent.hasExtra(getString(R.string.league_id))){
			league_id = extras.getInt(getString(R.string.league_id) );
			type = getString(R.string.type_league);
		}
		else
		{
			league_id = BAD_ID;
			type = getString(R.string.type_train);
		}
			
		Log.d("DEbug","id = "+player_id+" ligid= "+league_id+" tour id = "+tour_id+" type= "+type);
			
		}
	
     (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	
	
	
	 * Inicjalizacja tablerow
	 
	public void init(){
		allMaxScore=0;
		 mojaGra = new MyGame[gameNumbers];									//creating array of games
	        tr = new TableRow[gameNumbers];
	        tv = new Button[gameNumbers][12];
	        touched = new boolean[gameNumbers][12];
	        txHd = new TextView[gameNumbers];
	        //tx =(TextView) findViewById(R.id.txtMain);
	        tx =  new TextView(this);
	        tx.setTextColor(Color.WHITE);
	        
	        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			TableLayout tl = (TableLayout) findViewById(R.id.tl1);
			TableRow tr_top = (TableRow) findViewById(R.id.OneplrGame_max);
			tr_top.removeAllViews();
			tl.removeAllViews();
			tr_top.addView(tx);
			for(int i=0;i<gameNumbers;i++){
				mojaGra[i]= new MyGame();
				
				tr[i]= new TableRow(this);
				tr[i].setLayoutParams(lp);
				tr[i].setId(1000+i);
				tr[i].setBackgroundColor(Color.GREEN);
				tr[i].setPadding(10, 10, 10, 10);
				drawResult(tr[i],mojaGra[i].GetScore(),i);
				txHd[i]= new TextView(this);
				txHd[i].setText(getString(R.string.ymsig)+(i+1)+"\n"+mojaGra[i].getScore());
				tl.addView(txHd[i]);
				tl.addView(tr[i]);
				allMaxScore += mojaGra[i].getScore(); 
			}
			tx.setText(gameNumbers+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.player)+player);
	}
	
	
     * Rysowanie tabelki z wynikami
     
    public void drawResult(TableRow tr,String[] wyniki,int gameNumber){
    	
    	tr.removeAllViews();
        for(int i = 0; i < wyniki.length; i++){				//wpisywanie wynikow do przyciskow
        	
        	tv[gameNumber][i] = new Button(this);
        	if(i>8){
        		if(touched[gameNumber][i])
        			tv[gameNumber][i].setBackgroundColor(Color.LTGRAY);
        		else
        			tv[gameNumber][i].setBackgroundColor(Color.DKGRAY);
        	}
        	else if(touched[gameNumber][i])
        		tv[gameNumber][i].setBackgroundColor(Color.LTGRAY);
        	else
        		tv[gameNumber][i].setBackgroundColor(Color.BLACK);
        		tv[gameNumber][i].setTextSize(35);
        		tv[gameNumber][i].setText(wyniki[i]);
        		tv[gameNumber][i].setId((gameNumber*12)+i);
        		tv[gameNumber][i].setClickable(true);
        		tv[gameNumber][i].setOnClickListener( btnLtlstener);		//ustawienie listenera
        		tv[gameNumber][i].setTextColor(Color.WHITE);
        		tv[gameNumber][i].setPadding(10, 10, 10, 10);
        		tv[gameNumber][i].setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        		tr.addView(tv[gameNumber][i]);
        }
        
    }
    
    
    wcisniecie ktoregos z przyciskow/ramek 
	android.view.View.OnClickListener btnLtlstener  = new Button.OnClickListener() {
    	 
        public void onClick(View v) {
        	int iid =v.getId();
        	int tmpId;
        	int tmpGameNumber = iid/12;
        	tmpId = iid%12;
        	tv[tmpGameNumber][tmpId].setBackgroundColor(Color.BLACK);				//zmiana tla wcisnietego przycisku jesli pozniej da cancel lub powrot
        	
        	int tmp[]= new int[2];										
        	Intent myIntent = new Intent(getApplicationContext(), Editor.class);			//tworzenie intencji edytowania
        	tmp = mojaGra[tmpGameNumber].getFramePins(tmpId);							//pobieranie ilosci pinow w rzutach w ramce
        	if(tmp != null){												//jesli pobralo piny to dolacz je do intencji
        		myIntent.putExtra(getString(R.string.first), tmp[0]);
        		myIntent.putExtra(getString(R.string.second), tmp[1]);
        		myIntent.putExtra(getString(R.string.frmNmbr),  tmpId);
        		myIntent.putExtra(getString(R.string.gameNumber),tmpGameNumber);
        		startActivityForResult(myIntent, 0);						//uruchomienie aktywnosci z intencji edycji
        	}
                	
        }


   };

	 (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != RESULT_CANCELED){
			int ft = data.getIntExtra(getString(R.string.first),0);
			int st = data.getIntExtra(getString(R.string.second),0);
			int frmnb = data.getIntExtra(getString(R.string.frmNmbr),0);
			int gameNumber = data.getIntExtra(getString(R.string.gameNumber),0);
			mojaGra[gameNumber].edit(frmnb, ft, st);
			TableRow tmp = tr[gameNumber];
			drawResult(tmp,mojaGra[gameNumber].GetScore(),gameNumber);
			findViewById((gameNumber*12)+frmnb).setBackgroundColor(Color.LTGRAY);
			touched[gameNumber][frmnb] = true;
			txHd[gameNumber].setText(getString(R.string.ymsig)+gameNumber+"\n"+mojaGra[gameNumber].getScore());
			allMaxScore=0;
			for(int j=0;j<mojaGra.length;j++)
				allMaxScore+=mojaGra[j].getScore();
			tx.setText(gameNumber+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.player)+player);
		}
		else
			Toast.makeText(getApplicationContext(), getString(R.string.change_cancel), Toast.LENGTH_SHORT).show();
		
	}
	
	
	public void openDialogNumber(View v){
		  /*
		   List<String> strings = new ArrayList<String>( Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11","12","13","14","15") );
		   AlertDialog.Builder builder = new AlertDialog.Builder(this);
		   builder.setTitle(R.string.headNmbrGame);
		   final CharSequence[] items = strings.toArray(new String[strings.size()]);
		   System.out.println(strings.size());
		   builder.setItems(items, new DialogInterface.OnClickListener()
		   {
		   		public void onClick(DialogInterface dialog, int item) {//dzialanie po wybraniu
		   			System.out.println("Wybrano "+item);
		   			gameNumbers = (item+1);
		   			init();
		   		}
		   	});
		   		
		   AlertDialog alert = builder.create();
		   alert.show();
		}
    
	
	*//**
	 * method confirm numbers of games,turn off some buttons and init drawing a game table
	 * @param v
	 *//*
    public void click_ok(View v)    
    {
    		gameNumbers=0;
    		if(hmg.getText().length() != 0 )
    			gameNumbers = Integer.valueOf(hmg.getText().toString());
    		if(gameNumbers!=0){
    			findViewById(R.id.OneplrGame_panel).setVisibility(View.VISIBLE);
    			findViewById(R.id.OneplrGame_max).setVisibility(View.VISIBLE);

    			findViewById(R.id.OneplrGame_first_panelTXT).setVisibility(View.GONE);
    			findViewById(R.id.OneplrGame_first_panel).setVisibility(View.GONE);
    			 InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    			    mgr.hideSoftInputFromWindow(hmg.getWindowToken(), 0);
    			
    			init();
    			
    		}
    }
    
    *//**
     * reset all games
     * @param v
     *//*
    public void reset_all(View v){
    	
    	
    	 AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
    	 myAlertDialog.setTitle(getString(R.string.confirm));
    	 myAlertDialog.setMessage(getString(R.string.sure_reset));
    	 myAlertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

    	  public void onClick(DialogInterface arg0, int arg1) {
    	  // do something when the OK button is clicked
    		  init();
    	  }});
    	 myAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
    	       
    	  public void onClick(DialogInterface arg0, int arg1) {
    	  // do something when the Cancel button is clicked
    	  }});
    	 myAlertDialog.show();
    	 
    }
    *//**
     * add 1
     *//*
    public void add(View v){
    	AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
   	 myAlertDialog.setTitle(getString(R.string.confirm));
   	 myAlertDialog.setMessage(getString(R.string.sure_add));
   	 myAlertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

   	  public void onClick(DialogInterface arg0, int arg1) {
   		  gameNumbers++;
   		  init();
   	  }});
   	 myAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
   	       
   	  public void onClick(DialogInterface arg0, int arg1) {
   		  
   	  }});
   	 myAlertDialog.show();
    }
    *//**
     * save todatabase
     *//*
    public void save(View v){
    	AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
      	 myAlertDialog.setTitle(getString(R.string.confirm));
      	 myAlertDialog.setMessage(getString(R.string.save_confirm));
      	 myAlertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

      	  public void onClick(DialogInterface arg0, int arg1) {
      		  Date cDate = new Date();
      		  String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
      		  boolean isTeam = false;
      		  float avg = (float)(allMaxScore/gameNumbers);
      		  Log.d("DEbug", "type= "+type+"liga= "+league_id+" tour= "+tour_id+" date= "+fDate+" isTeam="+isTeam+" allmx= "+allMaxScore+" avg= "+avg);
      		
      		 if(match_db.addMatch(player_id,type, league_id, tour_id, fDate, isTeam, BAD_ID, allMaxScore, avg,gameNumbers) != -1){
      			  Toast.makeText(getApplicationContext(), getString(R.string.addOK),Toast.LENGTH_SHORT).show();
      			  finish();
      		 }
      		 else
      			 Toast.makeText(getApplicationContext(), getString(R.string.error_add),Toast.LENGTH_SHORT).show();
      		 
      	  }});
      	 myAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
      	       
      	  public void onClick(DialogInterface arg0, int arg1) {
      		  
      	  }});
      	 myAlertDialog.show();
    }
    
*/ }
 