package edu.roosevelt.vsshooter.button;

import android.graphics.Paint;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class FullHealButtonSmall extends LoadoutButton {
    
    public FullHealButtonSmall() {
        super(new Paint(), 155, 85, 75, 50);
        cost = 300;
        text = "Heal all HP\n(300)";
    }
    
    @Override
    protected void doAction() {
        PlayerShip.ship.takeDamage(-255);
    }
    
}
