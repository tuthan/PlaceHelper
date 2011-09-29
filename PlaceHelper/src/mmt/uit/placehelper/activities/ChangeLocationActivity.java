package mmt.uit.placehelper.activities;

import java.util.List;

import mmt.uit.placehelper.models.MyAddress;
import mmt.uit.placehelper.utilities.Constants;
import mmt.uit.placehelper.utilities.LocationAdapter;
import mmt.uit.placehelper.utilities.PointAddressUtil;

import mmt.uit.placehelper.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ChangeLocationActivity extends ListActivity {
	private List<MyAddress> lstAd;
	private ImageButton btnSearch;
	private AutoCompleteTextView textView;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_location);
		btnSearch = (ImageButton)findViewById(R.id.btnSearch);	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        textView = (AutoCompleteTextView) findViewById(R.id.searchLocation);
        textView.setAdapter(adapter);        
        btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lstAd = PointAddressUtil.getAdress(textView.getText().toString(),getApplicationContext());
				if (lstAd !=null){
					ListAdapter mAdapter = new LocationAdapter(getApplicationContext(), R.layout.row_location, lstAd);
					setListAdapter(mAdapter);
				}
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id){
		case Constants.CHANGE_LOC: 
			return new AlertDialog.Builder(this)
            .setIcon(R.drawable.app_icon)
            .setTitle("Do you want set to this location")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    SharedPreferences mSharePref = getSharedPreferences(Constants.PREF_NAME, 0);
                    SharedPreferences.Editor mEditor = mSharePref.edit();
                    mEditor.putFloat(Constants.KEY_LAT, new Float(lstAd.get(0).getLat()));
                    mEditor.putFloat(Constants.KEY_LNG, new Float(lstAd.get(0).getLng()));
                    mEditor.commit();
                    Toast.makeText(getApplicationContext(), "Your Location was saved!", Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(getApplicationContext(), CategoriesActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putDouble(Constants.KEY_LAT, lstAd.get(0).getLat());
                    mBundle.putDouble(Constants.KEY_LNG, lstAd.get(0).getLng());
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                    ChangeLocationActivity.this.finish();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    dialog.cancel();
                }
            })
            .create();
		}
		return null;
	}
	
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			showDialog(Constants.CHANGE_LOC);
			
		}
    static final String[] COUNTRIES = new String[] {
	"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
	"Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
	"Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
	"Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
	"Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
	"Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
	"British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
	"Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
	"Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
	"Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
	"Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
	"Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
	"East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
	"Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
	"Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia",
	"French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
	"Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
	"Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary",
	"Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
	"Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
	"Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
	"Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
	"Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
	"Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
	"Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
	"Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
	"Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
	"Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
	"Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
	"Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
	"Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal",
	"Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
	"Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea",
	"Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden",
	"Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas",
	"The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
	"Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
	"Ukraine", "United Arab Emirates", "United Kingdom",
	"United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
	"Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara",
	"Yemen", "Yugoslavia", "Zambia", "Zimbabwe"
    };
}

