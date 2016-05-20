package edu.roosevelt.vsshooter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameField extends SurfaceView {

    SurfaceHolder holder;
    volatile boolean on;
    static GameField active = null;
	public GameField(Context context) {
		super(VsShooter.getContext());
        on = true;
		holder = getHolder();
		holder.addCallback(new Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.v("GameField", "Thread start");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                
            }
		    
		});
		
	}
	
	public void start()
	{
	    Log.e(this.getClass().getName(), "start");
	    if (active != null)
	    {
	        active.stop();
	    }
	    active = this;
        on = true;
        Stage.setCurrentStage(stage);
        GameDrawThread t = new GameDrawThread(this);
        t.start();
	}
	
	public void stop()
	{
	    on = false; 
	}
	
	@SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event)
	{
	    stage.handleTouch(event);
	    return true;
	}
	
	private Stage stage;
	
	public void onDraw(Canvas c)
	{
	    if (c == null)
	    {
	        //on = false;
	        return;
	    }
		c.drawColor(Color.BLACK);
		stage.onDraw(c);
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
