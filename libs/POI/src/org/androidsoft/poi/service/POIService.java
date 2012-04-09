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
package org.androidsoft.poi.service;

import android.content.Context;
import au.com.bytecode.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.androidsoft.poi.model.POI;

/**
 *
 * @author pierre
 */
public abstract class POIService<T>
{
    public abstract String getAssetFile();

    public abstract T newInstance( String[] record );
    
    private List<T> mCachedList;

    /**
     * Get all POIs loaded from a CSV file stored as an asset resource.
     * @param context The context
     * @return The list of POIs
     */
    public List<T> getPOIs( Context context)
    {
        if( mCachedList != null )
        {
            return mCachedList;
        }
        
        InputStream is = null;
        try
        {
            is = context.getAssets().open( getAssetFile() );
            CSVReader reader = new CSVReader(new InputStreamReader( is ) , ';' );
            List<String[]> records = reader.readAll();
            mCachedList = getPOIs( records );
        }
        catch (IOException ex)
        {
            Logger.getLogger(POIService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(POIService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mCachedList;
      
    }
    
    /**
     * Filter a list of POIs by proximity to a given point
     * @param list The list of POIs
     * @param latitude The latitude of the point
     * @param longitude The longitude of the point
     * @param max Max POIs of the returned list
     * @param radius The maximum distance between the point and the POI
     * @return 
     */
    public static List<POI> getNearestPOI( List<POI> list , double latitude, double longitude, int max , long radius )
    {
        
        List<SortablePOI> listSort = new ArrayList<SortablePOI>();
        for (POI poi : list)
        {
            SortablePOI t = new SortablePOI(poi, latitude, longitude);
            if (t.dist < radius )
            {
                listSort.add(t);
            }
        }

        Collections.sort(listSort);

        List<POI> listNearest = new ArrayList<POI>();
        int count = (listSort.size() > max) ? max : listSort.size();
        for (int i = 0; i < count; i++)
        {
            SortablePOI sp = listSort.get(i);
            POI poi = sp.poi;
            
            poi.setDistance( sp.dist );
            listNearest.add( poi );
        }
        
        return listNearest;
    }
    
    private List<T> getPOIs(List<String[]> records )
    {
        List<T> listPOIs = new ArrayList<T>();
        
        for( String[] record : records )
        {
            T poi = newInstance( record );
            listPOIs.add(poi);
        }
        return listPOIs;
    }    
    
}
