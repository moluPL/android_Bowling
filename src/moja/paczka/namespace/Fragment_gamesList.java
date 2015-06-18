package moja.paczka.namespace;

import java.util.List;

import customTheme.Utils;
import MyUtility.MyGameFromDB;
import MyUtility.SearchData;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;




public class Fragment_gamesList extends Fragment 
{

	private View rootView;
	private LinearLayout lv;
	private int player_id,matchId;
	private TextView txt,tmp;
	private View v;
	private Context con;
	private SearchData search;
	
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Utils.setThemeToActivity(this);
		player_id = matchId = -1;
		
		rootView = inflater.inflate(R.layout.fragment_games_list, container, false);
		con=getActivity();
		lv = ((LinearLayout) rootView.findViewById(R.id.Games_list_linlay));
		search = new SearchData(con);
		player_id = getArguments().getInt(getString(R.string.player_id));
		matchId =  getArguments().getInt(getString(R.string.Match_info_mid));
		
		if(player_id!=-1 && matchId!=-1)
			setupView();
		else{
			txt = new TextView(con);
			txt.setText(getString(R.string.error_no_data));
			lv.addView(txt);
		}
		
		return rootView;
	}



	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}



	private void setupView() 
	{
			 lv.removeAllViews();
			 List<MyGameFromDB> lista =  search.getAllGame(false,player_id,2,matchId);
			 
			for(MyGameFromDB m : lista)
			{
				v = new View(con);
				v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,6));
				v.setBackgroundResource(R.drawable.divider);
				lv.addView(v);
				tmp = new TextView(con);
				tmp.setText(m.toString());
				lv.addView(tmp);
				
			}
			v = new View(con);
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,6));
			v.setBackgroundResource(R.drawable.divider);
			lv.addView(v);
				
				
			
	}
}
