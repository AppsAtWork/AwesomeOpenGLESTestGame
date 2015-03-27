package appsatwork_internal.awesomeopenglestestgame.Game;

import android.graphics.PointF;

import Engine.Gaming.GameObject;
import Engine.Drawing.OpenGLCanvas;
import Engine.Util.Color;

/**
 * Created by Casper on 23-2-2015.
 */
public class FireButton extends GameObject
{

    public FireButton(OpenGLCanvas canvas) {
        super(canvas);
        Load();
    }

    @Override
    public void Load() {
        OGLObject = Canvas.DrawRegularPolygon(new PointF(0.33f, -0.75f), 0.17f, 6, new Color(0.2f, 0.2f, 0.6f, 0.7f));
        OGLObject.RotateBy(45);
        OGLObject.ApplyTransformations();
    }

    @Override
    public void Update()
    {

    }
}
