package mmt.uit.placehelper;


import mmt.uit.placehelper.activities.CategoriesListActivity;
import mmt.uit.placehelper.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {
	
	private final int SPLASH_LENGHT = 2000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ph_main);             
        new Handler().postDelayed(new Runnable(){
        	 
            @Override
            public void run() {

                    /* Create an Intent that will start the Menu-Activity. */

                    Intent mainIntent = new Intent(MainActivity.this,CategoriesListActivity.class);

                    MainActivity.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    MainActivity.this.finish();

            }

        }, SPLASH_LENGHT);

    }


}
        
   