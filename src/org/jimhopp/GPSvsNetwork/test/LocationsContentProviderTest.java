package org.jimhopp.GPSvsNetwork.test;

import org.jimhopp.GPSvsNetwork.provider.LocationContentProvider;
import org.jimhopp.GPSvsNetwork.provider.LocationsContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.util.Log;

/**
 * @author jhopp
 *
 */
public class LocationsContentProviderTest extends ProviderTestCase2 {

	/**
	 * @param providerClass
	 * @param providerAuthority
	 */
	public LocationsContentProviderTest(Class providerClass,
			String providerAuthority) {
		super(providerClass, providerAuthority);
		// TODO Auto-generated constructor stub
	}
	
	public LocationsContentProviderTest() {
		this(org.jimhopp.GPSvsNetwork.provider.LocationsContentProvider.class,
				"org.jimhopp.GPSvsNetwork.provider.LocationsContentProvider");
	}
	public void testEmptyDatabase() {
		Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "/all"),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
		assertEquals(cursor.getCount(),0);
	}
	public void testInsertNetworkPoint() {
		Location loc = new Location("Network");
		ContentValues map = new ContentValues(); 
		map.put(LocationsContentProvider.TIME_COL, loc != null ? loc.getTime() : System.currentTimeMillis()); 
		map.put(LocationsContentProvider.TYPE_COL, "Network");
		map.put(LocationsContentProvider.LAT_COL, loc != null ? loc.getLatitude() : 0);
		map.put(LocationsContentProvider.LON_COL, loc != null ? loc.getLongitude() : 0);
		map.put(LocationsContentProvider.ACCURACY_COL, loc != null ? loc.getAccuracy(): 10000000.0);
		
		
		Uri url = getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);
        assertTrue(url.getPath().endsWith(("/1")));
	}
	public void testRetrieveSpecificPoint() {
		final double PARIS_LAT= 48.8566;
		final double PARIS_LON = 2.3522;
		Location loc = new Location("Network");
		ContentValues map = new ContentValues(); 
		map.put(LocationsContentProvider.TIME_COL, loc != null ? loc.getTime() : System.currentTimeMillis()); 
		map.put(LocationsContentProvider.TYPE_COL, "Network");
		map.put(LocationsContentProvider.LAT_COL, PARIS_LAT );
		map.put(LocationsContentProvider.LON_COL, PARIS_LON );
		map.put(LocationsContentProvider.ACCURACY_COL, loc != null ? loc.getAccuracy(): 10000000.0);
		
		
		Uri url = getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);
        String id = url.getPath().substring(1 + url.getPath().lastIndexOf("/"));
        
        Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, id),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
		assertEquals(cursor.getCount(),1);
		
	}
}
