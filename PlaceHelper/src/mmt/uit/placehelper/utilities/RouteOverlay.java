package mmt.uit.placehelper.utilities;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class RouteOverlay extends Overlay{
	
	private GeoPoint gPoint1;
	private GeoPoint gPoint2;
	private int color;
	
	public RouteOverlay (){
		
	}
	
	public RouteOverlay (GeoPoint gPoint1, GeoPoint gPoint2, int color){
		this.gPoint1 = gPoint1;
		this.gPoint2 = gPoint2;
		this.color = color;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		
		Projection projection = mapView.getProjection();
	    Paint paint = new Paint();
	    Point point = new Point();
	    paint.setAntiAlias(true);
	    projection.toPixels(gPoint1, point);
	    paint.setColor(color);
	    Point point2 = new Point();
	    projection.toPixels(gPoint2, point2);
	    paint.setStrokeWidth(5);
	    paint.setAlpha(120);
	    canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
	    super.draw(canvas, mapView, false);
	}
}
