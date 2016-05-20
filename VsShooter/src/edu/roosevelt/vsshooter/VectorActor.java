package edu.roosevelt.vsshooter;

import android.graphics.Bitmap;

public abstract class VectorActor extends Actor {
    
    protected boolean rotate;

    public VectorActor(Bitmap spriteSheet, int x, int y, int width, int height, int frameColumns, int frameRows, boolean rotate) {
        super(spriteSheet, x, y, width, height, frameColumns, frameRows);
        velocityAngle = 0;
        velocityMagnitude = 0;
        accelAngle = 0;
        accelMagnitude = 0;
        this.rotate = rotate;
        
    }
    protected double velocityAngle;
    protected double velocityMagnitude;
    protected double accelAngle;
    protected double accelMagnitude;
    
    
    public void updateVelocity(long milliSeconds)
    {
        xVel = Math.cos(Math.toRadians(velocityAngle))*velocityMagnitude;
        yVel = Math.sin(Math.toRadians(velocityAngle))*velocityMagnitude;
        double xAcc = Math.cos(Math.toRadians(accelAngle))*accelMagnitude*milliSeconds/1000;
        double yAcc = Math.sin(Math.toRadians(accelAngle))*accelMagnitude*milliSeconds/1000;
        xVel += xAcc;
        yVel += yAcc;
        velocityAngle = Math.toDegrees(Math.atan2(yVel, xVel));
        velocityMagnitude = Math.sqrt(xVel*xVel+yVel*yVel); //Square root of sum of products
        updateAcceleration(milliSeconds);
        if (rotate)
        {
            rotation.setRotate((float)velocityAngle);
        }
    }

    protected abstract void updateAcceleration(long milliSeconds);
}
