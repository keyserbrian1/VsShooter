package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.ship.EnemyShip;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public abstract class SecondaryGun extends Gun {
    
    protected int cost;
    
    public SecondaryGun(Actor source) {
        super(source);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void generateBullet(long shotProgress) {
        if (source instanceof EnemyShip || PlayerShip.ship.payEnergy(cost))
        {
            trueGenerateBullet(shotProgress);
        } else {
            PlayerShip.ship.disableSecondaryFire();
        }
    }

    protected abstract void trueGenerateBullet(long shotProgress);
    
}
