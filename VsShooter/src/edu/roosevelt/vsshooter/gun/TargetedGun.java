package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.TouchListener;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public abstract class TargetedGun implements TouchListener{
    
    protected PlayerShip source;
    protected int cost;
    
    public TargetedGun(PlayerShip source) {
        this.source = source;
        // TODO Auto-generated constructor stub
    }
    
    public abstract void generateBullet(double tapX, double tapY);
    
    public void handleTouchStart(double tapX, double tapY)
    {
        if (tapY<400 && source.payEnergy(cost))
        {
            generateBullet(tapX, tapY);
        }
    }
    
    public void handleTouchMove(double x, double y)
    {
        //do nothing
    }
    
    public void handleTouchEnd()
    {
        //do nothing
    }

    public void setActive() {
        source.stage.unregisterTouchListener(source.getTargetedGun());
        source.stage.unregisterTouchListener(source.backup);
        source.stage.registerTouchListener(this);
    }
    
}
