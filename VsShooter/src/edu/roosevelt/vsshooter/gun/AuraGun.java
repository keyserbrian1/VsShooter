package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.bullet.AuraBullet;
import edu.roosevelt.vsshooter.bullet.Bullet;

public class AuraGun extends SecondaryGun {
    
    boolean fired;
    int direction;
    protected int yDisplacement;
    protected int xDisplacement;
    
    public AuraGun(Actor source, boolean enemy) {
        super(source);
        bps = 4;
        velocity = 200;
        fired = false;
        if (enemy)
        {
            direction = 90;
            yDisplacement = source.getHeight();
            xDisplacement = 15;
        } else {
            direction = 270;
            yDisplacement = 0;
            xDisplacement = -15;
        }
        cost = 50;
    }
    
    @Override
    public void trueGenerateBullet(long shotProgress) {

            Bullet b = new AuraBullet(source.getWidth() / 2+source.getTrueX()+xDisplacement, source.getTrueY()+yDisplacement, 30, 30, direction, velocity);
            Stage.getCurrentStage().addChild(b);
            b.update(shotProgress);
            fired = true;
    }
    
}
