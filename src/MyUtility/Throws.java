package MyUtility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class Throws extends DbBaseAdapter{

	public Throws(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public long addThrow(String str)
	{
		try{
			if(db==null || !db.isOpen())
				db=open();
			ContentValues values = new ContentValues();
			if(str.length()>0){
				values.putNull(THROW_ID);
				values.put(THROW_LEFT, str);
			}
			else
				return -1;
			
			//Log.d(DEBUG_TAG, "Adding throw to Db:"+str);
			return db.insert(TABLE_THROWS_NAME, null, values);
				
	
		}catch(Exception e){
			Log.e("Error THROW_DB add", e.toString());
		}
		finally{
			close();
		}
		return -1;
	}
	
	public int getThrowId(String left){
		//Log.d(DEBUG_TAG, "getThrowId str left pins= "+left);
		Cursor cursor =null;
		int throwId = -1;
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    		String selectQuery = "SELECT "+THROW_ID+" FROM "+TABLE_THROWS_NAME+" WHERE "+THROW_LEFT+" like '"+left+"'";
    		cursor = db.rawQuery(selectQuery, null);
    		//	check if cursor not empty
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst()){
    				while(!cursor.isAfterLast()){
    					throwId = Integer.parseInt(cursor.getString(0));
    					cursor.moveToNext();
    				}
    			}
    		}
    			
    	
    	}catch(Exception e){
    		Log.e("ThrowsDB getter name Error", e.toString());
    		Log.e("error",left);
    	}
    	finally{
    		if(cursor!=null)
    			cursor.close();
    		
    		close();
    	}
    	return throwId;
		
	}
public int getNumberOfrecords()
{	
	Cursor cursor = null;
	int count=0;
	try{
		if(db==null || !db.isOpen())
			db=open();
		String selectQuery = "SELECT * "+" FROM "+TABLE_THROWS_NAME;
		cursor = db.rawQuery(selectQuery, null);
		if(cursor != null)
		{
			return cursor.getCount();
		}
		else
			return count;
	}catch(Exception e){
		Log.e("DB Error number patterns", e.toString());
	}finally{
		if(cursor!=null)
			cursor.close();
		close();
	}
	//Log.d("DEBUG","patternsDB COUNT= "+count);
		return count;
}

	
}
