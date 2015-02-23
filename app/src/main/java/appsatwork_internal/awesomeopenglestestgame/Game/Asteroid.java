package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import Engine.Gaming.GameObject;
import Engine.OpenGLCanvas;
import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.AtlasSprite;
import Engine.OpenGLObjects.Sprites.SpriteObjects.TextureSprite;
import Engine.Util.Vector2;
import Engine.Util.Velocity;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 22-2-2015.
 */
public class Asteroid extends GameObject
{
    private AtlasSprite asteroid;
    private Velocity velocity;

    public Asteroid(OpenGLCanvas canvas)
    {
        super(canvas);
        Load();
    }

    @Override
    public void Load() {
        asteroid = Canvas.DrawSprite(Canvas.LoadVariableTextureAtlas(R.drawable.sheet, R.raw.sheet_atlas), 5, new PointF(0,0), 0.3f, 0.3f, FittingType.Stretch);
        velocity = new Velocity(new Vector2(1f, 1f), 0.005f);
    }

    public void Update()
    {
        if(asteroid.Center().x + asteroid.Width()/2.0f > 0.4f || asteroid.Center().x - asteroid.Width()/2.0f< -0.4f)
        {
            velocity.InvertX();
        }
        if(asteroid.Center().y + asteroid.Width()/2.0f > 0.6f || asteroid.Center().y-asteroid.Width()/2.0f < -0.6f)
        {
            velocity.InvertY();
        }
        asteroid.RotateBy(0.5f);
        asteroid.TranslateBy(velocity.SpeedVector.X, velocity.SpeedVector.Y);
        asteroid.ApplyTransformations();
    }
}
