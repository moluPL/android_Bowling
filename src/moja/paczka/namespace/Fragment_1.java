package moja.paczka.namespace;


import java.util.List;
import MyUtility.Match_DB;
import MyUtility.MyMatch;
import MyUtility.SearchData;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_1 extends Fragment {

	private View rootView;
	private LinearLayout lv;
	private int player_id,tourId,lgId;
	private TextView txt;
	private View v;
	private Button btn_edit;
	private Button delete;
	private Context con;
	private int srtType;
	public Fragment_1(){
		
	}
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		 //Utils.setThemeToActivity(this);
		player_id = -1;
		srtType = -1;
		tourId = -1;
		lgId =  -1;
		rootView = inflater.inflate(R.layout.fragment_select_edit, container, false);
		player_id = getArguments().getInt(getString(R.string.player_id));
		srtType =  getArguments().getInt(getString(R.string.Match_info_srtType));
		if(getArguments().containsKey(getString(R.string.league_id)))
			lgId = getArguments().getInt(getString(R.string.league_id));
		if(getArguments().containsKey(getString(R.string.tournament_id)))
			tourId = getArguments().getInt(getString(R.string.tournament_id));
		con=getActivity();
		if(player_id!=-1 && srtType!=-1)
			setupView();
		else{
			txt = new TextView(con);
			txt.setText(getString(R.string.error_no_data));
			((LinearLayout) rootView.findViewById(R.id.Drawer_Center_List_Match)).addView(txt);
		}
		return rootView;
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		setupView();
	}
	/**
	 * 
	 * @return
	 */
	public boolean setupView(){
		if(rootView==null){
			return false;
		}
		lv =  (LinearLayout) rootView.findViewById(R.id.Drawer_Center_List_Match);
		lv.removeAllViews();
		SearchData search = new SearchData(con);
		List<MyMatch> mecze = null;
		if(srtType==3 || srtType==2)
		{
			if(lgId != -1)
				mecze=search.getAllMatchPlayer(player_id,srtType,lgId);
			else if(tourId != -1)
				mecze=search.getAllMatchPlayer(player_id,srtType,tourId);
			else if(lgId==-1 && tourId==-1)
				srtType=0;
		}
		else
			mecze=search.getAllMatchPlayer(player_id,srtType);
		if(mecze.size()==0)
		{
			txt = new TextView(con);
			txt.setText(getString(R.string.error_no_data));
			lv.addView(txt);
			
		}
		OnClickListener onClkLisDele = new OnClickListener() 
		{
			public void onClick(View v) {
				Log.d("tag delete","tag="+v.getTag());
				Match_DB dbMAtch = new Match_DB(con);
				dbMAtch.deleteFromId((Integer)Integer.parseInt((String) v.getTag()) );
				setupView();
			}
		};
		OnClickListener onClkLisEdit = new OnClickListener() 
		{
			public void onClick(View v) {
				Log.d("tagedit ","tag="+v.getTag());
				Intent mIntent = new Intent(con, EditMatch.class);
				mIntent.putExtra(getString(R.string.AllInfoMatches), (String) v.getTag());
				startActivityForResult(mIntent, 0);
			}
		};
		for(MyMatch m : mecze){
			
			txt=new TextView(con);
			txt.setText(m.toString(con));
			txt.setTag(m.getMyMatch_ID());
			txt.setTextAppearance(getActivity(),R.style.small_body_text_white);
			lv.addView(txt);
			
			delete = new Button(con);
			delete.setBackgroundResource(R.drawable.trash_empty);
			String deleteT = getString(R.string.Match_info_delete);
			delete.setText(deleteT);
			delete.setTextAppearance(getActivity(), R.style.medium_body_text_golden);
			delete.setTag(m.getMyMatch_ID());
			lv.addView(delete);
					
			btn_edit = new Button(con);
			btn_edit.setTextAppearance(getActivity(), R.attr.textBody);
			String editTxt = getResources().getString(R.string.Match_info_edit);
			btn_edit.setText(editTxt);
			btn_edit.setTag(m.getMyMatch_ID());
			lv.addView(btn_edit);
			
			v = new View(con);
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,6));
			v.setBackgroundResource(R.drawable.divider);
			lv.addView(v);
			
			btn_edit.setOnClickListener(onClkLisEdit);
			delete.setOnClickListener(onClkLisDele);
		}
		return true;
	}
	

	
	
	
}
