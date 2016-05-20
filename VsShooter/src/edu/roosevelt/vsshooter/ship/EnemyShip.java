package edu.roosevelt.vsshooter.ship;

import com.javaLibs.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import edu.roosevelt.vsshooter.Boom;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.gun.AuraGun;
import edu.roosevelt.vsshooter.gun.BasicGun;
import edu.roosevelt.vsshooter.gun.EnemyLaserGun;
import edu.roosevelt.vsshooter.gun.Gun;
import edu.roosevelt.vsshooter.gun.WaveGun;

public class EnemyShip extends Ship {
    
    protected EnemyGun[] guns;
    public static int lastPoints;
    public int points;
    public static int numBeaten = 0;
    
    public EnemyShip(int x, int y, int width, int height) {
        super(x, y, width, height);
        rotation.setRotate(180, frameWidth / 2, frameHeight / 2);
        xVel = 100;
    }
    
    public void update(long milliSeconds) {
        super.update(milliSeconds);
        for (EnemyGun g : guns) {
            if (g.unlocked) {
                g.update(milliSeconds);
            }
        }
    }
    
    public void handleZoneExit(int zoneMask) {
        if ((zoneMask & TOP_EDGE) != 0) {
            y = zoneTop;
            yVel = 0;
        }
        if ((zoneMask & BOTTOM_EDGE) != 0) {
            y = zoneBottom - height;
            yVel = 0;
        }
        if ((zoneMask & RIGHT_EDGE) != 0) {
            x = zoneRight - width;
            xVel = -xVel;
        }
        if ((zoneMask & LEFT_EDGE) != 0) {
            x = zoneLeft;
            xVel = -xVel;
        }
    }
    
    public void drawHealthBar() {
        Paint back = new Paint();
        back.setColor(Color.RED);
        Paint front = new Paint();
        front.setColor(Color.GREEN);
        Stage.getCurrentStage().draw(0, 0, 300, 5, back, -19);
        Stage.getCurrentStage().draw(0, 0, (float) (300 * health / 255), 5, front, -20);
    }
    
    public static EnemyShip generate() {
        EnemyShip e = new EnemyShip(150, 5, 30, 30);
        e.guns = new EnemyGun[4];
        e.guns[0] = new EnemyGun(new BasicGun(e, true), 30, 20, 30);
        e.guns[1] = new EnemyGun(new WaveGun(e, true), 50, 40, 50);
        e.guns[2] = new EnemyGun(new AuraGun(e, true), 100, 80, 100);
        e.guns[3] = new EnemyLaserGunHolder(new EnemyLaserGun(e), 30, 20, 40, 50);
        for (int i = 0; i < 3; i++) {
            e.guns[i].innerGun.velocity *= .7;// weaken enemies originally
            e.guns[i].bps *= .7;
        }
        e.guns[2].innerGun.velocity *= 2; // Aura gun is better slow, so speed it up instead
        Random rand = new Random();
        int points = lastPoints += rand.nextGaussian(60, 15);
        Log.e("Points", ""+points);
        e.points = points;
        int tries = 0;
        while (points > 0 && tries < points) {
            int gun = rand.nextInt(4);
            if (e.guns[gun].unlocked) {
                if (gun < 3) {
                    boolean bps = rand.nextBoolean();
                    if (bps) {
                        if (points >= e.guns[gun].bpsCost) {
                            points -= e.guns[gun].bpsCost;
                            e.guns[gun].bps *= 1.15;
                            e.guns[gun].bpsCost *= 1.05;
                            Log.e("Gun", gun+": bps");
                            tries = 0;
                        } else {
                            tries++;
                        }
                    } else {
                        if (points >= e.guns[gun].velocityCost) {
                            points -= e.guns[gun].velocityCost;
                            if (gun != 2) {
                                e.guns[gun].innerGun.velocity *= 1.15;
                            } else {
                                e.guns[gun].innerGun.velocity /= 1.15;// slow down the aura gun instead of speeding it up.
                            }
                            e.guns[gun].velocityCost *= 1.05;
                            Log.e("Gun", gun+": velocity");
                            tries = 0;
                        } else {
                            tries++;
                        }
                    }
                } else {// In the laser, it's got different values.
                    int target = rand.nextInt(3);
                    EnemyLaserGunHolder g = (EnemyLaserGunHolder) e.guns[3];
                    switch (target) {
                        case 0:
                            if (points >= g.widthCost) {
                                points -= g.widthCost;
                                ((EnemyLaserGun)g.innerGun).width *= 1.15;
                                g.widthCost *= 1.05;
                                Log.e("Gun", gun+": width");
                                tries = 0;
                            } else {
                                tries++;
                            }
                        case 1:
                            if (points >= g.durationCost) {
                                points -= g.durationCost;
                                ((EnemyLaserGun)g.innerGun).duration *= 1.15;
                                g.durationCost *= 1.05;
                                Log.e("Gun", gun+": duration");
                                tries = 0;
                            } else {
                                tries++;
                            }
                        case 2:
                            if (points >= g.delayCost) {
                                points -= g.delayCost;
                                g.delay /= 1.15;
                                g.delayCost *= 1.05;
                                Log.e("Gun", gun+": delay");
                                tries = 0;
                            } else {
                                tries++;
                            }
                    }
                }
            } else {
                if (points >= e.guns[gun].unlockCost) {
                    points -= e.guns[gun].unlockCost;
                    e.guns[gun].unlocked = true;
                    Log.e("Gun", ""+gun);
                } else {
                    tries++;
                }
            }
        }
        
        return e;
    }
    
    public void destroy()
    {
        numBeaten++;
        lastPoints = points;
        Boom b = new Boom((int)x,(int)y,width,height);
        stage.addChild(b);
        super.destroy();
    }
    
    public static class EnemyGun {
        public Gun innerGun;
        public double bps;
        public double velocity;
        public int bpsCost;
        public int velocityCost;
        public boolean unlocked;
        public int unlockCost;
        public int shotProgress;
        
        public EnemyGun(Gun innerGun, int bpsCost, int velocityCost, int unlockCost) {
            super();
            this.innerGun = innerGun;
            this.bps = innerGun.bps;
            this.bpsCost = bpsCost;
            this.velocityCost = velocityCost;
            this.unlockCost = unlockCost;
        }
        
        public void update(long milliSeconds) {
            long milliPerBullet = (int) (1000 / bps);
            shotProgress += milliSeconds;
            while (shotProgress > milliPerBullet) {
                shotProgress -= milliPerBullet;
                innerGun.generateBullet(shotProgress);
            }
        }
        
    }
    
    public static class EnemyLaserGunHolder extends EnemyGun {
        public int widthCost;
        public int durationCost;
        public int delayCost;
        public int delay;
        
        public EnemyLaserGunHolder(EnemyLaserGun innerGun, int widthCost, int durationCost, int delayCost,
                int unlockCost) {
            super(innerGun, 0, 0, unlockCost);
            this.widthCost = widthCost;
            this.durationCost = durationCost;
            this.delayCost = delayCost;
            this.delay = 3000;
        }
        public void update(long milliSeconds) {
            shotProgress += milliSeconds;
            while (shotProgress > delay) {
                shotProgress -= delay;
                innerGun.generateBullet(shotProgress);
            }
        }
        
    }
    
}
