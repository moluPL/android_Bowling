package MyUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;


public class SearchData extends DbBaseAdapter {

	
	private Cursor cursor;
	
	public SearchData(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Get the number of games for given player id
	 * @param player_id
	 * @return
	 */
	public int getNmbrGamesPlayer(int... params){
		int playerId = -1;
		int srtType = -1;
		int anId = -1;
		playerId=params[0];
		if(params.length>1){
			srtType=params[1];
		}
		if(params.length>2)
			anId=params[2];
		
		//List<MyGameFromDB> games = new ArrayList<MyGameFromDB>();
    	Cursor cursor =null;
    	String query=null;//type=null;
    	if(srtType==1)
    		query =  "select count(*) from game INNER JOIN match on game.match_id=match._id WHERE game.player_id="+playerId+" and match.league_id="+anId;
    	else if(srtType==2)
    		query =  "select count(*) from game INNER JOIN match on game.match_id=match._id WHERE game.player_id="+playerId+" and match.tournament_id="+anId;
    	else if(params.length<2)
    		query =  "select count(*)  from game where player_id="+playerId;
    	
    	int nmbrRows=0;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		cursor = db.rawQuery(query, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					
    					nmbrRows = cursor.getInt(0);
    					cursor.moveToNext();
    				}
    			}
    		}
    	}catch(Exception e)
    	{
    		Log.e("Error Getter search.get number games()", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
		
		return nmbrRows;
	}
	
	/**
	 * Get games from database by given parameter
	 * @param int[] params <br>
	 * 	 	params[0] = player id<br>
	 * 	 	params[1] = srt type<br>
	 * 		params[2] = id (league/tour)<br>
	 * 	srtType:<br>
	 * 			 - 0 normal all game<br>
	 * 			 - 1 games for series<br>
	 * 			 - 2 games from matchId <br>
	 * 			 - 3 games from tour<br>
	 * 			 - 4 games from league<br>
	 * 			 - 5 games from tour for high series<br>
	 * 			 - 6 games from league for highseries<br>
	 * @return List<MyGameFromDB>
	 */
	public List<MyGameFromDB> getAllGame(boolean fulls,int... params){
		
		
		List<MyGameFromDB> games = new ArrayList<MyGameFromDB>();
		
		int playerId = -1;
		int srtType = -1;
		int anId = -1;
		boolean full=false;
		
    	Cursor cursor =null;
    	int gameId,score,matchId,which,pattR,pattL;
    	List<String> listFrames;
    	String query=null,type=null;
		if(params.length>0){
			playerId=params[0];
			srtType=params[1];
		}
		if(params.length>2)
			anId=params[2];
		if(fulls)
			full=true;
		
    	if(srtType==0)
    		query =  "select * from game where player_id="+playerId+" order by match_id,which";
    	else if(srtType==1)
    		query =  "select * from game where player_id="+playerId+" and match_id in(select _id from match where numberOfGames>2)  order by match_id,which";
    	else if(srtType==2)
    		query =  "select * from game where player_id="+playerId+" and match_id="+anId+" order by "+GAME_WHICH;
    	else if(srtType==3)
    	{
    		query =  "select game."+GAME_ID+" , "+GAME_MATCH_ID+" , "+GAME_WHICH+" , "
    	+GAME_RESULT+" , game."+GAME_TYPE+" , "+GAME_PATTERN_LEFT+" , "+GAME_PATTERN_RIGHT+
    	" from game INNER JOIN MATCH ON game.match_id=match._id where game.player_id="
    	+playerId+" and match."+MATCH_TOURID+"="+anId+" order by game."+GAME_RESULT;	
    	}else if(srtType==4)
    	{
    		query =  "select game."+GAME_ID+" , "+GAME_MATCH_ID+" , "+GAME_WHICH+" , "
    		    	+GAME_RESULT+" , game."+GAME_TYPE+" , "+GAME_PATTERN_LEFT+" , "+GAME_PATTERN_RIGHT+
    		    	" from game INNER JOIN match ON game.match_id=match._id where game.player_id="
    		    	+playerId+" and match."+MATCH_LGID+"="+anId+" order by game."+GAME_RESULT;
    	}
    	else if(srtType==5)
    	{
    		query =  "select game."+GAME_ID+" , "+GAME_MATCH_ID+" , "+GAME_WHICH+" , "
    	+GAME_RESULT+" , game."+GAME_TYPE+" , "+GAME_PATTERN_LEFT+" , "+GAME_PATTERN_RIGHT+
    	" from game INNER JOIN match ON game.match_id=match._id where game.player_id="
    	+playerId+" and match."+MATCH_TOURID+"="+anId+" and "+MATCH_NMBR_GAMES+">2  order by game.match_id , game.which";	
    	}else if(srtType==6)
    	{
    		query =  "select game."+GAME_ID+", "+GAME_MATCH_ID+", "+GAME_WHICH+", "
    		    	+GAME_RESULT+", game."+GAME_TYPE+", "+GAME_PATTERN_LEFT+", "+GAME_PATTERN_RIGHT+""+
    		    	" from game INNER JOIN match ON game.match_id=match._id where game.player_id="
    		    	+playerId+" and match."+MATCH_LGID+"="+anId+" and "+MATCH_NMBR_GAMES+">2  order by match_id,which";
    	}
    	
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		cursor = db.rawQuery(query, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					
    					gameId=cursor.getInt(cursor.getColumnIndex(GAME_ID));
    					matchId = cursor.getInt(cursor.getColumnIndex(GAME_MATCH_ID));
    					which = cursor.getInt(cursor.getColumnIndex(GAME_WHICH));
    					score = cursor.getInt(cursor.getColumnIndex(GAME_RESULT));
    					type = cursor.getString(cursor.getColumnIndex(GAME_TYPE));
    					pattL = cursor.getInt(cursor.getColumnIndex(GAME_PATTERN_LEFT));
    					pattR = cursor.getInt(cursor.getColumnIndex(GAME_PATTERN_RIGHT));
    					if(full){
    						listFrames = new ArrayList<String>();
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME1)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME2)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME3)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME4)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME5)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME6)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME7)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME8)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME9)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME10)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME11)));
    						listFrames.add(cursor.getString(cursor.getColumnIndex(GAME_FRAME12)));
    						games.add(new MyGameFromDB(gameId, playerId, matchId, type, score, pattL, pattR, which,listFrames));	
    					}
    					else
    						games.add(new MyGameFromDB(gameId, playerId, matchId, type, score, pattL, pattR, which));
    					cursor.moveToNext();
    				}
    			}
    		}
    	}catch(Exception e)
    	{
    		Log.e("games DB getter Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
		
		return games;
	}
	/**
	 * 
	 * @param playerId
	 * @return
	 */
	public List<String> getPlayerInfo(int playerId){
		List<String> lista = new ArrayList<String>();
		try
		{
			if(db==null || !db.isOpen())
				open();
			String selectQuery = " SELECT * FROM "+TABLE_PLAYERS_NAME+" WHERE "+PLAY_ID+"="+playerId;
			cursor = db.rawQuery(selectQuery, null);
			if(cursor != null && cursor.getCount() > 0)
			{
				if(cursor.moveToFirst())
				{
					while(!cursor.isAfterLast())
					{
						lista.add(cursor.getString(1));
						lista.add(cursor.getString(2));
						lista.add(cursor.getString(3));
						cursor.moveToNext();
					}
				}
			}    		
		}catch(Exception e)
		{
			Log.e("SearchData getter numbers strike Error", e.toString());
		}
		finally
		{
			if(cursor!=null)
				cursor.close();
			close();
		}
		return lista;
	}
	/**
	 * Return list of all matchs of choosen player <br>
	 * params[0]=playerId <br>
	 * params[1]=sort type <br>
	 * params[2]=id of league or tour <br>
	 * you can sort it in diffrent way by giving second parameter(params[1]): <br>
	 * 0-sort by date and next by id if have the same date <br>
	 * 1-sort by highest result <br>
	 * 2-sort by specified legue Id -> params[2]=id <br>
	 * 3-sort by specified tour Id <br>
	 * 4-sort by type = trianing <br>
	 * @param playerId srtType <br>
	 * @return <br>
	 */
	public List<MyMatch> getAllMatchPlayer(int... params){
		List<MyMatch> matches = new ArrayList<MyMatch>();
		Cursor cursor =null;
    	String sortType = MATCH_DATE+" , "+MATCH_ID;
    	String where="";
		int srtType=params[1];
		int playerId=params[0];
		int lgId;
		int tourId;
		if(params.length>2){
			if(srtType==2){
				lgId = params[2];
				where=" and "+MATCH_LGID+"='"+lgId+"'";
				
			}
			else if(srtType==3){
				tourId=params[2];
				where=" and "+MATCH_TOURID+"='"+tourId+"'";
				
			}
		}
		
		
    	
    	if(srtType==0)
    		sortType =  MATCH_DATE+" , "+MATCH_ID+" desc";
    	else if(srtType==1)
    		sortType =  MATCH_TOTAL+" DESC";
    	else if(srtType==4){
    		sortType =  MATCH_DATE+" , "+MATCH_ID+" desc";
    		where=" and "+MATCH_TYPE+"='training' ";
    	}
    	
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		cursor = db.query(TABLE_MATCH_NAME, null, MATCH_PLAYER_ID+"="+playerId+where,null/*new String[]{Integer.toString(playerId)}*/,
    				null, null,sortType);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					
    					matches.add(new MyMatch(cursor.getString(0),cursor.getString(1),cursor.getString(2)
    							,cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)
    							,cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
    					cursor.moveToNext();
    				}
    			}
    		}
    	}catch(Exception e)
    	{
    		Log.e("DB getter Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
		return matches;
	}
	/**
	 * 
	 * @param int[] params <br>
	 * @return number of strikes <br>
	 * params[0]=playerId <br>
	 * params[1]=type of search <br>
	 * null = all //without this params<br>
	 * 1=league<br>
	 * 2=tournament<br>
	 * 3=training<br>
	 * params[2]= id of tour or lg<br>
	 * <br>
	 */
	public int getNmbrStrike(int... params){
		int playerId=0,lgId=-1,tourId = -1,type=-1;
		//String where="";
		String selectQuery="";
		playerId=params[0];
		if(params.length>2)
		{
			type=params[1];
			lgId=params[2];
			tourId=params[2];
		}
		/*
		 * select count(*) from fpg inner join frame on fpg.frame_id=frame_id
		 * inner join game  on game_id=game._id
		 * inner join match on match_id=match._id 
		 * where  fpg.player_id=? and frame.frame_strike=1 and match.league_id=?;
		 */
		if(type==1)
    		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_strike=1"+" and match.league_id="+lgId;
		if(type==2)
    		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_strike=1"+" and match.tournament_id="+tourId;
		else if(params.length<2)
			selectQuery="select count(*) from fpg inner join frame on fpg.frame_id=frame._id where frame.frame_strike=1 and fpg.player_id="+playerId;
		int nmbrRows=0;
		Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		//selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame_id inner join game  on game_id=game._id inner join match on match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_strike=1"+where;
    				/*
    				+ "WHERE "+FRAMEPG_PLAYER_ID+"="+playerId+
    				" and "+FRAMEPG_FRAME_ID+" in(select _id from "+TABLE_FRAME_NAME+" where "+FRAME_STRIKE+"=1)"+where;
    				*/
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					//TODO poprawic na getint!
    					nmbrRows=Integer.parseInt(cursor.getString(0).trim());
    					cursor.moveToNext();
    				}
    			}
    		}    		
    	}catch(SQLException e)
    	{
    		Log.e("SearchData getter numbers strike Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	
		return nmbrRows;
	}
	/**
	 * @param int[] params <br>
	 * @return number of spares <br>
	 * params[0]=playerId <br>
	 * params[1]=type of search <br>
	 * null = all //without this params<br>
	 * 1=league<br>
	 * 2=tournament<br>
	 * 3=training<br>
	 * params[2]= id of tour or lg<br>
	 * <br>
	 */
	public int getNmbrSpare(int... params){
		
		int playerId=0,lgId=0,tourId = 0,type=0;
		//String where="";
		String	selectQuery="";
		playerId=params[0];
		if(params.length>2)
		{
			type=params[1];
			lgId=params[2];
			tourId=params[2];
		}
		
		if(type==1)
    		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_spare=1"+" and match.league_id="+lgId;
		if(type==2)
    		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_spare=1"+" and match.tournament_id="+tourId;
		else if(params.length<2)
			selectQuery="select count(*) from fpg inner join frame on fpg.frame_id=frame._id where fpg.player_id="+playerId+" and frame.frame_spare=1 ";
		int nmbrRows=0;
		Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					nmbrRows=Integer.parseInt(cursor.getString(0));
    					cursor.moveToNext();
    				}
    			}
    		}    		
    	}catch(Exception e)
    	{
    		Log.e("SearchData getter numbers spare Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	
		return nmbrRows;
	}
	/**
	 * 
	 * @param int[] params <br>
	 * @return number of opens <br>
	 * params[0]=playerId <br>
	 * params[1]=type of search <br>
	 * null = all //without this params<br>
	 * 1=league<br>
	 * 2=tournament<br>
	 * 3=training<br>
	 * params[2]= id of tour or lg<br>
	 * <br>
	 */
	public int getNmbrOpen(int... params){
		int playerId=0,lgId=0,tourId = 0,type=0;
		//String where="";
		String	selectQuery="";
		playerId=params[0];
		if(params.length>2)
		{
			type=params[1];
			lgId=params[2];
			tourId=params[2];
		}
		
		if(type==1)
    		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_open=1"+" and match.league_id="+lgId;
		if(type==2)
    		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_open=1"+" and match.tournament_id="+tourId;
		else if(params.length<2)
			selectQuery="SELECT count(*) FROM fpg INNER JOIN frame on fpg.frame_id=frame._id WHERE fpg.player_id="+playerId+" and frame.frame_open=1 ";
		int nmbrRows=0;
		Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		//String	selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame_id inner join game  on game_id=game._id inner join match on match_id=match._id  where  fpg.player_id="+playerId+" and frame.frame_open=1"+where;
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					nmbrRows=Integer.parseInt(cursor.getString(0));
    					cursor.moveToNext();
    				}
    			}
    		}    		
    	}catch(Exception e)
    	{
    		Log.e("SearchData getter numbers OPEN Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
		
		return nmbrRows;
		
		
	}
	/**
	 * 
	 * @param int[] params <br>
	 * @return number of frames <br>
	 * params[0]=playerId <br>
	 * params[1]=type of search <br>
	 * null = all //without this params<br>
	 * 1=league<br>
	 * 2=tournament<br>
	 * 3=training<br>
	 * params[2]= id of tour or lg<br>
	 * <br>
	 */
	public int getNmbrFrame(int... params){
		int playerId=params[0];
		int nmbrRows=0,lgId=-1,type=0,tourId=-1;
		Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		if(params.length>2)
    		{
    			type=params[1];
    			lgId=params[2];
    			tourId=params[2];
    		}
    		String selectQuery = null;
    		if(type==1)
        		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and match.league_id="+lgId;
    		if(type==2)
        		selectQuery = "select count(*) from fpg inner join frame on fpg.frame_id=frame._id inner join game  on fpg.game_id=game._id inner join match on game.match_id=match._id  where  fpg.player_id="+playerId+" and match.tournament_id="+tourId;
    		else if(params.length<2)
    			selectQuery = " SELECT count(*) FROM "+TABLE_FPG+" WHERE "+FRAMEPG_PLAYER_ID+"="+playerId;
    		
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					nmbrRows=Integer.parseInt(cursor.getString(0));
    					cursor.moveToNext();
    				}
    			}
    		}    		
    	}catch(Exception e)
    	{
    		Log.e("SearchData getter numbers strike Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	
		return nmbrRows;
	}
	public MyMatch getMatchFromId(int _MATCH_ID) {
		MyMatch matches = null;
		Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		cursor = db.query(TABLE_MATCH_NAME, null, MATCH_ID+"="+_MATCH_ID,null/*new String[]{Integer.toString(playerId)}*/,
    				null, null,null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					
    					matches = new MyMatch(cursor.getString(0),cursor.getString(1),cursor.getString(2)
    							,cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)
    							,cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10));
    					cursor.moveToNext();
    				}
    			}
    		}
    	}catch(Exception e)
    	{
    		Log.e("DB getter Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
		return matches;
	}
	
	public String[] getFrameFromId(String id){
		String[] frame = new String[4];
		Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		cursor = db.rawQuery("SELECT * from "+TABLE_FRAME_NAME+" WHERE _id="+id,null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					
    					frame[0]=cursor.getString(cursor.getColumnIndex(FRAME_THROW1));
    					frame[1]=cursor.getString(cursor.getColumnIndex(FRAME_THROW2));
    					frame[2]=cursor.getString(cursor.getColumnIndex(FRAME_THROW1_ID));
    					frame[3]=cursor.getString(cursor.getColumnIndex(FRAME_THROW2_ID));
    					cursor.moveToNext();
    				}
    			}
    		}
    	}catch(Exception e)
    	{
    		Log.e("DB getter Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
		return frame;
	}
	public boolean[] getThrowFromId(String throw1Id) {
		boolean[] throwy = new boolean[10];
		Arrays.fill(throwy, false);
		Cursor cursor =null;
		String res = null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+THROW_LEFT+" FROM "+TABLE_THROWS_NAME+" WHERE _id="+throw1Id;
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					res = cursor.getString(cursor.getColumnIndex(THROW_LEFT));
    					cursor.moveToNext();
    				}
    			}
    		}
    			
    	
    	}catch(Exception e){
    		Log.e("ThrowsDB getter name Error", e.toString());
    		
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		
    		close();
    	}
    	for(int i=0;i<res.length();i++)
    	{
    		if(res.substring(i,i+1).equalsIgnoreCase("1") && i<res.length()-1 && res.substring(i+1,i+2).equalsIgnoreCase("0")){
    			throwy[9]=true;
    		}
    		if(res.substring(i,i+1).equalsIgnoreCase("0"))
    			;
    		else{
    			throwy[Integer.parseInt(res.substring(i, i+1))-1]=true;
    		}
    	}
    	
    		
    	return throwy;
	}

}
