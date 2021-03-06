package org.jimhopp.GPSvsNetwork.test;

import junit.framework.Assert;

import org.jimhopp.GPSvsNetwork.provider.LocationContentProvider;
import org.jimhopp.GPSvsNetwork.provider.LocationsContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.provider.BaseColumns;
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
	public void testRetrieveAllPoints() {
		final int ITERATIONS = 10;
		final double PARIS_LAT= 48.8566;
		final double PARIS_LON = 2.3522;
		final double ACCURACY = 100.0;
		long time = System.currentTimeMillis();
		for (int i=0;i<ITERATIONS;i++) {
		ContentValues map = new ContentValues(); 
			map.put(LocationsContentProvider.TIME_COL, time + 100 * i); 
			map.put(LocationsContentProvider.TYPE_COL, "Network");
			map.put(LocationsContentProvider.LAT_COL, PARIS_LAT + 10000.0 * i);
			map.put(LocationsContentProvider.LON_COL, PARIS_LON + 10000.0 * i);
			map.put(LocationsContentProvider.ACCURACY_COL, ACCURACY + 10 * i);
			getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);
		}
        
        Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "/all"),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
		assertEquals(cursor.getCount(),ITERATIONS);
		
	}
	public void testValuesRetrieved() {
		final double PARIS_LAT= 48.8566;
		final double PARIS_LON = 2.3522;
		final double ACCURACY = 10000000.0;
		long time = System.currentTimeMillis();
		ContentValues map = new ContentValues(); 
		map.put(LocationsContentProvider.TIME_COL, time); 
		map.put(LocationsContentProvider.TYPE_COL, "Network");
		map.put(LocationsContentProvider.LAT_COL, PARIS_LAT );
		map.put(LocationsContentProvider.LON_COL, PARIS_LON );
		map.put(LocationsContentProvider.ACCURACY_COL, ACCURACY);
		
		Uri url = getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);
        String id = url.getPath().substring(1 + url.getPath().lastIndexOf("/"));
        
        Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, id),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
		assertEquals(cursor.getCount(),1);
		assertTrue(cursor.moveToFirst());
		assertEquals(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),Integer.parseInt(id));
		assertEquals(cursor.getString(cursor.getColumnIndex(LocationsContentProvider.TYPE_COL)),
				"Network");
		assertEquals(cursor.getLong(cursor.getColumnIndex(LocationsContentProvider.TIME_COL)),
				time);
		assertEquals(cursor.getDouble(cursor.getColumnIndex(LocationsContentProvider.LAT_COL)), 
				PARIS_LAT, 0.001d);
		assertEquals(cursor.getDouble(cursor.getColumnIndex(LocationsContentProvider.LON_COL)), 
				PARIS_LON, 0.001d);
		assertEquals(cursor.getDouble(cursor.getColumnIndex(LocationsContentProvider.ACCURACY_COL)), 
				ACCURACY, 0.001d);
		
	}
	public void testRetrieveLastNetworkPoint() {
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
		
		Location locgps = new Location("GPS");
		map = new ContentValues(); 
		map.put(LocationsContentProvider.TIME_COL, loc != null ? loc.getTime() : System.currentTimeMillis()); 
		map.put(LocationsContentProvider.TYPE_COL, "GPS");
		map.put(LocationsContentProvider.LAT_COL, PARIS_LAT );
		map.put(LocationsContentProvider.LON_COL, PARIS_LON );
		map.put(LocationsContentProvider.ACCURACY_COL, loc != null ? loc.getAccuracy(): 10000000.0);
		
		
		url = getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);

        
        Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "lastnetwork"),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
        assertTrue(cursor.moveToFirst());
		assertEquals(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),1);
		
	}
	public void testRetrieveLastGPSPoint() {
		final double PARIS_LAT= 48.8566;
		final double PARIS_LON = 2.3522;
		Location loc = new Location("GPS");
		ContentValues map = new ContentValues(); 
		map.put(LocationsContentProvider.TIME_COL, loc != null ? loc.getTime() : System.currentTimeMillis()); 
		map.put(LocationsContentProvider.TYPE_COL, "GPS");
		map.put(LocationsContentProvider.LAT_COL, PARIS_LAT );
		map.put(LocationsContentProvider.LON_COL, PARIS_LON );
		map.put(LocationsContentProvider.ACCURACY_COL, loc != null ? loc.getAccuracy(): 10000000.0);
		
		
		Uri url = getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);
		String id = url.getPath().substring(1 + url.getPath().lastIndexOf("/"));
		
		Location locgps = new Location("Network");
		map = new ContentValues(); 
		map.put(LocationsContentProvider.TIME_COL, loc != null ? loc.getTime() : System.currentTimeMillis()); 
		map.put(LocationsContentProvider.TYPE_COL, "Network");
		map.put(LocationsContentProvider.LAT_COL, PARIS_LAT );
		map.put(LocationsContentProvider.LON_COL, PARIS_LON );
		map.put(LocationsContentProvider.ACCURACY_COL, loc != null ? loc.getAccuracy(): 10000000.0);
		
		
		url = getMockContentResolver().insert(LocationContentProvider.LOCATIONS_URI, map);

        
        Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "lastgps"),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
        assertTrue(cursor.moveToFirst());
		assertEquals(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),1);
		
	}
	public void testDeleteSinglePoint() {
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
		try {
			getMockContentResolver().delete(Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, id),
					    null, null);
			Assert.fail("expected runtime exception for unsupported function but got no exception");
		} catch (RuntimeException e) {	}
	}
	
	public void testDeleteAll() {
		testRetrieveAllPoints();
		try {
			getMockContentResolver().delete(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "/all"),      //uri                                   //selection, we want all rows 
					null,                                       //projections
					null);
		} catch (RuntimeException e) {
			fail("deleting all rows threw an exception and shouldn't have: " 
					+ e.getLocalizedMessage());
		}
		Cursor cursor = getMockContentResolver().query(
				Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "/all"),      //uri                                   //selection, we want all rows 
				null,                                       //projections
				null,                                       //select stmt
				null,                                       //selection args
				null);
		assertEquals(cursor.getCount(),0);	
	}
	public void testUpdateUnsupported() {
		try {
			int url = getMockContentResolver().update(Uri.withAppendedPath(LocationContentProvider.LOCATIONS_URI, "1"),
					    null, null,null);
			Assert.fail("expected runtime exception for unsupported function but got no exception");
		} catch (RuntimeException e) {	}
	}
}
