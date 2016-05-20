package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import edu.roosevelt.vsshooter.HoldableButton;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;

public class PlayWithLoadoutButton extends HoldableButton {
    
    public PlayWithLoadoutButton(int x, int y, int width, int height) {
        super(new Paint(), x, y, width, height);
        // TODO Auto-generated constructor stub
        paint.setColor(Color.GRAY);
        foregroundPaint.setColor(Color.YELLOW);
        tapForegroundPaint.setColor(Color.GREEN);
    }
    
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        setText("Play Game");
    }
    
    public void endTap()
    {
        super.endTap();
        Log.e("Playbutton", "tapped");
        VsShooter.getMain().beginGame();
    }
    
}
