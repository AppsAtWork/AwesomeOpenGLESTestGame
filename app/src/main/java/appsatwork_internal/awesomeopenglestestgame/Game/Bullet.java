package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import Engine.Gaming.GameObject;
import Engine.OpenGLCanvas;
import Engine.Objects.Sprites.FittingType;
import Engine.Objects.Sprites.SpriteObjects.TextureSprite;
import Engine.Util.Vector2;
import Engine.Util.Velocity;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 22-2-2015.
 */
public class Bullet extends GameObject
{
    private TextureSprite bullet;
    private PointF spawn;
    private Velocity velocity;
    private Vector2 direction;

    public Bullet(OpenGLCanvas canvas, PointF spawn, Vector2 direction) {
        super(canvas);
        this.spawn = spawn;
        this.direction = direction;
        Load();
    }

    @Override
    public void Load() {
        bullet = Canvas.DrawSprite(R.drawable.bullet, spawn, 0.02f, 0.1f, FittingType.Stretch);
        velocity = new Velocity(direction, 0.05f);
    }

    @Override
    public void Update()
    {
        bullet.TranslateBy(velocity.SpeedVector.X, velocity.SpeedVector.Y);
        bullet.ApplyTransformations();
    }

    public void Remove()
    {
        Canvas.DrawableList.Remove(bullet);
    }
}
