#include <GL/glew.h>
#include <GL/freeglut.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <assert.h>
#include "FrameSaver.h"
#include "Timer.h"
#include "Shapes.h"
#include "tga.h"
#include "Angel.h"

GLfloat cameraX = 0.0f; 
GLfloat cameraY = 0.0f; 
GLfloat cameraZ = 0.0f;
GLfloat alpha = 0.0;
GLfloat Theta = 0.0;


FrameSaver FrSaver ;
Timer TM ;

int Width = 800;
int Height = 800 ;
int Button = -1 ;
float Zoom = 1 ;
int PrevY = 0 ;

int Animate = 0 ;
int Recording = 0 ;

void resetArcball() ;
void save_image();
void instructions();
void set_colour(float r, float g, float b) ;

const int STRLEN = 100;
typedef char STR[STRLEN];

#define PI 3.1415926535897
#define X 0
#define Y 1
#define Z 2

//texture

GLuint texture_cube;
GLuint texture_crate;

// Structs that hold the Vertex Array Object index and number of vertices of each shape.
ShapeData cubeData;
ShapeData crateData;
// Matrix stack that can be used to push and pop the modelview matrix.
class MatrixStack {
    int    _index;
    int    _size;
    mat4*  _matrices;

   public:
    MatrixStack( int numMatrices = 32 ):_index(0), _size(numMatrices)
        { _matrices = new mat4[numMatrices]; }

    ~MatrixStack()
	{ delete[]_matrices; }

    void push( const mat4& m ) {
        assert( _index + 1 < _size );
        _matrices[_index++] = m;
    }

    mat4& pop( void ) {
        assert( _index - 1 >= 0 );
        _index--;
        return _matrices[_index];
    }
};

MatrixStack  mvstack;
mat4         model_view;
GLint        uModelView, uProjection, uView;
GLint        uAmbient, uDiffuse, uSpecular, uLightPos, uShininess;
GLint        uTex, uEnableTex;

// The eye point and look-at point.
// Currently unused. Use to control a camera with LookAt().
Angel::vec4 eye = Angel::vec4(0, 0.0, 50.0,1.0);
Angel::vec4 ref = Angel::vec4(0.0, 0.0, 0.0,1.0);
Angel::vec4 up = Angel::vec4(0.0,1.0,0.0,0.0);

double TIME = 0.0 ;



void drawCube(void)
{
    glBindTexture( GL_TEXTURE_2D, texture_cube );
    glUniform1i( uEnableTex, 1 );
    glUniformMatrix4fv( uModelView, 1, GL_TRUE, model_view );
    glBindVertexArray( cubeData.vao );
    glDrawArrays( GL_TRIANGLES, 0, cubeData.numVertices );
    glUniform1i( uEnableTex, 0 );
}

void drawCrate(void)
{
    glBindTexture( GL_TEXTURE_2D, texture_crate );
    glUniform1i( uEnableTex, 1 );
    glUniformMatrix4fv( uModelView, 1, GL_TRUE, model_view );
    glBindVertexArray( crateData.vao );
    glDrawArrays( GL_TRIANGLES, 0, crateData.numVertices );
    glUniform1i( uEnableTex, 0 );
}





void myKey(unsigned char key, int x, int y)
{
    float time ;
    switch (key) {
      // Problem 1: ESC, q, and Q to close the window.
    case 'q': case 'Q': case 27: exit(0); break;

      // Problem 7: Moving the camera nearer or farer.
    case 'i': case 'I': cameraX += sin(alpha); cameraZ -= cos(alpha); break;
    case 'o': case 'O': cameraX -= sin(alpha); cameraZ += cos(alpha); break;
    }
    glutPostRedisplay() ;
}


