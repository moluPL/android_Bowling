package MyUtility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class Frames_DB extends DbBaseAdapter {

	public Frames_DB(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public long addFrame(int gameId , int playerId , int open , int spare , int strike, int throw2Id, String throw1, String throw2, int throw1Id )
	{
		try{
			if(db==null || !db.isOpen())
				db=open();
			ContentValues values = new ContentValues();
			values.putNull(FRAME_ID);
			
			//values.put(FRAME_GAME_ID, gameId);
			//values.put(FRAME_PLAYER_ID, playerId);
			values.put(FRAME_THROW1,throw1);
			values.put(FRAME_THROW2, throw2);
			values.put(FRAME_OPEN,open);
			values.put(FRAME_SPARE,spare);
			values.put(FRAME_STRIKE,strike);
			values.put(FRAME_THROW1_ID,throw1Id);
			values.put(FRAME_THROW2_ID,throw2Id);
			return(db.insert(TABLE_MATCH_NAME, null, values));
				
	
		}catch(Exception e){
			Log.e("Error matchDB add", e.toString());
		}finally{
			close();
		}
		return -1;
			
	}
	public long getFrameIdfromThrows(int throw1,int throw2)
	{
		long frameId = -1;
    	Cursor cursor =null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+FRAME_ID+" FROM "+TABLE_FRAME_NAME+" WHERE "+FRAME_THROW1_ID+"="+throw1+" AND "+FRAME_THROW2_ID+"="+throw2;
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					frameId = Long.parseLong(cursor.getString(0));
    					cursor.moveToNext();
    				}
    			}
    		}
    		else
    			frameId=-1;
    	
    	}catch(Exception e){
    		Log.e("FrameDB getter search id Error", e.toString());
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		close();
    	}
    	return frameId;
	}
	
	public long getFrameIdfromScore(int throw1,int throw2)
	{
		long frameId = -1;
    	Cursor cursor =null;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+FRAME_ID+" FROM "+TABLE_FRAME_NAME+" WHERE "+FRAME_THROW1+"="+throw1+" AND "+FRAME_THROW2+"="+throw2;
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					frameId = Long.parseLong(cursor.getString(0));
    					cursor.moveToNext();
    				}
    			}
    		}
    		else
    			frameId=-1;
    	
    	}catch(Exception e){
    		Log.e("FrameDB getter search id Error", e.toString());
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		close();
    	}
    	return frameId;
	}
	
	
	
	
	

}
