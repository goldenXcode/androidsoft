package com.jwetherell.augmented_reality.ui;

import android.graphics.Color;

/**
 * This class holds radar attributes
 * @author Pierre Levy
 */
public class CustomParams
{
    private static int radarColor = Color.argb(100, 0, 0, 200);
    private static int radarLineColor = Color.argb(150,0,0,220);
    private static int radarTextColor = Color.rgb(255,255,255);
    

    /**
     * @return the radarLineColor
     */
    public static int getRadarLineColor()
    {
        return radarLineColor;
    }

    /**
     * @param aRadarLineColor the radarLineColor to set
     */
    public static void setRadarLineColor(int aRadarLineColor)
    {
        radarLineColor = aRadarLineColor;
    }

    /**
     * @return the radarColor
     */
    public static int getRadarColor()
    {
        return radarColor;
    }

    /**
     * @param aRadarColor the radarColor to set
     */
    public static void setRadarColor(int aRadarColor)
    {
        radarColor = aRadarColor;
    }

    /**
     * @return the radarTextColor
     */
    public static int getRadarTextColor()
    {
        return radarTextColor;
    }

    /**
     * @param aRadarTextColor the radarTextColor to set
     */
    public static void setRadarTextColor(int aRadarTextColor)
    {
        radarTextColor = aRadarTextColor;
    }
    
}
