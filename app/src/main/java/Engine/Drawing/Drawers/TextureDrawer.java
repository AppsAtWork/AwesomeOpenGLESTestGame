package Engine.Drawing.Drawers;

import android.opengl.GLES20;

import Engine.Objects.Sprites.SpriteObjects.OpenGLSprite;
import Engine.Objects.Sprites.TextureManagement;

/**
 * Created by Casper on 3/27/2015.
 */
public class TextureDrawer extends Drawer
{
    private OpenGLSprite sprite;
    public TextureDrawer(OpenGLSprite sprite)
    {
        this.sprite = sprite;
        BuildVertexBuffer(sprite.GetVertices());
    }

    @Override
    public void Draw(float[] projectionViewMatrix, int program)
    {
        if(verticesDirty)
        {
            BuildVertexBuffer(sprite.GetVertices());
            verticesDirty = false;
        }

        if(TextureManagement.ResendTextures)
            TextureManagement.SendTextures();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + sprite.TextureProvider.TextureSlot);
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
        GLES20.glVertexAttribPointer(texCoordHandle, 2, GLES20.GL_FLOAT, false, 0, sprite.GetUVBuffer());

        //Set the projection matrix in the shader.
        int projectionMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(projectionMatrixHandle, 1, false, projectionViewMatrix, 0);

        //Send over the drawing type to the shader (1 = texture).
        GLES20.glUniform1i(GLES20.glGetUniformLocation(program, "drawingType"), 1);

        //Get the texture sampler and set it to 0. 0 is the position where the texture is stored in SendTextureToOpenGLES().
        int samplerHandle = GLES20.glGetUniformLocation(program, "s_texture");
        GLES20.glUniform1i(samplerHandle, sprite.TextureProvider.TextureSlot);

        //Draw the sprite with the texture
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, sprite.DrawingList.GetFilledLength(), GLES20.GL_UNSIGNED_SHORT, sprite.DrawingList.GetFilledDrawingList());

        //Disable the arrays again
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordHandle);
    }
}
