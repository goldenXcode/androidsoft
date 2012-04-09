/* Copyright (c) 2012 Pierre LEVY androidsoft.org
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * POI Location Check Activity - Checks that one location service at least is enabled
 *
 * @author Pierre LEVY
 */
public abstract class POILocationCheckActivity extends Activity
{
    /**
     * Should return the message displayed in the dialogbox when no location service
     * Available.
     * Here is an example : <br />
     * <i> Location services (GPS and network) seem to be disabled.\n 
     * Do you want to enable one of them to use this application ?</i>
     * @return The message 
     */
    protected abstract String getNoLocationServiceMessage();

    @Override
    protected void onResume()
    {
        super.onResume();
        final LocationManager manager = (LocationManager) getSystemService( LOCATION_SERVICE );
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            buildAlertMessageNoLocation();
        }
    }


    private void buildAlertMessageNoLocation()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage( getNoLocationServiceMessage() ).setCancelable(false).setPositiveButton( getString( android.R.string.yes ), new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick( final DialogInterface dialog, final int id)
            {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton( getString( android.R.string.no ) , new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(final DialogInterface dialog, final int id)
            {
                dialog.cancel();
                finish();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