void myinit(void)
{
    // Load shaders and use the resulting shader program
    GLuint program = InitShader( "vshader.glsl", "fshader.glsl" );
    glUseProgram(program);

    // Generate vertex arrays for geometric shapes
    generateCube(program, &cubeData);
    generateCrate(program, &crateData);

    uModelView  = glGetUniformLocation( program, "ModelView"  );
    uProjection = glGetUniformLocation( program, "Projection" );
    uView       = glGetUniformLocation( program, "View"       );

    glClearColor( 0.1, 0.1, 0.2, 1.0 ); // dark blue background

    uAmbient   = glGetUniformLocation( program, "AmbientProduct"  );
    uDiffuse   = glGetUniformLocation( program, "DiffuseProduct"  );
    uSpecular  = glGetUniformLocation( program, "SpecularProduct" );
    uLightPos  = glGetUniformLocation( program, "LightPosition"   );
    uShininess = glGetUniformLocation( program, "Shininess"       );
    uTex       = glGetUniformLocation( program, "Tex"             );
    uEnableTex = glGetUniformLocation( program, "EnableTex"       );

    glUniform4f(uAmbient,    0.2f,  0.2f,  0.2f, 1.0f);
    glUniform4f(uDiffuse,    0.6f,  0.6f,  0.6f, 1.0f);
    glUniform4f(uSpecular,   0.2f,  0.2f,  0.2f, 1.0f);
    glUniform4f(uLightPos,  15.0f, 15.0f, 30.0f, 0.0f);
    glUniform1f(uShininess, 100.0f);

    glEnable(GL_DEPTH_TEST);

    //Problem2: Load a tga file into a buffer.
    TgaImage cubeImage;
    if (!cubeImage.loadTGA("crate.tga"))
    {
        printf("Error loading image file\n");
        exit(1);
    }
    
    TgaImage crateImage;
    if (!crateImage.loadTGA("crate.tga"))
    {
        printf("Error loading image file\n");
        exit(1);
    }

    // Tri-linear filtering for problem 5
    glGenTextures( 1, &texture_cube );
    glBindTexture( GL_TEXTURE_2D, texture_cube );
    glTexImage2D(GL_TEXTURE_2D, 0, 4, cubeImage.width, cubeImage.height, 0,
                 (cubeImage.byteCount == 3) ? GL_BGR : GL_BGRA,
                 GL_UNSIGNED_BYTE, cubeImage.data );

    glGenerateMipmap(GL_TEXTURE_2D);    
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT );
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT );
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    
    // Nearest neighbor filtering for problem 5    
    glGenTextures( 1, &texture_crate );
    glBindTexture( GL_TEXTURE_2D, texture_crate );
    glTexImage2D(GL_TEXTURE_2D, 0, 4, crateImage.width, crateImage.height, 0,
                 (crateImage.byteCount == 3) ? GL_BGR : GL_BGRA,
                 GL_UNSIGNED_BYTE, crateImage.data );
    
    glGenerateMipmap(GL_TEXTURE_2D);
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT );
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT );
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST );
    glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST );
    glUniform1i( uTex, 0);





}

void set_colour(float r, float g, float b)
{
    float ambient  = 0.2f;
    float diffuse  = 0.6f;
    float specular = 0.2f;
    glUniform4f(uAmbient,  ambient*r,  ambient*g,  ambient*b,  1.0f);
    glUniform4f(uDiffuse,  diffuse*r,  diffuse*g,  diffuse*b,  1.0f);
    glUniform4f(uSpecular, specular*r, specular*g, specular*b, 1.0f);
}

void display(void)
{

    // Clear the screen with the background colour (set in myinit)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    model_view = mat4(1.0f);    
    model_view *= Translate(-1.0f, 0.0f, -10.0f);
    model_view *= Translate(-cameraX,-cameraY,-cameraZ);
    glUniformMatrix4fv( uView, 1, GL_TRUE, model_view );
    drawCube();
    model_view *= Translate(2.0f, 0.0f, 0.0f);
    drawCrate();
    glutSwapBuffers();
    if(Recording == 1)
        FrSaver.DumpPPM(Width, Height) ;
}

void myReshape(int w, int h)
{
    Width = w;
    Height = h;

    glViewport(0, 0, w, h);

    mat4 projection = Perspective(50.0f, (float)w/(float)h, 1.0f, 1000.0f);
    glUniformMatrix4fv( uProjection, 1, GL_TRUE, projection );
}


void myIdle(){
  Theta += 0.05;
  glutPostRedisplay();
}


void instructions() 
{
    printf("Press:\n");
    printf("  s to save the image\n");
    printf("  r to restore the original view.\n") ;
    printf("  0 to set it to the zero state.\n") ;
    printf("  a to toggle the animation.\n") ;
    printf("  m to toggle frame dumping.\n") ;
    printf("  q to quit.\n");
}




/*********************************************************
     PROC: main()
     DOES: calls initialization, then hands over control
           to the event handler, which calls 
           display() whenever the screen needs to be redrawn
**********************************************************/

int main(int argc, char** argv) 
{
    glutInit(&argc, argv);
    glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowPosition (0, 0);
    glutInitWindowSize(Width,Height);
    glutCreateWindow(argv[0]);
    printf("GL version %s\n", glGetString(GL_VERSION));
    glewExperimental = GL_TRUE;
    glewInit();
    myinit();
    glutReshapeFunc (myReshape);
    glutKeyboardFunc( myKey );
    instructions();
    glutDisplayFunc(display);
    glutIdleFunc(myIdle);
    glutMainLoop();
    TM.Reset() ;
    return 0;         // never reached
}




