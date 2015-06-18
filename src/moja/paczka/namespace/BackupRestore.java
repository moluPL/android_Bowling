package moja.paczka.namespace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import customTheme.Utils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class BackupRestore extends Activity {

	private String databaseNamefinal;
	private Context context;
	private Button restore,backupF;
	public BackupRestore() {
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	 Utils.setThemeToActivity(this);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_restore);
        context = getBaseContext();
        databaseNamefinal = getString(R.string.database_name);
        setup();
        
    }
    public void setup(){
    	backupF = (Button) findViewById(R.id.btn_backupFile);
    	restore = (Button) findViewById(R.id.btn_restoreFile);
    	
    	backupF.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	exportDatabse(databaseNamefinal);
            }
        });
    	
    	restore.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	//importDataBase();
            	Intent myIntent = new Intent(context, RestoreFromFile.class);
            	startActivity(myIntent);
            	finish();
            }
        });
    }
    
    
	
	@SuppressLint("SimpleDateFormat")
	public void exportDatabse(String databaseName) {
		 File currentDB;
         File backupDB;
         FileChannel src = null;
         FileChannel dst = null;
         File data;
         Date date;
         File sd;
         FileInputStream input = null;
         FileOutputStream output = null;
		try 
        {
            sd = new File(Environment.getExternalStorageDirectory()+getString(R.string.BackUp_folder));
        	//File sd = Environment.getExternalStorageDirectory();
            data = Environment.getDataDirectory();
            String currentDBPath = "//data//"+getPackageName()+"//databases//"+databaseName+"";
            date = new Date();
            SimpleDateFormat smplDateFrmt = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss");
            String sDate= smplDateFrmt.format(date);
            String backupDBPath = sDate+getString(R.string.BackupFileExtension);
           
            if(!sd.isDirectory())
            	sd.mkdir();
            if (sd.canWrite()) {
                currentDB = new File(data, currentDBPath);
                backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
					input = new FileInputStream(currentDB);
					src = input.getChannel();
					output = new FileOutputStream(backupDB);
					dst = output.getChannel();
					
                    Long temp = dst.transferFrom(src, 0, src.size());
                    if(temp == src.size()){
                    	Toast.makeText(context,getString(R.string.Backup_commOk)+sDate, Toast.LENGTH_SHORT).show();
                    	finish();
                    }
                    else
                    	Toast.makeText(context,getString(R.string.BackUpError), Toast.LENGTH_SHORT).show();
                }
            }
        }catch(SecurityException ex)
        {
            	Log.e(getString(R.string.Debug_error),ex.toString());
            	Toast.makeText(context,getString(R.string.BackUpError), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        	Toast.makeText(context,getString(R.string.BackUpError), Toast.LENGTH_SHORT).show();
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
}
