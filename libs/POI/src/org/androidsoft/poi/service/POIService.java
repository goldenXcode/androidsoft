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
    
    public List<T> getPOIs( Context context)
    {
        InputStream is = null;
        try
        {
            is = context.getAssets().open( getAssetFile() );
            CSVReader reader = new CSVReader(new InputStreamReader( is ) , ';' );
            List<String[]> records = reader.readAll();
            return getPOIs( records );
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
        return null;
      
    }
    
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
    
    List<T> getPOIs(List<String[]> records )
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
