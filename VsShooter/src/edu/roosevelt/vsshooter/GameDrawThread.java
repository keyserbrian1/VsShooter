package edu.roosevelt.vsshooter;

import android.graphics.Canvas;
import android.util.Log;

public class GameDrawThread extends Thread {
    
    private GameField field;
    public GameDrawThread(GameField field) {
        this.field = field;
    }
    
    public void run() {
        Log.v("thread", "start");
        while (field.on) {
               Canvas c = null;
               try {
                      c = field.getHolder().lockCanvas();
                      synchronized (field.getHolder()) {
                             field.onDraw(c);
                      }
               } finally {
                      if (c != null) {
                             field.getHolder().unlockCanvasAndPost(c);
                      }
               }
               try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
  }
    
}
