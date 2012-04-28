/* Copyright (c) 2010-2012 Pierre LEVY androidsoft.org
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.androidsoft.poi.ui.activity;

import org.androidsoft.poi.map.POIOverlayResources;
import org.androidsoft.poi.map.POIOverlay;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.*;
import java.util.List;
import org.androidsoft.poi.listener.OnPOITapListener;
import org.androidsoft.poi.map.GeoUtils;
import org.androidsoft.poi.model.POI;

/**
 *
 * @author pierre
 */
public abstract class POIMapActivity extends MapActivity implements OnPOITapListener
{

    public static final String EXTRA_POINT_LAT = "map_center_lat";
    public static final String EXTRA_POINT_LON = "map_center_lon";
    private static final int ZOOM_DEFAULT = 15;
    private static final int ZOOM_FOCUSED = 19;
    private static final boolean MODE_SATELLITE = false;
    private int mZoom;
    private MapView mMapView;
    private MapController mMapController;
    private LocationManager mLocationManager;
    private GeoPoint mMapCenter;
    private List<Overlay> mMapOverlays;
    private MyLocationOverlay mMyLocationOverlay;
    
    public abstract int getMapViewId();

    public abstract int getLayout();

    public abstract int getMarkerRes();

    public abstract List<POI> getPOIs();

    public abstract POIOverlayResources getOverlayResources();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        mMapView = (MapView) findViewById(getMapViewId());
        mMapView.setBuiltInZoomControls(true);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = mLocationManager.getBestProvider(criteria, true);
        Location location = mLocationManager.getLastKnownLocation(provider);

        double intentLatitude = getIntent().getDoubleExtra(EXTRA_POINT_LAT, 0.0);
        double intentLongitude = getIntent().getDoubleExtra(EXTRA_POINT_LON, 0.0);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mZoom = getZoom();

        if ((intentLatitude != 0.0) && (intentLongitude != 0.0))
        {
            // the activity has been lauched with a commune's coordinate in extras
            mMapCenter = GeoUtils.convertLatLon(intentLatitude, intentLongitude);
            mZoom = getFocusedZoom();
        }
        else if (location != null)
        {
            mMapCenter = GeoUtils.convertGeoPoint(location);
        }
        else
        {
            double lat = 48.87153740744375;
            double lon = 2.342920216006007;
            mMapCenter = GeoUtils.convertLatLon(lat, lon);
        }
        mMapController = mMapView.getController();
        mMapController.setZoom(mZoom);
        mMapView.setSatellite(getSatellite());
        mMapController.animateTo(mMapCenter);

        mMapOverlays = mMapView.getOverlays();
        mMapOverlays.add(getPOIOverlay(mMapView));
        mMyLocationOverlay = getMyLocationOverlay(mMapView);
        mMapOverlays.add(mMyLocationOverlay);

        mMapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mMyLocationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mMyLocationOverlay.disableMyLocation();
    }

    protected int getZoom()
    {
        return ZOOM_DEFAULT;
    }

    protected int getFocusedZoom()
    {
        return ZOOM_FOCUSED;
    }

    protected boolean getSatellite()
    {
        return MODE_SATELLITE;
    }

    protected Overlay getPOIOverlay(MapView mapView)
    {
        Drawable marker = getResources().getDrawable(getMarkerRes());
        POIOverlay overlay = new POIOverlay(marker, mapView, getOverlayResources(), this);
        overlay.addOverlayItems(getPOIs());
        return overlay;
    }

    protected MyLocationOverlay getMyLocationOverlay(MapView mapView)
    {
        return new MyLocationOverlay(this, mapView);
    }
}
