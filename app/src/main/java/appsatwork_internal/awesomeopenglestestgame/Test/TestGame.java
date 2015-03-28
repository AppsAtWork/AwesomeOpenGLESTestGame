package appsatwork_internal.awesomeopenglestestgame.Test;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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
    private Shape rect;
    private boolean fingerDown;
    private PointF location;

    public TestGame(Context context, AttributeSet attrs) {

        super(context, attrs, 60);
        this.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    fingerDown = true;
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    fingerDown = false;

                location = new PointF(event.getX(), event.getY());

                return true;
            }
        });
    }

    @Override
    public void Initialize() {

    }

    @Override
    public void LoadContent() {
        rect = this.Canvas.DrawRectangle(new PointF(0,0), 1.0f, 1.0f, new Color(1.0f, 0.0f, 0.0f, 1.0f));
        polygon = this.Canvas.DrawRegularPolygon(new PointF(0,0), 0.1f, 5, new Color(1.0f, 1.0f, 0.0f, 1.0f));
        sprite = this.Canvas.DrawSprite(R.drawable.ship, new PointF(0.2f, 0.2f), 0.15f, 0.3f);
        line = this.Canvas.DrawLine(new PointF(0.0f, 0.0f), new PointF(0.5f, -0.3f), 10, new Color(1.0f, 1.0f, 1.0f, 1.0f));
    }

    @Override
    public void Update()
    {
        if(fingerDown) {
            rect.GetGeometry().RotateBy(0.5f);
            rect.GetGeometry().SetScale(ScreenSpaceToWorldSpace(location).y);
            rect.GetGeometry().SetCenter(ScreenSpaceToWorldSpace(location));
            rect.GetGeometry().ApplyTransformations();
        }

        polygon.GetGeometry().RotateBy(0.5f);
        polygon.GetGeometry().ApplyTransformations();

        sprite.GetBoundingBox().RotateBy(1.5f);
        sprite.GetBoundingBox().ApplyTransformations();

        line.GetGeometry().RotateBy(0.5f);
        line.GetGeometry().ApplyTransformations();
    }
}
