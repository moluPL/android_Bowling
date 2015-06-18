/**
 * 
 */
package MyUtility;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


/**
 * @author karlo
 *
 */
public class Tournament_DB extends  DbBaseAdapter{

	public Tournament_DB(Context applicationContext) {
		super(applicationContext);
	}

	public boolean addtournament(String vname, String vhome, String pattR_id,String pattL_id) {
		try{
    		if(db==null || !db.isOpen())
    			db = open();
    	
    		String insert = "INSERT INTO "+TABLE_TOUR_NAME+" VALUES (NULL ,'"+
    				vname+
    				"','"+
    				vhome+
    				"','"+
    				pattR_id+
    				"','"+
    				pattL_id+
    				"');";
    		db.execSQL(insert);
    	}catch(Exception e){
    		Log.e("DB ADDING TOURNAMENT Error", e.toString());
    		return false;
    	}
    	finally{
    		close();
    	}
    	return true;
	}
	public boolean UpdateTour(int id,String name,String place,String patternR_id,String patternL_id){
    	try{
    		if(db==null || !db.isOpen())
    			db = open();
    	
    		String insert = "UPDATE "+TABLE_TOUR_NAME+" SET "+
    				TOUR_NAME+"='"+name+
    				"',"+
    				TOUR_LOCATION+"='"+place+
    				"',"+
    				TOUR_PATT_RIGHT+"='"+patternR_id+
    				"',"+
    				TOUR_PATT_LEFT+"='"+patternL_id+
    				"' WHERE _id="+id+";";
    		db.execSQL(insert);
    	}catch(Exception e){
    		Log.e("DB LEAGUE Error", e.toString());
    		return false;
    	}
    	finally{
    		close();
    	}
    	return true;
    	
    }
	
	 /*
     *  get all names for selecting TOURNAMENTS
     * */
    public List<String> getAllNames()
    {
    	List<String> names = new ArrayList<String>();
    	Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		String selectQuery = "SELECT "+TOUR_ID+" , "+TOUR_NAME+" FROM "+TABLE_TOUR_NAME;
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					names.add(cursor.getString(0)+" "+cursor.getString(1));
    					cursor.moveToNext();
    				}
    			}
    		}    		
    	}catch(Exception e)
    	{
    		Log.e("TOURNAMENT DB getter all Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	return names;
    }
    
    public String[][] getLeagueFromId(int id)
    {
    	String[][] names = new String[2][5];
    	Cursor cursor =null;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		String selectQuery = "SELECT *  FROM "+TABLE_TOUR_NAME+" WHERE _id="+id;
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					names[0]=cursor.getColumnNames();
    					for(int i =0;i < 5 ; i++)
    						names[1][i]=cursor.getString(i);
    					cursor.moveToNext();
    				}
    			}
    		}
    		else return null;
    	}catch(Exception e)
    	{
    		Log.e("League DB getter Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	return names;
    }

}
