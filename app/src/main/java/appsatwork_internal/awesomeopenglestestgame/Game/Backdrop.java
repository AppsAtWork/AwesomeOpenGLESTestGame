package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import Engine.Gaming.GameObject;
import Engine.Drawing.OpenGLCanvas;
import Engine.Objects.Sprites.FittingType;
import Engine.Objects.Sprites.SpriteObjects.TextureSprite;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 22-2-2015.
 */
public class Backdrop extends GameObject
{
    private TextureSprite background;

    public Backdrop(OpenGLCanvas canvas) {
        super(canvas);
        Load();
    }

    @Override
    public void Load() {
        background = Canvas.DrawSprite(R.drawable.background, new PointF(0,0), 1.7f, 2.6f, FittingType.Stretch);
    }

    public void SetAtPosition(PointF pos)
    {
        background.SetCenter(pos);
        background.ApplyTransformations();
    }

    @Override
    public void Update() {  }
}
