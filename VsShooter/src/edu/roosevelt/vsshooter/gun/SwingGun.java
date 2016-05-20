package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.ship.PlayerShip;
import edu.roosevelt.vsshooter.turret.SwingTurret;

public class SwingGun extends TargetedGun {
    
    public SwingGun(PlayerShip source) {
        super(source);
        cost = 150;
    }
    
    @Override
    public void generateBullet(double tapX, double tapY) {
        if (tapY < 150)
        {
            tapY = 150;
        }
        SwingTurret t = new SwingTurret((int)tapX, (int)tapY, 2.0, 80, 9, true, true);
        Stage.getCurrentStage().addChild(t);
    }
    
}
