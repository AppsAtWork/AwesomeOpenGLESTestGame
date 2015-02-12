package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Casper on 7-2-2015.
 */
public class Rectangle extends OpenGLGeometry
{
    protected float[] BaseLeftUpper;
    protected float[] BaseRightUpper;
    protected float[] BaseRightLower;
    protected float[] BaseLeftLower;

    protected float[] LeftUpper;
    protected float[] RightUpper;
    protected float[] RightLower;
    protected float[] LeftLower;

    public float Width;
    public float Height;

    private float baseWidth;
    private float baseHeight;

    public Rectangle(float centerX, float centerY, float width, float height, float r, float g, float b, float a)
    {
        BaseLeftUpper = new float[] { -width/2.0f, height/2.0f };
        BaseLeftLower = new float[] { -width/2.0f, -height/2.0f };
        BaseRightUpper = new float[] { width/2.0f, height/2.0f };
        BaseRightLower = new float[] { width/2.0f, -height/2.0f};

        LeftUpper = new float[] { centerX - width/2.0f, centerY + height/2.0f };
        LeftLower = new float[] { centerX - width/2.0f, centerY - height/2.0f};
        RightUpper = new float[] {centerX + width/2.0f, centerY + height/2.0f};
        RightLower = new float[] {centerX + width/2.0f, centerY - height/2.0f};

        translation = new float[] { centerX , centerY };

        color = new float[] {r,g,b,a};
        Width = width;
        Height = height;
        baseWidth = width;
        baseHeight = height;

        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    public void ApplyTransformations()
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
    }

    public PointF GetCenter()
    {
        return new PointF((LeftUpper[0] + RightUpper[0])/2.0f, (LeftLower[1] + LeftUpper[1]) / 2.0f);
    }

    public PointF GetCorner(int number)
    {
        switch(number)
        {
            case 0:
                return new PointF(LeftUpper[0], LeftUpper[1]);
            case 1:
                return new PointF(RightUpper[0], RightUpper[1]);
            case 2:
                return new PointF(RightLower[0], RightLower[1]);
            case 3:
                return new PointF(LeftLower[0], LeftLower[1]);
        }
        return null;
    }

    protected void UpdateVertexBuffer()
    {
        vertices = new float[]{
                LeftUpper[0], LeftUpper[1], 0.0f,
                RightUpper[0], RightUpper[1], 0.0f,
                RightLower[0], RightLower[1], 0.0f,
                LeftLower[0], LeftLower[1], 0.0f
        };

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
        float[] pt = new float[] {point.x, point.y};
        float triangleSum =
                TriangleArea(LeftLower, pt, RightLower) +
                TriangleArea(RightLower, pt, RightUpper) +
                TriangleArea(RightUpper, pt, LeftUpper) +
                TriangleArea(pt, LeftUpper, LeftLower);

        return triangleSum - Area();
    }

    private float TriangleArea(float[] p1, float[] p2, float[] p3)
    {
        PointF a = new PointF(p1[0], p1[1]);
        PointF b = new PointF(p2[0], p2[1]);
        PointF c = new PointF(p3[0], p3[1]);

        //abs((Bx*Ay-Ax*By)+(Cx*Bx-Bx*Cx)+(Ax*Cy-Cx*Ay))/2
        return Math.abs(a.x*(b.y-c.y)+b.x*(c.y-a.y)+c.x*(a.y-b.y))/2.0f;
    }

    private float Area()
    {
        return Width * Height;
    }
}
