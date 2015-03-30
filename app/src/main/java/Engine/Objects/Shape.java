package Engine.Objects;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Engine.Objects.GeometryObjects.Border;
import Engine.Objects.GeometryObjects.Geometry;
import Engine.Objects.GeometryObjects.Rectangle;
import Engine.Util.Color;
import Engine.Util.Vector2;

/**
 * Created by Casper on 27-3-2015.
 */
public class Shape implements IDrawable
{
    private Geometry geometry;
    private Color color;
    private ShortBuffer geometryDrawingListBuffer;
    private FloatBuffer geometryVertexBuffer;

    //A filled shape
    public Shape(Geometry geometry, Color color)
    {
        this.geometry = geometry;
        this.color = color;
        this.geometryDrawingListBuffer =  BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
    }


    //Color getter and setter
    public void SetColor(Color color)
    {
        this.color = color;
    }
    public Color GetColor()
    {
        return this.color;
    }

    //Geometry getter (no setter)
    public Geometry GetGeometry()
    {
        return geometry;
    }

    public void Draw(float[] projectionViewMatrix, int programHandle)
    {
        //For now, just rebuild the vertex buffer at every draw call. Change this in the future to
        //To use observer patterns and such, or move the vertex buffer building entirely to geometry.
        this.geometryVertexBuffer = BufferBuilder.BuildFloatBuffer(geometry.GetVertices());

        //Get a handle to the vPosition member of the shader
        //If I understand correctly, this is pretty much the point that is being rasterized.
        int positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");

        //Enable the vertex array that will contain the positions that will be drawn
        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                geometryVertexBuffer);

        //We need to send our projectionview matrix to OpenGLES too.
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programHandle, "uMVPMatrix"),1,false,projectionViewMatrix,0);

        //Send over the drawing type to the shader
        GLES20.glUniform1i(GLES20.glGetUniformLocation(programHandle, "drawingType"), 0);

        //Send over the color of the square
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "vColor"),1, color.GetFloatArray(), 0);

        //OpenGLES now knows everything necessary to draw the square (except the color, later)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, geometry.GetDrawingList().length, GLES20.GL_UNSIGNED_SHORT, geometryDrawingListBuffer);

        //Disable the vertex array again :)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
