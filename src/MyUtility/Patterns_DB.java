package MyUtility;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;


public class Patterns_DB extends DbBaseAdapter{

	public Patterns_DB(Context context) {
		super(context);
		
	}
	public boolean addPatternFromPatterns(Patterns pattern)
	{
		try{
			String name;
			String length;
			String link;
			String ratio;
			String volume;
			String opb;
			String DB;
			if(pattern !=null)
			{
				name=pattern.getName();
				length=pattern.getLength();
				link=pattern.getLink();
				ratio=pattern.getRatio();
				volume=pattern.getVolume();
				opb=pattern.getOpb();
				DB=pattern.getDba();
			}
			else 
				return false;
			name = DatabaseUtils.sqlEscapeString(name);
			if(db==null || !db.isOpen())
				db=open();
			String insert = "INSERT INTO "+TABLE_PATTERNS_NAME+" VALUES(NULL,"+
				name+",'"+link+"','"+length+"','"+ratio+"','"+volume+"','"+opb+"','"+DB+"')";
			db.execSQL(insert);
		}catch(Exception e){
			Log.e("DB Error adding patterns", e.toString());
			return false;
		}finally{
			close();
		}
		return true;
		
		
	}
	public boolean addPattern(String name,String length,String link,String ratio,String volume,String opb,String DB){
		try{
			name = DatabaseUtils.sqlEscapeString(name);
			if(db==null || !db.isOpen())
				db=open();
			String insert = "INSERT INTO "+TABLE_PATTERNS_NAME+" VALUES(NULL,"+
				name+",'"+link+"','"+length+"','"+ratio+"','"+volume+"','"+opb+"','"+DB+"')";
			db.execSQL(insert);
		}catch(Exception e){
			Log.e("DB Error adding patterns", e.toString());
			return false;
		}finally{
			close();
		}
		return true;
		
	}
	
