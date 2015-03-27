package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import java.util.Random;

import Engine.Gaming.GameObject;
import Engine.Drawing.OpenGLCanvas;
import Engine.Objects.Sprites.FittingType;
import Engine.Util.Vector2;
import Engine.Util.Velocity;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 23-2-2015.
 */
public class Enemy extends GameObject
{
    private Random rand;
    private Velocity velocity;
    public Enemy(OpenGLCanvas canvas) {
        super(canvas);
        Load();
    }

    @Override
    public void Load()
    {
        rand = new Random();
        velocity = new Velocity(new Vector2(0, -1), 0.01f);
        OGLObject = Canvas.DrawSprite(Canvas.LoadVariableTextureAtlas(R.drawable.sheet, R.raw.sheet_atlas), rand.nextInt(4) + 1, new PointF(rand.nextFloat()*2.0f - 1.0f, 2.0f), 0.2f, 0.2f, FittingType.Stretch);
    }

    @Override
    public void Update() {
        OGLObject.TranslateBy(velocity.SpeedVector.X, velocity.SpeedVector.Y);
        OGLObject.ApplyTransformations();
    }

    public void Reset()
    {
        Canvas.DrawableList.Remove(OGLObject);
        OGLObject = Canvas.DrawSprite(Canvas.LoadVariableTextureAtlas(R.drawable.sheet, R.raw.sheet_atlas), rand.nextInt(4) + 1, new PointF(rand.nextFloat()*0.6f - 0.3f, 2.0f), 0.2f, 0.2f, FittingType.Stretch);
    }
}
