package edu.roosevelt.vsshooter.bullet;

import android.graphics.BitmapFactory;
import edu.roosevelt.vsshooter.R;
import edu.roosevelt.vsshooter.VsShooter;

public class BasicBullet extends Bullet {
    
    public BasicBullet(int x, int y, int width, int height, int direction, double velocity) {
        super(BitmapFactory.decodeResource(VsShooter.getContext().getResources(), R.drawable.bullet5), x, y, width, height, 1, 1, true);
        velocityAngle = direction;
        velocityMagnitude = velocity;
        //this.setHitboxOpacity(100);
    }

    @Override
    protected void updateAcceleration(long milliSeconds) {
        // Do nothing
    }
}
