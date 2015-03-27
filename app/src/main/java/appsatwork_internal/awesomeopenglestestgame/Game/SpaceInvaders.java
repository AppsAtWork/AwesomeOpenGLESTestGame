package appsatwork_internal.awesomeopenglestestgame.Game;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import Engine.Gaming.Game;
import Engine.Objects.Sprites.TextureManagement;
import Engine.Objects.Sprites.UVCoordProviders.Texture;
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
    Enemy enemy;

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
                boolean intersects = false;

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
                boolean coords1Button = false;
                boolean coords2Button = false;

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
        enemy = new Enemy(Canvas);
        Texture texture = new Texture(getResources(), R.drawable.bullet);
        TextureManagement.EnableTextureProvider(texture);
    }

    long lastEnemy = System.currentTimeMillis();
    int enemyCount = 0;
    @Override
    public void Update()
    {
        asteroid.Update();
        ship.Update();
        for(int i = 0; i < bullets.length; i++)
            if(bullets[i] != null)
                bullets[i].Update();

        if(System.currentTimeMillis() - lastEnemy > 4000)
        {
            //Spawn an enemy every 2000 ms
            enemy.Reset();
            lastEnemy = System.currentTimeMillis();
        }

         enemy.Update();
    }
}