package Engine.Objects.TextureObjects;

import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.List;

import Engine.Objects.TextureObjects.UVCoordProviders.Texture;
import Engine.Objects.TextureObjects.UVCoordProviders.TextureProvider;

/**
 * Created by Casper on 16-2-2015.
 */
public class TextureManager
{
    private boolean ResendTextures = true;
    private List<TextureProvider> textureProviders = new ArrayList<>();

    public TextureProvider GetTextureProvider(int resID)
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

    public void EnableTextureProvider(TextureProvider texture)
    {
        ResendTextures = true;
        textureProviders.add(texture);
    }

    private int[] GetTextureHandles()
    {
        int[] array = new int[textureProviders.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = textureProviders.get(i).TextureHandle;
        }
        return array;
    }

    public void UpdateTextures()
    {
        if(ResendTextures)
        {
            int[] textureHandles1 = GetTextureHandles();
            GLES20.glDeleteTextures(textureHandles1.length, textureHandles1, 0);
            ResendTextures = false;


            //Get handles for the texture(s)
            int[] textureHandles = new int[textureProviders.size()];
            GLES20.glGenTextures(textureProviders.size(), textureHandles, 0);

            for (int i = 0; i < textureProviders.size(); i++) {
                textureProviders.get(i).TextureHandle = textureHandles[i];
                textureProviders.get(i).TextureSlot = i;
                //Set the texture as active in OpenGLES
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandles[i]);

                //Set filtering on the textureProviders
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

                //Set wrapping on the textureProviders
                if (textureProviders.get(i).getClass() == Texture.class) {
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
                }

                //Load the bitmap into the texture that we just set params for
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, textureProviders.get(i).GetBitmap(), 0);
            }
        }
    }
}