package com.appsatwork.ezgles.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 28-3-2015.
 */
public class BufferBuilder
{
    public static ShortBuffer BuildShortBuffer(short[] shorts)
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(shorts.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        shortBuffer.put(shorts);
        shortBuffer.position(0);

        return shortBuffer;
    }

    public static FloatBuffer BuildFloatBuffer(float[] floats)
    {
        //Each float takes 4 bytes
        ByteBuffer buffer = ByteBuffer.allocateDirect(floats.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = buffer.asFloatBuffer();
        floatBuffer.put(floats);
        floatBuffer.position(0);

        return floatBuffer;
    }
}
