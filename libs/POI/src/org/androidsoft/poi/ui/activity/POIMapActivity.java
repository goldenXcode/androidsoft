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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.*;
import java.util.List;
import org.androidsoft.poi.model.POI;

/**
 *
 * @author pierre
 */
public abstract class POIMapActivity extends MapActivity
{
    private static final int ZOOM_DEFAULT = 18;
    private static final boolean MODE_SATELLITE = false;

    private MapView mMapView;
    private MapController mMapController;
    private LocationManager mLocationManager;
    private GeoPoint mPoint;
    private List<Overlay> mMapOverlays;
    private POIOverlay mItemizedOverlay;

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
        mMapController = mMapView.getController();
        mMapController.setZoom( getZoom());
        mMapView.setSatellite( getSatellite() );
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = mLocationManager.getBestProvider(criteria, true);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null)
        {
            mPoint = convertGeoPoint(location);
        }
        else
        {
            double lat = 48.87153740744375;
            double lon = 2.342920216006007;
            mPoint = convertLatLon(lat, lon);
        }
        mMapController.animateTo(mPoint);

        setPOIOverlay();

        mMapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }


    protected int getZoom()
    {
        return ZOOM_DEFAULT;
    }
    
    protected boolean getSatellite()
    {
        return MODE_SATELLITE;
    }

    private GeoPoint convertGeoPoint(Location location)
    {
        if (location != null)
        {
            return convertLatLon(location.getLatitude(), location.getLongitude());
        }
        return null;

    }

    private GeoPoint convertLatLon(double latitude, double longitude)
    {
        int lat = (int) (latitude * 1E6);
        int lng = (int) (longitude * 1E6);
        return new GeoPoint(lat, lng);

    }

    private void setPOIOverlay()
    {
        mMapOverlays = mMapView.getOverlays();
        Drawable marker = getResources().getDrawable(getMarkerRes());
        mItemizedOverlay = new POIOverlay(marker , mMapView , getOverlayResources() );

        for (POI poi : getPOIs() )
        {
            GeoPoint point = new GeoPoint( (int) (poi.getLatitude() * 1E6) , (int) (poi.getLongitude() * 1E6) );
            mItemizedOverlay.addOverlay( new OverlayItem( point , poi.getTitle() , poi.getDesciption()));
        }
        mMapOverlays.add(mItemizedOverlay);

        mMapView.postInvalidate();
    }

}
