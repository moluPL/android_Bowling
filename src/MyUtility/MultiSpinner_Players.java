package MyUtility;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author karlo on demo from http://stackoverflow.com/a/6022474 by user Destil
 */

public class MultiSpinner_Players extends Spinner implements OnMultiChoiceClickListener, OnCancelListener {
	
	
	 private List<String> items,choosen;
	 private boolean[] selected;
	 private String[] selected_id;
	 private String defaultText;
	 //private MultiSpinnerListener listener;
	 
	 
	 public MultiSpinner_Players(Context context) {
	        super(context);
	    }

	    public MultiSpinner_Players(Context arg0, AttributeSet arg1) {
	        super(arg0, arg1);
	    }

	    public MultiSpinner_Players(Context arg0, AttributeSet arg1, int arg2) {
	        super(arg0, arg1, arg2);
	    }



	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    public void onCancel(DialogInterface dialog) 
    {
        // refresh text on spinner
        choosen = new ArrayList<String>();
        boolean someUnselected = false;
        for (int i = 0; i < items.size(); i++) 
        {
            if (selected[i] == true) 
            {
            	someUnselected = true;
                choosen.add(selected_id[i]);
            }
        }
        String spinnerText;
        if (someUnselected) 
        {
            spinnerText = "want change?";
        } else 
        {
            spinnerText ="Select Players";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { spinnerText });
        setAdapter(adapter);
        //listener.onItemsSelected(selected);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[items.size()]), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    	dialog.cancel();
                    	
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(List<String> players) 
    {
    	if(players.size()>0)
    	{
    		selected_id = new String[players.size()];
    		for(int i =0;i<players.size();i++)
    			selected_id[i] = players.get(i).substring(0, players.get(i).indexOf(" "));
    		items = players;
    		for(String tmp : items)
    			tmp=tmp.substring(tmp.indexOf(" "));
    		this.defaultText = "Select Players";
    		choosen = new ArrayList<String>();

    		// all non-selected by default
    		selected = new boolean[items.size()];
    		for (int i = 0; i < selected.length; i++)
    			selected[i] = false;
    		// all text on the spinner
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,new String[] {defaultText});
    		setAdapter(adapter);
    	}
    	else{
    		defaultText="There is no players in database! Create one!";
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,new String[] {defaultText});
        		setAdapter(adapter);
        	
    	}
    }
    	
    	
    public List<String> getValues(){
    
    	return choosen;
    
    }
    
    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }

}
