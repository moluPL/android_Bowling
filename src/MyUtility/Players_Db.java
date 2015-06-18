package MyUtility;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class Players_Db extends DbBaseAdapter {
	

    public Players_Db(Context context){
    	super(context);
    	
    }
    
    public boolean updatePlayer(int id,String name,String surname,String email,boolean hand,String home,int year_birth){
    	if(db==null || !db.isOpen())
    		db = open();
    	int handR;
		if(hand)
			handR = 1;
		else
			handR = 0;
    	try{
    		String insert="UPDATE "+TABLE_PLAYERS_NAME+" set "+PLAY_NAME+"='"+name+"', "+PLAY_SURNAME+"='"+surname+
    				"', "+PLAY_EMAIL+"='"+email+"', "+PLAY_RHAND+"='"+handR+"', "+
    	PLAY_HOMETOWN+"='"+home+"', "+PLAY_AGE+"='"+year_birth+"' WHERE _id="+id+";";
    		
    		db.execSQL(insert);
    	}catch(Exception e){
    		Log.e("DB Error", e.toString());
    		return false;
    	}
    	close();
    	return true;
    }
    public boolean  new_player(String name,String surname,String email,boolean hand,String home,int year_birth){
    	if(db==null || !db.isOpen())
    		db = open();
    	int handR;
		if(hand)
			handR = 1;
		else
			handR = 0;
    	try{
    		String insert="INSERT INTO "+TABLE_PLAYERS_NAME+" VALUES( NULL,'"+name+"','"+surname+"','"+email+"','"+handR+"','"+
    	home+"',"+year_birth+",0,0,0);";
    		db.execSQL(insert);
    	}catch(Exception e){
    		Log.e("DB Error", e.toString());
    		return false;
    	}
    	close();
    	return true;
    }
    /*
     * Return String name player from id
     */
    public String getNameFromId(int id){
    	String names="";
    	Cursor cursor =null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+PLAY_NAME+","+PLAY_SURNAME+" FROM "+TABLE_PLAYERS_NAME+" WHERE "+PLAY_ID+"="+id;
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					names = cursor.getString(0)+" "+cursor.getString(1);
    					cursor.moveToNext();
    				}
    			}
    		}
    		else
    			names="";
    	
    	}catch(Exception e){
    		Log.e("PlayerDB getter name Error", e.toString());
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		close();
    	}
    	return names;
    	
    }
    
    public String[][] getPlayerFromId(int id){
    	String[][] names = new String[2][10];
    	Cursor cursor =null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT * FROM "+TABLE_PLAYERS_NAME+" WHERE "+PLAY_ID+"="+id;
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					
    					names[0] = cursor.getColumnNames();
    					for(int i =0;i<10;i++)
    						names[1][i]=cursor.getString(i);
    					
    					cursor.moveToNext();
    				}
    			}
    		}
    		else
    			names = null;
     	
    	}catch(Exception e){
    		Log.e("PlayerDB getter name Error", e.toString());
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		close();
    	}
    	return names;
    	
    }
    
   
    /*
     * Return List<String> all players that are in database if there will be no players return empty list
     */
    public List<String> getAllNames(){
    	List<String> names = new ArrayList<String>();
    	Cursor cursor =null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+PLAY_ID+" , "+PLAY_NAME+","+PLAY_SURNAME+" FROM "+TABLE_PLAYERS_NAME;
    		cursor = db.rawQuery(selectQuery, null);
    		
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					names.add(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2));
    					cursor.moveToNext();
    				}
    			}
    		}
    		else
    			names.clear();
    	
    	}catch(Exception e){
    		Log.e("PlayerDB getter Error", e.toString());
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		close();
    	}
    	return names;
    	
    }
}
  
		
