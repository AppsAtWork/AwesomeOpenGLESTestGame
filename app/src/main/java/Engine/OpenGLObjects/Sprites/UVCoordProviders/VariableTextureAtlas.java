package Engine.OpenGLObjects.Sprites.UVCoordProviders;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 23-2-2015.
 */
public class VariableTextureAtlas extends TextureProvider
{
    public static int AtlasResolution = 512;
    private int XMLID;
    private HashMap<Integer, float[]> UVMap = new HashMap<>();

    public VariableTextureAtlas(Resources resources, int textureID, int xmlID) {
        super(resources, textureID);
        XMLID = xmlID;
        try {
            ParseXML(resources);
        }
        catch(Exception e){};
    }

    private void ParseXML(Resources resources) throws XmlPullParserException, IOException {
        InputStream inputStream = resources.openRawResource(XMLID);
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(inputStream, null);

        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if (eventType == XmlPullParser.START_DOCUMENT)
            {
                Log.v(getClass().toString(), "Starting XML Parsing...");
            }
            else if (eventType == XmlPullParser.END_DOCUMENT)
            {
                Log.v(getClass().toString(), "Ending XML Parsing...");
            }
            else if (eventType == XmlPullParser.START_TAG)
            {
                String name = xpp.getName();
                if(name.toLowerCase().equals("atlas"))
                {
                    Log.v(getClass().toString(), "Encountered Atlas...");
                }
                else if(name.toLowerCase().equals("texture"))
                {
                    Log.v(getClass().toString(), "Encountered Texture...");

                    //Let's grab properties of this texture
                    String index = xpp.getAttributeValue(null, "Index");
                    String x = xpp.getAttributeValue(null, "X");
                    String y = xpp.getAttributeValue(null, "Y");
                    String width = xpp.getAttributeValue(null, "Width");
                    String height = xpp.getAttributeValue(null, "Height");

                    Log.v(getClass().toString(), index + "," + x + "," + y + "," + width + "," + height);

                    float x1 = Integer.parseInt(x)/(float)AtlasResolution;
                    float x2 = (Integer.parseInt(x) + Integer.parseInt(width))/(float)AtlasResolution;
                    float y1 = Integer.parseInt(y)/(float)AtlasResolution;
                    float y2 = (Integer.parseInt(y) + Integer.parseInt(height))/(float)AtlasResolution;

                    Log.v(getClass().toString(), index + "," + x1 + "," + y1 + "," + x2 + "," + y2);

                    UVMap.put(Integer.parseInt(index), new float[]
                    {
                       x1,y1,
                       x2,y1,
                       x2,y2,
                       x1,y2
                    });
                }
            }
            else if (eventType == XmlPullParser.END_TAG)
            {
                String name = xpp.getName();
                if(name.toLowerCase().equals("atlas"))
                {
                    Log.v(getClass().toString(), "Encountered end of Atlas...");
                }
                else if(name.toLowerCase().equals("texture"))
                {
                    Log.v(getClass().toString(), "Encountered end of Texture...");
                }
            }
            eventType = xpp.next();
        }
    }
/*
    UVMap.put(index, new float[] {
    AtlasResolution/(float)x,AtlasResolution/(float)y,
            AtlasResolution/(float)(x + width),  AtlasResolution/(float)(y),
            AtlasResolution/(float)(x + width),  AtlasResolution/(float)(y + height),
            AtlasResolution/(float)(x),  AtlasResolution/(float)(x + width)
});
    */


    @Override
    public float[] GetUVCoords(int index) {
        return UVMap.get(index);
    }
}
