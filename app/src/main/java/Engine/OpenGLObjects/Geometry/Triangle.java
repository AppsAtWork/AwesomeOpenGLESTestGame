package Engine.OpenGLObjects.Geometry;

import android.graphics.PointF;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Casper on 11-2-2015.
 */
public class Triangle extends OpenGLGeometry
{
    protected float[] BaseP1;
    protected float[] BaseP2;
    protected float[] BaseP3;

    protected float[] P1;
    protected float[] P2;
    protected float[] P3;


    public Triangle(PointF point1, PointF point2, PointF point3, float r, float g, float b, float a)
    {
        color = new float[] {r,g,b,a};

        BaseP1 = new float[] {point1.x, point1.y};
        BaseP2 = new float[] {point2.x, point2.y};
        BaseP3 = new float[] {point3.x, point3.y};

        P1 = new float[] {point1.x, point1.y};
        P2 = new float[] {point2.x, point2.y};
        P3 = new float[] {point3.x, point3.y};

        PointF correction = GetCenter();
        BaseP1[0] = BaseP1[0] - correction.x;
        BaseP1[1] = BaseP1[1] - correction.y;

        BaseP2[0] = BaseP2[0] - correction.x;
        BaseP2[1] = BaseP2[1] - correction.y;

        BaseP3[0] = BaseP3[0] - correction.x;
        BaseP3[1] = BaseP3[1] - correction.y;

        UpdateVertexBuffer();
        UpdateDrawListBuffer();
    }

    @Override
    public PointF GetCenter()
    {
        return new PointF((1.0f/3.0f) * (P1[0] + P2[0] + P3[0]), (1.0f/3.0f) * (P1[1] + P2[1] + P3[1]));
    }

    @Override
    public void ApplyTransformations()
    {
        //Reset coords
        P1[0] = BaseP1[0];
        P1[1] = BaseP1[1];

        P2[0] = BaseP2[0];
        P2[1] = BaseP2[1];

        P3[0] = BaseP3[0];
        P3[1] = BaseP3[1];

        //Scale
        P1[0] *= scale;
        P2[0] *= scale;
        P3[0] *= scale;
        P1[1] *= scale;
        P2[1] *= scale;
        P3[1] *= scale;

        //Rotate
        float sin = (float) Math.sin(Math.toRadians(degrees));
        float cos = (float) Math.cos(Math.toRadians(degrees));

        float p10 = P1[0];
        float p20 = P2[0];
        float p30 = P3[0];

        P1[0] = cos * P1[0] - sin * P1[1];
        P1[1] = sin * p10 + cos * P1[1];

        P2[0] = cos * P2[0] - sin * P2[1];
        P2[1] = sin * p20+ cos * P2[1];

        P3[0] = cos * P3[0] - sin * P3[1];
        P3[1] = sin * p30 + cos * P3[1];

        //Translate
        P1[0] = P1[0] + translation[0];
        P2[0] = P2[0] + translation[0];
        P3[0] = P3[0] + translation[0];

        P1[1] = P1[1] + translation[1];
        P2[1] = P2[1] + translation[1];
        P3[1] = P3[1] + translation[1];
        UpdateVertexBuffer();
    }

    @Override
    protected void UpdateVertexBuffer()
    {
        vertices = new float[] {
                P1[0], P1[1], 0.0f,
                P2[0], P2[1], 0.0f,
                P3[0], P3[1], 0.0f
        };

        //Each float takes 4 bytes
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    @Override
    protected void UpdateDrawListBuffer()
    {
        drawingOrder = new short[] {0,1,2};
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(drawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawingOrder);
        drawListBuffer.position(0);
    }

    @Override
    public float Intersects(PointF p)
    {
        PointF p1 = new PointF(P1[0], P1[1]);
        PointF p2 = new PointF(P2[0], P2[1]);
        PointF p3 = new PointF(P3[0], P3[1]);

        float alpha = ((p2.y - p3.y)*(p.x - p3.x) + (p3.x - p2.x)*(p.y - p3.y)) /
                ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
        float beta = ((p3.y - p1.y)*(p.x - p3.x) + (p1.x - p3.x)*(p.y - p3.y)) /
                ((p2.y - p3.y)*(p1.x - p3.x) + (p3.x - p2.x)*(p1.y - p3.y));
        float gamma = 1.0f - alpha - beta;

        if(alpha < 0 || beta < 0 || gamma < 0)
            return Math.abs(Math.min(alpha, (Math.min(beta, gamma))));
        else
            return 0;
    }
}