	public int getNumberOfRecords(){
		Cursor cursor = null;
		int count=0;
		try{
			if(db==null || !db.isOpen())
				db=open();
			String selectQuery = "SELECT * "+" FROM "+TABLE_PATTERNS_NAME;
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
		Log.d("DEBUG","patternsDB COUNT= "+count);
    		return count;
	}
	
	/*
	 * get Pattern from name
	 */
	public List<Patterns> getPattName(String name)
	{
    	Cursor cursor = null;
    	List<Patterns> listPatt = new ArrayList<Patterns>(); 
    	Patterns patt = null;
    	name = "%"+name+"%";
    	name = DatabaseUtils.sqlEscapeString(name);
    	String selectQuery;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		selectQuery = "SELECT * FROM "+TABLE_PATTERNS_NAME+" where name like "+name;
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					patt = new Patterns(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
    					listPatt.add(patt);
    					cursor.moveToNext();
    				}
    			}
    		}else{
    			return null;
    		}
    	}catch(Exception e)
    	{
    		Log.e("Patterns DB getterId Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	return listPatt;
	}
	
	/*
	 * get selected pattern from id
	 */
	public Patterns getPattId(int id)
	{
    	Cursor cursor = null;
    	Patterns patt = null;
    	String selectQuery;
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		
    		selectQuery = "SELECT * FROM "+TABLE_PATTERNS_NAME+" where _id="+id;
    		cursor = db.rawQuery(selectQuery, null);
    		if(id < 0)
    			return null;
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					patt = new Patterns(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
    					cursor.moveToNext();
    				}
    			}
    		}else{
    			return null;
    		}
    	}catch(Exception e)
    	{
    		Log.e("Patterns DB getterId Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	return patt;
	}
	
	/*
	 * Gett all Patterns
	 */
	public List<String> getAllPAtterns(){
		List<String> lista = new ArrayList<String>();
		Cursor cursor = null;
		try{
			if(db==null || !db.isOpen())
				db=open();
			String selectQuery = "SELECT * "+" FROM "+TABLE_LEAGUE_NAME;
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    		
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					lista.add(cursor.getString(0)+";"+cursor.getString(1)+";"+cursor.getString(2)
    				+";"+cursor.getString(3)+";"+cursor.getString(4)+";"+cursor.getString(5)+";"+cursor.getString(6)+";"+cursor.getString(7));
    					
    					cursor.moveToNext();
    				}
    			}
    			
    		}
    		
			
		}catch(Exception e){
			Log.e("DB Error patterns", e.toString());
		}finally{
			if(cursor!=null)
				cursor.close();
			close();
		}
		return lista;
			
	}
	
	/**
	 * get patterns from range<br>
	 * sort types:<br>
	 * -1: sort_field = PATTERNS_NAME<br>
	 * -2:sort_field =PATTERNS_LENGTH<br>
	 * -3:sort_field =PATTERNS_RATIO<br>
	 * -4:sort_field =PATTERNS_VOLUME<br>
	 * -5:sort_field =PATTERNS_OPB<br>
	 * -6:sort_field =PATTERNS_DB<br>
	 * 
	 * @param last_patt
	 * @param page
	 * @param to
	 * @param sort
	 * @param asc
	 * @return
	 */
	public List<Patterns> getPatternsRange(Patterns last_patt,int page,int to,int sort, boolean asc)
	//(patern,page,show,on which sort,desv or asc sort
    {
		
    	List<Patterns> patterns = new ArrayList<Patterns>();
    	@SuppressWarnings("unused")
		String last = null;
    	Cursor cursor =null;
    	Patterns patt=null;
    	String selectQuery,sort_field = null,ascdesc="ASC";
    	try
    	{
    		if(db==null || !db.isOpen())
    			open();
    		if(last_patt != null)
    			switch(sort){
    			case 1: sort_field = PATTERNS_NAME;
    				last=DatabaseUtils.sqlEscapeString(last_patt.getName());
    				break;
    			case 2:sort_field =PATTERNS_LENGTH;
    				last=last_patt.getLength();
    				break;
    			case 3:sort_field =PATTERNS_RATIO;
    				last=last_patt.getRatio();
    				break;
    			case 4:sort_field =PATTERNS_VOLUME;
    				last=last_patt.getVolume();
    				break;
    			case 5:sort_field =PATTERNS_OPB;
    				last=last_patt.getOpb();
    				break;
    			case 6:sort_field =PATTERNS_DB;
    				last=last_patt.getDba();
    				break;
    			default:sort_field =PATTERNS_NAME;
    				last=last_patt.getName();
    				break;
    			}
    		else
    			switch(sort){
    			case 1: 
    				sort_field = PATTERNS_NAME;
    				break;
    			case 2:
    				sort_field =PATTERNS_LENGTH;
    				break;
    			case 3:
    				sort_field =PATTERNS_RATIO;
    				break;
    			case 4:
    				sort_field =PATTERNS_VOLUME;
    				break;
    			case 5:
    				sort_field =PATTERNS_OPB;
    				break;
    			case 6:
    				sort_field =PATTERNS_DB;
    				break;
    			default:
    				sort_field =PATTERNS_NAME;
    				break;
    			}
    		if(!asc)
    			ascdesc="DESC";
    		else
    			ascdesc="ASC";
    		if(last_patt!=null && page>1)
    			selectQuery = "SELECT * FROM "+TABLE_PATTERNS_NAME+/*" where "+sort_field+" < "+last+*/" order by "+sort_field+" "+ascdesc/*+" limit "+(Integer.toString(to))*/;
    		else
    			selectQuery = "SELECT * FROM "+TABLE_PATTERNS_NAME+" order by "+sort_field+" "+ascdesc/*+" limit "+(Integer.toString(to))*/;
    		Log.d(DEBUG_TAG, selectQuery);
    		cursor = db.rawQuery(selectQuery, null);
    		if(cursor != null && cursor.getCount() > 0)
    		{
    			if(cursor.moveToFirst())
    			{
    				while(!cursor.isAfterLast())
    				{
    					patt = new Patterns(
    							cursor.getString(cursor.getColumnIndex(PATTERNS_ID)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_NAME)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_LINK)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_LENGTH)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_VOLUME)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_OPB)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_DB)),
    							cursor.getString(cursor.getColumnIndex(PATTERNS_RATIO)));
    					patterns.add(patt);
    					cursor.moveToNext();
    				}
    			}
    		}else{
    			return null;
    		}
    	}catch(Exception e)
    	{
    		Log.e("Patterns DB getter Error", e.toString());
    	}
    	finally
    	{
    		if(cursor!=null)
    			cursor.close();
    			close();
    	}
    	return patterns;
    }
	
	
	

}
