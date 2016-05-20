package edu.roosevelt.vsshooter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import edu.roosevelt.vsshooter.button.AuraButton;
import edu.roosevelt.vsshooter.button.FullHealButton;
import edu.roosevelt.vsshooter.button.FullHealButtonCheap;
import edu.roosevelt.vsshooter.button.FullHealButtonSmall;
import edu.roosevelt.vsshooter.button.HealButton;
import edu.roosevelt.vsshooter.button.HealButtonCheap;
import edu.roosevelt.vsshooter.button.HealButtonSmall;
import edu.roosevelt.vsshooter.button.LaserButton;
import edu.roosevelt.vsshooter.button.LoadoutButton;
import edu.roosevelt.vsshooter.button.PlayButton;
import edu.roosevelt.vsshooter.button.PlayWithLoadoutButton;
import edu.roosevelt.vsshooter.button.ResetButton;
import edu.roosevelt.vsshooter.button.SetLoadoutButton;
import edu.roosevelt.vsshooter.button.ShareButton;
import edu.roosevelt.vsshooter.button.TurretButton;
import edu.roosevelt.vsshooter.button.WaveButton;
import edu.roosevelt.vsshooter.ship.EnemyShip;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class VsShooter extends Activity { 
	
	private static Context context;
	private static VsShooter main;
	public static Bitmap laser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		main = this;
		setContentView(R.layout.activity_vs_shooter);
		context = getApplicationContext();
		laser = BitmapFactory.decodeResource(getResources(), R.drawable.bullet11);
		//if (savedInstanceState == null) { 
		//TODO: fix
		    GameFragment f = new MenuFragment();
			getFragmentManager().beginTransaction().add(R.id.container, f).commit();
			f.start();
		//}
	}
	
	public void beginGame()
	{
        GameFragment f = new GameFieldFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, f).commit();
        f.start();
        for (LoadoutButton a : LoadoutButton.activeButtons)
        {
        f.f.getStage().addChild(a);
        }
        LoadoutButton.inGame = true;
        EnemyShip.numBeaten = 0;
        EnemyShip.lastPoints = 0;
	}
	public void chooseLoadout()
    {
        GameFragment f = new LoadoutFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, f).commit();
        f.start();
        LoadoutButton.inGame = false;
    }
	public void showMenu()
    {
        GameFragment f = new MenuFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, f).commit();
        f.start();
        LoadoutButton.inGame = false;
    }
	
	public static VsShooter getMain()
	{
	    return main;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vs_shooter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up edu.roosevelt.vsshooter.button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static Context getContext() {
		return context;
	}

	public static abstract class GameFragment extends Fragment
	{
	    GameField f;
	    
	    public void start()
	    {
	        f.start();
	    }
	}
	public static class GameFieldFragment extends GameFragment {

		public GameFieldFragment() {

            f = new GameField(VsShooter.getContext());
            Stage s = new Stage(f);
          Actor i = new PlayerShip(150, 370, 30, 30);
          s.realAddChild(i);
          Actor j = EnemyShip.generate();
          s.realAddChild(j);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			//View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
			//return rootView;
		    return f;
		}
	}
	public static class MenuFragment extends GameFragment {

        public MenuFragment() { 

            f = new GameField(VsShooter.getContext());
            Stage s = new Stage(f);
            Button b = new PlayButton(100,100,100,50);
            s.realAddChild(b);
            b = new SetLoadoutButton(100,200,100,50);
            s.realAddChild(b);
            b = new ShareButton(100,300,100,50);
            s.realAddChild(b);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
            //return rootView;
            return f;
        }
    }
	public static class LoadoutFragment extends GameFragment {

        public LoadoutFragment() {

            f = new GameField(VsShooter.getContext());
            Stage s = new Stage(f);
            LoadoutButton.activeButtons.clear();
            Button b = new FullHealButton();
            s.realAddChild(b);
            b = new FullHealButtonSmall();
            s.realAddChild(b);
            b = new FullHealButtonCheap();
            s.realAddChild(b);
            b = new HealButton();
            s.realAddChild(b);
            b = new HealButtonSmall();
            s.realAddChild(b);
            b = new HealButtonCheap();
            s.realAddChild(b);
            b = new LaserButton();
            s.realAddChild(b);
            b = new TurretButton();
            s.realAddChild(b);
            b = new WaveButton();
            s.realAddChild(b);
            b = new AuraButton();
            s.realAddChild(b);
            b = new ResetButton();
            s.realAddChild(b);
            b = new PlayWithLoadoutButton(0, 350, 300, 50);
            s.realAddChild(b);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.fragment_main_screen, container, false);
            //return rootView;
            return f;
        }
    }
}
