package edu.roosevelt.vsshooter.turret;

import android.graphics.Bitmap;
import edu.roosevelt.vsshooter.Actor;

public abstract class Turret extends Actor {
    
    protected long lifetime;
    protected long fireDelay;
    protected long fireProgress;

    public Turret(Bitmap spriteSheet, int x, int y, int width, int height, int frameColumns, int frameRows,
            long lifetime, long fireDelay) {
        super(spriteSheet, x, y, width, height, frameColumns, frameRows);
        this.lifetime = lifetime;
        this.fireDelay = fireDelay;
        fireProgress = 0;
    }
    
    public void update(long milliSeconds)
    {
        super.update(milliSeconds);
        fireProgress += milliSeconds;
        while(fireProgress > fireDelay)
        {
            fireProgress -= fireDelay;
            generateBullet(fireProgress);
        }
        lifetime -= milliSeconds;
        if (lifetime <= 0)
        {
            destroy();
        }
    }

    protected abstract void generateBullet(long fireProgress);
    
}
