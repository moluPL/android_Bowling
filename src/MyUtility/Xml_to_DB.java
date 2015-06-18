package MyUtility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import moja.paczka.namespace.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;


public class Xml_to_DB {

	protected static final String urlPatterns = "https://dl.dropboxusercontent.com/u/22188636/baza_patt.xml";
	protected static final String urlVersions = "https://dl.dropboxusercontent.com/u/22188636/DB_Versions.xml";
	protected XmlPullParserFactory factory;
	protected XmlPullParser parser;
	protected InputStream is;
	protected ArrayList<Patterns> patternsList;
	protected String version;
	protected String pattV;
	protected String ballV;
	protected String spareV;
	protected Patterns patt;
	protected String tagName;
	protected String name;
	protected String link;
	protected String length;
	protected String ratio;
	protected String volume;
	protected String opb;
	protected String dba;
	protected Context context;
	protected XmlResourceParser pars;
	protected Db_versions db_v;

	public static final String PATTERN = "Pattern";
	public static final String BASE = "Pattern_base";    
	public static final String NAME = "name";
	public static final String LINK = "link";
	public static final String LENGTH = "length";
	public static final String RATIO = "ratio";
	public static final String VOLUME = "volume";
	public static final String OPB = "opb";
	public static final String DBA = "db";
	
	public static final String VERSIONS = "Versions";
	public static final String PATTERNSV = "PatternsV";
	public static final String BALLSV = "ballsV";
	public static final String SPARESV = "sparesV";

	
	
	
	/*
	 * empty constructor
	 */
	public Xml_to_DB(){
		
		ballV=null;
		spareV=null;
		pattV=null;
	}
	/*constructor with context
	 * 
	 */
	public Xml_to_DB(Context con){
		context=con;
		pars=null;
		ballV=null;
		spareV=null;
		pattV=null;
	}
	/*
	 * Downloading xml file from web
	 */
	public InputStream downloadXmlUrl(String urlString) throws IOException{
		   
		InputStream stream = null;
		try{
			   URL url = new URL(urlString);
			   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			   conn.setRequestMethod("GET");
			   conn.setDoInput(true);
			   conn.connect();
			   stream = conn.getInputStream();
		   }catch(Exception e){
			   Log.e("ERROR GET",e.toString());
		   }
	        return stream;
	}
	public String getVersion(){
		return version;
	}
	/*
	 * Checking if we need to download new file
	 */
	public Boolean checkVersionsPatterns()
	{
		
		try 
		{
			factory = XmlPullParserFactory.newInstance();
			parser = factory.newPullParser();
			is = downloadXmlUrl(urlVersions);
		
			if(is !=null)
				parser.setInput(is, null);
			else 
				return false;
			int eventType;
			eventType = parser.getEventType();
		
			boolean done = false;
			while (eventType != XmlPullParser.END_DOCUMENT && !done) 
			{
				tagName = parser.getName();
				switch (eventType) 
				{
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if (tagName.equals(PATTERNSV)) {
							pattV = parser.nextText().toString();
						}
						if (tagName.equals(BALLSV)) 
						{
							ballV = parser.nextText().toString();
						}
						if (tagName.equals(SPARESV)) {
							spareV = parser.nextText().toString();
						}
						break;
					case XmlPullParser.END_TAG:
						if (tagName.equals(VERSIONS)) {
							done = true;
						}   
						break;
				}
				eventType = parser.next();
			}
			if(pattV != null){
				version=pattV;
				db_v = new Db_versions(context);
				float patt_v_from_db =  db_v.getPattVersionF();
				System.out.println(patt_v_from_db);
				if(patt_v_from_db != (float)(-1.0) && pattV != null)
				{
					if(patt_v_from_db < Float.parseFloat(pattV)){
						return true;
					}
						
				}
			}
			
		} catch (XmlPullParserException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
    	{
    		// 	TODO Auto-generated catch block
    		e.printStackTrace();
    	} 
		finally
        {           
			//Close the xml file
        	try 
        	{
        		if(is !=null)
        			is.close();
        		if(pars !=null)
        			pars.close();
        	}
        	catch (IOException e) 
        	{
        		// 	TODO Auto-generated catch block
        		e.printStackTrace();
        	} 
		
        }
		return false;
	}
	  public List<Patterns> parsePatt(boolean overNet) {
	        try {
	        	if(overNet)
	        	{
	        		 factory = XmlPullParserFactory.newInstance();
	 	            parser = factory.newPullParser();
	 	            is = downloadXmlUrl(urlPatterns);
	 	           if(is !=null)
		            	parser.setInput(is, null);
		            else 
		            	return null;
	        	}
	        	else
	        	{
	        		
					//Get xml resource file
	                Resources res = context.getResources(); 
	                 
	                //Open xml file
	                pars = res.getXml(R.xml.baza_patt);
	                if(pars !=null)
	                	parser = pars;
	        		
	        	}
	            
	            
	            int eventType = parser.getEventType();
	            boolean done = false;
	            patt = new Patterns();
	            patternsList = new ArrayList<Patterns>();
	            while (eventType != XmlPullParser.END_DOCUMENT && !done) 
	            {
	                tagName = parser.getName();

	                switch (eventType) 
	                {
	                    case XmlPullParser.START_DOCUMENT:
	                        break;
	                    case XmlPullParser.START_TAG:
	                    	if (tagName.equals(BASE)) {
	                    		version = parser.getAttributeValue(0);
	                        }
	                        if (tagName.equals(PATTERN)) {
	                            patt = new Patterns();
	                        }
	                        if (tagName.equals(LINK)) {
	                            link = parser.nextText().toString();
	                        }
	                        if (tagName.equals(NAME)) {
	                            name = parser.nextText().toString();
	                        }
	                        if (tagName.equals(LENGTH)) {
	                            length = parser.nextText().toString();
	                        }
	                        if (tagName.equals(RATIO)) {
	                            ratio = parser.nextText().toString();
	                        }
	                        if (tagName.equals(VOLUME)) {
	                            volume = parser.nextText().toString();
	                        }
	                        if (tagName.equals(OPB)) {
	                            opb = parser.nextText().toString();
	                        }
	                        if (tagName.equals(DBA)) {
	                            dba = parser.nextText().toString();
	                        }
	                        break;
	                    case XmlPullParser.END_TAG:
	                        if (tagName.equals(BASE)) {
	                            done = true;
	                        } else if (tagName.equals(PATTERN)) {
	                            patt = new Patterns(name, link, length, volume,opb,dba,ratio);
	                            patternsList.add(patt);
	                            
	                        }
	                        break;
	                }
	                eventType = parser.next();
	                
	                
	            }
	        }catch (XmlPullParserException e)
	        {       
	            Log.e("Debug", e.getMessage(), e);
	        }
	        catch (IOException e)
	        {
	            Log.e("Debug", e.getMessage(), e);
	             
	        }           
	        finally
	        {           
	            //Close the xml file
	        	try {
	        		if(is !=null)
	        			is.close();
	        		if(pars !=null)
	        			pars.close();
	        		
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }

	        return patternsList;
	    }
	
	  public void add_toDB_local(Patterns_DB pattDb)
	  {
		  
		  db_v = new Db_versions(context);
		  db_v.addVersionPatt(version);
		  for(Patterns p  : patternsList){
			 // System.out.println(p.GetStringAll());
			  pattDb.addPatternFromPatterns(p);
		  }
	  }
	  
	  
	  
}
