package MyUtility;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;


public class Match_DB extends DbBaseAdapter {

	
	
	public Match_DB(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public long addMatch(int playerID,String type,int lg_id,int tour_id,String date,boolean isTeam,int team_id,int total, float avg, int gameNumbers){
		try{
			if(db==null || !db.isOpen())
				db=open();
			ContentValues values = new ContentValues();
			values.putNull(MATCH_ID);
			values.put(MATCH_PLAYER_ID, playerID);
			values.put(MATCH_NMBR_GAMES, gameNumbers);
			values.put(MATCH_TYPE, type);
			values.put(MATCH_TOTAL, total);
			values.put(MATCH_AVG, avg);
			values.put(MATCH_DATE, date);
			if(isTeam){
				values.put(MATCH_IS_TEAM, 1);
				values.put(MATCH_TEAM_ID, team_id);
			}
			else{
				values.put(MATCH_IS_TEAM, 0);
				values.put(MATCH_TEAM_ID,team_id);
			}
			values.put(MATCH_LGID, lg_id);
			values.put(MATCH_TOURID, tour_id);
			values.put(MATCH_DATE,date);
			return(db.insert(TABLE_MATCH_NAME, null, values));
				
	
		}catch(Exception e){
			Log.e("Error matchDB add", e.toString());
		}
		finally{
			db.close();
		}
		return -1;
			
	}
	
	public long updateMatch(int matchId,int playerID,String type,int lg_id,int tour_id,String date,boolean isTeam,int team_id,int total, float avg, int gameNumbers){
		try{
			if(db==null || !db.isOpen())
				db=open();
			ContentValues values = new ContentValues();
			String whereClause = " _id="+matchId;
			values.put(MATCH_PLAYER_ID, playerID);
			values.put(MATCH_NMBR_GAMES, gameNumbers);
			values.put(MATCH_TYPE, type);
			values.put(MATCH_TOTAL, total);
			values.put(MATCH_AVG, avg);
			values.put(MATCH_DATE, date);
			if(isTeam){
				values.put(MATCH_IS_TEAM, 1);
				values.put(MATCH_TEAM_ID, team_id);
			}
			else{
				values.put(MATCH_IS_TEAM, 0);
				values.put(MATCH_TEAM_ID,team_id);
			}
			values.put(MATCH_LGID, lg_id);
			values.put(MATCH_TOURID, tour_id);
			values.put(MATCH_DATE,date);
			return(db.update(TABLE_MATCH_NAME, values, whereClause, null));
				
	
		}catch(Exception e){
			Log.e("Error matchDB add", e.toString());
		}
		finally{
			db.close();
		}
		return -1;
			
	}
	
	public int deleteFromId(int id){
		if(db==null || !db.isOpen())
			db=open();
		
		return db.delete(TABLE_MATCH_NAME, MATCH_ID + "=" + id, null);
	}

	
	
	

}
