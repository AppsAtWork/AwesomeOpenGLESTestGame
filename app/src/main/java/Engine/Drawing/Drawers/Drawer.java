package Engine.Drawing.Drawers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public abstract class Drawer
{
    protected boolean verticesDirty = false;
    protected FloatBuffer vertexBuffer;

    public void SetDirty()
    {
        verticesDirty = true;
    }

    protected void BuildVertexBuffer(float[] vertices)
    {
        //Each float takes 4 bytes
        ByteBuffer buffer = ByteBuffer.allocateDirect(vertices.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
    }

    public abstract void Draw(float[] projectionViewMatrix, int program);
}
