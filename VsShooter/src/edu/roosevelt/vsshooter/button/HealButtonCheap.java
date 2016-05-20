package edu.roosevelt.vsshooter.button;

import android.graphics.Paint;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class HealButtonCheap extends LoadoutButton {
    
    public HealButtonCheap() {
        super(new Paint(), 0, 85, 150, 50);
        cost = 75;
        text = "Heal 100 HP\n(75)";
    }
    
    @Override
    protected void doAction() {
        PlayerShip.ship.takeDamage(-100);
    }
    
}
