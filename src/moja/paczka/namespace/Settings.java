package moja.paczka.namespace;

import customTheme.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Settings extends Activity {

	
	/** Called when the activity is first created. */
	 @Override
	 public void onCreate(Bundle savedInstanceState)
	 {
	  super.onCreate(savedInstanceState);
	  Utils.setThemeToActivity(this);
	  setContentView(R.layout.settings);
	  
	  OnClickListener onclkLis = new OnClickListener() {
		
		public void onClick(View v) {
			if(v.getId()==R.id.settings_lang_btn){
				
			}
			else if(v.getId()==R.id.settings_theme_btn){
				Intent myIntent = new Intent(getApplicationContext(),SettingTheme.class);
                startActivity(myIntent);
			}
		}
	  };
	  findViewById(R.id.settings_theme_btn).setOnClickListener(onclkLis);
	  findViewById(R.id.settings_lang_btn).setOnClickListener(onclkLis);
	 }
}
