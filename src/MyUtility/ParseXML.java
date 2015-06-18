package MyUtility;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


public class ParseXML {
	
	private String input="";
	 // We don't use namespaces
    //private static final String ns = null;
    
    public ParseXML() throws XmlPullParserException, IOException
    {
	   input ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><Balls version=1.0><ball><name>900 Global Hook! Black </name><brand>900 Global</brand><release_date>05/15/2012</release_date><radius_of_gyration>2.551</radius_of_gyration><differential>0.046</differential><mass_biass_diff>n/a</mass_biass_diff><lane_condition>Medium</lane_condition><finish>Polished</finish><coverstock>Reactive Resin</coverstock><coverstock_name>S43 Pearl Reactive</coverstock_name><factory_finish>n/a</factory_finish><core_type>Symmetric</core_type><core_name>n/a</core_name></ball><ball><name>900 Global Hook!  Hybrid</name><brand>900 Global</brand><release_date>05/15/2012</release_date><radius_of_gyration>2.551</radius_of_gyration><differential>0.046</differential><mass_biass_diff>n/a</mass_biass_diff><lane_condition>Medium</lane_condition><finish>Polished</finish><coverstock>Hybrid Reactive</coverstock><coverstock_name>S43 Hybrid Reactive</coverstock_name><factory_finish>n/a</factory_finish><core_type>Symmetric</core_type><core_name>Symmetric (10# has a pancake core)</core_name></ball>"; 
	   parse();
   }
    public void parse() throws XmlPullParserException, IOException {
    	System.out.println("PArse()");
    	String name="",brand="",rg="";
        try{
        	 // get a new XmlPullParser object from Factory
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            System.out.println("new parser");
           // XmlPullParser parser = Xml.newPullParser();
           // parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(input));
            int eventType = parser.getEventType();
            // process tag while not reaching the end of document
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType) 
                {
                    // at start of document: START_DOCUMENT
                    case XmlPullParser.START_DOCUMENT:
                        System.out.println("Nowy dokument!");
                        break;
 
                    // at start of a tag: START_TAG
                    case XmlPullParser.START_TAG:
                        // get tag name
                        String tagName = parser.getName();
                        // if <study>, get attribute: 'id'
                        if(tagName.equalsIgnoreCase("name")) {
                            name= parser.getText();
                            System.out.println("name::"+name);
                        }
                        // if <content>
                        else if(tagName.equalsIgnoreCase("brand")) {
                            brand = parser.nextText();
                            System.out.println("brand::"+brand);
                        }
                        // if <topic>
                        else if(tagName.equalsIgnoreCase("core_type")) {
                            	rg = parser.nextText();
                            	System.out.println("core::"+rg);
                        }
                        break;
                }
                // jump to next event
                eventType = parser.next();
             }
        }catch(Exception e){
        	e.printStackTrace();
        	Log.e("Error PArsera", e.toString());
        }
    }
    }
   