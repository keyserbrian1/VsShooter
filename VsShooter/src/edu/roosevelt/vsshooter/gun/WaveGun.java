package edu.roosevelt.vsshooter.gun;


import com.javaLibs.util.Random;

import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.bullet.Bullet;
import edu.roosevelt.vsshooter.bullet.WaveBullet;

public class WaveGun extends SecondaryGun {
    
    int direction;
    double period;
    int yDepth;
    
    public WaveGun(Actor source, boolean enemy) {
        super(source);
        bps = 5;
        velocity = 300;
        cost = 5;
        if (enemy)
        {
            direction = 90;
            yDepth = source.getHeight();
        } else {
            direction = 270;
            yDepth = 0;
        }
        period = (new Random()).nextDouble(.5, 1.5); 
    }
    
    @Override
    public void trueGenerateBullet(long shotProgress) {

            Bullet b = new WaveBullet(source.getWidth() / 2+source.getTrueX(), source.getTrueY()+yDepth, 14, 4, direction, velocity, 30, period);
            Stage.getCurrentStage().addChild(b);
            b.update(shotProgress);
    }
    
}
