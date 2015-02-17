package Engine.OpenGLObjects.Sprites;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import Engine.OpenGLObjects.OpenGLObject;

/**
 * Created by Casper on 17-2-2015.
 */
public class TextureSprite extends OpenGLObject
{
    private static float uvs[] = new float[8];
    private FloatBuffer uvBuffer;

    private Texture texture;

    public float Width() {return BaseWidth*scale;}
    public float Height() {return BaseHeight * scale;}

    private float BaseWidth;
    private float BaseHeight;

    @Override
    public PointF Center() { return new PointF((vertices[0] + vertices[3])/2.0f, (vertices[7] + vertices[1]) / 2.0f); }

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

    public float Area()
    {
        return Width() * Height();
    }

    public TextureSprite(Texture tex, float centerX, float centerY, float width, float height)
    {
        texture = tex;

        baseVertices = new float[]{
                -width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, height / 2.0f, 0.0f,
                width / 2.0f, -height / 2.0f, 0.0f,
                -width / 2.0f, -height / 2.0f, 0.0f
        };

        vertices = new float[]{
                centerX - width / 2.0f, centerY + height / 2.0f, 0.0f,
                centerX + width / 2.0f, centerY + height / 2.0f, 0.0f,
                centerX + width / 2.0f, centerY - height / 2.0f, 0.0f,
                centerX - width / 2.0f, centerY - height / 2.0f, 0.0f
        };

        translation = new float[]{centerX, centerY};

        BaseWidth = width;
        BaseHeight = height;

        UpdateVertexBuffer();
        UpdateDrawListBuffer();
        UpdateUVBuffer();
    }

    @Override
    public void Draw(float[] projectionViewMatrix, int program)
    {
        if(TextureManagement.ResendTextures)
        {
            TextureManagement.SendTextures();
            TextureManagement.ResendTextures = false;
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + texture.TextureSlot);
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                vertexBuffer);

        //Need to send the uv coords to OpenGLES as well
        int texCoordHandle = GLES20.glGetAttribLocation(program, "a_texCoord");

        //Enable a 'vertex' array to contain the UV vertices
        GLES20.glEnableVertexAttribArray(texCoordHandle);

        //Send the uv buffer over to OpenGLES
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        //Set the projection matrix in the shader.
        int projectionMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(projectionMatrixHandle, 1, false, projectionViewMatrix, 0);

        //Send over the drawing type to the shader (1 = texture).
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "drawingType"), 1);

        //Get the texture sampler and set it to 0. 0 is the position where the texture is stored in SendTextureToOpenGLES().
        int samplerHandle = GLES20.glGetUniformLocation(program, "s_texture");
        GLES20.glUniform1i(samplerHandle, texture.TextureHandle);

        //Draw the sprite with the texture
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawingOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        //Disable the arrays again
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }

    protected void UpdateUVBuffer()
    {
        uvs = texture.GetUVCoords();
        ByteBuffer buffer = ByteBuffer.allocateDirect(uvs.length*4);
        buffer.order(ByteOrder.nativeOrder());
        uvBuffer = buffer.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);
    }

    @Override
    protected void UpdateDrawListBuffer()
    {
        drawingOrder = new short[] {0,1,2,0,2,3};
        CreateDrawListBuffer();
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
}
