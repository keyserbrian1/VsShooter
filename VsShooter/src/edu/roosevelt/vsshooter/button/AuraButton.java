package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import edu.roosevelt.vsshooter.gun.AuraGun;
import edu.roosevelt.vsshooter.ship.PlayerShip;
import edu.roosevelt.vsshooter.ship.SecondaryGunCallback;

public class AuraButton extends LoadoutButton implements SecondaryGunCallback{
    
    private boolean active;
    
    public AuraButton() {
        super(new Paint(), 105, 30, 150, 50);
        cost = 0;
        text = "Fire damaging auras\n(40/shot)";
    }
    
    @Override
    protected void doAction() {
        // TODO Auto-generated method stub
        if (!active)
        {
            active = true;
            PlayerShip.ship.enableSecondaryFire(new AuraGun(PlayerShip.ship, false), this);
            foregroundPaint.setColor(Color.GREEN);
            
        } else {
            active = false;
            PlayerShip.ship.setTargetedGun(null, null);
            foregroundPaint.setColor(Color.YELLOW);
        }
    }
    
    public void secondaryGunChanged()
    {
        active = false;
        foregroundPaint.setColor(Color.YELLOW);
    }
    
}
