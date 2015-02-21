package appsatwork_internal.awesomeopenglestestgame;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import Engine.Game;
import Engine.OpenGLCanvas;
import Engine.OpenGLObjects.Geometry.Circle;
import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.TextureSprite;

/**
 * Created by Casper on 21-2-2015.
 */
public class TestGame extends Game
{
    Random rand;
    Ship ship;
    Asteroid asteroid;
    Backdrop drop;

    public TestGame(Context context)
    {
        super(context, 30);
        this.setOnTouchListener(listener);
    }

    OnTouchListener listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            PointF worldCoords = ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY()));
            ship.SetAtPosition(worldCoords);

            drop.SetAtPosition(new PointF(-worldCoords.x/4.0f, -worldCoords.y/4.0f));
            return true;
        }
    };

    @Override
    public void Initialize()
    {
        rand = new Random();
        drop = new Backdrop(Canvas);
        asteroid = new Asteroid(Canvas);
        ship = new Ship(Canvas);
    }

    @Override
    public void LoadContent()
    {
    }

    @Override
    public void Update()
    {
        asteroid.Update();
        ship.Update();
    }
}

class Backdrop
{
    private TextureSprite background;

    public Backdrop(OpenGLCanvas canvas)
    {
        background = canvas.DrawSprite(R.drawable.background, new PointF(0,0), 1.6f, 2.4f, FittingType.Stretch);
    }

    public void SetAtPosition(PointF pos)
    {
        background.SetCenter(pos);
        background.ApplyTransformations();
    }
}

class Ship
{
    private TextureSprite ship;
    private PointF pos;

    public Ship(OpenGLCanvas canvas)
    {
        pos = new PointF(0,0);
        ship = canvas.DrawSprite(R.drawable.ship, new PointF(0,0), 0.3f, 0.5f, FittingType.Stretch);
    }

    public void SetAtPosition(PointF pos)
    {
        this.pos = pos;
    }

    public void Update()
    {
        ship.SetCenter(pos);
        ship.ApplyTransformations();
    }
}

class Asteroid
{
    private TextureSprite asteroid;
    private int xSign = 1;
    private int ySign = 1;
    public Asteroid(OpenGLCanvas canvas)
    {
        asteroid = canvas.DrawSprite(R.drawable.asteroid, new PointF(0,0), 0.2f, 0.2f, FittingType.Stretch);
    }

    public void Update()
    {

        if(asteroid.LeftUpper().x > 0.4f || asteroid.RightUpper().x < -0.4f)
        {
            xSign *= -1;
        }
        if(asteroid.LeftUpper().y > 1.0f || asteroid.LeftLower().y < -1.0f)
        {
            ySign *= -1;
        }
        asteroid.TranslateBy(xSign * 0.01f, ySign*0.01f);
        asteroid.ApplyTransformations();
    }
}
