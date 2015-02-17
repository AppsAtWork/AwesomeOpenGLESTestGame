package Engine.OpenGLObjects.Sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casper on 16-2-2015.
 */
public class TextureManagement
{
    public static boolean ResendTextures = true;
    private static boolean Delete = false;
    private static List<TextureAtlas> atlasses = new ArrayList<>();
    private static List<Texture> textures = new ArrayList<>();
    private static Resources resources;

    public static Texture LoadAsTexture(Resources res, int resourceID)
    {
        return null;
    }

    public static TextureAtlas LoadAsTextureAtlas(Resources res, int resourceID, int textureSize)
    {
        TextureAtlas atlas = new TextureAtlas(textureSize, res, resourceID);
        atlasses.add(atlas);
        return atlas;
    }

    private static int[] GetAtlasHandles()
    {
        int[] array = new int[atlasses.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = atlasses.get(i).TextureHandle;
        }
        return array;
    }

    private static void SendTextures()
    {

    }

    public static void SendAtlasses()
    {
        if(Delete)
        {
            int[] textureHandles = GetAtlasHandles();
            GLES20.glDeleteTextures(textureHandles.length, textureHandles, 0);
        }

        //Get handles for the texture(s)
        int[] textureHandles = new int[atlasses.size()];
        GLES20.glGenTextures(atlasses.size(), textureHandles, 0);

        for(int i = 0; i < atlasses.size(); i++)
        {
            atlasses.get(i).TextureHandle = textureHandles[i]-1;
            atlasses.get(i).TextureSlot = i;
            //Set the texture as active in OpenGLES
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandles[i]);

            //Set filtering on the textures
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //Set wrapping on the textures - Atlasses are always POT textures, so no need to set this.
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            //Load the bitmap into the texture that we just set params for
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, atlasses.get(i).GetBitmap(), 0);
        }
    }
}