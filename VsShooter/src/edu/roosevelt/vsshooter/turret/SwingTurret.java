package edu.roosevelt.vsshooter.turret;

import android.graphics.BitmapFactory;
import edu.roosevelt.vsshooter.R;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;
import edu.roosevelt.vsshooter.bullet.BasicBullet;
import edu.roosevelt.vsshooter.bullet.Bullet;

public class SwingTurret extends Turret {
    
    int bulletsPerSwing;
    int bulletNumber;
    boolean offsetBackSwing;
    boolean startRight;
    double[] angles;
    double[] backAngles;
    boolean onBack;
    
    public SwingTurret(int x, int y, long lifetime, long fireDelay, int bulletsPerSwing, boolean offsetBackSwing,
            boolean startRight) {
        super(BitmapFactory.decodeResource(VsShooter.getContext().getResources(), R.drawable.bullet7), x, y, 10, 10, 1, 1, lifetime, fireDelay);
        this.bulletsPerSwing = bulletsPerSwing;
        this.offsetBackSwing = offsetBackSwing;
        bulletNumber = 0;
        this.startRight = startRight;
        angles = new double[bulletsPerSwing];
        double angleWidth = 180.0 / (bulletsPerSwing + 1);
        for (int i = 0; i < bulletsPerSwing; i++) {
            double angle = ((i + 1) * angleWidth);
            angles[i] = startRight ? 360 - angle : 180 + angle;
        }
        if (offsetBackSwing) {
            backAngles = new double[bulletsPerSwing + 1];
            for (int i = 0; i < bulletsPerSwing; i++) {
                backAngles[i] = angles[bulletsPerSwing - i-1] + angleWidth / 2;
            }
            backAngles[bulletsPerSwing] = startRight ? 360 - angleWidth : 180 + angleWidth;
        } else {
            backAngles = new double[bulletsPerSwing - 2];
            for (int i = 0; i < backAngles.length; i++) {
                backAngles[i] = angles[bulletsPerSwing - i - 2];
            }
        }
        z = -1;
    }
    
    public SwingTurret(int x, int y, double swings, long fireDelay, int bulletsPerSwing, boolean offsetBackSwing, boolean startRight)
    {
        this(x,y,calcLifetime(swings, bulletsPerSwing, fireDelay, offsetBackSwing),fireDelay,bulletsPerSwing,offsetBackSwing,startRight);
    }
    
    private static long calcLifetime(double swings, int bulletsPerSwing, long fireDelay, boolean offsetBackSwing) {
        int fullSwings = (int)(swings+.01);//deal with floating point inaccuracy
        double partialSwing = swings-fullSwings;
        boolean even = fullSwings%2==0;
        double backSwings = fullSwings/2;
        double frontSwings = fullSwings/2;
        if (even)
        {
            frontSwings += partialSwing;
        } else {
            frontSwings++;
            backSwings += partialSwing;
        }
        long time = (long)(frontSwings*bulletsPerSwing*fireDelay);
        time += (long)(backSwings*fireDelay*(offsetBackSwing?bulletsPerSwing+1:bulletsPerSwing-2));
        return time;
    }

    @Override
    protected void generateBullet(long fireProgress) {
        double[] activeAngles = onBack ? backAngles : angles;
        Bullet b = new BasicBullet(this.width / 2 + this.getTrueX(), this.height/2+this.getTrueY(), 14, 4,
                (int) activeAngles[bulletNumber], 300);
        Stage.getCurrentStage().addChild(b);
        b.update(fireProgress);
        bulletNumber++;
        if (bulletNumber >= activeAngles.length)
        {
            bulletNumber = 0;
            onBack = !onBack;
        }
    }
    
}
