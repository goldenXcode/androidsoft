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
package org.androidsoft.poi.ui.adapter;

import android.app.Activity;
import android.widget.BaseAdapter;
import java.util.List;
import org.androidsoft.poi.model.POI;

/**
 * POI Adapter used by list views
 * @author pierre
 */
public abstract class POIAdapter extends BaseAdapter
{
    private List<POI> mListPOI;
    protected Activity mActivity;

    /**
     * Constructor
     * @param activity
     * @param list 
     */
    public POIAdapter( Activity activity , List<POI> list )
    {
        mActivity = activity;
        mListPOI = list;
    }
    
    /**
     * {@inheritDoc }
     */
    public int getCount()
    {
        return mListPOI.size();
    }

    /**
     * {@inheritDoc }
     */
    public Object getItem(int  position )
    {
        return mListPOI.get( position );
    }

    /**
     * {@inheritDoc }
     */
    public long getItemId(int position )
    {
        return ( (POI) mListPOI.get( position )).getId();
    }

    
}
