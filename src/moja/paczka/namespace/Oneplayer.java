package moja.paczka.namespace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import customTheme.Utils;
import MyUtility.Game_one_player_DB;
import MyUtility.Match_DB;
import MyUtility.MyFrame;
import MyUtility.MyGame;
import MyUtility.Players_Db;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author: Karol Molendowski <kmolend@gmail.com>
 * @version beta
 * @since 2013.10.10
 * 
 * This Class count one player max score in ten-pin bowling game
 * at starts shows perfect game by touching the frame you can change the score
 */

/*Main window*/
 public class Oneplayer  extends Activity {
	
	 /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}


	private static final int BAD_ID = -1;
	// private static final int DEFAULT_MAX = 50;
	// private static final int DEFAULT_MIN = 1;
	// protected Button[][] tv;										//DELETE
	 protected List<TextView> txHd;									// text with score max
	 protected List<MyGame> mojaGra;									//Array instances of game class
	 protected List<TableRow> tr;										//
	 protected int gameNumbers;
	 protected int allMaxScore;
	 protected Button resetBtn,save,add;
	 protected EditText hmg;
	 protected TextView tx;
	 protected Bundle extras;
	 private List<boolean[]> touched;
	 protected boolean added=false;
	 private String player,type;
	 private Players_Db player_database;
	 private Match_DB match_db;
	 private Game_one_player_DB game_db;
	 private int league_id,tour_id,player_id;
	 protected TableLayout tl;
	 //private final static Color TOUCHED_END = Color.argb(alpha, red, green, blue);

    /** Called when the activity is first created.
     * 	Call constructor from super class
     * 	set up layout, and get data from parent activity
     * @param savedInstanceState
     *  */
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
    		game_db =  new Game_one_player_DB(getApplicationContext());
    		player = player_database.getNameFromId(player_id);
    		
    	}catch(Exception e){
    		Log.e("ERROR",e.toString());
    		e.printStackTrace();
    	}
    }
    /**
     * Get extra data from parent Activity by intent extras
     */
    public void getData(){
    	/**
    	 * getting intent and extras from this intent
    	 */
		Intent thisIntent = getIntent();
		Bundle extras = thisIntent.getExtras();
		/**
		 * if intent has extras get it and setup variables 
		 */
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
	
    /** (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		//call configurationchanges
		WindowManager winMan = (WindowManager)getBaseContext().getSystemService(Context.WINDOW_SERVICE);
		int tmpo = winMan.getDefaultDisplay().getOrientation();
		Log.d("orientation","change to"+tmpo);
		
		super.onConfigurationChanged(newConfig);
	}
	
	
	
	/**
	 * Initialization tablerow 
	 */
	public void init(){
		
		
		
		allMaxScore=0;
		 mojaGra = new ArrayList<MyGame>();									//creating array of games
	        tr = new ArrayList<TableRow>();
	        //tv = new Button[gameNumbers][12];
	        touched = new ArrayList<boolean[]>();
	        for(int i =0;i<gameNumbers;i++){
	        	touched.add(new boolean[12]);
	        }
	        txHd = new ArrayList<TextView>();
	        //tx =(TextView) findViewById(R.id.txtMain);
	        tx =  new TextView(this);
	        tx.setTextColor(Color.WHITE);
	        
	        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	        lp.setMargins(10, 10, 10, 10);
			tl = (TableLayout) findViewById(R.id.main_table_layout);
			TableRow tr_top = (TableRow) findViewById(R.id.OneplrGame_max);
			tr_top.removeAllViews();
			tl.removeAllViews();
			tr_top.addView(tx);
			for(int i=0;i<gameNumbers;i++){
				mojaGra.add(new MyGame());
				
				tr.add(new TableRow(this));
				tr.get(i).setLayoutParams(lp);
				tr.get(i).setTag(1000+i);
				//tr[i].setBackgroundColor(Color.LTGRAY);
				//tr[i].setPadding(1,1,1,1);
				//drawResult(tr[i],mojaGra[i].GetScore(),i);
				drawResult(tr.get(i),mojaGra.get(i),i);
				txHd.add(new TextView(this));
				txHd.get(i).setText(getString(R.string.ymsig)+(i+1)+"\n"+mojaGra.get(i).getScore());
				txHd.get(i).setTextAppearance(getApplicationContext(), R.style.medium_body_text_white);
				tl.addView(txHd.get(i));
				tl.addView(tr.get(i));
				allMaxScore += mojaGra.get(i).getScore(); 
			}
			tx.setText(gameNumbers+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.Match_info_avg)+":"+(allMaxScore/gameNumbers)+" \n "+getString(R.string.player)+player);
			tx.setTextAppearance(getApplicationContext(), R.style.medium_body_text_white);
	}
	 
    /**
     * wcisniecie ktoregos z przyciskow/ramek */
	OnClickListener btnLtlstener = new OnClickListener() 
	{
	//OnClickListener btnLtlstener  = new OnClickListener() {
        public void onClick(View v) {
        	v.setBackgroundColor(R.drawable.border2);
        	int iid =(Integer) v.getTag();
        	int tmpId;
        	int tmpGameNumber = iid/12;
        	tmpId = iid%12;
        	//tv[tmpGameNumber][tmpId].setBackgroundColor(Color.BLACK);				//zmiana tla wcisnietego przycisku jesli pozniej da cancel lub powrot
        	
        	int tmp[]= new int[2];										
        	Intent myIntent = new Intent(getApplicationContext(), Editor.class);			//tworzenie intencji edytowania
        	tmp = mojaGra.get(tmpGameNumber).getFramePins(tmpId);							//pobieranie ilosci pinow w rzutach w ramce
        	if(tmp != null){												//jesli pobralo piny to dolacz je do intencji
        		
        		myIntent.putExtra("MyFrame", mojaGra.get(tmpGameNumber).getOneFrame(tmpId));
        		myIntent.putExtra(getString(R.string.first), tmp[0]);
        		myIntent.putExtra(getString(R.string.second), tmp[1]);
        		myIntent.putExtra(getString(R.string.frmNmbr),  tmpId);
        		myIntent.putExtra(getString(R.string.gameNumber),tmpGameNumber);
        		startActivityForResult(myIntent, 0);						//uruchomienie aktywnosci z intencji edycji
        	}
        }
   };
	
	
	
	/**
     * Draw table with scores
     * @param tr Tablerow that have to redraw
     * @param wyniki String[] array that contain scores
     * @param gameNumebr int number of game that we have to redraw 
     */
   public void drawResult(TableRow tr,/*String[] wyniki*/MyGame gra,int gameNumber){
   	//removing all old views from tablerow
   	tr.removeAllViews();
   	int ilosc = gra.GetScore().length;
   	TableLayout trMain;
   	TableRow trPart1;
   	TextView txtNoR ;
   	TableRow trPart2 ;
   	TextView txtFr1 ;
   	TableRow trPart3 ; 
   	
   	//for(int i = 0; i < wyniki.length; i++){				//writing scores into the buttons
   	for(int i = 0; i < ilosc; i++)
   	{				
       	
       		
       		trMain = new TableLayout(getBaseContext());
       		trMain = (TableLayout) getLayoutInflater().inflate(R.layout.test_table_layout,tr,false);
       		
       		trMain.setTag((gameNumber*12)+i);
       		trMain.setOnClickListener(btnLtlstener);
       		//tr first part
       		
       		trPart1 = new TableRow(getBaseContext());
       		trPart1 = (TableRow) getLayoutInflater().inflate(R.layout.test_table_row_main, trMain,false);
       		//txtview number
       		
       		txtNoR = new TextView(getBaseContext());
       		txtNoR = (TextView) getLayoutInflater().inflate(R.layout.test_text_view,trPart1,false);
       		txtNoR.setText((i+1)+"");
       		trPart1.addView(txtNoR);
       		trMain.addView(trPart1);
       		
       		//tablerow frame part2
       		
       		trPart2 = new TableRow(getBaseContext());
       		trPart2 = (TableRow) getLayoutInflater().inflate(R.layout.test_table_row, trMain,false);
       		
       		txtFr1 = new TextView(getBaseContext());
       		txtFr1 = (TextView) getLayoutInflater().inflate(R.layout.test_text_view2,trPart2,false);
       		MyFrame ramka = gra.getOneFrame(i); 
       		txtFr1.setText(ramka.firstPinsToString());
       		trPart2.addView(txtFr1);
       		
       		if(!ramka.isStrike())
       		{
       			
       			
       			
       			TextView txtFr2 = new TextView(getApplicationContext());
       			txtFr2 = (TextView) getLayoutInflater().inflate(R.layout.test_text_view2,trPart2,false);
       			txtFr2.setText(ramka.secondPinsToString());
       			trPart2.addView(txtFr2);
       			
       		}
       		trMain.addView(trPart2);
       		
       		trPart3 = new TableRow(getBaseContext());
       		trPart3 = (TableRow) getLayoutInflater().inflate(R.layout.test_table_row,trMain,false);
       		
       		TextView txtScoreMax = new TextView(getBaseContext());
       		txtScoreMax = (TextView) getLayoutInflater().inflate(R.layout.test_text_view,trPart3,false);
       		String score = gra.GetScore()[i].split("]")[1].trim();
       		txtScoreMax.setText(score);
       		trPart3.addView(txtScoreMax);
       		trMain.addView(trPart3);
       		if(touched.get(gameNumber)[i]){
       			trMain.setBackgroundColor(R.drawable.border_touched);
       			for(int k =0;k<trMain.getChildCount();k++){
       				trMain.getChildAt(k).setBackgroundResource(R.drawable.border_touched);
       			}
       		}
       		tr.addView(trMain);
       		
       	}
   
   }
   
   
   
	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	public void finish(View v) {
		super.finish();
	}

	/** (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 * powrót z edycji ramki
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != RESULT_CANCELED){
			int ft = data.getIntExtra(getString(R.string.first),-1);
			int st = data.getIntExtra(getString(R.string.second),-1);
			int frmnb = data.getIntExtra(getString(R.string.frmNmbr),0);
			int gameNumber = data.getIntExtra(getString(R.string.gameNumber),0);
			Boolean filled = data.getBooleanExtra(getString(R.string.filled),false);
			boolean[] tmpFirst=null;
			boolean[] tmpSecond=null;
			if(filled){
				tmpFirst = data.getBooleanArrayExtra(getString(R.string.first_throw));
				tmpSecond = data.getBooleanArrayExtra(getString(R.string.second_throw));
			}

			if(tmpFirst!=null && tmpSecond!=null && filled==true){
				mojaGra.get(gameNumber).edit(frmnb, ft, st,tmpFirst,tmpSecond);
			}
			else if( ft!=-1 && st!=-1){
					mojaGra.get(gameNumber).edit(frmnb, ft, st);
				}
			touched.get(gameNumber)[frmnb] = true;
			TableRow tmp = tr.get(gameNumber);
			drawResult(tmp,mojaGra.get(gameNumber),gameNumber);
			//findViewById((gameNumber*12)+frmnb).setBackgroundColor(Color.LTGRAY);
			
			txHd.get(gameNumber).setText(getString(R.string.ymsig)+gameNumber+"\n"+mojaGra.get(gameNumber).getScore());
			allMaxScore=0;
			for(int j=0; j < mojaGra.size();j++)
				allMaxScore+=mojaGra.get(j).getScore();
			tx.setText(gameNumber+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.Match_info_avg)+":"+(allMaxScore/gameNumbers)+" \n "+getString(R.string.player)+player);
		}
		else
			Toast.makeText(getApplicationContext(), getString(R.string.change_cancel), Toast.LENGTH_SHORT).show();
		
	}
	
	
	/*
	public void openDialogNumber(View v){
		  
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
    
	
	/**
	 * method confirm numbers of games,turn off some buttons and init drawing a game table
	 * @param v View of current activity
	 */
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
    
    /**
     * reset all games
     * @param v View of current activity
     */
    public void reset_all(View v){
    	
    	//bulding alert dialog to confirm
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
    /**
     * add one new game
     * @param v View current view of current activity
     */
    public void add(View v){
    	AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
   	 myAlertDialog.setTitle(getString(R.string.confirm));
   	 myAlertDialog.setMessage(getString(R.string.sure_add));
   	 myAlertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

   	  public void onClick(DialogInterface arg0, int arg1) {
   		touched.add(new boolean[12]);
   		mojaGra.add(new MyGame());
   		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
		tr.add(new TableRow(getBaseContext()));
		tr.get(gameNumbers).setLayoutParams(lp);
		tr.get(gameNumbers).setTag(1000+gameNumbers);
		drawResult(tr.get(gameNumbers),mojaGra.get(gameNumbers),gameNumbers);
		txHd.add(new TextView(getBaseContext()));
		txHd.get(gameNumbers).setText(getString(R.string.ymsig)+(gameNumbers+1)+"\n"+mojaGra.get(gameNumbers).getScore());
		txHd.get(gameNumbers).setTextAppearance(getApplicationContext(), R.style.medium_body_text_white);
		tl.addView(txHd.get(gameNumbers));
		tl.addView(tr.get(gameNumbers));
		allMaxScore += mojaGra.get(gameNumbers).getScore();
		gameNumbers++;
		tx.setText(gameNumbers+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.Match_info_avg)+":"+(allMaxScore/gameNumbers)+" \n "+getString(R.string.player)+player);
		tx.setTextAppearance(getApplicationContext(), R.style.medium_body_text_white);
		
   	  }});
   	 myAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
   	       
   	  public void onClick(DialogInterface arg0, int arg1) {
   		  
   	  }});
   	 myAlertDialog.show();
    }
    /**
     * save to database
     * @param v View view of current activity
     */
    public void save(View v){
    	//bulding alert dialog for confirm
    	AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
      	 myAlertDialog.setTitle(getString(R.string.confirm));
      	 myAlertDialog.setMessage(getString(R.string.save_confirm));
      	 myAlertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
//if confirmed add to database      		
      	  @SuppressLint("SimpleDateFormat")
		public void onClick(DialogInterface arg0, int arg1) 
      	  {
      		  Date cDate = new Date();
      		  String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
      		  boolean isTeam = false;
      		  float avg = (float)(allMaxScore/gameNumbers);
      		  Log.d("DEbug", "type= "+type+"liga= "+league_id+" tour= "+tour_id+" date= "+fDate+" isTeam="+isTeam+" allmx= "+allMaxScore+" avg= "+avg);
      		
      		 long tmp_id = (long) match_db.addMatch(player_id,type, league_id, tour_id, fDate, isTeam, BAD_ID, allMaxScore, avg,gameNumbers);
      		 
      		 if(tmp_id != -1)
      		 {
      			 for(int i=0;i<mojaGra.size();i++){
      				
      				
      				Log.d("Debug Game pins",mojaGra.get(i).getPinsToString());
      				Log.d("Debug Game frames",mojaGra.get(i).getFramesToString());
      				Log.d("playyy","playerid="+player_id);
      				// game_db.addGame((int)tmp_id, player_id, type, i, mojaGra[i].getFramesToString(), 1, -1, -1, mojaGra[i].getPinsToString(),mojaGra[i].getScore());
      				 added = game_db.addGame(mojaGra.get(i), tmp_id, player_id, i, type, /*ballId,*/ -1, -1);
      			 }
      		 }
      		 if(added)
      		 {
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
    
 }
 