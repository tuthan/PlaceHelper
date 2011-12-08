package mmt.uit.placehelper.services;

import java.io.IOException;

import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceDetail;
import mmt.uit.placehelper.models.PlaceDetailRs;
import mmt.uit.placehelper.models.PlacesList;

import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

public class SearchPlace {
		
		// Create our transport.
		private static final HttpTransport transport = new ApacheHttpTransport();
		
		// Fill in the API key you want to use.
		private static final String API_KEY = "AIzaSyCRmej3hzfD4HeYcjO4yjZRSrYxhq_OuuQ";
		
		//Log Tag
		private static final String LOG_KEY = "pl_request";
		
		// URL for different request
		private static final String PLACES_SEARCH_URL =  "https://maps.googleapis.com/maps/api/place/search/json?";
		private static final String PLACES_AUTOCOMPLETE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
		private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
				
		private static HttpRequestFactory createRequestFactory(final HttpTransport transport) {
			   
			  return transport.createRequestFactory(new HttpRequestInitializer() {
			   public void initialize(HttpRequest request) {
			    GoogleHeaders headers = new GoogleHeaders();
			    headers.setApplicationName("PlaceHelper");
			    request.setHeaders(headers);
			    JsonHttpParser parser = new JsonHttpParser(new JacksonFactory()) ;			    			    
			    request.addParser(parser);
			   }
			});
		}
		
		
		public static PlacesList getPlaceList (double lat,double lng, String keyword){
			PlacesList rs_list = null; //Store list result places
			GenericUrl mUrl = new GenericUrl(PLACES_SEARCH_URL);
			
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			try {
				mUrl.put("keyword", keyword);
				mUrl.put("radius", 5000);
				mUrl.put("location", lat + "," + lng);
				mUrl.put("sensor", "false");
				mUrl.put("key", API_KEY);
				Log.v(LOG_KEY, "url= " + mUrl);
				HttpRequest request = httpRequestFactory.buildGetRequest(mUrl);			
				rs_list = request.execute().parseAs(PlacesList.class);
				Log.v(LOG_KEY, rs_list.status);
				
				for (Place pl:rs_list.results)
				{
					pl.setDistance(lat, lng);
					//Log.v(LOG_KEY, String.valueOf(pl.getDistance()));
				}
				
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rs_list;
			
			
			
		}
		
		public static PlaceDetailRs getDetail (String reference){
			GenericUrl mUrl = new GenericUrl(PLACES_DETAILS_URL);
			PlaceDetailRs pld = null;
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			try {
				
				mUrl.put("reference", reference);				
				mUrl.put("sensor", "false");
				mUrl.put("key", API_KEY);
				Log.v(LOG_KEY, "url= " + mUrl);
				HttpRequest request = httpRequestFactory.buildGetRequest(mUrl);			
				pld = request.execute().parseAs(PlaceDetailRs.class);
				Log.v(LOG_KEY, pld.status + pld.result.toString());
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pld;
		}
}
