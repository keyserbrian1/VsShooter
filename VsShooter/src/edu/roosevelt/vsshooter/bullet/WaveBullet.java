package edu.roosevelt.vsshooter.bullet;

import android.graphics.BitmapFactory;
import edu.roosevelt.vsshooter.R;
import edu.roosevelt.vsshooter.VsShooter;

public class WaveBullet extends Bullet {
    
    protected int periodTime;
    protected double period;
    protected double waveStrength;
    protected double displacement;
    protected double orthogVelocity;
    protected double displacementAngle;
    
    public WaveBullet(int x, int y, int width, int height, int direction, double velocity, double waveStrength,
            double period) {
        super(BitmapFactory.decodeResource(VsShooter.getContext().getResources(), R.drawable.bullet5), x, y, width,
                height, 1, 1, true);
        velocityAngle = direction;
        velocityMagnitude = velocity;
        displacementAngle = Math.toRadians(velocityAngle - 90);
        accelMagnitude = 0;
        // this.setHitboxOpacity(100);
        periodTime = 0;
        this.period = period;
        this.waveStrength = waveStrength;
        this.rotate = false;
        this.rotation.setRotate((float) velocityAngle);
        // orthogVelocity = waveStrength;
        // xVel = Math.cos(Math.toRadians(velocityAngle))*velocityMagnitude;
        // yVel = Math.sin(Math.toRadians(velocityAngle))*velocityMagnitude;
        // double xAcc = Math.cos(Math.toRadians(velocityAngle+90))*waveStrength;
        // double yAcc = Math.sin(Math.toRadians(velocityAngle+90))*waveStrength;
        // xVel += xAcc;
        // yVel += yAcc;
        // velocityAngle = Math.toDegrees(Math.atan2(yVel, xVel));
        // velocityMagnitude = Math.sqrt(xVel*xVel+yVel*yVel); //Square root of sum of products
    }
    
    @Override
    protected void updateAcceleration(long milliSeconds) {
        // periodTime += milliSeconds;
        // //accelMagnitude = Math.cos(Math.PI*2*periodTime/(1000.0*period))*waveStrength;
        // accelMagnitude = displacement*100;
        // orthogVelocity -= accelMagnitude*milliSeconds/1000.0;
        // displacement += orthogVelocity*milliSeconds/1000.0;
    }
    
    public void updateVelocity(long milliSeconds) {
        super.updateVelocity(milliSeconds);
    }
    
    public void update(long milliSeconds) {
        super.update(milliSeconds);
        periodTime += milliSeconds;
        double displacement = Math.sin(Math.PI * 2 * periodTime / (1000.0 * period)) * waveStrength;
        xDisplacement = Math.cos(displacementAngle)*displacement;
        yDisplacement = Math.sin(displacementAngle)*displacement;
    }
    
    public void handleZoneExit(int zoneMask) {
        zoneMask &= (TOP_EDGE | BOTTOM_EDGE);
        if (zoneMask != 0) {
            destroy();
        }
    }
}
