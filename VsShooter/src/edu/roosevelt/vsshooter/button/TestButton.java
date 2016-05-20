package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import edu.roosevelt.vsshooter.HoldableButton;

public class TestButton extends HoldableButton {
    
    public TestButton(int x, int y, int width, int height) {
        super(new Paint(), x, y, width, height);
        paint.setColor(Color.GRAY);
        foregroundPaint.setColor(Color.YELLOW);
        tapForegroundPaint.setColor(Color.BLUE);
    }
    
    @Override
    protected void startTap() {
        Log.d("TestButton", "Start");
    }
    
    @Override
    protected void cancelTap() {
        Log.d("TestButton", "Cancel");
        
    }
    
    @Override
    protected void resumeTap() {
        Log.d("TestButton", "Resume");
        
    }
    
    @Override
    protected void endTap() {
        Log.d("TestButton", "End");
        setText(toPrint.get(0)+"n");
    }
    
}
