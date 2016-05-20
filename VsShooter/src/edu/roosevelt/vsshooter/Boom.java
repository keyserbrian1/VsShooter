package edu.roosevelt.vsshooter;

import android.graphics.BitmapFactory;
import edu.roosevelt.vsshooter.ship.EnemyShip;

public class Boom extends Actor {
    
    public Boom(int x, int y, int width, int height) {
        super(BitmapFactory.decodeResource(VsShooter.getContext().getResources(), R.drawable.boom), x, y, width, height,
                9, 9);
        // TODO Auto-generated constructor stub
        playAnimation(0, 80, false, 40);
    }
    
    protected void animFinish()
    {
        destroy();
    }
    
    public void destroy()
    {
        EnemyShip e = EnemyShip.generate();
        stage.addChild(e);
        super.destroy();
    }
}
