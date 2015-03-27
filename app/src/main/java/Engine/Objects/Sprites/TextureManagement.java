package Engine.Objects.Sprites;

import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

import Engine.Objects.Sprites.UVCoordProviders.Texture;
import Engine.Objects.Sprites.UVCoordProviders.TextureProvider;

/**
 * Created by Casper on 16-2-2015.
 */
public class TextureManagement
{
    public static boolean ResendTextures = true;
    private static List<TextureProvider> textureProviders = new ArrayList<>();

    public static TextureProvider GetTextureProvider(int resID)
    {
        for(int i = 0; i < textureProviders.size(); i++)
        {
            TextureProvider provider = textureProviders.get(i);
            if(provider.ResourceID == resID)
            {
                return provider;
            }
        }
        return null;
    }

    public static void EnableTextureProvider(TextureProvider texture)
    {
        ResendTextures = true;
        textureProviders.add(texture);
    }

    private static int[] GetTextureHandles()
    {
        int[] array = new int[textureProviders.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = textureProviders.get(i).TextureHandle;
        }
        return array;
    }

    public static void SendTextures()
    {
        if(ResendTextures)
        {
            int[] textureHandles = GetTextureHandles();
            GLES20.glDeleteTextures(textureHandles.length, textureHandles, 0);
            ResendTextures = false;
        }

        //Get handles for the texture(s)
        int[] textureHandles = new int[textureProviders.size()];
        GLES20.glGenTextures(textureProviders.size(), textureHandles, 0);

        for(int i = 0; i < textureProviders.size(); i++)
        {
            textureProviders.get(i).TextureHandle = textureHandles[i];
            textureProviders.get(i).TextureSlot = i;
            //Set the texture as active in OpenGLES
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandles[i]);

            //Set filtering on the textureProviders
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //Set wrapping on the textureProviders
            if(textureProviders.get(i).getClass() == Texture.class)
            {
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            }

            //Load the bitmap into the texture that we just set params for
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureProviders.get(i).GetBitmap(), 0);
        }
    }
}