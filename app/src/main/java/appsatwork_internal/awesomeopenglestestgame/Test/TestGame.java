package appsatwork_internal.awesomeopenglestestgame.Test;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;

import Engine.Gaming.Game;
import Engine.Objects.Shape;
import Engine.Objects.Sprite;
import Engine.Util.Color;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 27-3-2015.
 */
public class TestGame extends Game
{
    private Shape polygon;
    private Sprite sprite;
    private Shape line;
    public TestGame(Context context, AttributeSet attrs) {
        super(context, attrs, 60);
    }

    @Override
    public void Initialize() {

    }

    @Override
    public void LoadContent() {
        this.Canvas.DrawRectangle(new PointF(0,0), 1.0f, 1.0f, new Color(1.0f, 0.0f, 0.0f, 1.0f));
        polygon = this.Canvas.DrawRegularPolygon(new PointF(0,0), 0.1f, 5, new Color(1.0f, 1.0f, 0.0f, 1.0f));
        sprite = this.Canvas.DrawSprite(R.drawable.ship, new PointF(0.2f, 0.2f), 0.15f, 0.3f);
        line = this.Canvas.DrawLine(new PointF(0.0f, 0.0f), new PointF(0.5f, -0.3f), 10, new Color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    @Override
    public void Update() {
        polygon.GetGeometry().RotateBy(0.5f);
        polygon.GetGeometry().ApplyTransformations();

        sprite.GetBoundingBox().RotateBy(1.5f);
        sprite.GetBoundingBox().ApplyTransformations();

        line.GetGeometry().RotateBy(0.5f);
        line.GetGeometry().ApplyTransformations();
    }
}
