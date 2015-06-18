package moja.paczka.namespace;


import java.util.List;

import MyUtility.MyMatch;
import MyUtility.SearchData;
import android.app.Fragment;
import android.content.Context;
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

public class Fragment_2 extends Fragment {

	private View rootView;
	private LinearLayout lv;
	private int player_id;
	private TextView txt;
	private View v;
	private Button btn_edit;
	private Button delete;
	private Context con;
	
	public Fragment_2(){
		
	}
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		
		rootView = inflater.inflate(R.layout.fragment_select_edit, container, false);
		player_id = getArguments().getInt(getString(R.string.player_id));
		con=getActivity();
		setupView();
		return rootView;
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
		List<MyMatch> mecze;
		
		mecze=search.getAllMatchPlayer(player_id,4);
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
			}
		};
		OnClickListener onClkLisEdit = new OnClickListener() 
		{
			public void onClick(View v) {
				Log.d("tagedit ","tag="+v.getTag());
				
			}
		};
		for(MyMatch m : mecze){
			
			txt=new TextView(con);
			txt.setText(m.toString(con));
			txt.setTag(m.getMyMatch_ID());
			lv.addView(txt);
			
			delete = new Button(con);
			delete.setBackgroundResource(R.drawable.trash_empty);
			String deleteT = getString(R.string.Match_info_delete);
			delete.setText(deleteT);
			delete.setTag(m.getMyMatch_ID());
			lv.addView(delete);
					
			btn_edit = new Button(con);
			btn_edit.setBackgroundResource(R.drawable.button);
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
