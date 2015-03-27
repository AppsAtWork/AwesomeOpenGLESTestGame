package appsatwork_internal.awesomeopenglestestgame.Test;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;

import Engine.Gaming.Game;
import Engine.Util.Color;

/**
 * Created by Casper on 27-3-2015.
 */
public class TestGame extends Game
{
    public TestGame(Context context, AttributeSet attrs) {
        super(context, attrs, 60);
    }

    @Override
    public void Initialize() {

    }

    @Override
    public void LoadContent() {
        this.Canvas.DrawRectangle(new PointF(0,0), 1.0f, 1.0f, 1.0f, 0, 0, 1.0f);
        this.Canvas.DrawRegularPolygon(new PointF(0,0), 0.1f, 5, new Color(1.0f, 1.0f, 0.0f, 1.0f));
    }

    @Override
    public void Update() {

    }
}
