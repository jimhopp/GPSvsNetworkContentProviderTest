package org.jimhopp.GPSvsNetwork.test;

import org.jimhopp.GPSvsNetwork.provider.LocationContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

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
}
