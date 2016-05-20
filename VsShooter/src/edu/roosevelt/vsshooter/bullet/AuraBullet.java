package edu.roosevelt.vsshooter.bullet;

import android.graphics.BitmapFactory;
import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.R;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;
import edu.roosevelt.vsshooter.ship.Ship;

public class AuraBullet extends Bullet {
    
    public AuraBullet(int x, int y, int width, int height, int direction, double velocity) {
        super(BitmapFactory.decodeResource(VsShooter.getContext().getResources(), R.drawable.bullet7), x, y, width, height, 1, 1, true);
        velocityAngle = direction;
        velocityMagnitude = velocity;
        //this.setHitboxOpacity(100);
        setDamage(0);
        setOpacity(127);
        permitPartialCrossing = true;
        z = 1;
    }

    @Override
    protected void updateAcceleration(long milliSeconds) {
        // Do nothing
    }
    
    public void handleCollision(Actor a2)
    {
        //remain on the Stage, damage target
        long milliSeconds = Stage.getCurrentStage().getInterval();
        double frameDamage = 340.0*milliSeconds/1000;//340 damage/second, means 5 full hits from the standard gun will kill.
        if (a2 instanceof Ship)
        {
            ((Ship)a2).takeDamage(frameDamage);
        }
    }
    
}
