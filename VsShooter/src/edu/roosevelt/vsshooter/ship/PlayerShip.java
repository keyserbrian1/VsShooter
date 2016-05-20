package edu.roosevelt.vsshooter.ship;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;
import edu.roosevelt.vsshooter.gun.BasicGun;
import edu.roosevelt.vsshooter.gun.BasicTargetedGun;
import edu.roosevelt.vsshooter.gun.Gun;
import edu.roosevelt.vsshooter.gun.SecondaryGun;
import edu.roosevelt.vsshooter.gun.TargetedGun;

public class PlayerShip extends Ship implements SensorEventListener {
    
    protected double energy = 300;
    protected double energyRegen = 20;
    protected double energyMax = 300;
    protected int vel;
    protected int speed = 500;
    protected Gun primaryGun;
    public Gun secondaryGun;
    private TargetedGun targetedGun;
    public BasicTargetedGun backup;
    private boolean secondaryFire;
    private TargetedGunCallback targetedGunCallback;
    private SecondaryGunCallback secondaryGunCallback;
    public static PlayerShip ship;
    
    public PlayerShip(int x, int y, int width, int height) {
        super(x, y, width, height);
        SensorManager m = (SensorManager) VsShooter.getMain().getSystemService(Context.SENSOR_SERVICE);
        Sensor a = m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m.registerListener(this, a, SensorManager.SENSOR_DELAY_GAME);
        primaryGun = new BasicGun(this, false);
        ship = this;
    }
    
    public void onAttach(Stage s) {
        super.onAttach(s);
        backup = new BasicTargetedGun(this);
        backup.setActive();
    }
    
    public void update(long milliSeconds) {
        super.update(milliSeconds);
        if (!secondaryFire) {
            primaryGun.update(milliSeconds);
        } else {
            secondaryGun.update(milliSeconds);
        }
        energy += energyRegen * milliSeconds / 1000.0;
        if (energy > energyMax) {
            energy = energyMax;
        }
    }
    
    public void updateVelocity(long milliSeconds) {
        xVel = vel;
        yVel = 0;
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        vel = (int) (-event.values[0] / event.values[1] * speed);
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    
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
            xVel = 0;
        }
        if ((zoneMask & LEFT_EDGE) != 0) {
            x = zoneLeft;
            xVel = 0;
        }
    }
    
    public double getEnergy() {
        return energy;
    }
    
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    
    public boolean payEnergy(double energy) {
        if (this.energy >= energy) {
            this.energy -= energy;
            return true;
        }
        return false;
    }
    
    public void drawHealthBar() {
        Paint back = new Paint();
        back.setColor(Color.RED);
        Paint front = new Paint();
        front.setColor(Color.GREEN);
        Paint frontEnergy = new Paint();
        frontEnergy.setColor(Color.CYAN);
        Paint fieldBack = new Paint();
        fieldBack.setColor(Color.BLACK);
        Stage.getCurrentStage().draw(0, 400, 300, 405, back, -19);
        Stage.getCurrentStage().draw(0, 400, (float) (300 * health / 255), 405, front, -20);
        Stage.getCurrentStage().draw(0, 406, 300, 411, back, -19);
        Stage.getCurrentStage().draw(0, 406, (float) (300 * energy / energyMax), 411, frontEnergy, -20);
        Stage.getCurrentStage().draw(0, 400, 300, Stage.getCurrentStage().getHeight(), fieldBack, -5);
    }
    
    public void trueDestroy() {
        super.trueDestroy();
        Stage.getCurrentStage().unregisterTouchListener(targetedGun);
        SharedPreferences p = VsShooter.getMain().getSharedPreferences("Scores", Context.MODE_PRIVATE);
        int highPoints = p.getInt("points", 0);
        int highKills = p.getInt("kills", 0);
        final AlertDialog.Builder builder = new AlertDialog.Builder(VsShooter.getMain());
        String s = "GAME OVER!\n" + "The hardest enemy you beat was worth " + EnemyShip.lastPoints + ", ";
        if (highPoints < EnemyShip.lastPoints) {
            s += "which is a new high score!\n";
            p.edit().putInt("points", EnemyShip.lastPoints).commit();
        } else {
            s += "and your high score is " + highPoints + ".\n";
        }
        s += "You killed a total of " + EnemyShip.numBeaten + " enemies, ";
        if (highKills < EnemyShip.numBeaten) {
            s += "more than ever before!\n";
            p.edit().putInt("kills", EnemyShip.numBeaten).commit();
        } else if (highKills > EnemyShip.numBeaten) {
            s += "but you once managed " + highKills + ".\n";
        } else {
            s += "a tie with your best effort.\n";
        }
        s += "Try another game! The enemies are procedurally generated, so there's a never-ending swarm for you to take down!";
        // set dialog's message to display
        builder.setMessage(s);
        
        // provide an OK button that simply dismisses the dialog
        builder.setPositiveButton("OK", null);
        // create AlertDialog from the AlertDialog.Builder
        
        Handler handler = new Handler(Looper.getMainLooper());
        
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                
                AlertDialog results = builder.create();
                results.show(); // display the modal dialog
                VsShooter.getMain().showMenu();
            }
        }, 1000);
        
    }
    
    public TargetedGun getTargetedGun() {
        return targetedGun;
    }
    
    public void setTargetedGun(TargetedGun targetedGun, TargetedGunCallback source) {
        if (targetedGun != null) {
            targetedGun.setActive();
        } else {
            backup.setActive();
        }
        this.targetedGun = targetedGun;
        if (targetedGunCallback != null) {
            targetedGunCallback.targetedGunChanged();
        }
        this.targetedGunCallback = source;
    }
    
    public void disableSecondaryFire() {
        secondaryFire = false;
        if (secondaryGunCallback != null) {
            secondaryGunCallback.secondaryGunChanged();
        }
        secondaryGunCallback = null;
    }
    
    public void enableSecondaryFire(SecondaryGun gun, SecondaryGunCallback callback) {
        secondaryGun = gun;
        secondaryFire = true;
        if (secondaryGunCallback != null) {
            secondaryGunCallback.secondaryGunChanged();
        }
        secondaryGunCallback = callback;
    }
}
