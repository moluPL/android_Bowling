package MyUtility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public abstract class DbBaseAdapter {

/**
 * Param's
 */

	protected Context context;	// the Activity or Application that is creating an object from this class.
	protected SQLiteDatabase db; // a reference to the database manager class.
	protected MySQLiteOpenHelper helper;
	
	/*
	 * the names for param of DB
	 */
	protected static final String DEBUG_TAG = "SqLiteManager";
    protected static final int DB_VERSION = 1;	//the version of the database
    protected static final String DB_NAME = "DB.Bms";	//the name of database
    
    protected static final String TABLE_PLAYERS_NAME = "players";	//the name of players table
    protected static final String TABLE_LEAGUE_NAME = "leagues";	//the name of table
    protected static final String TABLE_PATTERNS_NAME = "patterns";
    protected static final String TABLE_BALLS_NAME = "balls";
    protected static final String TABLE_TEAMS_NAME = "teams";
    protected static final String TABLE_TEAMSPLAYERS_NAME = "teams_players";
    protected static final String TABLE_MATCH_NAME = "match";
    protected static final String TABLE_TOUR_NAME = "tournament";
    protected static final String TABLE_GAME_NAME = "game";
    protected static final String TABLE_BALLPLAYER_NAME = "balls_players";
    protected static final String TABLE_VERSIONS_NAME = "versions_data";
    protected static final String TABLE_FRAME_NAME = "frame";
    protected static final String TABLE_THROWS_NAME = "throws";
    protected static final String TABLE_FPG = "fpg";
 /*
 * the names for 
 * PLAYERS
 *  database columns
 */
    protected static final String PLAY_ID="_id";
    protected static final String PLAY_NAME="name";
    protected static final String PLAY_SURNAME="surname";
    protected static final String PLAY_EMAIL="email";
    protected static final String PLAY_RHAND="hand";
    protected static final String PLAY_HOMETOWN="home";
    protected static final String PLAY_AGE="age";
    protected static final String PLAY_AVG="avg";
    protected static final String PLAY_NO_GAMES="no_games";
    protected static final String PLAY_AVG_LG="avg_lg";
    protected static final String PLAY_NO_GAMES_LG="no_games_LG";
    protected static final String PLAY_AVG_TOUR="avg_tour";
    protected static final String PLAY_NO_GAMES_TOUR="no_games_LG";
    protected static final String PLAY_HIGH="high";
    protected static final String PLAY_NR300="nr_300";

    
    /*
     * the names for database 
     * LEAGUE columns
     */
        protected static final String LEAGUE_ID="_id";
        protected static final String LEGAUE_NAME="name";
        protected static final String LEAGUE_PLACE="place";
        protected static final String LEAGUE_PATT_RIGHT="right_pattern";
        protected static final String LEAGUE_PATT_LEFT = "left_pattern";
        
     /*
     *	names for 
     *PATTERNS columns
     */
        protected static final String PATTERNS_ID="_id";
        protected static final String PATTERNS_LINK="link";
        protected static final String PATTERNS_NAME="name";
        protected static final String PATTERNS_LENGTH="length";
        protected static final String PATTERNS_RATIO="ratio";
        protected static final String PATTERNS_VOLUME="volume";
        protected static final String PATTERNS_OPB="opb";
        protected static final String PATTERNS_DB="db";
     /*
      * names for 
      * Rotation Balls columns   
      */
        protected static final String BALLS_ID = "_id";
        protected static final String BALLS_BRAND = "brand";
        protected static final String BALLS_NAME = "name";
        protected static final String BALLS_FACTORY_FINISH = "factory_finish";
        protected static final String BALLS_COVER = "cover";
        protected static final String BALLS_COVER_NAME = "cober_name";
        protected static final String BALLS_CORE_TYPE = "core_type";
        protected static final String BALLS_CORE_NAME = "core_name";
        protected static final String BALLS_LANE_COND = "lane_cond";
        
        

       /*
        * the names for database 
        * Teams columns
       */
        protected static final String TEAM_ID="_id";
       	protected static final String TEAM_NAME="name";
       	protected static final String TEAM_HOME="home";
       	protected static final String TEAM_CLUB="home_club";
       	protected static final String TEAM_EMAIL="email";
        
       	
       	/*
       	 * names  for 
       	 * TEAM_PLAYERS database
       	 */
       	protected static final String TEAM_PLAYERS_ID="team_id";
       	protected static final String PLAYERS_TEAM_ID="play_id";
       	
       	/*
       	 * names for 
       	 * MATCH table
       	 */
       	protected static final String MATCH_ID = "_id";
       	protected static final String MATCH_PLAYER_ID = "player_id";
       	protected static final String MATCH_TYPE = "type";
       	protected static final String MATCH_LGID = "league_id";
       	protected static final String MATCH_TOURID = "tournament_id";
       	protected static final String MATCH_DATE = "date";
       	protected static final String MATCH_IS_TEAM = "is_team";
       	protected static final String MATCH_TEAM_ID = "team_id";
       	protected static final String MATCH_TOTAL = "total";
       	protected static final String MATCH_AVG = "average";
       	protected static final String MATCH_NMBR_GAMES = "numberOfGames";
       	//protected static final String LEAGUE_MATCH_ = "";
       	
       	/*
       	 * names for column
       	 * GAMES
       	 */
    
       	protected static final String GAME_ID = "_id";
       	protected static final String GAME_PLAYER_ID = "player_id";
       	protected static final String GAME_MATCH_ID = "match_id";
       	protected static final String GAME_TYPE = "type";	// game type *)training *)league *)tournament
       	protected static final String GAME_RESULT = "result";
       	protected static final String GAME_STRIKES = "game_strikes";
       	protected static final String GAME_SPARES = "game_spares";
       	protected static final String GAME_OPENS = "game_opens";
       	protected static final String GAME_FRAMES = "frames";	//text frames [0-9/0-9]
       	protected static final String GAME_FRAME1 = "frame1";	//text frames [0-9/0-9]
       	protected static final String GAME_FRAME2 = "frame2";
       	protected static final String GAME_FRAME3 = "frame3";
       	protected static final String GAME_FRAME4 = "frame4";
       	protected static final String GAME_FRAME5 = "frame5";
       	protected static final String GAME_FRAME6 = "frame6";
       	protected static final String GAME_FRAME7 = "frame7";
       	protected static final String GAME_FRAME8 = "frame8";
       	protected static final String GAME_FRAME9 = "frame9";
       	protected static final String GAME_FRAME10 = "frame10";
       	protected static final String GAME_FRAME11 = "frame11";
       	protected static final String GAME_FRAME12 = "frame12";
       	
       	protected static final String GAME_PINS = "pins";		//text pins falls down 0-9, W-ends frame,N-none
       	protected static final String GAME_PATTERN_LEFT = "pattern_left";
       	protected static final String GAME_PATTERN_RIGHT = "pattern_right";
       	protected static final String GAME_BALL = "game_ball";
       	protected static final String GAME_WHICH = "which";		//which game in match it is
       	
       	/*
       	 * Names for FRAMES
       	 * columns
       	 */
       	protected static final String FRAME_ID = "_id";
       	protected static final String FRAME_THROW1 = "frame_throw1";
       	protected static final String FRAME_THROW2 = "frame_throw2";
       	protected static final String FRAME_SPARE = "frame_spare";
       	protected static final String FRAME_STRIKE = "frame_strike";
       	protected static final String FRAME_OPEN = "frame_open";
       	protected static final String FRAME_THROW1_ID = "throw1_id";
       	protected static final String FRAME_THROW2_ID = "throw2_id";
       	
       	
       	/*
       	 * names for THROW columns
       	 */
       	protected static final String THROW_ID = "_id";
       	protected static final String THROW_LEFT = "left";
       	
       	/*
       	 * names fo FRAME_PLAYER_GAME columns
       	 */
       	protected static final String FRAMEPG_FRAME_ID = "frame_id";
       	protected static final String FRAMEPG_PLAYER_ID = "player_id";
       	protected static final String FRAMEPG_GAME_ID = "game_id";
       	protected static final String FRAMEPG_WHICH = "which_frame";
       	protected static final String FRAMEPG_BOARD = "board";
       	protected static final String FRAMEPG_AIM = "aim";
       	protected static final String FRAMEPG_BALL_ID = "ball_id";
       	
       	
       	/*
       	 *names for  columns
       	 *in TOURNAMENT DB
       	 */
       	protected static final String TOUR_ID = "_id";
       	protected static final String TOUR_NAME = "tour_name";
       	protected static final String TOUR_LOCATION = "tour_location";
       	protected static final String TOUR_PATT_LEFT = "pattern_left";
       	protected static final String TOUR_PATT_RIGHT = "pattern_right";
       	
     	/*
       	 *names for  columns
       	 *in VERSIONS
       	 */
       	protected static final String VERSIONS_ID = "_id";
       	protected static final String VERSIONS_NAME = "version_name";
       	protected static final String VERSIONS_V = "version_v";
       	protected static final String VERSIONS_V_PATTERNS = "paatt_version_v";
       	protected static final String VERSIONS_V_BALLS = "ball_version_v";
       	protected static final String VERSIONS_V_SPARES = "spare_version_v";
       	
       	
       	
       
 	/*
	 * the SQLite query string that will create PLayers database table.
	 */
    protected final  String newPlayersTableQueryString = 	
			"create table  if not exists " +
			TABLE_PLAYERS_NAME +
			" (" +
			PLAY_ID + " integer primary key autoincrement not null," +
			PLAY_NAME + " text not null," +
			PLAY_SURNAME + " text not null," +
			PLAY_EMAIL + " text not null unique," +
			PLAY_RHAND + " INTEGER," +
			PLAY_HOMETOWN + " text," +
			PLAY_AGE + " text," +
			PLAY_AVG + " REAL," +
			PLAY_HIGH + " INTEGER," +
			PLAY_NR300 + " INTEGER" +
			");";
    
    /*
     * the SQLite query string that will create TOURNAMENT database table.
     */
    protected final String newTableTourQueryString = 	
			"create table  if not exists " +
			TABLE_TOUR_NAME +
			" (" +
			TOUR_ID + " integer primary key autoincrement not null," +
			TOUR_NAME + " text not null," +
			TOUR_LOCATION + " text,"+
			TOUR_PATT_LEFT+
			" integer not null,"+/*not null references "+TABLE_PATTERNS_NAME+"(_id)"+*/
			TOUR_PATT_RIGHT+" integer not null "+
			");";
    /*
     * the SQLite query string that will create LEAGUES database table.
     */
    protected final String newTableLeagueQueryString = 	
			"create table  if not exists " +
			TABLE_LEAGUE_NAME +
			" (" +
			LEAGUE_ID + " integer primary key autoincrement not null," +
			LEGAUE_NAME + " text not null," +
			LEAGUE_PLACE + " text,"+
			LEAGUE_PATT_LEFT+" integer not null, "+/*not null references "+TABLE_PATTERNS_NAME+"(_id)"+*/
			LEAGUE_PATT_RIGHT+" integer not null"+
			");";
    
  //the SQLite query string that will create TEAMS database table.
    protected final String newTableTeamsQueryString = 	
			"create table  if not exists " +
			TABLE_TEAMS_NAME +
			" (" +
			TEAM_ID + " integer primary key autoincrement not null," +
			TEAM_NAME + " text not null UNIQUE," +
			TEAM_HOME + " text not null,"+
			TEAM_CLUB  + " text ,"+
			TEAM_EMAIL + " text "+
			");";
  //SQLite query string create table PLAYERS_TEAMS
    protected final String newTableTeamsPlayersQueryString = 
    		"create table  if not exists " +
    		TABLE_TEAMSPLAYERS_NAME +
    				" (" +
    		"team_id integer not null references "+TABLE_TEAMS_NAME+"(_id),"+
    		"player_id  integer not null references "+TABLE_PLAYERS_NAME+"(_id),"+
    		"primary key (team_id, player_id)"+
    		");";
  //the SQLite query string that will create BALLS database table.
    protected final String newTableBallsQueryString = 	
			"create table  if not exists " +
			TABLE_BALLS_NAME +
			" (" +
			BALLS_ID + " integer primary key autoincrement not null," +
			BALLS_NAME + " text not null," +
			BALLS_BRAND + "text not null," +
			BALLS_CORE_NAME + "text,"+
			BALLS_CORE_TYPE + "text,"+
			BALLS_COVER_NAME + "text,"+
			BALLS_COVER + "text,"+
			BALLS_FACTORY_FINISH + "text,"+
			BALLS_LANE_COND + "text"+
			");";
    
    /*
     * Make PATTERNS table
     */
    protected final String newTablePatternsQueryString = 	
			"create table  if not exists " +
			TABLE_PATTERNS_NAME +
			" (" +
			PATTERNS_ID + " integer primary key autoincrement not null," +
			PATTERNS_NAME + " text not null," +
			PATTERNS_LINK+ " text not null," +
			PATTERNS_LENGTH+ " INTEGER NOT NULL," +
			PATTERNS_RATIO+ " REAL," +
			PATTERNS_VOLUME+ " REAL," +
			PATTERNS_OPB+ " REAL ," +
			PATTERNS_DB+ " INTEGER " +
			");";
    
    /*
     *	make MATCH table 
     */
    protected final String newTableMATCHQueryString = 
    "create table if not exists " +
	TABLE_MATCH_NAME+" ( "+
	MATCH_ID + " integer primary key autoincrement not null," +
	MATCH_PLAYER_ID + " integer   references "+TABLE_PLAYERS_NAME+"(_id),"+
   	MATCH_TYPE + " text not null,"+
   	MATCH_LGID  +" integer  references "+TABLE_LEAGUE_NAME+"(_id),"+
   	MATCH_TOURID  +" integer  references "+TABLE_TOUR_NAME+"(_id),"+
   	MATCH_DATE +" text not null,"+
   	MATCH_IS_TEAM +" integer not null,"+
   	MATCH_TEAM_ID+" integer  references "+TABLE_TEAMS_NAME+"(_id),"+
   	MATCH_TOTAL +" integer not null,"+
   	MATCH_AVG +" real not null,"+
   	MATCH_NMBR_GAMES +" integer not null"+
   ");";
    
    
    /*
     * THROW TABLE MAKE
     */
    protected final String newTableTHROWQueryString = 
    	    "create table if not exists " +
    		TABLE_THROWS_NAME+" ( "+
    		THROW_ID + " integer primary key autoincrement not null," +
    		THROW_LEFT+ " text UNIQUE not null"+
    		");";
    
    /*
     * FRAME'S table make
     */
    protected final String newTableFRAMEQueryString = 
    	    "create table if not exists " +
    		TABLE_FRAME_NAME+" ( "+
    		FRAME_ID + " integer primary key autoincrement not null," +
    	   	FRAME_OPEN +" integer not null,"+
    	   	FRAME_SPARE +" integer not null,"+
    	   	FRAME_STRIKE +" integer not null,"+
    	   	FRAME_THROW1 +" integer not null,"+
    	   	FRAME_THROW2+" integer ,"+
    	   	FRAME_THROW1_ID  +" integer  references "+TABLE_THROWS_NAME+"(_id),"+
    	   	FRAME_THROW2_ID  +" integer  references "+TABLE_THROWS_NAME+"(_id)"+
    	   ");";
    /*
     *		make GAME table 
     */
    protected final String newTableGAMEQueryString = 
    		 "create table if not exists " +
    					TABLE_GAME_NAME+" ( "+
    					GAME_ID + " integer primary key autoincrement not null," +
    					GAME_MATCH_ID + " integer  references "+TABLE_MATCH_NAME+"(_id),"+
    					GAME_PLAYER_ID + " integer  references "+TABLE_PLAYERS_NAME+"(_id),"+
    					GAME_TYPE + " text not null,"+
    					GAME_WHICH + " integer not null,"+
    					GAME_FRAMES + " text not null,"+
    					GAME_FRAME1 + " integer references "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME2 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME3 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME4 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME5 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME6 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME7 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME8 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME9 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME10 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME11 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_FRAME12 + " integer REFERENCES "+TABLE_FRAME_NAME+" ("+FRAME_ID+"),"+
    					GAME_BALL    + " integer REFERENCES "+TABLE_BALLS_NAME+" (_id),"+
    					GAME_PATTERN_LEFT + " integer not null,"+
    					GAME_PATTERN_RIGHT + " integer not null,"+
    					//GAME_PINS + " text,"+//1-9 pins left
    					GAME_RESULT + " integer not null"+
    		");";
    
   
    
    
    /*
     * FRAME_GAME_PLAYER table make	    
     */
    protected final String newTableFRAME_GAME_PLAYERQueryString =
    		"create table if not exists " +
    				TABLE_FPG+" ( "+
    				FRAMEPG_FRAME_ID + " integer  references "+TABLE_FRAME_NAME+"(_id),"+
    				FRAMEPG_GAME_ID  +" integer  references "+TABLE_GAME_NAME+"(_id),"+
    				FRAMEPG_PLAYER_ID  +" integer  references "+TABLE_PLAYERS_NAME+"(_id),"+
    				FRAMEPG_WHICH + " integer not null,"+
    				FRAMEPG_AIM + " integer ,"+
    				FRAMEPG_BOARD + " integer ,"+
    				FRAMEPG_BALL_ID + " integer "+
    				");";
    
  //the SQLite query string that will create VERSIONS database table.
    protected final String newVersionsQueryString = 	
			"create table  if not exists " +
			TABLE_VERSIONS_NAME +
			" (" +
			VERSIONS_ID + " integer primary key autoincrement not null," +
			VERSIONS_NAME + " text not null UNIQUE," +
			VERSIONS_V + " text not null"+
			");";
    
    //TRIGGERS
    protected final String triggerDeletePlayer =
    		" CREATE TRIGGER IF NOT EXISTS deleteMatch_trigger BEFORE DELETE ON "+TABLE_PLAYERS_NAME+
    		" FOR EACH ROW "+
    				" BEGIN "+
    		" DELETE FROM "+TABLE_MATCH_NAME+" WHERE "+TABLE_MATCH_NAME+"."+MATCH_PLAYER_ID+" = old._id ;"+
    		//" DELETE FROM "+TABLE_GAME_NAME+" WHERE "+TABLE_GAME_NAME+"."+GAME_PLAYER_ID+" = old._id ;"+
    				" END ;";
    protected final String triggerDeleteMatch =
    		" CREATE TRIGGER IF NOT EXISTS deleteMatch_trigger BEFORE DELETE ON "+TABLE_MATCH_NAME+
    		" FOR EACH ROW "+
    				" BEGIN "+
    		" DELETE FROM "+TABLE_GAME_NAME+" WHERE "+TABLE_GAME_NAME+"."+GAME_MATCH_ID+" = old._id ;"+
    				" END ;";
    
    protected final String triggerDeleteGame =
    		" CREATE TRIGGER IF NOT EXISTS deleteGame_trigger BEFORE DELETE ON "+TABLE_GAME_NAME+
    		" FOR EACH ROW "+
    				" BEGIN "+
    		" DELETE FROM "+TABLE_FPG+" WHERE "+TABLE_FPG+"."+FRAMEPG_GAME_ID+" = old._id ;"+
    				" END ;";
    
    /**
     * 
     * end of param's
     */
    
    public DbBaseAdapter(Context context){
    	this.context=context;
    }
    /*open database */
    public SQLiteDatabase open(){
    	try{
    			helper = new MySQLiteOpenHelper(context);
    			db = helper.getWritableDatabase();
    			//Log.d(DEBUG_TAG, "OPEN writeable DATABASE");
    	}catch(NullPointerException e){
    		Log.e("DB OPENING WRITEABLE ERROR", e.toString());
    		e.printStackTrace();
    		db = helper.getReadableDatabase();
    		//Log.d(DEBUG_TAG, "Open database in read mode");
    	}
    	catch(Exception e){
			Log.e("DB OPENING READABLE ERROR",e.toString() );
			//System.exit(-1);
			Toast.makeText(context,"Cannot open database ina any mode R/W" ,Toast.LENGTH_LONG).show();
			db.close();
    	}finally{
    		if(db!=null && db.isOpen())
    			return db;
    	}
		return db;
    	

    }
    /* close database */
    public void close(){
    	if(!(db==null) && db.isOpen())
    	{
    		db.close();
    	}
    	
    }
   
    
/*
 *Inner class heleper
*/
    protected  class MySQLiteOpenHelper extends SQLiteOpenHelper{
    	
    	
    	
		public MySQLiteOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, DB_NAME, null, DB_VERSION);	//constructor from super class
		}

		public MySQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);	//constructor from super class
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// execute the query string to the database.
			db.execSQL(newTablePatternsQueryString);
			db.execSQL(newTableBallsQueryString);
			db.execSQL(newPlayersTableQueryString);
			db.execSQL(newTableLeagueQueryString);
			db.execSQL(newTableTourQueryString);
			db.execSQL(newTableTeamsQueryString);			
			db.execSQL(newTableTeamsPlayersQueryString);
			db.execSQL(newTableMATCHQueryString);	
			db.execSQL(newTableGAMEQueryString);
			db.execSQL(newVersionsQueryString);
			db.execSQL(newTableFRAMEQueryString);
			db.execSQL(newTableTHROWQueryString);
			db.execSQL(newTableFRAME_GAME_PLAYERQueryString);
			db.execSQL(triggerDeleteMatch);
			db.execSQL(triggerDeleteGame);
			db.execSQL(triggerDeletePlayer);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// NOTHING TO DO HERE. THIS IS THE ORIGINAL DATABASE VERSION.
			// OTHERWISE, YOU WOULD SPECIFIY HOW TO UPGRADE THE DATABASE
			// FROM OLDER VERSIONS.
			 Log.w(TABLE_PLAYERS_NAME, "Upgrading database from version "
				        + oldVersion + " to " + newVersion
				        + ", which will destroy all old data");
			 
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUE_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOUR_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATTERNS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BALLS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMSPLAYERS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSIONS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAME_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_THROWS_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BALLPLAYER_NAME);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FPG);
				    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH_NAME);
				    onCreate(db);		    
		}
    }
    /*
     * end of inner class
     */
}

