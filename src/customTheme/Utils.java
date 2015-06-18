package customTheme;

import moja.paczka.namespace.R;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

public class Utils {
	 
	 public static String SIZE="";
	  public static boolean settingChanged=false;
	  public static String THEME="";

	  
	  public static void setThemeToActivity(Fragment act )
	  {
		  
	  }
	  
	 public static void setThemeToActivity(Activity act )
	  {
	  
	   try {
	  

	   if (Utils.SIZE.equalsIgnoreCase("LARGE"))
	   {
	       act.setTheme(R.style.Theme_LargeText);
	       Log.d(" ", "Theme Large txt size is to be is applied.");
	   }
	   if (Utils.SIZE.equalsIgnoreCase("TABLET"))
	   {
	       act.setTheme(R.style.Theme_TabletLargeTextWhite);
	       Log.d(" ", "Theme Large txt size is to be is applied.");
	   }
	   if (Utils.SIZE.equalsIgnoreCase("MEDIUM"))
	   {
	       act.setTheme(R.style.Theme_MediumText);
	       Log.d(" ", "Theme Small text Size is to be is applied.");
	   }
	   
	    if (Utils.SIZE.equalsIgnoreCase("SMALL"))
	   {
	       act.setTheme(R.style.Theme_SmallText);
	       Log.d(" ", "Theme Small text Size is to be is applied.");
	   }
	    if (Utils.SIZE.equalsIgnoreCase("SMALL_WHITE"))
		   {
		       act.setTheme(R.style.Theme_SmallTextWhite);
		       Log.d(" ", "Theme Small text Size is to be is applied.");
		   }
	   
	    if (Utils.SIZE.equalsIgnoreCase("LARGE_WHITE"))
		   {
		       act.setTheme(R.style.Theme_LargeTextWhite);
		       Log.d(" ", "Theme Large txt size is to be is applied.");
		   }
	    if (Utils.SIZE.equalsIgnoreCase("LARGE_GREEN"))
		   {
		       act.setTheme(R.style.Theme_LargeTextGreen);
		       Log.d(" ", "Theme Large txt size is to be is applied.");
		   }
	    
	    if(Utils.SIZE.equalsIgnoreCase("DEFAULT"))
	    {
	     act.setTheme(R.style.Theme_DefaultText);
	     Log.d("", "theme default text size is applied.");
	    }
	    
	   /**
	    * 	THEMES
	    */
	    if(Utils.THEME.equalsIgnoreCase("Default_theme"))
	    {
	     act.setTheme(R.style.Theme_DefaultTheme);
	     Log.d("", "Default theme is to be applied.");
	    }


	    if(Utils.THEME.equalsIgnoreCase("Gray"))
	    {
	     act.setTheme(R.style.Theme_Gray);
	     Log.d("", "gray theme is to be applied.");
	    }

	    if(Utils.THEME.equalsIgnoreCase("Radial"))
	    {
	     act.setTheme(R.style.Theme_Radial);
	     Log.d("", "radial theme is to be applied.");
	    }
	    
	    if(Utils.THEME.equalsIgnoreCase("Radial"))
	    {
	     act.setTheme(R.style.Theme_Radial);
	     Log.d("", "radial theme is to be applied.");
	    }
	    
	    if(Utils.THEME.equalsIgnoreCase("Golden_small"))
	    {
	     act.setTheme(R.style.Theme_Golden_small);
	     Log.d("", "radial theme is to be applied.");
	    }
	    if(Utils.THEME.equalsIgnoreCase("Golden_medium"))
	    {
	     act.setTheme(R.style.Theme_Golden_medium);
	     Log.d("", "radial theme is to be applied.");
	    }
	    
	    if(Utils.THEME.equalsIgnoreCase("Golden_large"))
	    {
	     act.setTheme(R.style.Theme_Golden_large);
	     Log.d("", "radial theme is to be applied.");
	    }
	    
	    if(Utils.THEME.equalsIgnoreCase("Pins"))
	    {
	     act.setTheme(R.style.Theme_Pins);
	     Log.d("", "pins theme is to be applied.");
	    }
	    
	  
	   }
	   catch (Exception e) {
	  e.printStackTrace();
	 }
	  
	  }
	}
