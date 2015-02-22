package appsatwork_internal.awesomeopenglestestgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import appsatwork_internal.awesomeopenglestestgame.Game.SpaceInvaders;


public class MainActivity extends Activity
{
    private SpaceInvaders invaders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    public void FireButton_OnClick(View view)
    {
        if(invaders == null)
            invaders = (SpaceInvaders)(findViewById(R.id.invaders));
        invaders.SpawnBullet();
    }
}
