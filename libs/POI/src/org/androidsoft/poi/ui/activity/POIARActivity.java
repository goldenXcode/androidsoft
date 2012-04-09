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

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.data.NetworkDataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.*;
import org.androidsoft.poi.ar.POIMarker;
import org.androidsoft.poi.listener.OnPOITapListener;

/**
 *
 * @author pierre
 */
public abstract class POIARActivity extends AugmentedReality implements OnPOITapListener
{

    private static final String TAG = "Demo";
    private static final String locale = Locale.getDefault().getLanguage();
    private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
    private static final ThreadPoolExecutor exeService = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, queue);
    private static final Map<String, NetworkDataSource> sources = new ConcurrentHashMap<String, NetworkDataSource>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart()
    {
        super.onStart();

        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocationChanged(Location location)
    {
        super.onLocationChanged(location);

        updateData(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void markerTouched(Marker marker)
    {
        POIMarker m = (POIMarker) marker;
        onPOITap(m.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateDataOnZoom()
    {
        super.updateDataOnZoom();
        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
    }

    private void updateData(final double lat, final double lon, final double alt)
    {
        try
        {
            exeService.execute(
                    new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            for (NetworkDataSource source : sources.values())
                            {
                                download(source, lat, lon, alt);
                            }
                        }
                    });
        }
        catch (RejectedExecutionException rej)
        {
            Log.w(TAG, "Not running new download Runnable, queue is full.");
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception running download Runnable.", e);
        }
    }

    private static boolean download(NetworkDataSource source, double lat, double lon, double alt)
    {
        if (source == null)
        {
            return false;
        }

        String url;
        try
        {
            url = source.createRequestURL(lat, lon, alt, ARData.getRadius(), locale);
        }
        catch (NullPointerException e)
        {
            return false;
        }

        List<Marker> markers;
        try
        {
            markers = source.parse(url);
        }
        catch (NullPointerException e)
        {
            return false;
        }

        ARData.addMarkers(markers);
        return true;
    }
}
