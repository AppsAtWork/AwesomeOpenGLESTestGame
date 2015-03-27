package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import Engine.Gaming.GameObject;
import Engine.OpenGLCanvas;
import Engine.Objects.Sprites.FittingType;
import Engine.Objects.Sprites.SpriteObjects.AtlasSprite;
import Engine.Util.Vector2;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 22-2-2015.
 */
public class Spaceship extends GameObject {
    AtlasSprite ship;
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
        ship = Canvas.DrawSprite(Canvas.LoadVariableTextureAtlas(R.drawable.sheet, R.raw.sheet_atlas), 0, new PointF(0,0), 0.3f, 0.45f, FittingType.Stretch);
    }

    @Override
    public void Update() {

    }
}
