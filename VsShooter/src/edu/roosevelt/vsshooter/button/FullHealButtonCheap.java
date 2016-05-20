package edu.roosevelt.vsshooter.button;

import android.graphics.Paint;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class FullHealButtonCheap extends LoadoutButton {
    
    public FullHealButtonCheap() {
        super(new Paint(), 0, 140, 150, 50);
        cost = 150;
        text = "Heal all HP\n(150)";
    }
    
    @Override
    protected void doAction() {
        PlayerShip.ship.takeDamage(-255);
    }
    
}
