package moja.paczka.namespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import customTheme.Utils;
import MyUtility.MyFrame;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Editor extends Activity {

	private TextView title;
	private TextView title2;
	private LinearLayout manualsLayout;
	private TableRow numberSelectLayout;
	private Bundle extras;
	private int[] pins,pins2;
	private CheckBox[][] rba;
	private boolean[] first,second;
	private boolean state = false,filled=false;
	private int gameNumber,tmp;
	private MyFrame ramka;
	
	/**
	 * get data from extras from intent from parent Activity
	 * @return int[] 
	 */
	public int[] getData(){	
		int[] piny = new int[3];
		extras = getIntent().getExtras();
		if (extras == null) 
		{
		    return null;
		}
		piny[0]=extras.getInt(getString(R.string.first));
		piny[1]=extras.getInt(getString(R.string.second));
		piny[2]=extras.getInt(getString(R.string.frmNmbr));
		gameNumber = extras.getInt(getString(R.string.gameNumber));
		ramka=(MyFrame) extras.getSerializable("MyFrame");
		
		return piny;

	}
	/**
	 * setting-up texts
	 * @param pins int[] with pins from game
	 */
	public void setTitle(int[] pins){
		if(pins!=null){
			title=(TextView)findViewById(R.id.titleEdit);			//ustawianie tytulow
			title.setText(getString(R.string.game_number)+(gameNumber+1)+" \n"+getString(R.string.editor_tittle)+(pins[2]+1));	//ustawienie numeru ramki
			title2=(TextView)findViewById(R.id.actualScore);				
			title2.setText(getString(R.string.editor_tittle2)+"["+pins[0]+" | "+pins[1]+"]");	//ustawienie pinow w rzutach
			title2.setTextColor(ColorStateList.valueOf(Color.LTGRAY));
		}
		else
			Toast.makeText(getApplicationContext(),R.string.error_no_data, Toast.LENGTH_SHORT).show();
	}
	
	 /**
	  * 	Akcja przycisku save i zakoncz
	  */
	android.view.View.OnClickListener btnLtlstenerSave  = new Button.OnClickListener() {
		public void onClick(View v) {
			state=true;
			finish();
        }
   };
   android.view.View.OnClickListener btnLtlstenerCancel  = new Button.OnClickListener() {
		public void onClick(View v) {
			finish();
        }
   };
	
	/**
	 * UStawienia listenera ktory rzut wybieramy
	 */
	android.view.View.OnClickListener btnLtlstener  = new Button.OnClickListener() {
		public void onClick(View v) {
			int id = v.getId();
			if(id==2){
				if(pins[0] != 10)	//sprawdza czy nie bylo strike'a w pierwszym rzucie
					openDialog(id);
				else				//jesli byl nie mozna edytowac drugiego rzutu
					Toast.makeText(getApplicationContext(), getString(R.string.forbid_edit_2_throw), Toast.LENGTH_SHORT).show();
			}
			else					
				openDialog(id);
        }
   };
  
  
	
	
	
	/**
	 * przy tworzeniu Aktywnosci
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 Utils.setThemeToActivity(this);
		pins = new int[3];
		pins2 = new int[3];
		rba = new CheckBox[2][10];
		first = new boolean[10];
		second = new boolean[10]; 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editors);
		pins = getData();
		
		if(pins!=null)
			setTitle(pins);
		
		/*
		 * przyciski edycji
		 */
		
		Button btn = (Button) findViewById(R.id.btn_pin1);		
		Button btn2 = (Button) findViewById(R.id.btn_pin2);
		Button btnSave = (Button) findViewById(R.id.editorOk);
		Button btnCancel = (Button) findViewById(R.id.EditorCancel);
		numberSelectLayout = (TableRow) findViewById(R.id.numberSelectPins);
		manualsLayout = (LinearLayout) findViewById(R.id.ManualPins);
		rba[0][0] = (CheckBox) findViewById(R.id.pin1);
		rba[0][1] = (CheckBox) findViewById(R.id.pin2);
		rba[0][2] = (CheckBox) findViewById(R.id.pin3);
		rba[0][3] = (CheckBox) findViewById(R.id.pin4);
		rba[0][4] = (CheckBox) findViewById(R.id.pin5);
		rba[0][5] = (CheckBox) findViewById(R.id.pin6);
		rba[0][6] = (CheckBox) findViewById(R.id.pin7);
		rba[0][7] = (CheckBox) findViewById(R.id.pin8);
		rba[0][8] = (CheckBox) findViewById(R.id.pin9);
		rba[0][9] = (CheckBox) findViewById(R.id.pin10);
		rba[1][0] = (CheckBox) findViewById(R.id.pin21);
		rba[1][1] = (CheckBox) findViewById(R.id.pin22);
		rba[1][2] = (CheckBox) findViewById(R.id.pin23);
		rba[1][3] = (CheckBox) findViewById(R.id.pin24);
		rba[1][4] = (CheckBox) findViewById(R.id.pin25);
		rba[1][5] = (CheckBox) findViewById(R.id.pin26);
		rba[1][6] = (CheckBox) findViewById(R.id.pin27);
		rba[1][7] = (CheckBox) findViewById(R.id.pin28);
		rba[1][8] = (CheckBox) findViewById(R.id.pin29);
		rba[1][9] = (CheckBox) findViewById(R.id.pin210);
		
		pins2 = getData();
		//pins2[2]=pins[2];
		if(ramka.getfilled())
		{
			first=ramka.getLeftF();
			second=ramka.getLeftS();
			for(int i=0;i<10;i++){
    			rba[0][i].setEnabled(!second[i]);
    			rba[1][i].setEnabled(!first[i]);
    			rba[0][i].setChecked(first[i]);
    			rba[1][i].setChecked(second[i]);
    		}
		}
		else
		{
			pins2[0]=0;
			pins2[1]=0;		
			Arrays.fill(first, false);
			Arrays.fill(second, false);
		}
		
		/*
		 * listener for switch
		 */
		Switch swtch = (Switch) findViewById(R.id.switchManualSetPins);
		swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) 
		        {
		            // The toggle is enabled
		        	manualsLayout.setVisibility(View.VISIBLE);
		        	numberSelectLayout.setVisibility(View.GONE);
		        	filled=true;
		        	ramka.edit(pins2[0],pins2[1],first,second);
		        	setTitle(pins2);
		        	
		        }
		        else 
		        {
		            // The toggle is disabled
		        	filled=false;
		        	numberSelectLayout.setVisibility(View.VISIBLE);
		        	manualsLayout.setVisibility(View.GONE);
		        	setTitle(pins);
		        }
		    }
		});
		/*
		 * listener for clicking radio button from first throws
		 */
		OnCheckedChangeListener radioListener = new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				tmp = Integer.parseInt((String) buttonView.getText());
				tmp--;

				if(isChecked){
					rba[1][tmp].setEnabled(false);
					first[tmp] = true;
					pins2[0]++;
					setTitle(pins2);
				}
				else{
					rba[1][tmp].setEnabled(true);
					first[tmp] = false;
					pins2[0]--;
					setTitle(pins2);
				}
				
			}
		};
		//seconds
		OnCheckedChangeListener radioListener2 = new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				tmp = Integer.parseInt((String) buttonView.getText());
				tmp--;
				
				//Log.d("DEbug Editor",(String) buttonView.getTag());
				if(isChecked){
					rba[0][tmp].setEnabled(false);
					second[tmp]=true;
					pins2[1]++;
					setTitle(pins2);
				}
				else{
					rba[0][tmp].setEnabled(true);
					second[tmp]=false;
					pins2[1]--;
					setTitle(pins2);
				}
				
			}
		};
		
		
		
		for(int i=0;i<10;i++){
			rba[0][i].setOnCheckedChangeListener(radioListener);
			//rba[0][i].setTag(R.id.firstsPins, i);
		}
		for(int i=0;i<10;i++){
			rba[1][i].setTag(R.id.secondsPins, i);
			rba[1][i].setOnCheckedChangeListener(radioListener2);
			
		}
		
				
		btn.setId(1);
		btn2.setId(2);
		btn.setOnClickListener(btnLtlstener);
		btn2.setOnClickListener(btnLtlstener);
		btnSave.setOnClickListener(btnLtlstenerSave);
		btnCancel.setOnClickListener(btnLtlstenerCancel);
		
		
	}
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	/**
	 * finish this activity
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		  if(state){
			  Intent data = new Intent();
			  data.putExtra(getString(R.string.frmNmbr),pins[2]);
			  if(filled){
				  data.putExtra(getString(R.string.first),pins2[0]);
				  data.putExtra(getString(R.string.second),pins2[1]);
				  data.putExtra(getString(R.string.first_throw),first);
				  data.putExtra(getString(R.string.second_throw),second);
				  data.putExtra(getString(R.string.filled), filled);
			  }
			  else
			  {
				  data.putExtra(getString(R.string.filled), filled);
				  data.putExtra(getString(R.string.first),pins[0]);
				  data.putExtra(getString(R.string.second),pins[1]);
			  }
			  data.putExtra(getString(R.string.gameNumber),gameNumber);
		  // 	Activity finished ok, return the data
			  setResult(RESULT_OK, data);
		  }
		  else
			  setResult(RESULT_CANCELED);
		super.finish();
		
		
	} 
	
/**
 * Dialog wyswietlajacy wybor pinów
 * @see android.app.Activity#onCreateDialog(int)
 */
	protected void openDialog(int id) {
	   int count = (10-pins[0]);
	   filled=false;
	   List<String> strings = new ArrayList<String>( Arrays.asList("0","1", "2", "3", "4", "5", "6", "7", "8", "9", "X") );
	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   builder.setTitle(getResources().getString(R.string.choose_nmbr_pins));
	   switch(id){
	   		case 1:
	   			final CharSequence[] items = strings.toArray(new String[count]);
	   			builder.setItems(items, new DialogInterface.OnClickListener()
	   			{
	   				public void onClick(DialogInterface dialog, int item) {//dzialanie po wybraniu pina w pierwszym rzucie
	   					Toast.makeText(getApplicationContext(), getString(R.string.u_choose)+" "+items[item], Toast.LENGTH_SHORT).show();
	   					pins[0]=item;
	   					
	   					if(item>pins[1] || item+pins[1]>10)
	   						pins[1]=10-item;
	   					setTitle(pins);
	   					ramka.edit(pins[0],pins[1]);
	   					Log.d("Editor DEBug",ramka.toString());
			   			}
	   			});
	   			break;
	   		case 2 :														//dzialanie po wybraniu edycji drugiego rzutu
	   			strings=strings.subList(0, count);
	   			strings.add("/");
	   			final CharSequence[] items1 = strings.toArray(new String[count]);
	   			builder.setItems(items1, new DialogInterface.OnClickListener()
	   			{
	   				public void onClick(DialogInterface dialog, int item) {												//dzialanie po wybraniu pina w drugim rzucie
	   					pins[1]=item;
	   					
	   					setTitle(pins);
	   					ramka.edit(pins[0],pins[1]);
	   				}
	   			});
	   			break;
	   	}
	   AlertDialog alert = builder.create();
	   alert.show();
	   
	}
	
}