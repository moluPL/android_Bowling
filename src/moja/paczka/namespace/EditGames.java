package moja.paczka.namespace;

import java.util.ArrayList;
import java.util.List;
import customTheme.Utils;
import MyUtility.Game_one_player_DB;
import MyUtility.Match_DB;
import MyUtility.MyFramFromDB;
import MyUtility.MyFrame;
import MyUtility.MyGame;
import MyUtility.MyGameFromDB;
import MyUtility.MyMatch;
import MyUtility.Players_Db;
import MyUtility.SearchData;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint("InflateParams")
public class EditGames extends Activity {
	
	private static final int BAD_ID = -1;
	// private static final int DEFAULT_MAX = 50;
	// private static final int DEFAULT_MIN = 1;
	// protected Button[][] tv;										//Buttons with game frames
	 protected List<TextView> txHd;									// text with score max
	 protected List<MyGame> mojaGra;									//Array instances of game class
	 protected List<TableRow> tr;									//Array instances of game class
	 protected List<Integer> games_id;
	 protected List<MyGameFromDB> games;
	 protected int gameNumbers;
	 protected int allMaxScore;
	 protected Button resetBtn,save,add;
	 protected EditText hmg;
	 protected TextView tx;
	 protected Bundle extras;
	 private List<boolean[]> touched;
	 private String player;
	 private Players_Db player_database;
	 private Match_DB match_db;
	 private Game_one_player_DB game_db;
	 private int player_id,match_id;
	 private SearchData search;
	 private MyMatch mMatch;
	 private TableLayout tl;
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
   		findViewById(R.id.OneplrGame_panel).setVisibility(View.VISIBLE);
		findViewById(R.id.OneplrGame_max).setVisibility(View.VISIBLE);
		findViewById(R.id.OneplrGame_first_panelTXT).setVisibility(View.GONE);
		findViewById(R.id.OneplrGame_first_panel).setVisibility(View.GONE);
		findViewById(R.id.oneplayergamebtn_add).setVisibility(View.GONE);
		findViewById(R.id.oneplayergamebtn_add).setEnabled(false);;
   		player_database = new Players_Db(getApplicationContext());
   		match_db = new Match_DB(getApplicationContext());
   		game_db =  new Game_one_player_DB(getApplicationContext());
   		player = player_database.getNameFromId(player_id);
   		getData();
   		getFromDB();
   		
   		
   	}catch(Exception e){
   		e.printStackTrace();
   		//Log.e("ERROR",e.toString());
   		//e.printStackTrace();
   	}
   }
   public void finish(View v) {
		super.finish();
	}
   private void getFromDB() 
   {
	   
	   MyFramFromDB myFram;
	   
	   search = new SearchData(getBaseContext());
	   mMatch = search.getMatchFromId(match_id);
	   games = search.getAllGame(true,player_id,2,match_id);
	   try
	   {
		   if(games!=null && games.size()>0)
		   {
			   gameNumbers=games.size();
			   games_id = new ArrayList<Integer>();
			   init();
		   }
		   else
		   {
			   Toast.makeText(getApplicationContext(), getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
	   			finish();
		   }
		for(int j = 0 ; j < gameNumbers ; j++)
		{
			games_id.add(games.get(j).getGAME_ID());
			for(int i =0;i<12;i++)
			{
				
				
				if(!games.get(j).getFrame(i).equalsIgnoreCase("-1"))
				{
					
					myFram = new MyFramFromDB(search.getFrameFromId(games.get(j).getFrame(i)));
					
					if(myFram.getThrow1Id().equalsIgnoreCase("-1"))
					{
						mojaGra.get(j).edit(i, Integer.parseInt(myFram.getfThrow1()),Integer.parseInt(myFram.getfThrow2()));
					}else
					{
						boolean[] first = search.getThrowFromId(myFram.getThrow1Id());
						boolean[] second = search.getThrowFromId(myFram.getThrow2Id());
						mojaGra.get(j).edit(i, Integer.parseInt(myFram.getfThrow1()), Integer.parseInt(myFram.getfThrow2()),
								first, second);
					}
				}
			}
		}
		
		
		allMaxScore=0;
		for(int i=0;i<gameNumbers;i++){
			drawResult(tr.get(i),mojaGra.get(i),i);
			txHd.get(i).setText(getString(R.string.ymsig)+(i+1)+"\n"+mojaGra.get(i).getScore());
			allMaxScore += mojaGra.get(i).getScore();
		}
		tx.setText(gameNumbers+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.Match_info_avg)+":"+(allMaxScore/gameNumbers)+" \n "+getString(R.string.player)+player);
		
		
		}catch(Exception e)
		{
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
	   player_id = -1;
	   match_id = -1;
		Intent thisIntent = getIntent();
		Bundle extras = thisIntent.getExtras();
		/**
		 * if intent has extras get it and setup variables 
		 */
		if(thisIntent.hasExtra(getString(R.string.Match_info_mid))){
			match_id = extras.getInt(getString(R.string.Match_info_mid));
			
		}
		else{
			Toast.makeText(getApplicationContext(), getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
    		finish();
		}
		if(thisIntent.hasExtra(getString(R.string.player_id)))
			player_id = extras.getInt( getString(R.string.player_id) );
		else{
			Toast.makeText(getApplicationContext(), getString(R.string.error_no_data), Toast.LENGTH_SHORT).show();
    		finish();
		}
			
		
		
			
		
			
		}
	
   /** (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		//call configurationchanges
		super.onConfigurationChanged(newConfig);
	}
	
	
	
	/**
	 * Initialization tablerow 
	 */
	public void init(){
		
		
		allMaxScore=0;
		 mojaGra = new ArrayList<MyGame>();									//creating array of games
	        tr = new ArrayList<TableRow>();
	        touched = new ArrayList<boolean[]>();
	        for(int i =0;i<gameNumbers;i++){
	        	touched.add(new boolean[12]);
	        }
	        txHd = new ArrayList<TextView>();
	        //tx =(TextView) findViewById(R.id.txtMain);
	        tx =  new TextView(this);
	        tx.setTextColor(Color.WHITE);
	        
	        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			tl = (TableLayout) findViewById(R.id.main_table_layout);
			TableRow tr_top = (TableRow) findViewById(R.id.OneplrGame_max);
			tr_top.removeAllViews();
			tl.removeAllViews();
			tr_top.addView(tx);
			for(int i=0;i<gameNumbers;i++){
				mojaGra.add(new MyGame());
				
				tr.add(new TableRow(this));
				tr.get(i).setLayoutParams(lp);
				tr.get(i).setId(1000+i);
				//tr[i).setBackgroundColor(Color.GREEN);
				tr.get(i).setPadding(10, 10, 10, 10);
				drawResult(tr.get(i),mojaGra.get(i),i);
				txHd.add(new TextView(this));
				txHd.get(i).setText(getString(R.string.ymsig)+(i+1)+"\n"+mojaGra.get(i).getScore());
				tl.addView(txHd.get(i));
				tl.addView(tr.get(i));
				allMaxScore += mojaGra.get(i).getScore(); 
			}
			tx.setTextAppearance(getApplicationContext(), R.style.medium_body_text_white);
			tx.setText(gameNumbers+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.player)+player);
			
	}
	
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
	       		trMain = (TableLayout) getLayoutInflater().inflate(R.layout.test_table_layout, null);
	       		
	       		trMain.setTag((gameNumber*12)+i);
	       		trMain.setOnClickListener(btnLtlstener);
	       		//tr first part
	       		
	       		trPart1 = new TableRow(getBaseContext());
	       		trPart1 = (TableRow) getLayoutInflater().inflate(R.layout.test_table_row_main, null);
	       		//txtview number
	       		
	       		txtNoR = new TextView(getBaseContext());
	       		txtNoR = (TextView) getLayoutInflater().inflate(R.layout.test_text_view, null);
	       		txtNoR.setText((i+1)+"");
	       		trPart1.addView(txtNoR);
	       		trMain.addView(trPart1);
	       		
	       		//tablerow frame part2
	       		
	       		trPart2 = new TableRow(getBaseContext());
	       		trPart2 = (TableRow) getLayoutInflater().inflate(R.layout.test_table_row, null);
	       		
	       		txtFr1 = new TextView(getBaseContext());
	       		txtFr1 = (TextView) getLayoutInflater().inflate(R.layout.test_text_view2, null);
	       		MyFrame ramka =gra.getOneFrame(i); 
	       		txtFr1.setText(ramka.firstPinsToString());
	       		trPart2.addView(txtFr1);
	       		
	       		if(!ramka.isStrike())
	       		{
	       			
	       			
	       			
	       			TextView txtFr2 = new TextView(getApplicationContext());
	       			txtFr2 = (TextView) getLayoutInflater().inflate(R.layout.test_text_view2, null);
	       			txtFr2.setText(ramka.secondPinsToString());
	       			trPart2.addView(txtFr2);
	       			
	       		}
	       		trMain.addView(trPart2);
	       		
	       		trPart3 = new TableRow(getBaseContext());
	       		trPart3 = (TableRow) getLayoutInflater().inflate(R.layout.test_table_row, null);
	       		
	       		TextView txtScoreMax = new TextView(getBaseContext());
	       		txtScoreMax = (TextView) getLayoutInflater().inflate(R.layout.test_text_view, null);
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
	   
   
   /**
    * wcisniecie ktoregos z przyciskow/ramek */
	android.view.View.OnClickListener btnLtlstener  = new Button.OnClickListener() {
   	 
       public void onClick(View v) {
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
protected boolean addedok;

	/** (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 * powrót z edycji ramki
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode != RESULT_CANCELED)
		{
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
			txHd.get(gameNumber).setTextAppearance(getApplicationContext(), R.style.medium_body_text_white);
			allMaxScore=0;
			for(int j=0;j<mojaGra.size();j++)
				allMaxScore+=mojaGra.get(j).getScore();
			tx.setText(gameNumber+getString(R.string.aygms)+allMaxScore+" "+getString(R.string.Match_info_avg)+":"+(allMaxScore/gameNumbers)+" \n "+getString(R.string.player)+player);
		}
		else
			Toast.makeText(getApplicationContext(), getString(R.string.change_cancel), Toast.LENGTH_SHORT).show();
		
	}
	
	/**
	 * method confirm numbers of games,turn off some buttons and init drawing a game table
	 * @param v View of current activity
	 */
  
   
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
   		  finish();
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
     	  public void onClick(DialogInterface arg0, int arg1) 
     	  {
     		 
     		  String fDate =  mMatch.getMyMatch_DATE();
     		  //boolean isTeam = false;
     		  float avg = (float)(allMaxScore/gameNumbers);
     		  
     		 long tmp_id = match_db.updateMatch(match_id,player_id,mMatch.getMyMatch_TYPE(),
     				 Integer.parseInt(mMatch.getMyMatch_LGID()), Integer.parseInt(mMatch.getMyMatch_TOURID()),
     				 fDate, false, BAD_ID, allMaxScore, avg,gameNumbers);
     		 
     		 if(tmp_id != -1)
     		 {
     			 for(int i=0;i<mojaGra.size();i++)
     			 {
     				addedok = game_db.updateGame(games_id.get(i),mojaGra.get(i),match_id,player_id, i,mMatch.getMyMatch_TYPE()/*, ballId*/, -1, -1);
     			 }
     			setResult(RESULT_OK);
     			 finish();
     		 }
     		 else if(!addedok)
    			 Toast.makeText(getApplicationContext(), getString(R.string.error_add),Toast.LENGTH_SHORT).show();
     		 else
     		 {
     			  Toast.makeText(getApplicationContext(), getString(R.string.addOK),Toast.LENGTH_SHORT).show();
     			  finish();
     		 }
     		 
     		 
     	  }});
     	 myAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
     	       
     	  public void onClick(DialogInterface arg0, int arg1) {
     		  
     	  }});
     	 myAlertDialog.show();
   }
   // (non-Javadoc)
  	// * @see android.app.Activity#onResume()
  	 
  	@Override
  	protected void onResume() {
  		super.onResume();
  	}
  	// (non-Javadoc)
  	// * @see android.app.Activity#onBackPressed()
  	 
  	@Override
  	public void onBackPressed() {
  		setResult(RESULT_CANCELED);
  		super.onBackPressed();
  	}


  	
}
