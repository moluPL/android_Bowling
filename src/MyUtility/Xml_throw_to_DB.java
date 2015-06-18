package MyUtility;

import java.io.IOException;
import java.io.InputStream;
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

/**
 * Klasa ³aduj¹ca dane z pliku xml do bazy danych
 * ³aduje wszystkie mozliwe kombinacje stracen pinow podczas gry
 * @author karlo
 *
 */
public class Xml_throw_to_DB 
{
	
private static final Object BASE = "pins";
private static final Object THROW = "throw";
/**
 * Zmienne 
 */
	protected XmlPullParserFactory factory;
	protected XmlPullParser parser;
	protected InputStream is;
	protected XmlResourceParser pars;
	protected Context myContext;
	protected String tagName;
	
	/**
	 * Constructor with context
	 * @param con
	 */
	public Xml_throw_to_DB(Context con){
		myContext = con;
	}
	public List<String> AddThrowsFromXML()
	{
		
		try
		{
			//	Get xml resource file
			Resources res = myContext.getResources(); 
         
			//Open xml file
			pars = res.getXml(R.xml.pins_left);
			if(pars !=null)
				parser = pars;
			
			ArrayList<String> listaStr = new ArrayList<String>();
            int eventType = parser.getEventType();
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) 
            {
                tagName = parser.getName();

                switch (eventType) 
                {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                    	if (tagName.equals(BASE)) {
                    		
                        }
                        if (tagName.equals(THROW)) {
                            listaStr.add(parser.nextText().toString());
                        }
                      
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals(BASE)) 
                        {
                            done = true;
                        } 
                        break;
                }
                eventType = parser.next();
                
                
            }
            return listaStr;
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
		return null;
	}
}
