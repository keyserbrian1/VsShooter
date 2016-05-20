package edu.roosevelt.vsshooter.button;

import com.javaLibs.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import edu.roosevelt.vsshooter.HoldableButton;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;

public class PlayButton extends HoldableButton {
    
    public PlayButton(int x, int y, int width, int height) {
        super(new Paint(), x, y, width, height);
        // TODO Auto-generated constructor stub
        paint.setColor(Color.GRAY);
        foregroundPaint.setColor(Color.YELLOW);
        tapForegroundPaint.setColor(Color.GREEN);
    }
    
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        setText("Play Game\n(Random Loadout)");
    }
    
    public void endTap()
    {
        super.endTap();
        Log.e("Playbutton", "tapped");
        LoadoutButton[] buttons = new LoadoutButton[10];
        buttons[0]=new FullHealButton();
        buttons[1]=new FullHealButtonSmall();
        buttons[2]=new FullHealButtonCheap();
        buttons[3]=new HealButton();
        buttons[4]=new HealButtonSmall();
        buttons[5]=new HealButtonCheap();
        buttons[6]=new LaserButton();
        buttons[7]=new TurretButton();
        buttons[8]=new WaveButton();
        buttons[9]=new AuraButton();
        Random rnd = new Random();
        rnd.shuffleArray(buttons);
        LoadoutButton.activeButtons.clear();
        for(LoadoutButton b : buttons)
        {
            int used;
            if (LoadoutButton.activeButtons.isEmpty()) {
                used = 0;
            } else {
                LoadoutButton last = LoadoutButton.activeButtons.get(LoadoutButton.activeButtons.size() - 1);
                used = last.getTrueX() + last.getWidth();
            }
            if (used + b.getWidth() <= 300) {
                b.setX(used);
                b.setY(412);
                LoadoutButton.activeButtons.add(b);
                b.inLoadout = true;
            }
        }
        VsShooter.getMain().beginGame();
    }
    
}
