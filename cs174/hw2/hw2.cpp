#include <stdio.h>
#include <GL/glew.h>
#include <GL/freeglut.h>
#include "Angel.h"

typedef Angel::vec4  color4;
typedef Angel::vec4  point4;

const int NumVertices = 288; //(8 cubes)(6 faces)(2 triangles/face)(3 vertices/triangle)

point4 points[NumVertices];
color4 colors[NumVertices];

GLuint  model_view;  // model-view matrix uniform shader variable location
// Projection transformation parameters

GLfloat eyeX = 0.0;
GLfloat eyeY = 0.0;
GLfloat eyeZ = 30.0;

GLfloat alpha = 0.0;
GLfloat cubeRotation = 0.0;

const GLfloat moveDegree = 1.0 * DegreesToRadians;
const GLfloat moveUnit = 0.25;

GLfloat left = -10.0, right = 10.0;
GLfloat bottom = -10.0, top = 10.0;
GLfloat zNear = 5.0, zFar = 100.0;

GLuint  projection; // projection matrix uniform shader variable location
GLuint vColor;
GLuint myColorInShader;
int colorOffset=0;


GLfloat radius = 50.0;
GLfloat theta = 0.0;
GLfloat phi = 0.0;


// Vertices of a unit cube centered at origin, sides aligned with axes
point4 vertices[64] = {
    point4(5.0, 5.0, 15.0, 1.0 ), 
    point4(5.0, 15.0, 15.0, 1.0 ),
    point4(15.0, 15.0, 15.0, 1.0 ),
    point4(15.0, 5.0, 15.0, 1.0 ),
    point4(5.0, 5.0, 5.0, 1.0 ),
    point4(5.0, 15.0, 5.0, 1.0 ),
    point4(15.0, 15.0, 5.0, 1.0 ),
    point4(15.0, 5.0, 5.0, 1.0 ),

    point4(-5.0, 5.0, 15.0, 1.0 ),
    point4(-5.0, 15.0, 15.0, 1.0 ),
    point4(-15.0, 15.0, 15.0, 1.0 ),
    point4(-15.0, 5.0, 15.0, 1.0 ),
    point4(-5.0, 5.0, 5.0, 1.0 ),
    point4(-5.0, 15.0, 5.0, 1.0 ),
    point4(-15.0, 15.0, 5.0, 1.0 ),
    point4(-15.0, 5.0, 5.0, 1.0 ),

    point4(-5.0, -5.0, 15.0, 1.0 ),
    point4(-5.0, -15.0, 15.0, 1.0 ),
    point4(-15.0, -15.0, 15.0, 1.0 ),
    point4(-15.0, -5.0, 15.0, 1.0 ),
    point4(-5.0, -5.0, 5.0, 1.0 ),
    point4(-5.0, -15.0, 5.0, 1.0 ),
    point4(-15.0, -15.0, 5.0, 1.0 ),
    point4(-15.0, -5.0, 5.0, 1.0 ),

    point4(5.0, -5.0, 15.0, 1.0 ),
    point4(5.0, -15.0, 15.0, 1.0 ),
    point4(15.0, -15.0, 15.0, 1.0 ),
    point4(15.0, -5.0, 15.0, 1.0 ),
    point4(5.0, -5.0, 5.0, 1.0 ),
    point4(5.0, -15.0, 5.0, 1.0 ),
    point4(15.0, -15.0, 5.0, 1.0 ),
    point4(15.0, -5.0,  5.0, 1.0 ),

    point4(5.0, 5.0, -15.0, 1.0 ),
    point4(5.0, 15.0, -15.0, 1.0 ),
    point4(15.0, 15.0, -15.0, 1.0 ),
    point4(15.0, 5.0, -15.0, 1.0 ),
    point4(5.0, 5.0, -5.0, 1.0 ),
    point4(5.0, 15.0, -5.0, 1.0 ),
    point4(15.0, 15.0, -5.0, 1.0 ),
    point4(15.0, 5.0, -5.0, 1.0 ),

    point4(-5.0, 5.0, -15.0, 1.0 ),
    point4(-5.0, 15.0, -15.0, 1.0 ),
    point4(-15.0, 15.0, -15.0, 1.0 ),
    point4(-15.0, 5.0, -15.0, 1.0 ),
    point4(-5.0, 5.0, -5.0, 1.0 ),
    point4(-5.0, 15.0, -5.0, 1.0 ),
    point4(-15.0, 15.0, -5.0, 1.0 ),
    point4(-15.0, 5.0, -5.0, 1.0 ),

    point4(-5.0, -5.0, -15.0, 1.0 ),
    point4(-5.0, -15.0, -15.0, 1.0 ),
    point4(-15.0, -15.0, -15.0, 1.0 ),
    point4(-15.0, -5.0, -15.0, 1.0 ),
    point4(-5.0, -5.0, -5.0, 1.0 ),
    point4(-5.0, -15.0, -5.0, 1.0 ),
    point4(-15.0, -15.0, -5.0, 1.0 ),
    point4(-15.0, -5.0, -5.0, 1.0 ),

    point4(5.0, -5.0, -15.0, 1.0 ),
    point4(5.0, -15.0, -15.0, 1.0 ),
    point4(15.0, -15.0, -15.0, 1.0 ),
    point4(15.0, -5.0, -15.0, 1.0 ),
    point4(5.0, -5.0, -5.0, 1.0 ),
    point4(5.0, -15.0, -5.0, 1.0 ),
    point4(15.0, -15.0, -5.0, 1.0 ),
    point4(15.0, -5.0, -5.0, 1.0 )

};

