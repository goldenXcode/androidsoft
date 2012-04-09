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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 *
 * @author pierre
 */
public class POIOverlayItem extends OverlayItem
{
    private int mId;
    
    public POIOverlayItem( GeoPoint point , String title , String description , int id )
    {
        super( point , title , description );
        mId = id;
    }
    
    public int getId()
    {
        return mId;
    }
}
