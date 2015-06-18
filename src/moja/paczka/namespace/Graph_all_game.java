/**
 * 
 */
package moja.paczka.namespace;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import MyUtility.Leagues_DB;
import MyUtility.MyGameFromDB;
import MyUtility.MyMatch;
import MyUtility.MyMatchAvgComparator;
import MyUtility.SearchData;
import MyUtility.Tournament_DB;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author karlo
 *
 */
public class Graph_all_game extends Activity {

	protected LinearLayout layout,mainLayout;
	protected List<MyMatch> mecze;
	protected SearchData search;
	private Context context;
	private int league_id=-1,tour_id=-1;
	Boolean charts;
	private int	playeraId;
	private Spinner spinChart;
	private TextView txtSpin;
	private XYSeriesRenderer rendererMAvg,rendererLow,rendererHigh,rendererAvg;
	
	/**
	 * 
	 */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			//Utils.setThemeToActivity(this);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.graph_all_game);
			layout = (LinearLayout) findViewById(R.id.GraphTest);
			mainLayout = (LinearLayout) findViewById(R.id.Graph_all_main_linlayout);
			context = getApplicationContext();
			getData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * add spinner to choose chart
	 */
	public void initSpinner(){
		txtSpin = new TextView(context);
		txtSpin.setText(getString(R.string.AllInfoSpinnerSelectText));
		mainLayout.addView(txtSpin);
		spinChart = new Spinner(context);
		
		mainLayout.addView(spinChart);
    	 // Spinner Drop down elements
        List<String> items = new ArrayList<String>();
        items.add(getString(R.string.AllInfoSpinnerItemPieChartXSpOp));			//1
        items.add(getString(R.string.AllInfoSpinnerItemLineChartAVG));			//2
        items.add(getString(R.string.AllInfoSpinnerItemLineChartAvgMoving));	//3
        items.add(getString(R.string.AllInfoSpinnerItemLineChartbothAVG));		//4
        items.add(getString(R.string.AllInfoSpinnerItemRangeMinMaxChart));		//5
        items.add(getString(R.string.AllInfoSpinnerItemChartHighToAVG));		//6
        if(items.size()>0)
        {
        	items.add(0, getString(R.string.AllInfoSpinnerSelectText));
        	
        }
        else
        {
        	items.add(0,getString(R.string.NoData));
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinChart.setAdapter(dataAdapter);
        
		/*
		 * Selecting some chart
		 */
		spinChart.setOnItemSelectedListener(new OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    	    	if( position == 1 ){
    	    		
    	    		if(tour_id != -1){
    	    			drawXspareOpenPie(playeraId,1,tour_id);
    	    		}
    	    		else if(league_id != -1){
    	    			drawXspareOpenPie(playeraId,0,league_id);
    	    		}
    	    		else{
    	    			drawXspareOpenPie(playeraId);
    	    		}
    	    	}
    	    	else if( position == 2 ){
    	    		
    	    		if(tour_id!=-1){
    	    			drawAllMatchAvg(playeraId,3,tour_id,1);
    	    		}
    	    		else if(league_id!=-1){
    	    			drawAllMatchAvg(playeraId,2,league_id,1);
    	    		}
    	    		else{
    	    			drawAllMatchAvg(playeraId,0,-1,1);
    	    		}
    	    	}
    	    	else if( position == 3 ){
    	    		
    	    		if(tour_id!=-1){
    	    			drawAllMatchAvg(playeraId,3,tour_id,2);
    	    		}
    	    		else if(league_id!=-1){
    	    			drawAllMatchAvg(playeraId,2,league_id,2);
    	    		}
    	    		else{
    	    			drawAllMatchAvg(playeraId,0,-1,2);
    	    		}
    	    	}
    	    	else if( position == 4 ){
    	    		
    	    		if(tour_id!=-1){
    	    			drawAllMatchAvg(playeraId,3,tour_id,3);
    	    		}
    	    		else if(league_id!=-1){
    	    			drawAllMatchAvg(playeraId,2,league_id,3);
    	    		}
    	    		else{
    	    			drawAllMatchAvg(playeraId,0,-1,3);
    	    		}
    	    	}
    	    	else if( position == 5 )
    	    	{
    	    		if(tour_id!=-1){
    	    			drawMinMaxRangeChart(playeraId,3,tour_id);
    	    		}
    	    		else if(league_id!=-1){
    	    			drawMinMaxRangeChart(playeraId,2,league_id);
    	    		}
    	    		else{
    	    			drawMinMaxRangeChart(playeraId,0);
    	    		}
    	    	}
    	    	else if( position == 6 ){
    	    		if(tour_id!=-1){
    	    			drawBarsAvgHighLow(playeraId,3,tour_id);
    	    		}
    	    		else if(league_id!=-1){
    	    			drawBarsAvgHighLow(playeraId,2,league_id);
    	    		}
    	    		else{
    	    			drawBarsAvgHighLow(playeraId,0);
    	    		}
    	    	}
    	    }

    	    public void onNothingSelected(AdapterView<?> parentView) {
    	        // your code here
    	    	
    	    }

    	});
	}
	/**
	 * Display player basic infos and stats info in txt mode<br>
	 * @param INT[] PARAMS<br>
	 * params[0]= player id<br>
	 * params[1] = sort type<br>
	 * 	-0=all info<br>
	 *  -1=league<br>
	 *  -2=tournament<br>
	 *  params[2]=tour or legaue id<br>
	 *  <br>
	 */
	public void addInfo(int... params){
		try{
			int playerId = params[0];
			int srt=0,id=-1;
			if(params.length>2){
				srt = params[1];
				id = params[2];
			}
			
			Spinner spinner_league,spinner_tour = null;
			int opens=0;
			int strikes=0;
			int spares=0;
			int total=0;
			int frames=0;
			int totalGames=0;
			double percent=0;
			List<MyGameFromDB> gameList = null;
			List<MyMatch> mecze;
			TextView txt;
			search = new SearchData(context);
			txt = new TextView(context);
			
			List<String> lista=search.getPlayerInfo(playerId);//getting player info name,lastname,email
			if(lista.size()>0){
				layout.removeAllViews();		//removes all views it's necessary to add your own
				txt.setText(getString(R.string.name2)+lista.get(0));		//set name
				layout.addView(txt);
				txt=new TextView(context);
				txt.setText(getString(R.string.surname2)+lista.get(1));	//set lastname
				layout.addView(txt);
				txt=new TextView(context);
				txt.setText(getString(R.string.email2)+lista.get(2));	//set lastname
				layout.addView(txt);
			}
			//listLayout.setLayoutParams(new LayoutParams(1080, 1920));
			//layout.addView(listLayout);
		/*		 * Player stats			*/
			if(srt==1){
				opens = search.getNmbrOpen(playerId,srt,id);
				strikes = search.getNmbrStrike(playerId,srt,id);
				spares = search.getNmbrSpare(playerId,srt,id);
				frames = search.getNmbrFrame(playerId,srt,id);
				totalGames = search.getNmbrGamesPlayer(playerId,srt,id);
				mecze = search.getAllMatchPlayer(playerId,2,id);
			}
			else if(srt==2){
				opens = search.getNmbrOpen(playerId,srt,id);
				strikes = search.getNmbrStrike(playerId,srt,id);
				spares = search.getNmbrSpare(playerId,srt,id);
				frames = search.getNmbrFrame(playerId,srt,id);
				totalGames = search.getNmbrGamesPlayer(playerId,srt,id);
				mecze = search.getAllMatchPlayer(playerId,3,id);
			}
			else{
				opens = search.getNmbrOpen(playerId);
				strikes = search.getNmbrStrike(playerId);
				spares = search.getNmbrSpare(playerId);
				frames = search.getNmbrFrame(playerId);
				totalGames = search.getNmbrGamesPlayer(playerId);
				mecze = search.getAllMatchPlayer(playerId,1);
			}
			
			if(mecze.size()>0){
				//number of match
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoNoMatchPlay)+mecze.size());
				layout.addView(txt);
				/*
				 * Total Pinfall
				 */
				for(MyMatch m : mecze){
					total += Integer.parseInt(m.getMyMatch_TOTAL());
				}
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoTotalPinfall)+total);
				layout.addView(txt);
				//total games
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoNoGamePlay)+totalGames);
				layout.addView(txt);
				//total frames
				txt = new TextView(context);
				txt.setText("frames : "+frames);
				layout.addView(txt);
				//total strikes
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoTotalStrikes)+strikes);
				layout.addView(txt);
				//total percent % of strikes
				txt = new TextView(context);
				percent=((double)strikes*100)/(double)frames;
				txt.setText(getString(R.string.AllInfoPercentStrikes)+String.format("%.2f", percent)+"%");
				layout.addView(txt);
				//total spares
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoTotalSpares)+spares);
				layout.addView(txt);
				//total percent % of spares
				txt = new TextView(context);
				percent=((double)spares*100)/(double)frames;
				txt.setText(getString(R.string.AllInfoPercentSpares)+String.format("%.2f", percent)+"%");
				layout.addView(txt);
				//total opens
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoTotalOpens)+opens);
				layout.addView(txt);
				//total percent % of opens
				txt = new TextView(context);
				percent=((double)opens*100)/(double)frames;
				txt.setText(getString(R.string.AllInfoPercentOpens)+String.format("%.2f", percent)+"%");
				layout.addView(txt);
				/*
				 * high series
				 */
				if(srt==1){
					gameList=search.getAllGame(false,playerId,6,id);
				}else if(srt==2){
					gameList=search.getAllGame(false,playerId,5,id);
				}else{
					gameList=search.getAllGame(false,playerId,1);
				}
				if(gameList.size()>0){
					int max=0,tmp=0,jed = 0,mId;
					int j,i;
					for(i = 0 ; i <= gameList.size()-3 ; i++){
						tmp=gameList.get(i).getGAME_RESULT();
						mId=gameList.get(i).getGAME_MATCH_ID();
						for(j = 1 ; j < 3 ; j++)
						{
							if(gameList.get(i+j).getGAME_MATCH_ID()!=mId)
								break;
							tmp += gameList.get(i+j).getGAME_RESULT();
						}
						if(j==3){
							if(tmp>max){
								max=tmp;
								jed=i;
							}
						}
					}
				//	high series score
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoHighSeriesR)+max);
					layout.addView(txt);
				//	high series type
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoHighseriesTypeR)+gameList.get(jed).getGAME_TYPE());
					layout.addView(txt);
				//	high series date
					MyMatch oneMat = search.getMatchFromId(gameList.get(jed).getGAME_MATCH_ID());
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoHighseriesDateR)+oneMat.getMyMatch_DATE());
					layout.addView(txt);
				//	average in high series
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoHighAvgR)+(max/3));
					layout.addView(txt);
				}else{
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoHighSeriesERRor));
					layout.addView(txt);
				}
				/*
				 * high match
				 */
				//high match
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoHighSeries)+mecze.get(0).getMyMatch_TOTAL());
				layout.addView(txt);
				//number of game in high match
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoHighSeriesNoGame)+mecze.get(0).getMyMatch_NMBR_GAMES());
				layout.addView(txt);
				//average of high match
				txt = new TextView(context);
				int tmp1=Integer.parseInt(mecze.get(0).getMyMatch_TOTAL());
				int tmp2=Integer.parseInt(mecze.get(0).getMyMatch_NMBR_GAMES());
				txt.setText(getString(R.string.AllInfoHighSeriesaverage)+(tmp1/tmp2));
				layout.addView(txt);
				//type of high  match
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoHighseriesType)+mecze.get(0).getMyMatch_TYPE());
				layout.addView(txt);
				//date of high match
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoHighseriesDate)+mecze.get(0).getMyMatch_DATE());
				layout.addView(txt);
				/*
				 * highest avg
				 */
				Collections.sort(mecze, new MyMatchAvgComparator());
				//Collections.reverse(mecze);
				
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoHighAvg)+mecze.get(mecze.size()-1).getMyMatch_AVG());
				layout.addView(txt);
				//date of high avg
				txt = new TextView(context);
				txt.setText(getString(R.string.AllInfoHighAvgDate)+mecze.get(mecze.size()-1).getMyMatch_DATE());
				layout.addView(txt);
				/*
				 * league Spinner
				 */
				if(srt==0){
					spinner_league = new Spinner(context);
					//Database handler
		        	Leagues_DB db = new Leagues_DB(getApplicationContext());
		        	 // Spinner Drop down elements
		            List<String> names = db.getAllNames();
		            if(names.size()>0)
		            {
		            	names.add(0, "Select a League");
		            	//button_league.setError(null);
		            }
		            else
		            {
		            	names.add(0,getString(R.string.AllInfoLeagueError));
		            	//button_league.setError(getString(R.string.error_no_data));
		            	//button_league.setEnabled(false);
		            	
		            }
		     
		            // Creating adapter for spinner
		            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		                    android.R.layout.simple_spinner_item, names);
		     
		            // Drop down layout style - list view with radio button
		            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		     
		            // attaching data adapter to spinner
		            spinner_league.setAdapter(dataAdapter);
		            db.close();
					layout.addView(spinner_league);
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoLeagueAvg));
					txt.setId(R.id.AllInfoTxtViewLG);
					layout.addView(txt);
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoLeagueNoGames));
					txt.setId(R.id.AllInfoTxtViewLG2);
					layout.addView(txt);
					
					
					spinner_league.setOnItemSelectedListener(new OnItemSelectedListener() {
			    	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	    	if(position > 0){
			    	    		String tmp = parentView.getSelectedItem().toString();
			    	    		league_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
			    	    		
			    	    		loadAVG(playeraId,league_id,0);
			    	    		//button_league.setEnabled(true);
			    	    	}
			    	    	else
			    	    		;
			    	    		//button_league.setEnabled(false);
			    	    }
			    	    public void onNothingSelected(AdapterView<?> parentView) {
			    	        // your code here	
			    	    }
			    	});
					
					/*
					 * tour spinner 
					 */
					spinner_tour=new Spinner(context);
					//Database handler
	            	Tournament_DB tour_db = new Tournament_DB(getApplicationContext());
	            	
	            	 // Spinner Drop down elements
	                List<String> names1 = tour_db.getAllNames();
	                if(names1.size()>0)
	                {
	                	names1.add(0, "Select Tournament");
	                	//button_tournament.setError(null);
	                }
	                else
	                {
	                	names1.add(0,getString(R.string.error_no_data));
	                	//button_tournament.setError(getString(R.string.error_no_data));
	                	//button_tournament.setEnabled(false);
	                	
	                }
	         
	                // Creating adapter for spinner
	                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
	                        android.R.layout.simple_spinner_item, names1);
	         
	                // Drop down layout style - list view with radio button
	                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	         
	                // attaching data adapter to spinner
	                spinner_tour.setAdapter(dataAdapter1);
	                tour_db.close();
	                //txtViews
	                layout.addView(spinner_tour);
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoTourAvg));
					txt.setId(R.id.AllInfoTxtViewtour);
					layout.addView(txt);
					txt = new TextView(context);
					txt.setText(getString(R.string.AllInfoTourNoGames));
					txt.setId(R.id.AllInfoTxtViewtour2);
					layout.addView(txt);
	                //listener
	                spinner_tour.setOnItemSelectedListener(new OnItemSelectedListener() {
	            	    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	            	    	if(position > 0){
	            	    		String tmp = parentView.getSelectedItem().toString();
	            	    		tour_id = Integer.parseInt(tmp.substring(0,tmp.indexOf(" ") ) );
	            	    		loadAVG(playeraId, tour_id, 1);
	            	    	}
	            	    	else
	            	    		;
	            	    		//button_tournament.setEnabled(false);
	            	    }
	            	    public void onNothingSelected(AdapterView<?> parentView) {
	            	        // your code here	
	            	    }
	            	});
	                
				}
			}else	// begin in-> if(mecze.size>0) 
			{/*set error text*/
				txt = new TextView(context);
				txt.setText(getString(R.string.NoData));
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * load league average into txtView
	 */
	public void loadAVG(int player_id , int lg_tour_id , int type){
		search=new SearchData(context);
		double avg=0;
		int total=0;
		int gameNmbr=0;
		List<MyMatch> lista = null;
		TextView txt2 = null;
		TextView txt1 = null;
		if(type==0)
		{
			lista = search.getAllMatchPlayer(player_id,2,lg_tour_id);
			txt2 = (TextView) findViewById(R.id.AllInfoTxtViewLG);
			txt1 = (TextView) findViewById(R.id.AllInfoTxtViewLG2);
		}
		else if(type==1)
		{
				lista = search.getAllMatchPlayer(player_id,3,lg_tour_id);
				txt2 = (TextView) findViewById(R.id.AllInfoTxtViewtour);
				txt1 = (TextView) findViewById(R.id.AllInfoTxtViewtour2);
		}
		if(lista.size()>0)
		{
				for(MyMatch m:lista){
					total+=Integer.parseInt(m.getMyMatch_TOTAL());
					gameNmbr+=Integer.parseInt(m.getMyMatch_NMBR_GAMES());
				}
				avg=(double)total/gameNmbr;
				if(type==0)
				{
					txt2.setText(getString(R.string.AllInfoLeagueAvg)+String.format("%.2f", avg));
					txt1.setText(getString(R.string.AllInfoLeagueNoGames)+gameNmbr);
				}else
				{
					txt2.setText(getString(R.string.AllInfoTourAvg)+String.format("%.2f", avg));
					txt1.setText(getString(R.string.AllInfoTourNoGames)+gameNmbr);
				}
			}
			else
			{
				if(type==0){
					txt2.setText(getString(R.string.NoData));
					txt1.setText(getString(R.string.NoData));
				}
				else
					txt2.setText(getString(R.string.NoData));
					txt1.setText(getString(R.string.NoData));
			}
	}
	/**
	 * Get Data from intent Extras
	 */
	public int getData(){
    	/*** getting intent and extras from this intent*/
		//int playerId;
		Intent thisIntent = getIntent();
		Bundle extras = thisIntent.getExtras();
		/*** if intent has extras get it and setup variables */
		if(thisIntent.hasExtra(getString(R.string.player_id))){
			playeraId = extras.getInt(getString(R.string.player_id));
		}
		else{
			Log.e(getString(R.string.Debug_error)+context.toString(), getString(R.string.NoData)+getString(R.string.player_id));
			super.finish();
		}
		if(thisIntent.hasExtra(getString(R.string.charts))){
			charts = extras.getBoolean(getString(R.string.charts));
		}
		else{
			charts=false;
			Log.e(getString(R.string.Debug_error)+context.toString(),  getString(R.string.NoData)+getString(R.string.charts));
		}
		if(thisIntent.hasExtra(getString(R.string.league_id))){
			league_id = extras.getInt(getString(R.string.league_id));
		}
		else{
			league_id=-1;
			//Log.e(getString(R.string.Debug_error)+context.toString(), "-> there is no player ID");
		}
		if(thisIntent.hasExtra(getString(R.string.tournament_id))){
			tour_id = extras.getInt(getString(R.string.tournament_id));
		}
		else{
			tour_id=-1;
			//Log.e(getString(R.string.Debug_error)+context.toString(), getString(R.string.NoData)+getString(R.string.tournament_id));
		}
		if(charts)
		{
			initSpinner();
		}
		else
		{
			if(tour_id!=-1)
				addInfo(playeraId,2,tour_id);
			else if(league_id!=-1)
				addInfo(playeraId,1,league_id);
			else
				addInfo(playeraId);
		}
		
		
		return playeraId;
	}
		/**
		 * This method draw a pie chart with percenteg of strikes , spares , opens <br>
		 * @param int[] params <br>
		 * 1) params[0] = playerId <br>
		 * 2) params[1] = type <br>
		 * 		- 0 = league<br>
		 * 		- 1 = tournament<br>
		 * 3) params[2] = _id of league or tour
		 */
	public void drawXspareOpenPie(int... params){
		search = new SearchData(context);
		int playerId=params[0];
		int type,id,strikes,spares,opens,total;
		double perX,perSp,perOp;
		if(params.length>2)
		{
			type=params[1]+1;
			id=params[2];
			strikes = search.getNmbrStrike(playerId,type,id);	//get numbers of strikes from DB
			spares = search.getNmbrSpare(playerId,type,id);		//get numbers of spares from DB
			opens = search.getNmbrOpen(playerId,type,id);		//get numbers of opens
		}else
		{
		strikes = search.getNmbrStrike(playerId);	//get numbers of strikes from DB
		spares = search.getNmbrSpare(playerId);		//get numbers of spares from DB
		opens = search.getNmbrOpen(playerId);		//get numbers of opens
		}
		total=strikes+spares+opens;
		perX = (double)strikes*100/(double)total;
		perSp= (double)spares*100/(double)total;
		perOp= (double)opens*100/(double)total;
		if(strikes>0 || spares>0 || opens>0)
		{
			CategorySeries series = new CategorySeries(getString(R.string.PieChartTitleSSO));
			series.add(getString(R.string.strikes)+String.format("%.2f", perX)+"%",strikes);
			series.add(getString(R.string.spares)+String.format("%.2f", perSp)+"%",spares);
			series.add(getString(R.string.opens)+String.format("%.2f", perOp)+"%",opens);
			DefaultRenderer renderer = new DefaultRenderer();
			SimpleSeriesRenderer simpleRenderer = new SimpleSeriesRenderer();
			simpleRenderer.setColor(Color.RED);
			renderer.addSeriesRenderer(simpleRenderer);
			simpleRenderer = new SimpleSeriesRenderer();
			simpleRenderer.setColor(Color.BLUE);
			renderer.addSeriesRenderer(simpleRenderer);
			simpleRenderer = new SimpleSeriesRenderer();
			simpleRenderer.setColor(Color.DKGRAY);
			renderer.addSeriesRenderer(simpleRenderer);
			renderer.isInScroll();
			renderer.setZoomButtonsVisible(true);   //set zoom button in Graph
			renderer.setApplyBackgroundColor(true);
			renderer.setBackgroundColor(Color.BLACK); //set background color
        //	renderer.setChartTitle(getString(R.string.PieChartTitleSSO));
			renderer.setChartTitleTextSize((float) 30);
			renderer.setShowLabels(true);  
			renderer.setLabelsTextSize(20);
			renderer.setLegendTextSize(25);
			renderer.setDisplayValues(true);
			renderer.setAntialiasing(true);
        // 	renderer.setFitLegend(true);
			renderer.setMargins(new int[]{50,50,50,50});
			GraphicalView view = ChartFactory.getPieChartView(context, series, renderer);
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 550));
			layout.removeAllViews();
			layout.addView(view);
		}else{
			TextView txt = new TextView(context);
			txt.setText(getString(R.string.NoData));
			layout.removeAllViews();
			layout.addView(txt);
		}
	}
	/**
	 * 
	 * @param int[] params <br>
	 * 1) params[0]= playerID <br>
	 * 2) params[1]= type of search <br>
	 *  	0-sort by date and next by id if have the same date <br>
	 *  	1-sort by  result asc <br>
	 *  	2-sort by specified legue Id -> params[2]=id <br>
	 *  	3-sort by specified tour Id <br>
	 *  	4-sort by type=training <br> 
	 * 3) params[2]=id for tour or legue <br>
	 * 4) params[3] what line should show<br>
	 * 		-0 = error <br>
	 * 		-1 = show average in matches<br>
	 * 		-2 = show moving average during season
	 * 		-3 = show both lines charts
	 */
	public void drawAllMatchAvg(int...params){
		try{
			String title =getString(R.string.AllInfoHighSeriesaverage);
			int playerID=0,srtType=0,anId = 0;
			int both=0;
			XYSeries series,seriesAVG;
			search = new SearchData(context);
			playerID=params[0];
			if(params.length>1)
			{
				 srtType=params[1];
			}
			if(params.length>2 && srtType!=0)
				anId = params[2];
			if(params.length>3)
				both=params[3];
			if(params.length>2 && srtType!=0)
			{
				
				mecze=search.getAllMatchPlayer(playerID,srtType,anId);
				
			}
			else
			{
				mecze=search.getAllMatchPlayer(playerID,srtType);
			}
			
			
			if(mecze.size()>0)
			{
				//Now we create XYseries
				series = new XYSeries(title);
				seriesAVG = new XYSeries(getString(R.string.AllInfoHighSeriesaverage2));
				// Now we create the renderer
				XYSeriesRenderer renderer = new XYSeriesRenderer();
				XYSeriesRenderer renderer2 = new XYSeriesRenderer();
				XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
				XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				int x =0;
				int total=0;
				int noGames=0;
				double temp=0;
				for(MyMatch s:mecze)
				{
					
					int y =Integer.parseInt(s.getMyMatch_AVG());
					series.add(x,y);// x,y
					
					total+=Integer.parseInt(s.getMyMatch_TOTAL());
					noGames+=Integer.parseInt(s.getMyMatch_NMBR_GAMES());
					temp=(double)total/(double)noGames;
					seriesAVG.add(x, temp);
					if(x%2==0){
						seriesAVG.addAnnotation(String.format("%.2f",(temp)), x,temp-30 );
						series.addAnnotation(s.getMyMatch_AVG(), x, y+20);
					}
					else{
						seriesAVG.addAnnotation(String.format("%.2f",(temp)), x,temp+30 );
						series.addAnnotation(s.getMyMatch_AVG(), x, y-20);
					}
					mRenderer.addXTextLabel(x, s.getMyMatch_DATE());
					x++;
				}
				if(both==2 || both==3){
					renderer2.setLineWidth(2);
					renderer2.setColor(Color.BLUE);
					renderer.setAnnotationsColor(Color.BLUE);
					renderer2.setPointStyle(PointStyle.X);
					mRenderer.addSeriesRenderer(renderer2);
					dataset.addSeries(seriesAVG);
				}
				if(both==1 || both==3 || both==0){
					renderer.setAnnotationsColor(Color.RED);
					renderer.setLineWidth(2);
					renderer.setColor(Color.RED);
//				 	Include low and max value
					renderer.setDisplayBoundingPoints(true);
					// we add point markers
					renderer.setPointStyle(PointStyle.CIRCLE);
					renderer.setFillPoints(true);
					renderer.setPointStrokeWidth(2);
					mRenderer.addSeriesRenderer(renderer);
					dataset.addSeries(series);
				}
				
				mRenderer.setXLabelsAngle((float) 270.0);
				mRenderer.setXLabels(RESULT_OK);
				// We want to avoid black border
				mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00)); // transparent margins
				// 	Disable Pan on two axis
				mRenderer.setPanEnabled(false, false);
				mRenderer.setZoomEnabled(true, true);
				mRenderer.setZoomButtonsVisible(true);
				//mRenderer.setAntialiasing(true);
				mRenderer.setYLabelsAlign(Align.RIGHT);
				mRenderer.setXLabelsAlign(Align.RIGHT);
				mRenderer.setYTitle(getString(R.string.AllInfoScoreAVG));
				//mRenderer.setXTitle(getString(R.string.AllInfoMatches));
				//mRenderer.setDisplayChartValues(true);
				mRenderer.setYAxisMax(400);
				mRenderer.setYAxisMin(0);
				mRenderer.setShowGrid(true); // we show the grid
				mRenderer.setInScroll(true);
				mRenderer.setClickEnabled(true);
				mRenderer.setMargins(new int[]{50,50,50,50});
				mRenderer.setBarSpacing(1.5);
			
				GraphicalView chartView = ChartFactory.getLineChartView(getApplicationContext(), dataset, mRenderer); 
				chartView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,500 ));
				layout.removeAllViews();
				layout.addView(chartView);
			}
			else{
				TextView txt = new TextView(context);
				txt.setText(getString(R.string.NoData));
				layout.removeAllViews();
				layout.addView(txt);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * draw range chart from max an min from one match <bR> 
	 * @param int[] params <br>
	 * params[0] = player id<br>
	 * params[1] = srttype <br>
	 * 		0-sort by date and next by id if have the same date <br>
	 *  	1-sort by  result asc <br>
	 *  	2-sort by specified legue Id -> params[2]=id <br>
	 *  	3-sort by specified tour Id <br>
	 *  	4-sort by type=training <br>
	 * params[2] =  id <br>
	 * 
	 */
	public void drawMinMaxRangeChart(int... params)
	{
		try{
			//String title =getString(R.string.AllInfoSpinnerItemRangeMinMaxChart);
			int playerID=0,srtType=0,anId = 0;
			
			RangeCategorySeries series;
			search = new SearchData(context);
			playerID=params[0];
			if(params.length>1)
			{
				 srtType=params[1];
			}
			if(params.length>2 && srtType!=0)
				anId = params[2];
			
			if(params.length>2 && srtType!=0)
			{
				
				mecze=search.getAllMatchPlayer(playerID,srtType,anId);
				
			}
			else
			{
				mecze=search.getAllMatchPlayer(playerID,srtType);
			}
			
			
			if(mecze.size()>0)
			{
				//Now we create RangeSeries
				series = new RangeCategorySeries(getString(R.string.AllInfoSpinnerItemRangeMinMaxChart));
				// Now we create the renderer
				XYSeriesRenderer renderer = new XYSeriesRenderer();
				XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
				XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				double how = mecze.size();
				mRenderer.setXAxisMax(how+(how/5));
				double x = 0;
				int match_id=-1;
				
				for(MyMatch m : mecze)
				{
					
					match_id = Integer.parseInt( m.getMyMatch_ID() );
					List<MyGameFromDB> games  = search.getAllGame(false,playerID,2,match_id);
					series.add(games.get(0).getGAME_RESULT(),games.get(games.size()-1).getGAME_RESULT());// min,max
					mRenderer.addXTextLabel(x, m.getMyMatch_DATE());
					x++;
				}
				dataset.addSeries(series.toXYSeries());
				renderer.setDisplayChartValues(true);
				mRenderer.setShowGrid(true);
				
				mRenderer.setBarSpacing(0.1); // adding spacing between the line or stacks
				mRenderer.setXLabelsAngle((float) 270.0);
				mRenderer.setXLabelsAlign(Align.RIGHT);
				mRenderer.setYLabelsAlign(Align.RIGHT);
				mRenderer.setLabelsColor(Color.RED);
				mRenderer.setXLabels(RESULT_OK);
				mRenderer.addSeriesRenderer(renderer);
				mRenderer.setYAxisMax(400.0);
				mRenderer.setYAxisMin(0.0);
				renderer.setChartValuesTextSize(10);
				mRenderer.setPanEnabled(false, false);
				mRenderer.setZoomEnabled(true, true);
				mRenderer.setZoomButtonsVisible(true);
				mRenderer.setMargins(new int[]{50,50,50,50});
				mRenderer.setBarSpacing(1.5);
				mRenderer.setInScroll(true);
				//renderer.setChartValuesFormat(new DecimalFormat("#.##"));
				renderer.setColor(Color.WHITE);
				
				GraphicalView chartView = ChartFactory.getRangeBarChartView(context, dataset, mRenderer, BarChart.Type.DEFAULT);
				chartView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,500 ));
				layout.removeAllViews();
				layout.addView(chartView);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			finish();
		}
	}
	/**
	 * display bars with high game average and low
	 * @param params int[]
	 * params[0] = player id<br>
	 * params[1] = srttype <br>
	 * 		0-sort by date and next by id if have the same date <br>
	 *  	1-sort by  result asc <br>
	 *  	2-sort by specified legue Id -> params[2]=id <br>
	 *  	3-sort by specified tour Id <br>
	 *  	4-sort by type=training <br>
	 * params[2] =  id <br>
	 */
	public void drawBarsAvgHighLow(int... params){
		try{
			//String title =getString(R.string.AllInfoSpinnerItemChartHighToAVG);
			int playerID=0,srtType=0,anId = 0;
			
			XYSeries seriesAvg;
			XYSeries seriesMAvg;
			XYSeries seriesLow;
			XYSeries seriesHigh;
			search = new SearchData(context);
			playerID=params[0];
			if(params.length>1)
			{
				 srtType=params[1];
			}
			if(params.length>2 && srtType!=0)
				anId = params[2];
			
			if(params.length>2 && srtType!=0)
			{
				
				mecze=search.getAllMatchPlayer(playerID,srtType,anId);
				
			}
			else
			{
				mecze=search.getAllMatchPlayer(playerID,srtType);
			}
			
			
			if(mecze.size()>0)
			{
				//Now we create RangeSeries
				seriesAvg = new XYSeries(getString(R.string.AllInfoHighSeriesaverage));
				seriesMAvg = new XYSeries(getString(R.string.AllInfoHighSeriesaverage2));
				seriesHigh = new XYSeries(getString(R.string.AllInfohighGame));
				seriesLow = new XYSeries(getString(R.string.AllInfoLowGame));
				// Now we create the renderer
				rendererAvg = new XYSeriesRenderer();
				rendererAvg.setColor(Color.rgb(0, 188, 255));
				rendererAvg.setDisplayChartValues(true); 
				rendererAvg.setChartValuesTextSize(10);

				
				rendererMAvg = new XYSeriesRenderer();
				rendererMAvg.setColor(Color.rgb(162,0,255));
				rendererMAvg.setDisplayChartValues(true); 
				rendererMAvg.setChartValuesTextSize(10);

				rendererLow = new XYSeriesRenderer();
				rendererLow.setColor(Color.rgb(43,255,0));
				rendererLow.setDisplayChartValues(true); 
				rendererLow.setChartValuesTextSize(10);
				
				rendererHigh = new XYSeriesRenderer();
				rendererHigh.setColor(Color.rgb(255,154,0));
				rendererHigh.setDisplayChartValues(true); 
				rendererHigh.setChartValuesTextSize(10);
				// Now we create the renderer
				XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
				XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				
				int match_id=-1;
				int total=0;
				int nogames=0;
				int temp=0;
				double how = mecze.size();
				mRenderer.setXAxisMax(how+(how/10));
				double x = 0+how/10;
				
				for(MyMatch m : mecze)
				{
					
					match_id = Integer.parseInt( m.getMyMatch_ID() );
					total += Integer.parseInt(m.getMyMatch_TOTAL());
					nogames += Integer.parseInt(m.getMyMatch_NMBR_GAMES());
					temp=total/nogames;
					seriesMAvg.add(x, temp);
					seriesAvg.add(x, Double.parseDouble(m.getMyMatch_AVG()));
					List<MyGameFromDB> games  = search.getAllGame(false,playerID,2,match_id);
					seriesLow.add(x, games.get(0).getGAME_RESULT());
					seriesHigh.add(x, games.get(games.size()-1).getGAME_RESULT());
					
					
					mRenderer.addXTextLabel(x, m.getMyMatch_DATE());
					x++;
				}
				
				dataset.addSeries(seriesAvg);
				dataset.addSeries(seriesMAvg);
				dataset.addSeries(seriesLow);
				dataset.addSeries(seriesHigh);
				
				charts=true;
				rendererHigh.setDisplayChartValues(true);
				
				rendererLow.setDisplayChartValues(true);
				
				rendererMAvg.setDisplayChartValues(true);
				//rendererMAvg.setChartValuesSpacing((float)1.5);
				rendererMAvg.setDisplayChartValuesDistance(20);
				//rendererMAvg.setDisplayBoundingPoints(false);
				
				rendererAvg.setDisplayChartValues(true);
				rendererAvg.setDisplayChartValuesDistance(20);
				//rendererAvg.setDisplayBoundingPoints(false);
				//renderer.setColor(Color.WHITE);
				//renderer.setChartValuesTextSize(10);
				//renderer.setChartValuesFormat(new DecimalFormat("#.##"));
				
				
				/*add renderer to mrenderer*/
				mRenderer.addSeriesRenderer(rendererAvg);
				mRenderer.addSeriesRenderer(rendererMAvg);
				mRenderer.addSeriesRenderer(rendererHigh);
				mRenderer.addSeriesRenderer(rendererLow);
				
				/*
				 * setting up mrenderer
				 */
				//mRenderer.setFitLegend(true);
				mRenderer.setInScroll(true);
				mRenderer.setShowGrid(true); // we show the grid
				//mRenderer.setGridColor(Color.WHITE);
				mRenderer.setMargins(new int[]{50,150,50,150});
				mRenderer.setXLabels(RESULT_OK);
				mRenderer.setYTitle(getString(R.string.AllInfoScore));
				mRenderer.setXLabelsPadding((float)2.0);
				mRenderer.setBarSpacing(1.5); // adding spacing between the line or stacks
				//mRenderer.setBarWidth(2);
				mRenderer.setXLabelsAngle((float) 270.0);
				mRenderer.setXLabelsAlign(Align.RIGHT);
				mRenderer.setYLabelsAlign(Align.RIGHT);
				mRenderer.setLabelsColor(Color.RED);
				mRenderer.setYAxisMax(400.0);
				mRenderer.setYAxisMin(0.0);
				mRenderer.setXAxisMin(0.0);
				//mRenderer.setXAxisMax(400.0);
				mRenderer.setPanEnabled(false, false);
				mRenderer.setZoomEnabled(true, true);
				mRenderer.setZoomButtonsVisible(true);

				
				GraphicalView chartView = ChartFactory.getBarChartView(context,
						dataset, mRenderer,BarChart.Type.DEFAULT);
				
				chartView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,600 ));
				layout.removeAllViews();
				layout.addView(chartView);
				Button btn = new Button(context);
				btn.setOnClickListener(new Button.OnClickListener() 
				{
		   		 	public void onClick(View v){
		   		 		
		   		 			charts= !charts;
		   		 			rendererHigh.setDisplayChartValues(charts);
		   		 			rendererLow.setDisplayChartValues(charts);
		   		 			rendererMAvg.setDisplayChartValues(charts);
		   		 			rendererAvg.setDisplayChartValues(charts);
		   		 		}
		   		 	}
		   		 	);
				layout.addView(btn);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			finish();
		}
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	/**
	 * 
	 */
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	/**
	 * 
	 */
	public Graph_all_game() {
	}

}
