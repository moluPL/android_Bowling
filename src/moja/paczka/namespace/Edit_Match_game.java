package moja.paczka.namespace;


import java.util.List;
import customTheme.Utils;
import MyUtility.Leagues_DB;
import MyUtility.Players_Db;
import MyUtility.Tournament_DB;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;


public class Edit_Match_game extends Activity implements OnNavigationListener {

	private Players_Db players_database;
	//private String[] mMenuTitles;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList,mDrawerRight;
    private Spinner spinnerLiga;
    private Spinner spinnerTour;
    private Button btn_showAll,btn_showLG,btn_showTrain,btn_showTour;
    private SpinnerAdapter tmp;
    private OnClickListener btn_onclickList;
	private ActionBarDrawerToggle mDrawerToggle;
	protected CharSequence mDrawerTitle;
	protected CharSequence mTitle;
	protected SpinnerAdapter mSpinnerAdapter;
	protected int playerId,lg_id,tour_id;
	protected int srt;
	private android.app.FragmentManager fgmng;
	protected android.app.FragmentTransaction fragTran;
	private Fragment_1 fg1;

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Utils.setThemeToActivity(this);
		
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_edit_game);
		
		if(android.os.Build.VERSION.SDK_INT < 11) { 
		    requestWindowFeature(Window.FEATURE_NO_TITLE); 
		} 
		/* Setting up id's 
		 */
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (LinearLayout) findViewById(R.id.drawer_LeftMenu);
		mDrawerRight = (LinearLayout) findViewById(R.id.drawer_RightMenu);
		btn_showAll = (Button) findViewById(R.id.Drawer_play_all);
		btn_showLG = (Button) findViewById(R.id.Drawer_play_league);
		btn_showTour = (Button) findViewById(R.id.Drawer_play_tournamnet);
		btn_showTrain = (Button) findViewById(R.id.Drawer_play_train);
		spinnerLiga = (Spinner) findViewById(R.id.Drawer_spinner_league);
		spinnerTour = (Spinner) findViewById(R.id.Drawer_spinner_tour);
		mTitle = mDrawerTitle = getTitle();
		playerId = tour_id = lg_id = srt = -1;
		
		/*Setting up 'spinner' on actionBar 
		 */
		// enable ActionBar app icon to behave as action to toggle nav drawer
		ActionBar acBar = getActionBar(); 
        acBar.setDisplayHomeAsUpEnabled(true);
        //acBar.setHomeButtonEnabled(true);
		acBar.setDisplayShowTitleEnabled(false);
		acBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
		mSpinnerAdapter = loadPlayers();
		acBar.setListNavigationCallbacks(mSpinnerAdapter, this);
		
		/* Setting up menu items
		 */
		btn_showLG.setEnabled(false);
		btn_showTour.setEnabled(false);
		btn_showAll.setEnabled(false);
		btn_showTrain.setEnabled(false);
		tmp = loadLeague();
		if(tmp!=null)
			spinnerLiga.setAdapter(tmp);
		tmp = loadTour();
		if(tmp!=null)
			spinnerTour.setAdapter(tmp);
		OnItemSelectedListener onsl =  new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
    	    {
    	    	if(position > 0){
    	    		if(parentView.getId()==spinnerLiga.getId()){
    	    			String tmp = parentView.getSelectedItem().toString();
        	    		lg_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
        	    		btn_showLG.setEnabled(true);
    	    		}
    	    		else{
    	    			String tmp = parentView.getSelectedItem().toString();
        	    		tour_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
        	    		btn_showTour.setEnabled(true);
    	    		}
    	    		
    	    	}
    	    	else{
    	    		btn_showLG.setEnabled(false);
    	    		btn_showTour.setEnabled(false);
    	    	}
    	    }
    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here	
    	    }
    	};
		spinnerLiga.setOnItemSelectedListener(onsl);
		
		btn_onclickList = new OnClickListener() {
			
			public void onClick(View v) {
				Bundle args = new Bundle();
				if(v==btn_showAll){
					srt=0;
				}else if(v==btn_showTrain){
					srt=4;
				}else if(v==btn_showLG){
					srt=2;
					 args.putInt(getString(R.string.league_id),lg_id);
				}else if(v==btn_showTour)
				{
					srt=3;
					args.putInt(getString(R.string.tournament_id),tour_id);
				}
				if(fragTran!=null && !(fragTran.isEmpty()) )
				{
					fragTran.remove(fg1);
				}
					mDrawerLayout.closeDrawer(mDrawerRight);
					 mDrawerLayout.closeDrawer(mDrawerList);
					 fgmng = getFragmentManager();
					 fragTran = fgmng.beginTransaction();
					 fg1 = new Fragment_1();
				     args.putInt(getString(R.string.player_id),playerId);
				     args.putInt(getString(R.string.Match_info_srtType),srt);
				     fg1.setArguments(args);
					 fragTran.add(R.id.content_frame,fg1);
					 fragTran.commit();
					
				
				
			}
		};
		btn_showAll.setOnClickListener(btn_onclickList);
		btn_showTrain.setOnClickListener(btn_onclickList);
		btn_showLG.setOnClickListener(btn_onclickList);
		btn_showTour.setOnClickListener(btn_onclickList);
		/* 
		 * 	Setting up drawer toggle button
		 */
		// ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
		 mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_menu,  /* nav drawer icon to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description */
	                R.string.drawer_close  /* "close drawer" description */
	                ) {

	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                //getActionBar().setTitle(mTitle);
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                //getActionBar().setTitle(mDrawerTitle);
	            }
	        };

	        // Set the drawer toggle as the DrawerListener
	        mDrawerLayout.setDrawerListener(mDrawerToggle);

	        
		
	}
	
	/**
	 * selecting player from action bar
	 * enabling buttons after selecting
	 */
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		if(itemPosition>0){
			playerId=Integer.parseInt(mSpinnerAdapter.getItem(itemPosition).toString().substring(0,1));
			btn_showAll.setEnabled(true);
			btn_showTrain.setEnabled(true);
			mDrawerLayout.openDrawer(mDrawerList);
		}
		else{
			btn_showLG.setEnabled(false);
			btn_showTour.setEnabled(false);
			btn_showAll.setEnabled(false);
			btn_showTrain.setEnabled(false);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		
		return false;
	}
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(android.view.MenuItem item) {
	        return mDrawerToggle.onOptionsItemSelected(item);
	    }
	    
	
	/* (non-Javadoc)
		 * @see android.app.Activity#onBackPressed()
		 */
		@Override
		public void onBackPressed() {
			super.onBackPressed();
		}

	/**
	 * 
	 * @return
	 */
	private ArrayAdapter<String> loadTour() {
		//Database handler
    	Tournament_DB tour_db = null;
    	ArrayAdapter<String> dataAdapter = null;
    	try{
    		tour_db= new Tournament_DB(getApplicationContext());
    	
    		// Spinner Drop down elements
    		List<String> names = tour_db.getAllNames();
    		if(names.size()>0)
    		{
        	names.add(0, "Select Tournament");
    		}
    		else
    		{
        	names.add(0,getString(R.string.error_no_data));
    		}
    		// 	Creating adapter for spinner
    		dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
    		// 	Drop down layout style - list view with radio button
    		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		
    		// 	attaching data adapter to spinner
    		//spinner_tour.setAdapter(dataAdapter);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		tour_db.close();
    	}
    	return dataAdapter;
	}

	
	
	
	public ArrayAdapter<String> loadPlayers(){
   	 // Spinner Drop down elements
		ArrayAdapter<String> dataAdapter = null;
		try{
			players_database = new Players_Db(getApplicationContext());
			List<String> names =  players_database.getAllNames();
			if(names.size() > 0)
			{
				names.add(0, getString(R.string.one_player_choose));
			}
			else{
				names.add(getString(R.string.error_no_data));
			}
		// 	Creating adapter for spinner
			dataAdapter = new ArrayAdapter<String>(this,
               android.R.layout.simple_spinner_item, names);

       // 	Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			/* attaching data adapter to spinner
       		spiner.setAdapter(dataAdapter);
			 */
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			players_database.close();
		}
		
		return dataAdapter;   
   }
	public ArrayAdapter<String> loadLeague(){
    	//Database handler
		Leagues_DB db = null;
		ArrayAdapter<String> dataAdapter = null;
		try{
			db = new Leagues_DB(getApplicationContext());
    	 // Spinner Drop down elements
			List<String> names = db.getAllNames();
			if(names.size()>0)
			{
				names.add(0,getString(R.string.SelectALeague));
			}
			else
			{
				names.add(0,getString(R.string.error_no_data));
			}
        // Creating adapter for spinner
			dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        // Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        //spinner_league.setAdapter(dataAdapter);
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
        db.close();
		}
		return dataAdapter;
	}
}

