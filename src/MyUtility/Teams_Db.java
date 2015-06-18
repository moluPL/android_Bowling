package MyUtility;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

public class Teams_Db extends DbBaseAdapter {
	

    public Teams_Db(Context context){
    	super(context);
    	
    }
    /**
     * Create new Team
     * @param name
     * @param email
     * @param home
     * @param club
     * @param players
     * @return
     */
    public boolean  newTeam(String name,String email,String home,String club,List<String> players){
    	try{
    		String dname,dclub,dhome,demail;
    		if(db==null || !db.isOpen())
    			db = open();
    		dname=DatabaseUtils.sqlEscapeString(name);
    		dhome=DatabaseUtils.sqlEscapeString(home);
    		dclub=DatabaseUtils.sqlEscapeString(club);
    		demail=DatabaseUtils.sqlEscapeString(email);
    		String insert = "INSERT INTO "+TABLE_TEAMS_NAME+" VALUES (NULL ,"+
    				dname+
    				","+
    				dhome+
    				","+
    				dclub+
    				","
    				+demail+
    				");";
    		db.execSQL(insert);
    		String tmp_id = getTeam_id(name);
    		
    		if(!tmp_id.isEmpty())
    			addPlayersToTeam(players, tmp_id);
    		else
    			return false;
    			
    	}catch(Exception e){
    		Log.e("DB Error", e.toString());
    		return false;
    	}
    	finally{
    		close();
    	}
    	return true;
    }
    public boolean addPlayersToTeam(List<String> players,String team_id){
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		for(String player:players)
    		{
    			String insert="INSERT INTO "+TABLE_TEAMSPLAYERS_NAME+" VALUES("+team_id+","+player+");";
    			db.execSQL(insert);
    		}
    	}catch(Exception e){
    		Log.e("DB Error", e.toString());
    		return false;
    	}
    	finally{
    		close();
    	}
    	return true;
    }
    /*
     * Get team id from name
     */	
    public String getTeam_id(String name){
    	String tmp_id="";
    	Cursor cursor = null;
    	String dname = DatabaseUtils.sqlEscapeString(name);
    	try{
    		
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+TEAM_ID+" FROM "+TABLE_TEAMS_NAME+" WHERE NAME="+dname+"";
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor.moveToFirst()){
    				tmp_id = cursor.getString(0);
    				Log.d("Debug", tmp_id);
    		}
    	}catch(Exception e){
    		Log.e("DB Error", e.toString());
    	}
    	finally{
    		if(cursor != null)
    			cursor.close();
    			close();
    	}
    	return tmp_id;
    }
    /*
     * Return List<String> all Teams that are in database if there will be no Teams return empty list
     */
    public List<String> getAllTeams(){
    	List<String> teams = new ArrayList<String>();
    	Cursor cursor=null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		
    		String selectQuery = "SELECT "+TEAM_ID+" , "+TEAM_NAME+","+TEAM_HOME+", "+TEAM_CLUB+","+TEAM_EMAIL+" FROM "+TABLE_TEAMS_NAME;
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					teams.add(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2));
    					cursor.moveToNext();
    				}
    			}
    		}
    		else
    			teams.clear();
    	}catch(Exception e){
    		Log.e("TeamDB Error", e.toString());
    	}	
    	finally{
    		if(cursor != null)
    			cursor.close();
    		close();
    		
    	}
    	return teams;
    	
    	
    }
}
  
		
