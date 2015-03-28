package Engine.Objects;

import android.opengl.GLES20;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Engine.Objects.GeometryObjects.Rectangle;
import Engine.Objects.TextureObjects.UVCoordProviders.SimpleTextureAtlas;
import Engine.Objects.TextureObjects.UVCoordProviders.Texture;
import Engine.Objects.TextureObjects.UVCoordProviders.TextureProvider;
import Engine.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;

/**
 * Created by Casper on 27-3-2015.
 */
public class Sprite implements IDrawable
{
    private Rectangle Rectangle;
    private TextureProvider TextureProvider;

    private ShortBuffer DrawingListBuffer;
    private FloatBuffer VertexBuffer;
    private FloatBuffer UVBuffer;

    public Sprite(Rectangle rectangle, Texture provider)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.DrawingListBuffer = BufferBuilder.BuildShortBuffer(rectangle.GetDrawingList());
        this.VertexBuffer =  BufferBuilder.BuildFloatBuffer(rectangle.GetVertices());
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(0));
    }

    public Sprite(Rectangle rectangle, SimpleTextureAtlas provider, int textureIndex)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.DrawingListBuffer = BufferBuilder.BuildShortBuffer(rectangle.GetDrawingList());
        this.VertexBuffer = BufferBuilder.BuildFloatBuffer(rectangle.GetVertices());
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(textureIndex));
    }

    public Sprite(Rectangle rectangle, VariableTextureAtlas provider, int textureIndex)
    {
        this.Rectangle = rectangle;
        this.TextureProvider = provider;
        this.DrawingListBuffer = BufferBuilder.BuildShortBuffer(rectangle.GetDrawingList());
        this.VertexBuffer = BufferBuilder.BuildFloatBuffer(rectangle.GetVertices());
        this.UVBuffer = BufferBuilder.BuildFloatBuffer(TextureProvider.GetUVCoords(textureIndex));
    }


    public void SetBoundingBox(Rectangle rectangle)
    {
        this.Rectangle = rectangle;
    }

    public Rectangle GetBoundingBox()
    {
        return this.Rectangle;
    }

    public void Draw(float[] projectionViewMatrix, int program)
    {
        //For now, just rebuild the vertex buffer at every draw call. Change this in the future to
        //To use observer patterns and such, or move the vertex buffer building entirely to geometry.
        VertexBuffer = BufferBuilder.BuildFloatBuffer(Rectangle.GetVertices());

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + TextureProvider.TextureSlot);
        int positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                VertexBuffer);

        //Need to send the uv coords to OpenGLES as well
        int texCoordHandle = GLES20.glGetAttribLocation(program, "a_texCoord");

        //Enable a 'vertex' array to contain the UV vertices
        GLES20.glEnableVertexAttribArray(texCoordHandle);

        //Send the uv buffer over to OpenGLES
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, UVBuffer);

        //Set the projection matrix in the shader.
        int projectionMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(projectionMatrixHandle, 1, false, projectionViewMatrix, 0);

        //Send over the drawing type to the shader (1 = texture).
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "drawingType"), 1);

        //Get the texture sampler and set it to 0. 0 is the position where the texture is stored in SendTextureToOpenGLES().
        int samplerHandle = GLES20.glGetUniformLocation(program, "s_texture");
        GLES20.glUniform1i(samplerHandle, TextureProvider.TextureSlot);

        //Draw the sprite with the texture
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, Rectangle.GetDrawingList().length, GLES20.GL_UNSIGNED_SHORT, DrawingListBuffer);

        //Disable the arrays again
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }
}
