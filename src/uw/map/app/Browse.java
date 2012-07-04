package uw.map.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class Browse extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.browse_main);
        final ListView lv1=(ListView)findViewById(R.id.ListView01);
        /*Calling all the strings from the XML file*/
        final String[] categories =getResources().getStringArray(R.array.categories);
        final String[] eng_builds = getResources().getStringArray(R.array.eng_buildings);
        final String[] art_builds = getResources().getStringArray(R.array.arts_buildings);
        final String[] math_builds = getResources().getStringArray(R.array.math_cs_buildings);
        final String[] env_builds = getResources().getStringArray(R.array.env_buildings);
        final String[] sci_builds = getResources().getStringArray(R.array.arts_buildings);
        final String[] research = getResources().getStringArray(R.array.research_buildings);
        final String[] residences = getResources().getStringArray(R.array.residence_buildings);
        final String[] services = getResources().getStringArray(R.array.services_buildings);
	    final String[] all_build = getResources().getStringArray(R.array.all_builds);
     // By using setAdpater method in list view we an add string array in list.
     lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,categories));
     lv1.setTextFilterEnabled(true);
     lv1.setCacheColorHint(0);
/*Action Listener for the ListView*/
     lv1.setOnItemClickListener(new OnItemClickListener(){
    	public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			// TODO Auto-generated method stub
    		
    		if(lv1.getItemAtPosition(pos).equals(categories[0]))//if user clicks engineering                                                       
    		{                                
    	        catBrowse(eng_builds);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[1]))//if user clicks arts                                                       
    		{                                
    	        catBrowse(art_builds);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[2]))//if user clicks environment                                                       
    		{                                
    	        catBrowse(env_builds);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[3]))//if user clicks math/cs                                                       
    		{                                 
    	        catBrowse(math_builds);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[4]))//if user clicks residence                                                       
    		{                                                                                                           
    	        catBrowse(residences);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[5]))//if user clicks research                                                       
    		{                                                                                                           
    	        catBrowse(research);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[6]))//if user clicks services                                                       
    		{                                                                                                           
    	        catBrowse(services);
    		}
    		if(lv1.getItemAtPosition(pos).equals(categories[7]))//if user clicks search
    		{
    			search(all_build);
    		}
    	}
     });
     
    }
    public void catBrowse(final String[] s)
    {
    	setContentView(R.layout.browse_cat);
    	final ListView list = (ListView)findViewById(R.id.list);
    	list.setCacheColorHint(0);
        list.setTextFilterEnabled(true);
        list.setAdapter(new ArrayAdapter<String>(Browse.this,android.R.layout.simple_list_item_1,s));
        list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
	    		if(list.getItemAtPosition(pos).equals("Back"))
	    		{
    				onCreate(null);//goes back to the onCreate method(the beginning)
	    		}
	    		else
	    		{
	    			String buildChoice = list.getItemAtPosition(pos).toString();
	    			Bundle b = new Bundle();
	    			b.putString("buildChoice", buildChoice);
	    			//Intent i = new Intent(Browse.this,)
	    		}

				
			}
    	
    	});
    	
    }
        public void toSearch(final String[] s)
    {
    	search(s);
    }
    // Search Helper
    // true if findThis is in inThis
    private boolean includes(String findThis, String inThis){
    	findThis = findThis.toLowerCase();
    	inThis = inThis.toLowerCase();
    	if	(inThis.indexOf(findThis) != -1){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public void search(final String[] s)
    {
    	setContentView(R.layout.search);
    	final EditText text = (EditText)findViewById(R.id.searchBox);
    	Button go = (Button)findViewById(R.id.go);
    	Button back = (Button)findViewById(R.id.bk);
    	final int sizeString = s.length;
    	final String[] a = new String[sizeString];
    	for(int i = 0;i<sizeString;i++)
    		a[i] = s[i];
    	back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onCreate(null);
			}
		});
    	go.setOnClickListener(new View.OnClickListener(){
    		public void onClick (View v)
    		{
    			List<String> li = new ArrayList<String>();//arraylist of strings to hold search matches
    			String entered = text.getText().toString().trim();//takes the entered string in the textbox
    			if (entered.length() == 0){
    				toSearch(s);
    			}
    			else{
    			boolean[] count = new boolean[sizeString];//used to keep track of which string is matched in the search
    			for (int i = 0; i < sizeString;i++)count[i]=false;//initializing all as false at the beginning
    			String[] query = entered.split(" ");//splits the inputed text wherever there is a space into an array
    			int temp,x=0,qSize= query.length;
    			
    			for (int n = 0; n < qSize;n++)
    			{
    				for (int i = 0; i < sizeString;i++)
    				{
    					if (includes(query[n],a[i]) && !count[i])
    					{
    						li.add(a[i]);//adds the string to the arraylist
							count[i]= true;
    					}
    				}
    			}
    			
    			if (li.size() == 0)
    			{
    				Context context = getApplicationContext();
    				CharSequence text = "Sorry! No results found";
    				int duration = Toast.LENGTH_SHORT;

    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
    				onCreate(null);
    			}

    			setContentView(R.layout.search_results);
    			final ListView list= (ListView)findViewById(R.id.search_list);
    			list.setCacheColorHint(0);
    			String[] results = new String[li.size()+1];
    			int y = 0;
    			for (int i =0;i<sizeString;i++)
    			{
    				if (count[i]){
    					results[y] = s[i];//adding the strings from the arraylist to the final string array
    					y++;
    				}
    			}
    			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    			results[li.size()] = "Back";
    			list.setAdapter(new ArrayAdapter<String>(Browse.this,android.R.layout.simple_list_item_1 ,results));
    			list.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						if(list.getItemAtPosition(pos).equals("Back"))
			    		{
		    				toSearch(s);//goes back to the onCreate method(the beginning)
			    		}
						else
						{
							String choice = list.getItemAtPosition(pos).toString();
							Bundle b = new Bundle();
							b.putString("buildChoice",choice);
							//Intent i = new Intent(Browse.this,);
							//i.putExtras(b);
							
						}
						
					}
    				
    			});
    			//adds the string to the listview via arrayadapter
    		}}
    	});
    		
    }
    
}