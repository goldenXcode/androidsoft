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
package org.androidsoft.poi.map;

import org.androidsoft.poi.listener.OnPOITapListener;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import com.readystatesoftware.mapviewballoons.BalloonOverlayResources;
import java.util.ArrayList;
import java.util.List;
import org.androidsoft.poi.model.POI;

/**
 *
 * @author pierre
 */
public class POIOverlay extends BalloonItemizedOverlay<POIOverlayItem>
{

    private ArrayList<POIOverlayItem> mOverlays;
    private POIOverlayResources mRes;
    private OnPOITapListener mListener;

    public POIOverlay(Drawable defaultMarker, MapView mapView, POIOverlayResources res , OnPOITapListener listener )
    {
        super(boundCenterBottom(defaultMarker), mapView);
        mOverlays = new ArrayList<POIOverlayItem>();
        mRes = res;
        mListener = listener;
        setBalloonBottomOffset( res.getBottomOffset() );
        populate();
    }

    public void addOverlayItem(POIOverlayItem item)
    {
        mOverlays.add(item);
        populate();
    }
    
    public void addOverlayItems( List<POI> listPOI )
    {
        for (POI poi : listPOI )
        {
            GeoPoint point = new GeoPoint( (int) (poi.getLatitude() * 1E6) , (int) (poi.getLongitude() * 1E6) );
            POIOverlayItem item = new POIOverlayItem( point , poi.getTitle() , poi.getDescription() , poi.getId());
            mOverlays.add(item);
        }
        populate();
    }

    @Override
    protected POIOverlayItem createItem(int i)
    {
        return mOverlays.get(i);
    }

    // Removes overlay item i
    public void removeItem(int i)
    {
        mOverlays.remove(i);
        populate();
    }

    // Returns present number of items in list
    @Override
    public int size()
    {
        return mOverlays.size();
    }

    @Override
    public int getBalloonInnerLayoutId()
    {
        return mRes.getInnerLayoutId();
    }

    @Override
    public int getBalloonMainLayoutId()
    {
        return mRes.getMainLayoutId();
    }

    @Override
    public BalloonOverlayResources getBalloonOverlayResources()
    {
        BalloonOverlayResources res = new BalloonOverlayResources();
        res.setLayout(mRes.getLayout());
        res.setItemTitleId(mRes.getItemTitleId());
        res.setItemSnippetId(mRes.getItemSnippetId());
        res.setCloseId(mRes.getCloseId());
        return res;
    }

    @Override
    protected boolean onBalloonTap(int index, POIOverlayItem item)
    {
        mListener.onPOITap( item.getId() );
        return super.onBalloonTap(index, item);
    }
    
    
}