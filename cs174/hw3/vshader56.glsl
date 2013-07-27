#version 150 

in   vec4 vPosition;
in   vec3 vNormal;

// output values that will be interpretated per-fragment
out  vec3 fN;
out  vec3 fE;
out  vec3 fL;

uniform mat4 View;
uniform vec4 LightPosition;
uniform mat4 Transformation;
uniform mat4 Projection;


void main()
{
    fN = (View*Transformation*vec4(vNormal,0)).xyz;
    fE = - (View*Transformation*vPosition).xyz;
    fL = (View*(vec4(0.0,0.0,0.0,1.0) - Transformation*vPosition)).xyz;    
    gl_Position = Projection * View * Transformation* vPosition;
}
