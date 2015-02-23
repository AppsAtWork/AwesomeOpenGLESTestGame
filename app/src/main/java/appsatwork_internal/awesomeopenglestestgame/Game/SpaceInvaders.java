package appsatwork_internal.awesomeopenglestestgame.Game;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import Engine.Gaming.Game;
import Engine.Gaming.GameObject;
import Engine.OpenGLCanvas;
import Engine.OpenGLObjectManager;
import Engine.OpenGLObjects.OpenGLObject;
import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.AtlasSprite;
import Engine.OpenGLObjects.Sprites.SpriteObjects.TextureSprite;
import Engine.OpenGLObjects.Sprites.TextureManagement;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.Texture;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.VariableTextureAtlas;
import Engine.Util.Vector2;
import Engine.Util.Velocity;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 21-2-2015.
 */
public class SpaceInvaders extends Game
{
    Random rand;
    Spaceship ship;
    Asteroid asteroid;
    Backdrop drop;
    FireButton fireButton;
    int bulletCount = 0;
    Bullet[] bullets = new Bullet[10];

    public SpaceInvaders(Context context)
    {
        super(context, 60);
        this.setOnTouchListener(listener);
    }

    public SpaceInvaders(Context context, AttributeSet atrs)
    {
        super(context, atrs, 60);
        this.setOnTouchListener(listener);
    }

    public void SpawnBullet()
    {
        if(bulletCount >= bullets.length)
            bulletCount = 0;

        if(bullets[bulletCount] != null)
            bullets[bulletCount].Remove();

        bullets[bulletCount++] = new Bullet(Canvas, new PointF(ship.Position.x, ship.Position.y + 0.22f), ship.Direction.Clone());
    }

    long lastBullet = 0;
    OnTouchListener listener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getPointerCount() == 1)
            {
                //Touching at one point
                PointF worldCoords = ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY()));
                boolean intersects = fireButton.Intersects(worldCoords);

                if(intersects)
                {
                    if( System.currentTimeMillis() - lastBullet > 100)
                    {
                        lastBullet = System.currentTimeMillis();
                        SpawnBullet();
                    }
                }
                else
                {
                    ship.SetAtPosition(worldCoords);
                    drop.SetAtPosition(new PointF(-worldCoords.x / 4.0f, -worldCoords.y / 4.0f));
                }

            }
            else if(event.getPointerCount() == 2)
            {
                PointF worldCoords1 = ScreenSpaceToWorldSpace(new PointF(event.getX(0), event.getY(0)));
                PointF worldCoords2 = ScreenSpaceToWorldSpace(new PointF(event.getX(1), event.getY(1)));
                //Touching at two points
                boolean coords1Button = fireButton.Intersects(worldCoords1);
                boolean coords2Button = fireButton.Intersects(worldCoords2);

                if(coords1Button || coords2Button)
                {
                    if( System.currentTimeMillis() - lastBullet > 100)
                    {
                        lastBullet = System.currentTimeMillis();
                        SpawnBullet();
                    }
                }

                PointF pos;
                if(coords1Button)
                    pos = worldCoords2;
                else
                    pos = worldCoords1;

                ship.SetAtPosition(pos);
                drop.SetAtPosition(new PointF(-pos.x / 4.0f, -pos.y / 4.0f));
            }

            return true;
        }
    };

    @Override
    public void Initialize()
    {
        rand = new Random();
    }

    @Override
    public void LoadContent()
    {
        drop = new Backdrop(Canvas);
        asteroid = new Asteroid(Canvas);
        ship = new Spaceship(Canvas);
        fireButton = new FireButton(Canvas);
        Texture texture = new Texture(getResources(), R.drawable.bullet);
        TextureManagement.EnableTextureProvider(texture);
    }

    @Override
    public void Update()
    {
        asteroid.Update();
        ship.Update();
        for(int i = 0; i < bullets.length; i++)
            if(bullets[i] != null)
                bullets[i].Update();
    }
}