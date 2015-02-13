package Engine.OpenGLObjects;

import android.graphics.PointF;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 7-2-2015.
 */
public class Rectangle extends OpenGLGeometry
{
/*    protected float[] BaseLeftUpper;
    protected float[] BaseRightUpper;
    protected float[] BaseRightLower;
    protected float[] BaseLeftLower;

    protected float[] LeftUpper;
    protected float[] RightUpper;
    protected float[] RightLower;
    protected float[] LeftLower;*/

    public float Width;
    public float Height;

    public Rectangle(float centerX, float centerY, float width, float height, float r, float g, float b, float a)
    {
//        BaseLeftUpper = new float[] { -width/2.0f, height/2.0f };
//        BaseLeftLower = new float[] { -width/2.0f, -height/2.0f };
//        BaseRightUpper = new float[] { width/2.0f, height/2.0f };
//        BaseRightLower = new float[] { width/2.0f, -height/2.0f};
//
//        LeftUpper = new float[] { centerX - width/2.0f, centerY + height/2.0f };
//        LeftLower = new float[] { centerX - width/2.0f, centerY - height/2.0f};
//        RightUpper = new float[] {centerX + width/2.0f, centerY + height/2.0f};
//        RightLower = new float[] {centerX + width/2.0f, centerY - height/2.0f};

        baseVertices = new float[] {
                -width/2.0f, height/2.0f, 0.0f,
                -width/2.0f, -height/2.0f, 0.0f,
                width/2.0f, height/2.0f, 0.0f,
                width/2.0f, -height/2.0f, 0.0f
        };

        vertices = new float[] {
                centerX - width/2.0f, centerY + height/2.0f, 0.0f,
                centerX - width/2.0f, centerY - height/2.0f, 0.0f,
                centerX + width/2.0f, centerY + height/2.0f, 0.0f,
                centerX + width/2.0f, centerY - height/2.0f, 0.0f
        };

        translation = new float[] { centerX , centerY };

        color = new float[] {r,g,b,a};
        Width = width;
        Height = height;

        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    /*public void ApplyTransformations()
    {
        LeftUpper = new float[] {BaseLeftUpper[0], BaseLeftUpper[1]};
        LeftLower = new float[] {BaseLeftLower[0], BaseLeftLower[1]};
        RightUpper = new float[] {BaseRightUpper[0], BaseRightUpper[1]};
        RightLower = new float[] {BaseRightLower[0], BaseRightLower[1]};

        //Apply scaling
        LeftUpper[0] *= scale;
        LeftUpper[1] *= scale;

        LeftLower[0] *= scale;
        LeftLower[1] *= scale;

        RightUpper[0] *= scale;
        RightUpper[1] *= scale;

        RightLower[0] *= scale;
        RightLower[1] *= scale;

        //Apply to global vars
        Width  = baseWidth * scale;
        Height = baseHeight * scale;


        //Apply rotation
        float sin = (float) Math.sin(Math.toRadians(degrees));
        float cos = (float) Math.cos(Math.toRadians(degrees));

        float lu0 = LeftUpper[0];
        float ll0 = LeftLower[0];
        float ru0 = RightUpper[0];
        float rl0 = RightLower[0];

        LeftUpper[0] = cos * LeftUpper[0] - sin * LeftUpper[1];
        LeftUpper[1] = sin * lu0 + cos * LeftUpper[1];

        LeftLower[0] = cos * LeftLower[0] - sin * LeftLower[1];
        LeftLower[1] = sin * ll0 + cos * LeftLower[1];

        RightUpper[0] = cos * RightUpper[0] - sin * RightUpper[1];
        RightUpper[1] = sin * ru0 + cos * RightUpper[1];

        RightLower[0] = cos * RightLower[0] - sin * RightLower[1];
        RightLower[1] = sin * rl0 + cos * RightLower[1];

        //Apply translation
        LeftUpper[0] += translation[0];
        LeftUpper[1] += translation[1];

        LeftLower[0] += translation[0];
        LeftLower[1] += translation[1];

        RightUpper[0] += translation[0];
        RightUpper[1] += translation[1];

        RightLower[0] += translation[0];
        RightLower[1] += translation[1];

        UpdateVertexBuffer();
    }*/

    public PointF GetCenter()
    {
        return new PointF((vertices[0] + vertices[3])/2.0f, (vertices[7] + vertices[1]) / 2.0f);
    }

    public PointF LeftUpper()
    {
        return new PointF(vertices[0], vertices[1]);
    }

    public PointF RightUpper()
    {
        return new PointF(vertices[3], vertices[4]);
    }

    public PointF RightLower()
    {
        return new PointF(vertices[6], vertices[7]);
    }

    public PointF LeftLower()
    {
        return new PointF(vertices[9], vertices[10]);
    }

    protected void UpdateVertexBuffer()
    {
        //Each float takes 4 bytes
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    protected void UpdateDrawListBuffer()
    {
        drawingOrder = new short[] {0,1,2,0,2,3};
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }

    @Override
    public float Intersects(PointF point)
    {
        float triangleSum =
                TriangleArea(LeftLower(), point, RightLower()) +
                TriangleArea(RightLower(), point, RightUpper()) +
                TriangleArea(RightUpper(), point, LeftUpper()) +
                TriangleArea(point, LeftUpper(), LeftLower());

        return triangleSum - Area();
    }

    private float TriangleArea(PointF a, PointF b, PointF c)
    {
        return Math.abs(a.x*(b.y-c.y)+b.x*(c.y-a.y)+c.x*(a.y-b.y))/2.0f;
    }

    private float Area()
    {
        return Width * Height;
    }
}
