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
package org.androidsoft.poi.ar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import com.jwetherell.augmented_reality.data.DataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.ArrayList;
import java.util.List;
import org.androidsoft.poi.model.POI;
import org.androidsoft.poi.service.POIService;

/**
 * Data Source of POIs
 * @author pierre
 */
public class POIDataSource extends DataSource
{

    private List<Marker> mCachedMarkers = new ArrayList<Marker>();
    private static Bitmap icon = null;
    private static POIService mServicePOI;
    private static Context mContext;

    public POIDataSource( Context context , POIService service , int iconRes )
    {
        mContext = context;
        Resources res = context.getResources();
        if (res == null)
        {
            throw new NullPointerException();
        }

        createIcon( res , iconRes );
        mServicePOI = service;
    }

    protected final void createIcon( Resources res , int iconRes )
    {
        if (res == null)
        {
            throw new NullPointerException();
        }

        icon = BitmapFactory.decodeResource(res, iconRes );
    }

    public List<Marker> getMarkers()
    {
        List<POI> listPOIs = mServicePOI.getPOIs( mContext );
        for( POI poi : listPOIs )
        {
            Marker marker = new POIMarker( poi.getTitle(), poi.getLatitude(), poi.getLongitude(), 0, Color.DKGRAY, icon , poi.getId() );
            mCachedMarkers.add(marker);
        }

        return mCachedMarkers;
    }
}
