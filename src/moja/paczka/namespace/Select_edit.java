package moja.paczka.namespace;

import customTheme.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Select_edit extends Activity {

	private Button btn_edit_Player,btn_create_player;
	private Button btn_edit_lg,btn_create_lg;;
	private Button btn_edit_tour,btn_create_tour;
	private Button btn_edit_match;
	private Button btn_edit_game;
	private Intent mintent;
	private Context mContext;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_edit);
		setup();
	}
	
	public void setup(){
		
		btn_edit_Player = (Button) findViewById(R.id.Select_edit_player);
		btn_edit_lg = (Button) findViewById(R.id.Select_edit_league);
		btn_edit_tour = (Button) findViewById(R.id.Select_edit_tournament);
		btn_edit_match = (Button) findViewById(R.id.Select_edit_match);
		btn_edit_game = (Button) findViewById(R.id.Select_edit_game);
		btn_create_lg = (Button) findViewById(R.id.btn_edit_league_create);
		btn_create_player = (Button) findViewById(R.id.edit_create_player_button);
		btn_create_tour = (Button) findViewById(R.id.btn_Edit_create_tour);
		mContext = getApplicationContext();
		OnClickListener onClkListener = new OnClickListener() {
			
			public void onClick(View v) {
				
				if(v==btn_edit_Player){
					mintent = new Intent(mContext, EditPlayer.class);
					startActivity(mintent);
				}
				else if(v==btn_edit_game){
					
				}
				else if(v==btn_edit_lg){
					mintent = new Intent(mContext, EditLeague.class);
					startActivity(mintent);
				}
				else if(v==btn_edit_match){
					mintent = new Intent(mContext, Edit_Match_game.class);
					startActivity(mintent);
				}
				else if(v==btn_edit_tour){
					mintent = new Intent(mContext, editTour.class);
					startActivity(mintent);
				}
				else if(v==btn_create_player){
					mintent = new Intent(mContext,Create_player.class);
					startActivity(mintent);
				}
				else if(v==btn_create_lg){
					mintent = new Intent(mContext, Create_league.class);
					startActivity(mintent);
				}
				else if(v==btn_create_tour){
					mintent = new Intent(mContext, Create_tournament.class);
					startActivity(mintent);
				}
			}
		};
		btn_create_lg.setOnClickListener(onClkListener);
		btn_create_player.setOnClickListener(onClkListener);
		btn_create_tour.setOnClickListener(onClkListener);
		btn_edit_Player.setOnClickListener(onClkListener);
		btn_edit_game.setOnClickListener(onClkListener);
		btn_edit_lg.setOnClickListener(onClkListener);
		btn_edit_match.setOnClickListener(onClkListener);
		btn_edit_tour.setOnClickListener(onClkListener);
		
	}

}
