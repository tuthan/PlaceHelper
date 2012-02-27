package mmt.uit.placehelper.models;

import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.api.client.util.Key;


public class Polyline {
	@Key
	private String points;
	

	/**
	 * @return the points
	 */
	public String getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(String points) {
		this.points = points;
	}
	
	/*
	 * I use code here
	 * http://jeffreysambells.com/posts/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java/
	 * Thanks Jeffrey
	 * */
	public  List<GeoPoint> getDecodePoly() {

		String encoded = this.points;			
	    List<GeoPoint> poly = new ArrayList<GeoPoint>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;

	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;

	        GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),
	             (int) (((double) lng / 1E5) * 1E6));
	        poly.add(p);
	    }

	    return poly;
	}
	
	
	//Class util for decode Polyline to geopoint and get zoom level
	//http://stackoverflow.com/questions/6708408/map-view-draw-directions-using-google-directions-api-decoding-polylines
	private class PolylineDecoder {
		/**
		 * Transform a encoded PolyLine to a Array of GeoPoints.
		 * Java implementation of the original Google JS code.
		 * @see Original encoding part: <a href="http://code.google.com/apis/maps/documentation/polylinealgorithm.html">http://code.google.com/apis/maps/documentation/polylinealgorithm.html</a>
		 * @return Array of all GeoPoints decoded from the PolyLine-String.
		 * @param encoded_points String containing the encoded PolyLine. 
		 * @param countExpected Number of points that are encoded in the PolyLine. Easiest way is to use the length of the ZoomLevels-String. 
		 * @throws DecodingException 
		 */
		public GeoPoint[] decodePoints(String encoded_points, int countExpected){
		    int index = 0;
		    int lat = 0;
		    int lng = 0;
		    int cnt = 0;
		    GeoPoint[] out = new GeoPoint[countExpected];

		    try {
		        int shift;
		        int result;
		        while (index < encoded_points.length()) {
		            shift = 0;
		            result = 0;
		            while (true) {
		                int b = encoded_points.charAt(index++) - '?';
		                result |= ((b & 31) << shift);
		                shift += 5;
		                if (b < 32)
		                    break;
		            }
		            lat += ((result & 1) != 0 ? ~(result >> 1) : result >> 1);

		            shift = 0;
		            result = 0;
		            while (true) {
		                int b = encoded_points.charAt(index++) - '?';
		                result |= ((b & 31) << shift);
		                shift += 5;
		                if (b < 32)
		                    break;
		            }
		            lng += ((result & 1) != 0 ? ~(result >> 1) : result >> 1);
		            /* Add the new Lat/Lng to the Array. */
		            out[cnt++] = new GeoPoint((lat*10),(lng*10));
		        }
		        return out;
		    }catch(Exception e) {
		        e.printStackTrace();
		    }
		    return out;
		}

		public int[] decodeZoomLevels(String encodedZoomLevels){
		    int[] out = new int[encodedZoomLevels.length()];
		    int index = 0;

		    for(char c : encodedZoomLevels.toCharArray())
		        out[index++] = c - '?';
		    return out;

		}
		}

}
