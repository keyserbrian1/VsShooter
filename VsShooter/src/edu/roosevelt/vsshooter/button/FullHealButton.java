package edu.roosevelt.vsshooter.button;

import android.graphics.Paint;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class FullHealButton extends LoadoutButton {
    
    public FullHealButton() {
        super(new Paint(), 185, 195, 100, 50);
        cost = 200;
        text = "Heal all HP\n(200)";
    }
    
    @Override
    protected void doAction() {
        PlayerShip.ship.takeDamage(-255);
    }
    
}
