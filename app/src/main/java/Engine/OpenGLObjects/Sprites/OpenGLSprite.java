package Engine.OpenGLObjects.Sprites;

import android.graphics.PointF;

import Engine.OpenGLObjects.OpenGLObject;

/**
 * Created by Casper on 11-2-2015.
 */
public class OpenGLSprite extends OpenGLObject
{
    @Override
    public void Draw(float[] projectionMatrix, int program)
    {

    }

    @Override
    public PointF Center() {
        return null;
    }

    @Override
    public void ApplyTransformations() {

    }

    @Override
    protected void UpdateVertexBuffer() {

    }

    @Override
    protected void UpdateDrawListBuffer() {

    }

    @Override
    public float Intersects(PointF point) {
        return 0.0f;
    }
}
