package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import Engine.Gaming.GameObject;
import Engine.OpenGLCanvas;
import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.TextureSprite;
import Engine.Util.Vector2;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 22-2-2015.
 */
public class Spaceship extends GameObject {
    TextureSprite ship;
    public Vector2 Direction;
    public PointF Position;

    public Spaceship(OpenGLCanvas canvas) {
        super(canvas);
        Load();
    }

    public void SetAtPosition(PointF pos)
    {
        Position = pos;
        ship.SetCenter(pos);
        ship.ApplyTransformations();
    }

    @Override
    public void Load()
    {
        Position = new PointF(0,0);
        Direction = new Vector2(0,1);
        ship = Canvas.DrawSprite(R.drawable.ship, new PointF(0,0), 0.2f, 0.3f, FittingType.Stretch);
    }

    @Override
    public void Update() {

    }
}
