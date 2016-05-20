package edu.roosevelt.vsshooter.button;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import edu.roosevelt.vsshooter.Button;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;

public class ShareButton extends Button {

    public ShareButton(int x, int y, int width, int height) {
        super(new Paint(), x, y, width, height);
        // TODO Auto-generated constructor stub
        paint.setColor(Color.GRAY);
        foregroundPaint.setColor(Color.YELLOW);
    }
    @Override
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        setText("Share scores");
    }

    @Override
    protected void startTap() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void cancelTap() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void resumeTap() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void endTap() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My scores");
        SharedPreferences p = VsShooter.getMain().getSharedPreferences("Scores", Context.MODE_PRIVATE);
        int highPoints = p.getInt("points", 0);
        int highKills = p.getInt("kills", 0);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "My best points: "+highPoints + "\nMy best kills: "+highKills);
        // need to specify what the data is
        shareIntent.setType("text/plain");

        // display apps that can share text
        VsShooter.getMain().startActivity(Intent.createChooser(shareIntent, "Share scores to:"));
    }
    

    
}
