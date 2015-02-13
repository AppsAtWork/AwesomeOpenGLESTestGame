package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Casper on 2/12/2015.
 */
public class Line extends OpenGLGeometry
{
    private float BaseThickness;
    private float Thickness;

    public PointF P1() { return new PointF(vertices[0], vertices[1]);}
    public PointF P2() { return new PointF(vertices[3], vertices[4]);}
    @Override
    public PointF Center() { return new PointF(((P1().x + P2().x)/2.0f), ((P1().y + P2().y)/2.0f)); }

    public Line(PointF p1, PointF p2, float thickness, float r, float g, float b, float alpha)
    {
        BaseThickness = thickness;
        Thickness = thickness;

        vertices = new float[] {
                p1.x, p1.y, 0.0f,
                p2.x, p2.y, 0.0f
        };

        PointF center = Center();
        baseVertices = new float[] {
                p1.x - center.x, p1.y - center.y, 0.0f,
                p2.x - center.x, p2.y - center.y, 0.0f
        };

        color = new float[] { r,g,b,alpha};
        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    @Override
    public void ApplyTransformations()
    {
        super.ApplyTransformations();
        Thickness = BaseThickness * scale;
    }

    @Override
    protected void UpdateVertexBuffer()
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    @Override
    protected void UpdateDrawListBuffer()
    {
        drawingOrder = new short[] { 0,1 };
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }

    @Override
    public float Intersects(PointF point)
    {
        return DistanceToLine(point)/5.0f;
    }

    private float DistanceToLine(PointF point)
    {

        float upper = Math.abs((P2().y - P1().y)*point.x - (P2().x-P1().x)*point.y + P2().x*P1().y-P2().y*P1().x);
        return (float)(upper/(Math.sqrt((P2().y-P1().y)*(P2().y-P1().y) + (P2().x - P1().x)* (P2().x - P1().x))));

    }

    @Override
    public void Draw(float[] projectionViewMatrix, int program)
    {
        GLES20.glLineWidth(Thickness);
        GLESDrawingMode = GLES20.GL_LINES;

        super.Draw(projectionViewMatrix, program);

        GLES20.glLineWidth(1.0f);
        GLESDrawingMode = GLES20.GL_TRIANGLES;
    }
}