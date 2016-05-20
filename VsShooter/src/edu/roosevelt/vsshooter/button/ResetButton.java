package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import edu.roosevelt.vsshooter.HoldableButton;
import edu.roosevelt.vsshooter.Stage;

public class ResetButton extends HoldableButton {
    
    public ResetButton() {
        super(new Paint(), 0, 295, 300, 50);
        // TODO Auto-generated constructor stub
        paint.setColor(Color.GRAY);
        foregroundPaint.setColor(Color.YELLOW);
        tapForegroundPaint.setColor(Color.GREEN);
    }
    
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        setText("Reset Loadout");
    }
    
    public void endTap()
    {
        super.endTap();
        Log.e("Playbutton", "tapped");
        for(LoadoutButton b : LoadoutButton.activeButtons)
        {
            b.inLoadout = false;
            b.setX(b.sourceX);
            b.setY(b.sourceY);
        }
        LoadoutButton.activeButtons.clear();
    }
    
}