// RGBA olors
color4 vertex_colors[8] = {
    color4( 0.32549, 0.40784, 0.58431, 1.0 ),  // ucla blue
    color4( 1.0, 0.0, 0.0, 1.0 ),  // red
    color4( 1.0, 1.0, 0.0, 1.0 ),  // yellow
    color4( 0.0, 1.0, 0.0, 1.0 ),  // green
    color4( 0.0, 0.0, 1.0, 1.0 ),  // blue
    color4( 1.0, 0.0, 1.0, 1.0 ),  // magenta
    color4( 1.0, 1.0, 1.0, 1.0 ),  // white
    color4( 0.0, 1.0, 1.0, 1.0 )   // cyan
};

//----------------------------------------------------------------------------

int bufIndex = 0;
int cIndex = 0;

void
quad( int a, int b, int c, int d )
{
  colors[bufIndex] = vertex_colors[cIndex]; points[bufIndex] = vertices[a]; bufIndex++;
  colors[bufIndex] = vertex_colors[cIndex]; points[bufIndex] = vertices[b]; bufIndex++;
  colors[bufIndex] = vertex_colors[cIndex]; points[bufIndex] = vertices[c]; bufIndex++;
  colors[bufIndex] = vertex_colors[cIndex]; points[bufIndex] = vertices[a]; bufIndex++;
  colors[bufIndex] = vertex_colors[cIndex]; points[bufIndex] = vertices[c]; bufIndex++;
  colors[bufIndex] = vertex_colors[cIndex]; points[bufIndex] = vertices[d]; bufIndex++;
}

void
colorcube()
{
  int i;
  for(i=0;i<8;i++){
    quad( 8*i+1, 8*i, 8*i+3, 8*i+2 );
    quad( 8*i+2, 8*i+3, 8*i+7, 8*i+6 );
    quad( 8*i+3, 8*i, 8*i+4, 8*i+7 );
    quad( 8*i+6, 8*i+5, 8*i+1, 8*i+2 );
    quad( 8*i+4, 8*i+5, 8*i+6, 8*i+7 );
    quad( 8*i+5, 8*i+4, 8*i+0, 8*i+1 );
    cIndex++;
  }
}

//----------------------------------------------------------------------------

