package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import edu.roosevelt.vsshooter.gun.SwingGun;
import edu.roosevelt.vsshooter.ship.PlayerShip;
import edu.roosevelt.vsshooter.ship.TargetedGunCallback;

public class TurretButton extends LoadoutButton implements TargetedGunCallback{
    
    private boolean active;
    
    public TurretButton() {
        super(new Paint(), 0, 195, 100, 50);
        cost = 0;
        text = "Create a rotating turret\n(150/turret)";
    }
    
    @Override
    protected void doAction() {
        // TODO Auto-generated method stub
        if (!active)
        {
            active = true;
            PlayerShip.ship.setTargetedGun(new SwingGun(PlayerShip.ship), this);
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
