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
package org.androidsoft.poi.model;

import android.graphics.drawable.Drawable;

/**
 * Abstract POI
 * @author pierre
 */
public abstract class AbstractPOI implements POI
{
    private int mId;
    private double mLat;
    private double mLon;
    private long mDistance;
    

    /**
     * @return the id
     */
    public int getId()
    {
        return mId;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        mId = id;
    }
    
    /**
     * @return the _lat
     */
    public double getLatitude()
    {
        return mLat;
    }

    /**
     * @param lat the _lat to set
     */
    public void setLatitude(double lat)
    {
        mLat = lat;
    }

    /**
     * @return the _lon
     */
    public double getLongitude()
    {
        return mLon;
    }

    /**
     * @param lon the _lon to set
     */
    public void setLongitude(double lon)
    {
        mLon = lon;
    }
    
    public Drawable getIcon()
    {
        return null;
    }

    public long getDistance()
    {
        return mDistance;
    }
    
    public void setDistance( long distance )
    {
        mDistance = distance;
    }
    
    protected final Integer getInt( String src )
    {
        try
        {
            return  Integer.parseInt(src);
        }
        catch( NumberFormatException e )
        {
            return null;
        }
    }

    protected final Double getDouble( String src )
    {
        try
        {
            return  Double.parseDouble(src);
        }
        catch( NumberFormatException e )
        {
            return null;
        }
    }

}
