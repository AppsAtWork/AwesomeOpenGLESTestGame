package Engine.OpenGLObjects.Sprites;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casper on 16-2-2015.
 */
public class TextureManagement
{
    private static List<TextureAtlas> atlasses = new ArrayList<>();
    private static int[] textureHandles;
    public static boolean sent = false;

    public static void AddTextureAtlas(TextureAtlas atlas)
    {
        atlasses.add(atlas);
    }

    public static void SendAtlasses()
    {
        if(textureHandles != null)
        {
            GLES20.glDeleteTextures(textureHandles.length, textureHandles, 0);
        }

        //Get handles for the texture(s)
        textureHandles = new int[atlasses.size()];
        GLES20.glGenTextures(atlasses.size(), textureHandles, 0);

        for(int i = 0; i < atlasses.size(); i++)
        {
            //Set the texture as active in OpenGLES
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandles[i]);

            //Set filtering on the textures
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //Set wrapping on the textures
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            //Load the bitmap into the texture that we just set params for
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, atlasses.get(i).bitmap, 0);
        }
    }
}