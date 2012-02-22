package mmt.uit.placehelper.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mmt.uit.placehelper.models.Direction;
import mmt.uit.placehelper.models.Place;
import mmt.uit.placehelper.models.PlaceDetailRs;
import mmt.uit.placehelper.models.PlacesList;

import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

public class SearchPlace {
		
		// Create our transport.
		private static final HttpTransport transport = new ApacheHttpTransport();
		
		// Fill in the API key you want to use.
		private static final String API_KEY = "AIzaSyBwWstg6SSW3wE8JUEw6ELP-J40tZr8vjw";
		
		//Log Tag
		private static final String LOG_GETLIST = "pl_place_list";
		private static final String LOG_GETDETAIL = "pl_place_detail";
		private static final String LOG_GETDIRECTION = "pl_directions";
		
		// URL for different request
		private static final String PLACES_SEARCH_URL =  "https://maps.googleapis.com/maps/api/place/search/json?";
		//private static final String PLACES_AUTOCOMPLETE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
		private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
		private static final String DIRECTIONS_URL ="https://maps.googleapis.com/maps/api/directions/json?";
				
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
		
		
		public static PlacesList getPlaceList (double lat,double lng, String keyword, String lang, int radius, String types){
			PlacesList rs_list = null; //Store list result places
			GenericUrl mUrl = new GenericUrl(PLACES_SEARCH_URL);						
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			try {
				if(keyword!=null){
				mUrl.put("name", keyword);}
				mUrl.put("radius", radius);
				mUrl.put("location", lat + "," + lng);
				mUrl.put("sensor", "true");
				mUrl.put("lang", lang);
				if(types!=null){
				mUrl.put("types",types);}
				mUrl.put("key", API_KEY);				
				//Log.v(LOG_GETLIST, "url= " + mUrl);
				HttpRequest request = httpRequestFactory.buildGetRequest(mUrl);			
				HttpResponse response = request.execute();
				int sttCode = response.getStatusCode();
				if(sttCode==200){								
				rs_list = response.parseAs(PlacesList.class);
				
				for (Place pl:rs_list.getResults())
				{
					pl.setDistance(lat, lng);
					Log.v(LOG_GETLIST, pl.toString());
				}
				Log.v(LOG_GETLIST, rs_list.getStatus());
				}
				
				Log.v(LOG_GETLIST, String.valueOf(sttCode));
				
				
				
				
				
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return rs_list;
			
			
			
		}
		
		public static PlaceDetailRs getDetail (String reference, String lang){
			GenericUrl mUrl = new GenericUrl(PLACES_DETAILS_URL);
			PlaceDetailRs pld = null;
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			try {
				
				mUrl.put("reference", reference);				
				mUrl.put("sensor", "true");
				mUrl.put("key", API_KEY);
				mUrl.put("lang", lang);
				//Log.v(LOG_GETDETAIL, "url= " + mUrl);
				HttpRequest request = httpRequestFactory.buildGetRequest(mUrl);			
				pld = request.execute().parseAs(PlaceDetailRs.class);
				Log.v(LOG_GETDETAIL, "Result: " + pld.getStatus() + pld.getResult().toString());
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return pld;
		}
		
		public static Direction getDirection (String origin, String dest, String mode){
			GenericUrl mUrl = new GenericUrl(DIRECTIONS_URL);
			Direction result = null;
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			try {
				
				mUrl.put("origin", origin);				
				mUrl.put("destination", dest);
				mUrl.put("mode", mode);
				mUrl.put("sensor", "true");
				mUrl.put("units", "metric");
				Log.v(LOG_GETDIRECTION, "url= " + mUrl);
				HttpRequest request = httpRequestFactory.buildGetRequest(mUrl);			
				result = request.execute().parseAs(Direction.class);
				Log.v(LOG_GETDIRECTION, "Result: " + result.getStatus());
				
			}
			
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
			
		}
		
		
}
