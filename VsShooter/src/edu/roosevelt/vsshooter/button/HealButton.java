package edu.roosevelt.vsshooter.button;

import android.graphics.Paint;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class HealButton extends LoadoutButton {
    
    public HealButton() {
        super(new Paint(), 0, 30, 100, 50);
        cost = 100;
        text = "Heal 100 HP\n(100)";
    }
    
    @Override
    protected void doAction() {
        PlayerShip.ship.takeDamage(-100);
    }
    
}
