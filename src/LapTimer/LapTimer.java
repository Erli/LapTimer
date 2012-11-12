package LapTimer;

import com.LapTimer.*;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LapTimer extends Activity

{
	TextView tv = null;
	String actualLocation = null;
	double maximum;

	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)

	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);
		// MapView MapView = (MapView) findViewById (R.id.mapview);
		// mapView.setBuiltInZoomControls (true);
		final Button button = (Button) findViewById(R.id.start);
		final Button stop = (Button) findViewById(R.id.Stop);

		button.setText("Start");
		stop.setText("Stop");

		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				start = false;
				button.setVisibility(View.INVISIBLE);
				stop.setVisibility(View.VISIBLE);

			}
		});

		button.setText("Start");
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View a) {
				start = true;
				stop.setVisibility(View.INVISIBLE);
				button.setVisibility(View.VISIBLE);

			}
		});

	}

	private int pom;
	private long cas = 0, cas2 = 0;
	private double prumernaRychlost = 0, doba = 0, dobaBehu = 0;
	private float draha;
	private String rychlostS, rych, latitude, longitude, akcelerace;
	private boolean poprve = true, start = false;

	public class MyLocationListener implements LocationListener

	{

		LocationManager lm = null;

		Location actualLocation = null;

		public void LapTimer(Context context) {
			lm = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);

			if (lm != null) {
				actualLocation = lm
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			}

			startLocationUpdates();
		}

		public void startLocationUpdates() {
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1l, 0, this);
		}

		public void stopLocationUpdates() {
			lm.removeUpdates(this);
		}

		public void onLocationChanged(Location loc)

		{
if (start){
			actualLocation = loc;

			cas = actualLocation.getTime();
			if (poprve) {
				cas = actualLocation.getTime();
				cas2 = actualLocation.getTime();
				poprve = false;
			}

			doba = (cas - cas2) / 1000;
			cas2 = cas;

			dobaBehu = dobaBehu + doba;
			draha = draha + (float) (doba * actualLocation.getSpeed());

			prumernaRychlost = ((int) (((draha / dobaBehu) / 0.277) * 10) * 0.1);

			double rychlost = actualLocation.getSpeed() / 0.277;
			if (rychlost > maximum) {
				maximum = rychlost;
			}
			rychlostS = String.valueOf(((int) (rychlost * 10)) * 0.1);
			rych = String.valueOf(((int) (maximum * 10)) * 0.1);

			if (pom < (int) rychlost) {
				akcelerace = "zrychleni";
			}
			if (pom == (int) rychlost) {
				akcelerace = "konstantni rychlost";
			}
			if (pom > (int) rychlost) {
				akcelerace = "zpomalení";
			}
			pom = (int) rychlost;

			setContentView(R.layout.activity_main);
			// maximalka
			((TextView) findViewById(R.id.maximalka)).setText("Maximalka"
					+ rych + " km/h");
			// rychlost
			((TextView) findViewById(R.id.rychlost)).setText("Rychlost"
					+ rychlostS + "km/h");
			// latitude
			latitude = String.valueOf(loc.getLatitude());
			((TextView) findViewById(R.id.latitude)).setText("Latitude"
					+ latitude);
			// longtitude
			longitude = String.valueOf(loc.getLongitude());
			((TextView) findViewById(R.id.longitude)).setText("Longitude"
					+ longitude);

			((TextView) findViewById(R.id.akcelerace)).setText(akcelerace);

			((TextView) findViewById(R.id.draha)).setText("Celkova draha:"
					+ draha + "m");

			((TextView) findViewById(R.id.dobaBehu)).setText("Doba behu:"
					+ dobaBehu + "s");

			((TextView) findViewById(R.id.prumernaRychlost))
					.setText("Prùmìrná rychlost:" + prumernaRychlost + "km/h");

			/*
			 * loc.getLongitude(); String Text = "My current location is: " +
			 * "Latitud = " + loc.getLatitude() + "Longitud = " +
			 * loc.getLongitude(); Toast.makeText(getApplicationContext(), Text,
			 * Toast.LENGTH_SHORT) .show();
			 * Toast.makeText(getApplicationContext(), clock,
			 * Toast.LENGTH_SHORT) .show(); list.add(Text);
			 */
}

		}

		public void onProviderDisabled(String provider)

		{

			Toast.makeText(getApplicationContext(),

			"Gps Disabled",

			Toast.LENGTH_SHORT).show();

			// maximalka
			((TextView) findViewById(R.id.maximalka)).setText("Maximalka "
					+ rych + "km/h");
			// rychlost
			((TextView) findViewById(R.id.rychlost)).setText("Rychlost"
					+ rychlostS + "km/h");
			// latitude

			((TextView) findViewById(R.id.latitude)).setText("Latitude"
					+ latitude);
			// longtitude

			((TextView) findViewById(R.id.longitude)).setText("Longitude"
					+ longitude);
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), "Gps Enabled",
					Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras)

		{

		}

	}/* End of Class MyLocationListener */

}