package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import edu.roosevelt.vsshooter.gun.LaserGun;
import edu.roosevelt.vsshooter.ship.PlayerShip;
import edu.roosevelt.vsshooter.ship.TargetedGunCallback;

public class LaserButton extends LoadoutButton implements TargetedGunCallback{
    
    private boolean active;
    
    public LaserButton() {
        super(new Paint(), 155, 140, 125, 50);
        cost = 0;
        text = "Fire a laser!\n(300/sec)";
    }
    
    @Override
    protected void doAction() {
        // TODO Auto-generated method stub
        if (!active)
        {
            active = true;
            PlayerShip.ship.setTargetedGun(new LaserGun(PlayerShip.ship), this);
            foregroundPaint.setColor(Color.GREEN);
            
        } else {
            active = false;
            PlayerShip.ship.setTargetedGun(null, null);
            foregroundPaint.setColor(Color.YELLOW);
        }
    }
    
    public void targetedGunChanged()
    {
        active = false;
        foregroundPaint.setColor(Color.YELLOW);
    }
    
}
