package moja.paczka.namespace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
//import java.util.Date;

import customTheme.Utils;
import MyUtility.Db_versions;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class RestoreFromFile extends Activity {

	
	private  File sd;
	private  Context context;
	private String[] mFileList;
	private String mChosenFile;
	private ListView lv;
	private Button btn_load,btn_cancel;
	EditText edTxt;
	private String databaseName;
	private static String FTYPE;    
	
	
	public RestoreFromFile() {
	}
	public void onCreate(Bundle savedInstanceState) {
    	
		Utils.setThemeToActivity(this);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.restore_from_file);
        context = getApplicationContext();
        FTYPE=getString(R.string.BackupFileExtension);
        lv=(ListView) findViewById(R.id.fileList);
        btn_cancel = (Button) findViewById(R.id.fileCancel);
        btn_load = (Button) findViewById(R.id.fileLoad);
        edTxt = (EditText) findViewById(R.id.fileName);
        databaseName=getString(R.string.database_name);
        loadFileList();
        //Dialog dial = onCreateDialog(1);
        //dial.show();
        
        btn_cancel.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
				
			}
		});
        if(mFileList.length==0){
        	Toast.makeText(context,getString(R.string.BackupRestoreError)+sd.toString(), Toast.LENGTH_SHORT).show();
        	edTxt.setText(getString(R.string.BackupRestoreError)+sd.toString());
        	btn_load.setEnabled(false);
        }
        else{
        	btn_load.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					info();
				}
			});
        	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
        			this, 
        			android.R.layout.simple_list_item_1,
        			mFileList );
	
	        lv.setAdapter(arrayAdapter); 
	        lv.setOnItemClickListener(new OnItemClickListener() {
	
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mChosenFile=mFileList[position];
					edTxt.setText(mChosenFile);
					
				}
			});
        }
	}
	public void info(){
		//bulding alert dialog to confirm
   	 AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
   	 myAlertDialog.setTitle(getString(R.string.confirm));
   	 myAlertDialog.setMessage(getString(R.string.RestoreConfirminfo));
   	 myAlertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

   	  public void onClick(DialogInterface arg0, int arg1) {
   	  // do something when the OK button is clicked
   		  restore();
   	  }});
   	 myAlertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
   	       
   	  public void onClick(DialogInterface arg0, int arg1) {
   	  // do something when the Cancel button is clicked
   	  }});
   	 myAlertDialog.show();
	}
	/**
	 * copy file with db to app data folder
	 */
	private void restore(){
		File currentDB;
        File backupDB;
        FileChannel src = null;
        FileChannel dst = null;
        File data;
        //Date date;
        File sd;
        FileInputStream input = null;
        FileOutputStream output = null;
		try 
       {
			String currentDBPath = "//data//"+getPackageName()+"//databases//";
			String backupDBPath = mChosenFile;
			sd = new File(Environment.getExternalStorageDirectory()+getString(R.string.BackUp_folder));
			data = new File(Environment.getDataDirectory()+currentDBPath);
           
          
           if(!data.isDirectory())
           	data.mkdir();
           if (data.canWrite()) {
               currentDB = new File(data, databaseName);
               backupDB = new File(sd, backupDBPath);

               if (backupDB.exists()) {
					input = new FileInputStream(backupDB);
					src = input.getChannel();
					output = new FileOutputStream(currentDB);
					dst = output.getChannel();
					
                   Long temp = dst.transferFrom(src, 0, src.size());
                   if(temp == src.size()){
                   	Toast.makeText(context,getString(R.string.restore_commOk)+mChosenFile, Toast.LENGTH_SHORT).show();
                   	Db_versions db_v = new Db_versions(context);
        			db_v.open();
        			db_v.close();
                   	finish();
                   }
                   else
                   	Toast.makeText(context,getString(R.string.DB_error), Toast.LENGTH_SHORT).show();
               }
           }
       }catch(SecurityException ex)
       {
           	Log.e(getString(R.string.Debug_error),ex.toString());
           	Toast.makeText(context,ex.toString(), Toast.LENGTH_SHORT).show();
       }
       catch (Exception e) 
       {
       	e.printStackTrace();
       	Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
       }
       finally{
       	if(src!=null && src.isOpen())
				try {
					src.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
       	if(dst!= null && dst.isOpen())
				try {
					dst.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
           try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
           try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
       
	}
	private void loadFileList() {
		try{
			sd = Environment.getExternalStorageDirectory();
	        if(sd.canRead())
	        	sd = new File(Environment.getExternalStorageDirectory()+getString(R.string.BackUp_folder));
	        else
	        	sd = Environment.getRootDirectory();
	        if(!sd.isDirectory())
	        	sd.mkdir();
	        if(sd.isDirectory()){
	        	 FilenameFilter filter = new FilenameFilter() {

	                 public boolean accept(File dir, String filename) {
	                     File sel = new File(dir, filename);
	                     return filename.contains(FTYPE) || sel.isDirectory();
	                 }

	             };
	             mFileList = sd.list(filter);
	        }
	        else {
	            mFileList= new String[0];
	        }
	        
	        
		}catch(SecurityException sExc){
			Log.e(getString(R.string.Debug_error), sExc.toString());
			
		}
		
	}
	/*
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog = null;
	    AlertDialog.Builder builder = new Builder(this);

	    switch(id) {
	        case DIALOG_LOAD_FILE:
	            builder.setTitle("Choose your file");
	            if(mFileList == null) {
	                Log.e("TAG", "Showing file picker before loading the file list");
	                dialog = builder.create();
	                return dialog;
	            }
	            builder.setItems(mFileList, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                    mChosenFile = mFileList[which];
	                    //you can do stuff with the file here too
	                }
	            });
	            break;
	    }
	    dialog = builder.show();
	    return dialog;
	}
	*/
}
		