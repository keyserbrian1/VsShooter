package edu.roosevelt.vsshooter.button;

import android.graphics.Color;
import android.graphics.Paint;
import edu.roosevelt.vsshooter.gun.WaveGun;
import edu.roosevelt.vsshooter.ship.PlayerShip;
import edu.roosevelt.vsshooter.ship.SecondaryGunCallback;

public class WaveButton extends LoadoutButton implements SecondaryGunCallback{
    
    private boolean firing;
    
    public WaveButton() {
        super(new Paint(), 105, 195, 75, 50);
        cost = 0;
        text = "Fire waving bullets\n(5/shot)";
    }
    
    @Override
    protected void doAction() {
        // TODO Auto-generated method stub
        if (!firing)
        {
            firing = true;
            PlayerShip.ship.enableSecondaryFire(new WaveGun(PlayerShip.ship, false), this);
            foregroundPaint.setColor(Color.GREEN);
            
        } else {
            firing = false;
            PlayerShip.ship.disableSecondaryFire();
            foregroundPaint.setColor(Color.YELLOW);
        }
    }
    
    public void secondaryGunChanged()
    {
        firing = false;
        foregroundPaint.setColor(Color.YELLOW);
    }
    
}
