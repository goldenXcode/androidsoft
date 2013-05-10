package org.androidsoft.games.utils.level;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
{

    static final int NUM_ITEMS = 5;
    static int[] sColorsId = {
        R.color.blue,
        R.color.violet,
        R.color.green,
        R.color.orange,
        R.color.red
    };

    static int[] sDarkColorsId = {
        R.color.dark_blue,
        R.color.dark_violet,
        R.color.dark_green,
        R.color.dark_orange,
        R.color.dark_red
    };
    
    static int[] sColors = new int[ sColorsId.length];
    static int[] sDarkColors = new int[sDarkColorsId.length];
    
    
    private static int[] sButtons =
    {
        R.drawable.button_locked,
        R.drawable.button_new,
        R.drawable.button_1star,
        R.drawable.button_2stars,
        R.drawable.button_3stars
    };
    
    static int[] sButtonShape = {
        R.drawable.button_blue,
        R.drawable.button_violet,
        R.drawable.button_green,
        R.drawable.button_orange,
        R.drawable.button_red,
    };
    
    
    
    MyAdapter mAdapter;
    ViewPager mPager;
    static List<Level> mLevels;
    static Context mContext;
    static Bitmap mBitmapLock;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.level_pager);

        mAdapter = new MyAdapter(getFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        Resources res = getResources();
        initColors( res , sColorsId , sColors );
        initColors( res , sDarkColorsId , sDarkColors );
        mBitmapLock = BitmapFactory.decodeResource( res , R.drawable.lock );

        mContext = getApplicationContext();

    }
    
    private void initColors( Resources res, int[] ids , int[] colors )
    {
        for( int i = 0 ; i < ids.length ; i++ )
        {
            colors[i] = res.getColor( ids[i] );
        }
    }

    private static List<Level> getInitLevelList(int grid)
    {
        List<Level> list = new ArrayList<Level>();
        for (int i = 0; i < getLevelPerGrid(); i++)
        {
            int status = ((i == 0) && (grid == 0)) ? 0 : -1;
            list.add(new Level(grid, i + 1, status, 0));

        }
        return list;

    }

    private static int getLevelPerGrid()
    {
        return 12;
    }

    public static class MyAdapter extends FragmentPagerAdapter
    {

        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position)
        {
            return LevelFragment.newInstance(position);
        }
    }

    public static class LevelFragment extends Fragment
    {

        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num" as an
         * argument.
         */
        static LevelFragment newInstance(int num)
        {
            LevelFragment f = new LevelFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its instance
         * number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState)
        {
            View v = inflater.inflate(R.layout.main, container, false);
            View tv = v.findViewById(R.id.text);
            tv.setBackgroundColor(sDarkColors[ mNum]);
            ((TextView) tv).setText("Fragment #" + mNum);
            GridView gv = (GridView) v.findViewById(R.id.level_grid1);
            gv.setAdapter((ListAdapter) new LevelAdapter(mContext, getInitLevelList(mNum), sButtons , sButtonShape[ mNum ] , mBitmapLock ));
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState)
        {
            super.onActivityCreated(savedInstanceState);
            /*setListAdapter(new ArrayAdapter<String>(getActivity(),
             android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));*/
        }
    }
}
