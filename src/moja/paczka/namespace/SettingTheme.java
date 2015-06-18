package moja.paczka.namespace;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import customTheme.Utils;

public class SettingTheme extends Activity implements View.OnClickListener

{
 /** Called when the activity is first created. */
 @Override
 public void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  Utils.setThemeToActivity(this);
  setContentView(R.layout.config);

  findViewById(R.id.button1).setOnClickListener(this);
  findViewById(R.id.button1L).setOnClickListener(this);
  findViewById(R.id.button1LT).setOnClickListener(this);
  findViewById(R.id.button1LW).setOnClickListener(this);
  findViewById(R.id.button2).setOnClickListener(this);
  findViewById(R.id.button2M).setOnClickListener(this);
  findViewById(R.id.button3).setOnClickListener(this);
  findViewById(R.id.button4).setOnClickListener(this);
  findViewById(R.id.button5).setOnClickListener(this);
  findViewById(R.id.button5L).setOnClickListener(this);
  findViewById(R.id.button6).setOnClickListener(this);
  findViewById(R.id.button7).setOnClickListener(this);
  findViewById(R.id.button8).setOnClickListener(this);
 }

 public void onClick(View v)
 {
  switch (v.getId())
  {
  case R.id.button1:
	  Utils.SIZE="DEFAULT";
      Utils.settingChanged=true;
      Utils.THEME="Default_theme";
      save();
     // finish();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
  case R.id.button1L:
	  Utils.SIZE="LARGE";
      Utils.settingChanged=true;
      Utils.THEME="Default_theme";
      save();
     // finish();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
   break;
  case R.id.button1LT:
	  Utils.SIZE="TABLET";
      Utils.settingChanged=true;
      Utils.THEME="Default_theme";
      save();
     // finish();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
   break;
  case R.id.button1LW:
	  Utils.SIZE="LARGE_GREEN";
      Utils.settingChanged=true;
      Utils.THEME="Default_theme";
      save();
     // finish();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
   break;
  case R.id.button2:
      Utils.THEME="Gray";
      Utils.settingChanged=true;
      Utils.SIZE="SMALL";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
   break;
  case R.id.button2M:
      Utils.THEME="Gray";
      Utils.settingChanged=true;
      Utils.SIZE="MEDIUM";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
   break;
  case R.id.button2L:
      Utils.THEME="Gray";
      Utils.settingChanged=true;
      Utils.SIZE="LARGE";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
   break;
  case R.id.button3:
       Utils.THEME="Radial";
       Utils.settingChanged=true;
       Utils.SIZE="LARGE";
       save();
       startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
       break;
       
  case R.id.button4:
      Utils.THEME="Golden_small";
      Utils.settingChanged=true;
      //Utils.SIZE="SMALL";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
      break;
  case R.id.button5:
      Utils.THEME="Golden_medium";
      Utils.settingChanged=true;
      save();
      //Utils.SIZE="SMALL";
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
      break;
  case R.id.button5L:
      Utils.THEME="Golden_large";
      Utils.settingChanged=true;
      save();
      //Utils.SIZE="SMALL";
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
      break;
      
  case R.id.button6:
      Utils.THEME="Pins";
      Utils.settingChanged=true;
      Utils.SIZE="SMALL_WHITE";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
      break;
  case R.id.button7:
      Utils.THEME="Pins";
      Utils.settingChanged=true;
      Utils.SIZE="LARGE_WHITE";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
      break;
  case R.id.button8:
      Utils.THEME="Pins";
      Utils.settingChanged=true;
      Utils.SIZE="SMALL";
      save();
      startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
      break;
      
  default :
   break;

  }

 }
     public void onBackPressed() {
  // TODO Auto-generated method stub
  super.onBackPressed();
  startActivity(new Intent( SettingTheme.this,  PierwszyActivity.class));
  SettingTheme.this.finish();

 }
     public void save(){
    	 SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
         editor.putString("size",Utils.SIZE);
         editor.putString("theme",Utils.THEME);
         editor.apply();
     }
 
}




