package edu.roosevelt.vsshooter.button;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import edu.roosevelt.vsshooter.HoldableButton;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public abstract class LoadoutButton extends HoldableButton {
    
    protected int cost;
    public static boolean inGame;
    public boolean inLoadout = false;
    public static ArrayList<LoadoutButton> activeButtons = new ArrayList<LoadoutButton>();
    public int sourceX;
    public int sourceY;
    protected String text;
    
    public LoadoutButton(Paint paint, int x, int y, int width, int height) {
        super(paint, x, y, width, height);
        sourceX = x;
        sourceY = y;
        z = -10;
        paint.setColor(Color.GRAY);
        foregroundPaint.setColor(Color.YELLOW);
        tapForegroundPaint.setColor(Color.CYAN);
    }
    
    public void onAttach(Stage s) {
        super.onAttach(s);
        setText(text);
    }
    
    @Override
    protected void endTap() {
        super.endTap();
        if (!inGame) {
            if (!inLoadout) {
                int used;
                if (activeButtons.isEmpty()) {
                    used = 0;
                } else {
                    LoadoutButton last = activeButtons.get(activeButtons.size() - 1);
                    used = last.getTrueX() + last.getWidth();
                }
                if (used + width <= 300) {
                    this.setX(used);
                    this.setY(412);
                    activeButtons.add(this);
                    inLoadout = true;
                }
            }
        } else {
            if (PlayerShip.ship.payEnergy(cost)) {
                doAction();
            }
        }
    }
    
    protected abstract void doAction();
    
}
