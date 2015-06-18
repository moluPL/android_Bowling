package moja.paczka.namespace;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import customTheme.Utils;
import MyUtility.Patterns;
import MyUtility.Patterns_DB;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Selecting_pattern extends Activity{
	
	private Button btn_save,btn_cancel,download,btn_prev,btn_next,btn_search,btn_search_dialog_ok,btn_search_dialog_cancel;
	private Spinner spin;
	private TableLayout tl;
	private TableRow tr_sort_panel,tr_error_txt,tr_download,tr_sort_txt,tr_navi;
	private TableRow[] tr;
	private int show,sort,selected_id,page,limit;
	private Patterns_DB database;
	private List<Patterns> list,list_patt;
	private Patterns last_pattern;
	private Dialog search_dialog;
	private Context myContext;
	private String name;
	private static final String NAME_PATTERN = "^[A-Z_a-z]{3,15}$";
	private Pattern patt;
	private EditText vname;
	private boolean asc;
	List<RadioButton> listRadio;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// FIRST SET UP LAYOUT THAN INIT BUTTONS ETC
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecting_patterns);
		init();
	}
	/*
	 * Setting up main G'ui
	 */
	public void init(){
		limit=0;
		last_pattern = null;
        show=10;
        sort=1;
        page=1;
        asc=false;
        //getting context and setting up buttons
		myContext = this;
		search_dialog = null;
		list_patt =  new ArrayList<Patterns>();
		last_pattern=null;
		btn_cancel = (Button) findViewById(R.id.select_pat_btn_cancel);
		btn_save = (Button) findViewById(R.id.select_pat_btn_save);
		spin = (Spinner) findViewById(R.id.select_pat_spinner_how_much_item);
		//ArrayAdapter spinnerAdapter = new ArrayAdapter<T>(this,android.R.layout.simple_spinner_dropdown_item,);
		tr_error_txt = (TableRow) findViewById(R.id.select_pat_TRerror_txt);
		tr_navi =  (TableRow) findViewById(R.id.select_pat_navig_panel);
		tr_sort_panel = (TableRow) findViewById(R.id.select_pat_TRsort_panel);
		tr_download = (TableRow) findViewById(R.id.select_pat_TRdownload);
		tr_sort_txt = (TableRow) findViewById(R.id.select_pat_TRsortTxt);
		btn_prev = (Button) findViewById(R.id.select_pat_btn_prev);
		btn_next = (Button) findViewById(R.id.select_pat_btn_next);
		download = (Button) findViewById(R.id.select_pat_btn_download);
		btn_search = (Button) findViewById(R.id.select_pattern_search_button);
		
		OnClickListener onclkSort = new OnClickListener() {
			
			public void onClick(View v) {
				if(v.getId()==R.id.select_pat_btn_srot_name){
					if(sort==1)
						asc=!asc;
					else
						asc=false;
					sort=1;
					
					loadPatterns(false);
				}else if(v.getId()==R.id.select_pat_btn_srot_db){
					if(sort==6)
						asc=!asc;
					else
						asc=false;
					sort=6;
					loadPatterns(false);
				}else if(v.getId()==R.id.select_pat_btn_srot_length){
					if(sort==2)
						asc=!asc;
					else
						asc=false;
					sort=2;
					loadPatterns(false);
				}else if(v.getId()==R.id.select_pat_btn_srot_opb){
					if(sort==5)
						asc=!asc;
					else
						asc=false;
					sort=5;
					loadPatterns(false);
				}else if(v.getId()==R.id.select_pat_btn_srot_ratio){
					if(sort==3)
						asc=!asc;
					else
						asc=false;
					sort=3;
					loadPatterns(false);
				}else if(v.getId()==R.id.select_pat_btn_srot_volume){
					if(sort==4)
						asc=!asc;
					else
						asc=false;
					sort=4;
					loadPatterns(false);
				}
				
			}
		};
		
		findViewById(R.id.select_pat_btn_srot_name).setOnClickListener(onclkSort);
		findViewById(R.id.select_pat_btn_srot_db).setOnClickListener(onclkSort);
		findViewById(R.id.select_pat_btn_srot_length).setOnClickListener(onclkSort);
		findViewById(R.id.select_pat_btn_srot_opb).setOnClickListener(onclkSort);
		findViewById(R.id.select_pat_btn_srot_ratio).setOnClickListener(onclkSort);
		findViewById(R.id.select_pat_btn_srot_volume).setOnClickListener(onclkSort);
		
		//setting up download button
		download.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// START ACTIVITY FOR DOWNLOADING DATA FROM SERVER
				Intent my_intent = new Intent(v.getContext(),DownloadData.class);
				startActivityForResult(my_intent, 0);
			}
		});
		
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				setResult(selected_id);
				finish();
			}
		});
		
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				setResult(-1);
				finish();
			}
		});
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// SET UP NEXT PAGE OF DATAS
				int tmp = ((page+1)*show);
				if(tmp>limit){
					btn_next.setEnabled(false);
					btn_next.setClickable(false);
					btn_next.setError("");
					Log.d("DEBUG", "disable");
				}
				
					Log.d("DEBUG", "P*s = "+(page*show)+" LIMIT= "+limit);
					page += 1;
					last_pattern = list_patt.get(page-2);
					btn_next.setText(getString(R.string.next)+" no"+Integer.toString(page+1));
					btn_prev.setText(getString(R.string.prev)+" no"+Integer.toString(page-1));
					btn_prev.setEnabled(true);
					loadPatterns(false);
				
			}
		});
		btn_prev.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				btn_next.setClickable(true);
				btn_next.setEnabled(true);
				btn_next.setError(null);
				if(page>2){
					page--;
					//last_pattern = list_patt.get(page-2);
					btn_prev.setText(getString(R.string.prev)+" no"+Integer.toString(page-1));
					btn_next.setText(getString(R.string.next)+" no"+Integer.toString(page+1));
					loadPatterns(false);
				}
				else if(page==2){
					btn_prev.setEnabled(false);
					page--;
					//last_pattern = null;
					btn_prev.setText(getString(R.string.prev)+" no"+Integer.toString(page-1));
					btn_next.setText(getString(R.string.next)+" no"+Integer.toString(page+1));
					loadPatterns(false);
				}
				
			}
		});
		/*
		 * Search button activity
		 */
		btn_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				search_dialog = new Dialog(myContext);
				//search_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				search_dialog.setContentView(R.layout.search_dialog);
				search_dialog.setTitle(getString(R.string.search));
				btn_search_dialog_cancel = (Button) search_dialog.findViewById(R.id.search_dialog_cancel_btn);
				vname = (EditText) search_dialog.findViewById(R.id.search_dialog_editText);
				btn_search_dialog_ok = (Button) search_dialog.findViewById(R.id.search_dialog_ok_btn);
				btn_search_dialog_ok.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						if(getValue()){
							name = vname.getText().toString();
							loadPatterns(true);
							search_dialog.dismiss();
						}
						
					}
				});
				
				btn_search_dialog_cancel.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						search_dialog.dismiss();
					}
				});
		
				search_dialog.setCancelable(true);
				search_dialog.show();
			}
		});
		
		
		//set up spinner how much should show
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item,
                new String[] {"10","20","30","40","50","60" });
		adapter.setDropDownViewResource(R.layout.spinner_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    		show = (position+1)*10;
    	    		page=1;
    	    		btn_prev.setText(getString(R.string.prev)+" no"+Integer.toString(page-1));
    	    		btn_next.setText(getString(R.string.next)+" no"+Integer.toString(page+1));
    	    		loadPatterns(false);
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	});
        
		
	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// AFTER DOWNLOAD A DATA FROM WEB CLEAR ERRORS MESSAGES AND PROCEED TO LOAD DATA FROM DATABASE
		if(resultCode==1){
			tr_download.setVisibility(View.GONE);
			tr_error_txt.setVisibility(View.GONE);
			loadPatterns(false);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	OnClickListener reload =  new OnClickListener() {
		
		public void onClick(View v) {
			loadPatterns(false);
			
		}
	};
	protected RadioButton.OnClickListener onclkRadio =  new RadioButton.OnClickListener() 
	 { 
		 public void onClick(View v) 
		 { 
			 //Turn on the button now...once clicked, always clicked with RadioButtons 
			 btn_save.setEnabled(true);  
			 selected_id = v.getId();
			 Log.d("DEbug ID","Id" + Integer.toString(selected_id));
			 for(RadioButton button : listRadio) 
			 { 
				 if(selected_id == button.getId()) 
				 { 
					 button.setChecked(true);
					 
				 } 
				 else 
				 { 
					 //Turn all others off 
					 button.setChecked(false); 
				 } 
			 } 
		 } 
	 }; 
	/**
	 * LOAD PATTERNS INTO UI
	 * @param search
	 */
	public void loadPatterns(boolean search){
		ProgressDialog prog = new ProgressDialog(this);
		prog.setMessage("Please wait...."); 
        prog.show();
		try{
			tl = (TableLayout) findViewById(R.id.tlPatterns);
			RadioButton groupButton;
			listRadio = new ArrayList<RadioButton>();
			database = new Patterns_DB(getApplicationContext());
			limit = database.getNumberOfRecords();
			//TextView txt;
			if(search)
			{
				list = database.getPattName(name);
			}
			else
				list = database.getPatternsRange(last_pattern,page,show, sort, asc);
			Log.d("debug","page = "+page+" show="+show);
			int size=(page*show);
			int sizeL=0;
			if(list != null){
				sizeL = list.size();
				limit = list.size();
			}
			else if(search){
				setSearchError();
			}
			else{
				TextView tx = new TextView(getApplicationContext());
				tx.setText(getString(R.string.DB_error));
				tl.addView(tx);
			}
			int i=0;
			
			tl.removeAllViews();
			if(sizeL > 0)
			{				//check if there is some patterns in database
				//show visible sorting panel and hide download panel if it was visible
				if(tr_download.isShown())
					tr_download.setVisibility(View.GONE);
				if(tr_error_txt.isShown())
					tr_error_txt.setVisibility(View.GONE);
				if(!tr_sort_panel.isShown())
					tr_sort_panel.setVisibility(View.VISIBLE);
				if(!tr_sort_txt.isShown())
					tr_sort_txt.setVisibility(View.VISIBLE);
				if(!tr_navi.isShown())
					tr_navi.setVisibility(View.VISIBLE);
				if(!btn_next.isEnabled())
					btn_next.setEnabled(true);
				
				
				tr = new TableRow[show+1];
				
				for(i=0;i<show+1;i++)
				{
					tr[i]= new TableRow(this);
					tr[i].setBackgroundColor(R.drawable.border2);
					//tr[i].setLayoutParams(params)
					
				}
				
				
				
				String[] names = new String[] {"Link","select","name","length","ratio","volume","oil per board","db" };
				for (String str:names){
					TextView txt1 = new TextView(this);
					txt1.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
					txt1.setGravity(Gravity.LEFT);
					txt1.setPadding(5, 3, 5, 3);
					txt1.setText(str);
					tr[0].addView(txt1);
				}
				
				int  i1 = (size-show) ;
				for(int j=1;j<show+1;j++)
				{
					if(!(i1<limit))
						break;
					Log.d("DEBUG", "i = "+i1+" j = "+j+" limit= "+(limit-1)+" show = "+(show+1));
					 groupButton = new RadioButton(getBaseContext()); 
					setUpText(tr[j],list.get(i1),groupButton);	//Set up text in a row
					
					/*
					 * radio group button check etc
					 */
					listRadio.add(groupButton);
					groupButton.setOnClickListener(onclkRadio);
					 ++i1;
				}
				
				for(TableRow trr : tr)
				{
					tl.addView(trr);
				}
				/*
				 * adding last element for navi
				 */
				
				list_patt.add(list.get(limit-1));
				
			}
			else if(search){
				setSearchError();
			}
			else if(size<1 && page==1){
				TextView error = (TextView) findViewById(R.id.select_pat_txtErrorDownload);
				error.setText("There is no patterns in database!");
				error.setError("No data");
				tr_download.setVisibility(View.VISIBLE);
				tr_error_txt.setVisibility(View.VISIBLE);
			}
			else if(sizeL<1 && page>1){
				Toast.makeText(getApplicationContext(), "End of Data!", Toast.LENGTH_LONG).show();
				btn_next.setEnabled(false);
				btn_prev.setEnabled(true);
			}
			prog.dismiss();
		}catch(Exception e){
			Log.e("Error setting up patterns",e.toString());
			e.printStackTrace();
		}finally{
//			prog.dismiss();
			database.close();
		}
	}
	
	public void setUpText(TableRow tr,Patterns patt,RadioButton rbtn)
	{
		int id;
		TextView txt;
		Button link = new Button(this, null, android.R.attr.buttonBarButtonStyle);
		final String url = patt.getLink();

		//setting up id
		id = Integer.parseInt(patt.getId());
		tr.setId(id);
		//setting up link
		link.setText("WWW");
		link.setPaintFlags(link.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
		link.layout(5, 5, 5, 5);
		link.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v) {
		        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		        startActivity(i);
		    }
		});
		tr.addView(link);
		//setting up radio buttons
		rbtn.setId(id);
		tr.addView(rbtn);
		/*
		 * setting up rest of data
		 */
		//name
		txt = new TextView(this);
		txt.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
		txt.setGravity(Gravity.LEFT);
		txt.setPadding(5, 3, 5, 3);
		txt.setText(patt.getName());
		tr.addView(txt);
	
		//length
			txt = new TextView(this);
			txt.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
			txt.setGravity(Gravity.LEFT);
			txt.setPadding(5, 3, 5, 3);
			txt.setText(patt.getLength());
			tr.addView(txt);
		//ratio
			txt = new TextView(this);
			txt.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
			txt.setGravity(Gravity.LEFT);
			txt.setPadding(5, 3, 5, 3);
			txt.setText(patt.getRatio());
			tr.addView(txt);
			//Voluime
			txt = new TextView(this);
			txt.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
			txt.setGravity(Gravity.LEFT);
			txt.setPadding(5, 3, 5, 3);
			txt.setText(patt.getVolume());
			tr.addView(txt);

			//opb
			txt = new TextView(this);
			txt.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
			txt.setGravity(Gravity.LEFT);
			txt.setPadding(5, 3, 5, 3);
			txt.setText(patt.getOpb());
			tr.addView(txt);

			//db
			txt = new TextView(this);
			txt.setTextAppearance(getApplicationContext(), R.style.small_body_text_white);
			txt.setGravity(Gravity.LEFT);
			txt.setPadding(5, 3, 5, 3);
			txt.setText(patt.getDba());
			tr.addView(txt);

	}
	
	public boolean getValue()
	{
		String tmp;
		patt = Pattern.compile(NAME_PATTERN);
		tmp = vname.getText().toString();
		if(patt.matcher(tmp).matches()){
			name = tmp;
		}
		else{
			vname.setError(getString(R.string.error_searchName));
			return false;
		}
		
		return true;
	}
	public void setSearchError(){
		TextView tx = new TextView(getApplicationContext());
		TextView tx2 = new TextView(getApplicationContext());
		tx.setText(getString(R.string.search_error_empty));
		tx2.setText(getString(R.string.search_error_reload));
		tx2.setTextColor(Color.BLUE);
		tx2.setClickable(true);
		tx2.setOnClickListener(reload);
		tl.addView(tx);
		tl.addView(tx2);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		setResult(-1);
		finish();
		super.onBackPressed();
	}

}
