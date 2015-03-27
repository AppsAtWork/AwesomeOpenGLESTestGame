package Engine.Drawing.DrawingListGenerators;

import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public interface IDrawingListGenerator
{
    ShortBuffer GetFilledDrawingList();
    ShortBuffer GetOpenedDrawingList();
}
