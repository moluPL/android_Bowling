package MyUtility;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class Db_versions extends DbBaseAdapter {

	protected float patt_v;
	protected float ball_v;
	protected float spare_v;
	public Db_versions(Context context) {
		super(context);
	}
	
	public boolean addVersionPatt(String version){
		
    		Cursor cursor =null;
	    	try
	    	{
	    		if(db==null || !db.isOpen())
	    			open();
	    		
	    		String selectQuery = "SELECT "+VERSIONS_V+" FROM "+TABLE_VERSIONS_NAME+" WHERE version_name like  '%"+VERSIONS_V_PATTERNS+"%'";
	    		cursor = db.rawQuery(selectQuery, null);
	    		if(cursor != null && cursor.getCount() > 0)
	    		{
	    			String insert = "update "+TABLE_VERSIONS_NAME+" SET "+VERSIONS_V+"='"+version+"' WHERE version_name like  '%"+VERSIONS_V_PATTERNS+"%'";
	        		db.execSQL(insert);
	    			
	    		}else{
	    			String insert = "INSERT into "+TABLE_VERSIONS_NAME+" VALUES (NULL ,'"+
	        				VERSIONS_V_PATTERNS+
	        				"','"+
	        				version+
	        				"');";
	        		db.execSQL(insert);
	    			
	    			
	    		}
	    	}catch(Exception e)
	    	{
	    		Log.e("Versions DB getter Error", e.toString());
	    	}
	    	finally
	    	{
	    		if(cursor!=null)
	    			cursor.close();
	    			close();
	    	}
    	return true;
	}
	
	 public float getPattVersionF()
	 {
	    	
	    	Cursor cursor =null;
	    	try
	    	{
	    		if(db==null || !db.isOpen())
	    			open();
	    		
	    		String selectQuery = "SELECT "+VERSIONS_V+" FROM "+TABLE_VERSIONS_NAME+" WHERE version_name like  '%"+VERSIONS_V_PATTERNS+"%'";
	    		cursor = db.rawQuery(selectQuery, null);
	    		if(cursor != null && cursor.getCount() > 0)
	    		{
	    			if(cursor.moveToFirst())
	    			{
	    				while(!cursor.isAfterLast())
	    				{
	    					//System.out.println((cursor.getString(0)));
	    					patt_v = Float.parseFloat(cursor.getString(0));
	    					cursor.moveToNext();
	    				}
	    			}
	    		}else{
	    			patt_v = (float)(-1.0);
	    		}
	    	}catch(Exception e)
	    	{
	    		Log.e("Versions DB getter Error", e.toString());
	    	}
	    	finally
	    	{
	    		if(cursor!=null)
	    			cursor.close();
	    			close();
	    	}
	    	return patt_v;
	    }

	

}
