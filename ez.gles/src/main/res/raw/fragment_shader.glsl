precision mediump float;
varying vec2 v_texCoord;
uniform sampler2D s_texture;
uniform vec4 vColor;
uniform int drawingType;
void main()
{
    if(drawingType == 1)
        { gl_FragColor = texture2D( s_texture, v_texCoord ); }
    else if(drawingType == 0)
        { gl_FragColor = vColor; }
}