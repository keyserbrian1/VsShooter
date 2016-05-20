package edu.roosevelt.vsshooter.button;

import android.graphics.Paint;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class HealButtonSmall extends LoadoutButton {
    
    public HealButtonSmall() {
        super(new Paint(), 235, 85, 50, 50);
        cost = 200;
        text = "Heal 100 HP\n(200)";
    }
    
    @Override
    protected void doAction() {
        PlayerShip.ship.takeDamage(-100);
    }
    
}
