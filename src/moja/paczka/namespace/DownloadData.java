package moja.paczka.namespace;

import java.util.List;

import customTheme.Utils;
import MyUtility.Db_versions;
import MyUtility.Patterns;
import MyUtility.Patterns_DB;
import MyUtility.Xml_to_DB;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DownloadData extends Activity
{
	
	
	private Button save,cancel,ok;
	private TextView linear;
	private Boolean newVersion;
	private String version;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		 Utils.setThemeToActivity(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		save = (Button) findViewById(R.id.download_btn_start);
		cancel = (Button) findViewById(R.id.download_btn_cancel);
		ok = (Button) findViewById(R.id.download_btn_ok);
		linear = (TextView) findViewById(R.id.txt1);
		newVersion=false;
		version="1.0";
		ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(1);
				finish();
			}
		});
		
		save.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save.setEnabled(false);
				cancel.setEnabled(false);
				new downloadXml().execute();
				setResult(1);
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Cancel downloading",Toast.LENGTH_LONG).show();
				setResult(0);
				finish();
			}
		});
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		setResult(0);
		super.onBackPressed();
	}


	public void updateTXT(String s){
		linear.append(s);
	}
	/*
	 * Inner class for AsyncTask
	 */
	protected class downloadXml extends AsyncTask<DownloadData, Void, List<Patterns>>
	{
		ProgressDialog prog;
		Xml_to_DB x;
		List<Patterns> list;

		protected List<Patterns> doInBackground(DownloadData... params) 
		{
			
			x = new Xml_to_DB(getApplicationContext());
			
			newVersion=x.checkVersionsPatterns();
			
			if(newVersion)
			{
				version=x.getVersion();
				list = x.parsePatt(true);
				
				
			}
			else{
				list=null;
			}
			return list;
		   
		} 
		   
		  
		  protected void onPostExecute(String result) {
			  
			  
		  }
		  
		  
		  protected void onPreExecute() {
			  prog = new ProgressDialog(DownloadData.this);
	          prog.setTitle("DownLoading");
			  prog.setMessage("Please wait...."); 
			  prog.setCancelable(true);
	          prog.show();  
		   }
		  
		  /* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(List<Patterns> result) {
			prog.dismiss();
			if(list !=null && list.size()>0)
			{
				for(Patterns p : list){
				  linear.append("Donwloaded: "+p.getName()+"\n");
				}
				Toast.makeText(getApplicationContext(), "Done Downloading!",Toast.LENGTH_SHORT).show();
				new addToDataBase().execute(list);
				
			}
			else if(!newVersion){
				Toast.makeText(getApplicationContext(), getString(R.string.noNewDownload),Toast.LENGTH_LONG).show();
				linear.append(getString(R.string.noNewDownload));
				ok.setEnabled(true);
			}
			else
			{
				Toast.makeText(getApplicationContext(), getString(R.string.failDownload),Toast.LENGTH_LONG).show();
				linear.append(getString(R.string.failDownload));
				ok.setEnabled(true);
			}
			
		}
		protected void onProgressUpdate(Void...voids ) {
		  }
	}
	/*
	 * Adding patterns to DATABASE
	 */
	private class addToDataBase extends AsyncTask<List<Patterns>, Integer, String>
	{
		ProgressDialog prog;

		@Override
		protected String doInBackground(List<Patterns>... params) {
			int i,end;
			end= params[0].size();
			prog.setMax(end);
			Patterns_DB datab = new Patterns_DB(getApplicationContext());
			Db_versions dbv = new Db_versions(getApplicationContext());
			dbv.addVersionPatt(version);
			for(i =0;i<end;i++)
			{	
				//Log.d("DEBUG",params[0].get(i).getName());
				params[0].get(i).addToDB(datab);
				publishProgress((int) (( (i+1) / (float) end) * 100),i,end);
				if (isCancelled()) break;
			}
			return Integer.toString(i);
		}
		
		protected void onPreExecute() {
			  prog = new ProgressDialog(DownloadData.this);
	          prog.setTitle("Adding to DataBase");
			  prog.setMessage("Please wait...."); 
			  prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			  prog.setCancelable(true);
			 // prog.setProgressPercentFormat(null);
	          prog.show();  
		   }

	
		protected void onPostExecute(String result) {
			prog.dismiss();
			Toast.makeText(getApplicationContext(), "Done! all data("+result+") is in Database!",Toast.LENGTH_LONG).show();
			ok.setEnabled(true);
			
		}

	
		protected void onProgressUpdate(Integer... values) {
			prog.setProgress(values[0]);
			
			
		}
		
		
		
	}
	
}
