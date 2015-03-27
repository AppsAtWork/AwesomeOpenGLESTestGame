package Engine.Drawing.Drawers;

import android.opengl.GLES20;
import Engine.Objects.Geometry.Geometry;

/**
 * Created by Casper on 3/27/2015.
 */
public class GeometryDrawer extends Drawer
{
    private Geometry geometry;
    public GeometryDrawer(Geometry geometry)
    {
        this.geometry = geometry;
        BuildVertexBuffer(geometry.GetVertices());
    }

    @Override
    public void Draw(float[] projectionViewMatrix, int programHandle)
    {
        if(verticesDirty)
        {
            BuildVertexBuffer(geometry.GetVertices());
            verticesDirty = false;
        }

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
                vertexBuffer);

        //We need to send our projectionview matrix to OpenGLES too.
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programHandle, "uMVPMatrix"),1,false,projectionViewMatrix,0);

        //Send over the drawing type to the shader
        GLES20.glUniform1i(GLES20.glGetUniformLocation(programHandle, "drawingType"), 0);

        //Send over the color of the square
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "vColor"),1, geometry.GetColor().GetFloatArray() ,0);

        //OpenGLES now knows everything necessary to draw the square (except the color, later)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, geometry.DrawingList.GetFilledLength(), GLES20.GL_UNSIGNED_SHORT, geometry.DrawingList.GetFilledDrawingList());

        //Disable the vertex array again :)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}