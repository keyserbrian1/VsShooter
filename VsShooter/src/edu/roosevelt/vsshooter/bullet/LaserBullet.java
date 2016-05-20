package edu.roosevelt.vsshooter.bullet;

import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.TouchListener;
import edu.roosevelt.vsshooter.VsShooter;
import edu.roosevelt.vsshooter.ship.PlayerShip;
import edu.roosevelt.vsshooter.ship.Ship;

public class LaserBullet extends Bullet implements TouchListener {
    
    int milliSecondRemain;
    double cost;
    
    boolean enemy;
    
    Ship source;
    
    public LaserBullet(int x, int y, int width, int duration, Ship source) {
        super(VsShooter.laser, x, y, width,
                1000, 1, 1, true);
        // TODO Auto-generated constructor stub
        setDamage(0);
        enemy = !(source instanceof PlayerShip);
        this.source = source;
        if (enemy) {
            velocityAngle = 90;
            milliSecondRemain = duration;
        } else {
            velocityAngle = 270;
            cost = duration;
        }
        velocityMagnitude = 0.001;
        setOpacity(200);
        z = -5;
    }
    
    public void onAttach(Stage s) {
        super.onAttach(s);
        if (!enemy) {
            s.registerTouchListener(this);
        }
    }
    
    public void update(long milliSeconds) {
        if (enemy) {
            milliSecondRemain -= milliSeconds;
            if (milliSecondRemain < 0) {
                this.destroy();
            }
        } else {
            PlayerShip pSource = (PlayerShip) source;
            double frameCost = cost * milliSeconds / 1000.0;
            if (!pSource.payEnergy(frameCost)) {
                this.destroy();
            }
        }
        super.update(milliSeconds);
    }
    
    @Override
    protected void updateAcceleration(long milliSeconds) {
        // TODO Auto-generated method stub
        
    }
    
    public void handleCollision(Actor a2) {
        // remain on the Stage, damage target
        long milliSeconds = Stage.getCurrentStage().getInterval();
        double frameDamage = 128.0 * milliSeconds / 1000.0;// 128 damage/second, means 2 seconds of contact will kill.
        if (a2 instanceof Ship) {
            ((Ship) a2).takeDamage(frameDamage);
        }
    }
    
    public void handleZoneExit(int zoneMask) {
        // do nothing, the bullet naturally crosses the screen and doesn't move on its own.
    }
    
    @Override
    public void handleTouchStart(double x, double y) {
        // do nothing
        
    }
    
    @Override
    public void handleTouchMove(double x, double y) {
        // do nothing
        
    }
    
    @Override
    public void handleTouchEnd() {
        if (!enemy) {
            this.destroy();
        }
        
    }
    
}