// OpenGL initialization
void
init()
{
    colorcube();

    // Create a vertex array object
    GLuint vao;
    glGenVertexArrays( 1, &vao );
    glBindVertexArray( vao );

    // Create and initialize a buffer object
    GLuint buffer;
    glGenBuffers( 1, &buffer );
    glBindBuffer( GL_ARRAY_BUFFER, buffer );
    glBufferData( GL_ARRAY_BUFFER, sizeof(points) + sizeof(colors),
		  NULL, GL_STATIC_DRAW );
    glBufferSubData( GL_ARRAY_BUFFER, 0, sizeof(points), points );
    glBufferSubData( GL_ARRAY_BUFFER, sizeof(points), sizeof(colors), colors );

    // Load shaders and use the resulting shader program
    GLuint program = InitShader( "vshader31.glsl", "fshader31.glsl" );
    glUseProgram( program );

    // set up vertex arrays
    GLuint vPosition = glGetAttribLocation( program, "vPosition" );
    glEnableVertexAttribArray( vPosition );
    glVertexAttribPointer( vPosition, 4, GL_FLOAT, GL_FALSE, 0,
			   BUFFER_OFFSET(0) );

    vColor = glGetAttribLocation( program, "vColor" ); 
    glEnableVertexAttribArray( vColor );
    glVertexAttribPointer( vColor, 4, GL_FLOAT, GL_FALSE, 0,
			   BUFFER_OFFSET(sizeof(points)) );

    glEnable( GL_DEPTH_TEST );

    model_view = glGetUniformLocation(program, "model_view");
    projection = glGetUniformLocation(program, "projection");
    myColorInShader = glGetUniformLocation(program, "myColorInShader");
    glClearColor( 0.0, 0.0, 0.0, 1.0 ); 
}

//----------------------------------------------------------------------------



void
display( void ){

  glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

  mat4 p = Frustum(left, right, bottom, top, zNear, zFar);
  glUniformMatrix4fv(projection, 1, GL_TRUE, p);
  point4 eyePos(eyeX, eyeY, eyeZ, 1);

  
  color4 myColor;

  int i;
  for (i=0; i<8; i++){
  mat4  mv = RotateY(alpha/DegreesToRadians) * Translate(-eyePos) * RotateY(cubeRotation);
  glUniformMatrix4fv( model_view, 1, GL_TRUE, mv );
  
  myColor = vertex_colors[(i+colorOffset) % 8];    
  glUniform4fv(myColorInShader, 1, myColor);

  glDrawArrays( GL_TRIANGLES, 36*i, NumVertices/8 );
  }
  glutSwapBuffers();
}

//----------------------------------------------------------------------------

void
keyboard( unsigned char key, int x, int y ){
  switch( key ) {
  case 'q': case 'Q': exit( EXIT_SUCCESS ); break;
  case 'c': case 'C': colorOffset = colorOffset+1; break;
  case 'i': case 'I': eyeX += sin(alpha); eyeZ -= cos(alpha); break;
  case 'm': case 'M': eyeX -= sin(alpha); eyeZ += cos(alpha); break;
  case 'j': case 'J': eyeX -= moveUnit*cos(alpha); eyeZ -+ moveUnit*sin(alpha); break;
  case 'k': case 'K': eyeX += moveUnit*cos(alpha); eyeZ -+ moveUnit*sin(alpha); break;
  case 'n': case 'N': left *= 0.9; right *= 0.9; break;
  case 'w': case 'W': left *= 1.1; right *= 1.1; break;
  case 'r': case 'R': eyeX =0; eyeY = 0; eyeZ = 30.0; alpha = 0; left = -10.0; right = 10.0;
    cubeRotation = 0; break;
  }
  glutPostRedisplay();
}

void specialKey(int key, int x, int y){
  switch(key) {
  case GLUT_KEY_UP: eyeY += moveUnit; break;
  case GLUT_KEY_DOWN: eyeY -= moveUnit; break;
  case GLUT_KEY_LEFT: eyeX -= moveUnit; break;
  case GLUT_KEY_RIGHT: eyeX += moveUnit; break;
  }
  glutPostRedisplay();
}


//----------------------------------------------------------------------------

int
main( int argc, char **argv )
{
    glutInit( &argc, argv );
    glutInitDisplayMode( GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH );
    glutInitWindowSize( 512, 512 );
    glutInitContextVersion( 3, 2 );
    glutInitContextProfile( GLUT_CORE_PROFILE );
    glutCreateWindow( "Color Cube" );

    glewExperimental=GL_TRUE;
    glewInit();
    init();

    glutDisplayFunc( display );
    glutKeyboardFunc( keyboard );
    glutSpecialFunc( specialKey );
    glutMainLoop();

    return 0;
}
