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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.androidsoft.poi.listener.OnPOITapListener;
import org.androidsoft.poi.model.POI;
import org.androidsoft.poi.ui.adapter.POIAdapter;

/**
 *
 * @author pierre
 */
public abstract class POIListActivity extends Activity implements AdapterView.OnItemClickListener, OnPOITapListener
{
    
    abstract protected int getLayout();
    abstract protected int getListId();
    abstract protected POIAdapter getAdapter();

    /**
     * {@inheritDoc }
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView( getLayout() );
        ListView lvPOIs = (ListView) findViewById( getListId() );
        
        lvPOIs.setAdapter( getAdapter() );
        lvPOIs.setOnItemClickListener( this );
        
    }
    

    /**
     * {@inheritDoc }
     */
    @Override
    public void onItemClick(AdapterView<?> list, View v, int position, long id )
    {
        POIAdapter adapter = (POIAdapter) list.getAdapter();
        POI poi = (POI) adapter.getItem(position);
        onPOITap( poi.getId() );
    }
    
}
    