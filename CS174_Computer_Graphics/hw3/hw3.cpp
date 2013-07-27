#include <stdio.h>
#include <GL/glew.h>
#include <GL/freeglut.h>
#include "Angel.h"

const int NumTimesToSubdivide = 5;
const int NumTriangles        = 4096;  // (4 faces)^(NumTimesToSubdivide + 1)
const int NumVertices         = 3 * NumTriangles;
typedef Angel::vec4 point4;
typedef Angel::vec4 color4;
point4 points[NumVertices];
vec3   normals[NumVertices];
GLuint  Transformation, View, Projection,AmbientProduct, DiffuseProduct, SpecularProduct;
GLuint LightPosition, Shininess;
int Index = 0;

// Parameters for Projection
GLfloat Theta = 0.0; GLfloat alpha = 0.0; GLfloat sphereRotation = 0.0;
GLfloat cameraX = 0.0; GLfloat cameraY = 17.32; GLfloat cameraZ = 30.0;
GLfloat left = -10.0, right = 10.0; GLfloat bottom = -10.0, top = 10.0;
GLfloat zNear = 5.0, zFar = 100.0;
const GLfloat moveUnit = 0.25;
mat4 v,t,t2,p;

 // Initialize shader lighting parameters
point4 light_position( 0.0, 0.0, 0.0, 0.0 );
color4 light_ambient( 0.5, 0.5, 0.5, 1.0 );
color4 light_diffuse( 0.5, 0.5, 0.5, 1.0 );
color4 light_specular( 0.5, 0.5, 0.5, 1.0 );

color4 mAmb,mDif,mSpec;
float material_shininess;
color4 ambient_product, diffuse_product, specular_product;



void
triangle( const point4& a, const point4& b, const point4& c ){
    vec3  normal = normalize( cross(b - a, c - b) );
    normals[Index] = normal;  points[Index] = a;  Index++;
    normals[Index] = normal;  points[Index] = b;  Index++;
    normals[Index] = normal;  points[Index] = c;  Index++;
}

point4
unit( const point4& p ){
  float len = p.x*p.x + p.y*p.y + p.z*p.z;
  point4 t;
  if ( len > DivideByZeroTolerance ) {
    t = p / sqrt(len);
    t.w = 1.0;
  }
  return t;
}

void
divide_triangle( const point4& a, const point4& b, const point4& c, int count ){
  if ( count > 0 ) {
    point4 v1 = unit( a + b );
    point4 v2 = unit( a + c );
    point4 v3 = unit( b + c );
    divide_triangle(  a, v1, v2, count - 1 );
    divide_triangle(  c, v2, v3, count - 1 );
    divide_triangle(  b, v3, v1, count - 1 );
    divide_triangle( v1, v3, v2, count - 1 );
  }
  else triangle( a, b, c );    
}

void
tetrahedron( int count ){
  point4 v[4] = {
    vec4( 0.0, 0.0, 1.0, 1.0 ),
    vec4( 0.0, 0.942809, -0.333333, 1.0 ),
    vec4( -0.816497, -0.471405, -0.333333, 1.0 ),
    vec4( 0.816497, -0.471405, -0.333333, 1.0 )
  };

  divide_triangle( v[0], v[1], v[2], count );
  divide_triangle( v[3], v[2], v[1], count );
  divide_triangle( v[0], v[3], v[1], count );
  divide_triangle( v[0], v[2], v[3], count );
}


void
init(){
  // Subdivide a tetrahedron into a sphere
  tetrahedron( NumTimesToSubdivide );
  
  // Create a vertex array object
  GLuint vao;
  glGenVertexArrays( 1, &vao );
  glBindVertexArray( vao );

  // Create and initialize a buffer object
  GLuint buffer;
  glGenBuffers( 1, &buffer );
  glBindBuffer( GL_ARRAY_BUFFER, buffer );
  glBufferData( GL_ARRAY_BUFFER, sizeof(points) + sizeof(normals),NULL, GL_STATIC_DRAW );
  glBufferSubData( GL_ARRAY_BUFFER, 0, sizeof(points), points );
  glBufferSubData( GL_ARRAY_BUFFER, sizeof(points),sizeof(normals), normals );
  
  // Load shaders and use the resulting shader program
  GLuint program = InitShader( "vshader56.glsl", "fshader56.glsl" );
  glUseProgram( program );
  
  // set up vertex arrays
  GLuint vPosition = glGetAttribLocation( program, "vPosition" );
  glEnableVertexAttribArray( vPosition );
  glVertexAttribPointer( vPosition, 4, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(0) );

  GLuint vNormal = glGetAttribLocation( program, "vNormal" ); 
  glEnableVertexAttribArray( vNormal );
  glVertexAttribPointer( vNormal, 3, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(sizeof(points)) );

  AmbientProduct = glGetUniformLocation(program, "AmbientProduct");
  DiffuseProduct = glGetUniformLocation(program, "DiffuseProduct");
  SpecularProduct = glGetUniformLocation(program, "SpecularProduct");
  LightPosition = glGetUniformLocation(program, "LightPosition");
  Shininess = glGetUniformLocation(program, "Shininess");
  Transformation = glGetUniformLocation( program, "Transformation" );
  View = glGetUniformLocation( program, "View" );
  Projection = glGetUniformLocation( program, "Projection" );

  glEnable( GL_DEPTH_TEST );
  glClearColor(0.0, 0.0, 0.0, 0.0 ); /* Black background */
}

void sphere0(){
  t = RotateY(0.5*Theta) * Translate(0.0, 0.0, 0.0) * Scale(5.0, 5.0, 5.0);
  glUniformMatrix4fv( Transformation, 1, GL_TRUE, t );
  
  mAmb[0] = 1.0, mAmb[1] = 0.0, mAmb[2] = 0.0, mAmb[3] = 1.0 ;
  mDif[0] = 1.0, mDif[1] = 0.0, mDif[3] = 0.0, mDif[4] = 1.0 ;
  mSpec[0] = 1.0, mSpec [1] = 1.0, mSpec[2] = 1.0, mSpec[3] = 1.0 ;
  material_shininess = 10.0;

  ambient_product = light_ambient * mAmb;
  diffuse_product = light_diffuse * mDif;
  specular_product = light_specular * mSpec;

  glUniform4fv( AmbientProduct,1, ambient_product);
  glUniform4fv( DiffuseProduct,1, diffuse_product);
  glUniform4fv( SpecularProduct,1, specular_product);
  glUniform4fv( LightPosition, 1, light_position);
  glUniform1f( Shininess,material_shininess); 

  glDrawArrays( GL_TRIANGLES, 0, NumVertices );
}

void sphere1(){
  t = RotateY(1.5 * Theta) * Translate(10.0, 0.0, 0.0) * Scale(1.0, 1.0, 1.0);
  glUniformMatrix4fv( Transformation, 1, GL_TRUE, t );

  mAmb[0] = 0.0, mAmb[1] = 1.0, mAmb[2] = 0.0, mAmb[3] = 1.0 ;
  mDif[0] = 0.0, mDif[1] = 1.0, mDif[2] = 0.0, mDif[3] = 1.0 ;
  mSpec[0] = 1.0, mSpec [1] = 1.0, mSpec[2] = 1.0, mSpec[3] = 1.0;
  material_shininess = 5.0;

  ambient_product = light_ambient * mAmb;
  diffuse_product = light_diffuse * mDif;
  specular_product = light_specular * mSpec;

  glUniform4fv( AmbientProduct,1, ambient_product);
  glUniform4fv( DiffuseProduct,1, diffuse_product);
  glUniform4fv( SpecularProduct,1, specular_product);
  glUniform4fv( LightPosition, 1, light_position);
  glUniform1f( Shininess,material_shininess); 

    glDrawArrays( GL_TRIANGLES, 0, NumVertices );
  }

  void sphere2(){

    t = RotateY(0.8 * Theta) * Translate(14.0, 0.0, 0.0) * Scale(1.5, 1.5, 1.5);
    glUniformMatrix4fv( Transformation, 1, GL_TRUE, t );
 
    mAmb[0] = 0.0, mAmb[1] = 0.0, mAmb[2] = 1.0, mAmb[3] = 1.0 ;
    mDif[0] = 0.0, mDif[1] = 0.0, mDif[2] = 1.0, mDif[3] = 1.0 ;
    mSpec[0] = 1.0, mSpec [1] = 1.0, mSpec[2] = 1.0, mSpec[3] = 1.0 ;
    material_shininess = 5.0;
    ambient_product = light_ambient * mAmb;
    diffuse_product = light_diffuse * mDif;
    specular_product = light_specular * mSpec;
    glUniform4fv( AmbientProduct,1, ambient_product);
    glUniform4fv( DiffuseProduct,1, diffuse_product);
    glUniform4fv( SpecularProduct,1, specular_product);
    glUniform4fv( LightPosition, 1, light_position);
    glUniform1f( Shininess,material_shininess); 

    glDrawArrays( GL_TRIANGLES, 0, NumVertices );
  }

  void sphere3(){ 
    t = RotateY(0.6 * Theta) * Translate(18.0, 0.0, 0.0) * Scale(2.0, 2.0, 2.0);

    mAmb[0] = 1.0, mAmb[1] = 0.0, mAmb[2] = 1.0, mAmb[3] = 1.0 ;
    mDif[0] = 1.0, mDif[1] = 0.0, mDif[2] = 1.0, mDif[3] = 1.0 ;
    mSpec[0] = 1.0, mSpec [1] = 1.0, mSpec[2] = 1.0, mSpec[3] = 1.0 ;
    material_shininess = 5.0;

    ambient_product = light_ambient * mAmb;
    diffuse_product = light_diffuse * mDif;
    specular_product = light_specular * mSpec;
    glUniformMatrix4fv( Transformation, 1, GL_TRUE, t );
    glUniform4fv( AmbientProduct,1, ambient_product);
    glUniform4fv( DiffuseProduct,1, diffuse_product);
    glUniform4fv( SpecularProduct,1, specular_product);
    glUniform4fv( LightPosition, 1, light_position);
    glUniform1f( Shininess,material_shininess);  
    glDrawArrays( GL_TRIANGLES, 0, NumVertices );

    t2 = RotateX(-5.0* Theta) * Translate(0.0, 1.5, 0.0) * Scale(0.2, 0.2, 0.2);
    mAmb[0] = 0.5, mAmb[1] = 0.5, mAmb[2] = 0.5, mAmb[3] = 1.0;
    mDif[0] = 0.5, mDif[1] = 0.5, mDif[2] = 0.5, mDif[3] = 1.0;
    ambient_product = light_ambient * mAmb;
    diffuse_product = light_diffuse * mDif;
    glUniformMatrix4fv( Transformation, 1, GL_TRUE, t*t2 );
	glUniform4fv( AmbientProduct,1, ambient_product);
    glUniform4fv( DiffuseProduct,1, diffuse_product);
    
    glDrawArrays( GL_TRIANGLES, 0, NumVertices );
  }

void sphere4(){

  t = RotateY(0.5 * Theta) * Translate(22.0, 0.0, 0.0) * Scale(1.5, 1.5, 1.5);

  mAmb[0] = 1.0, mAmb[1] = 0.7, mAmb[2] = 1.0, mAmb[3] = 1.0 ;
  mDif[0] = 1.0, mDif[1] = 0.7, mDif[2] = 1.0, mDif[3] = 1.0 ;
  mSpec[0] = 1.0, mSpec [1] = 1.0, mSpec[2] = 1.0, mSpec[3] = 1.0 ;
  material_shininess = 5.0;

  ambient_product = light_ambient * mAmb;
  diffuse_product = light_diffuse * mDif;
  specular_product = light_specular * mSpec;

  glUniformMatrix4fv( Transformation, 1, GL_TRUE, t );
  glUniform4fv( AmbientProduct,1, ambient_product);
  glUniform4fv( DiffuseProduct,1, diffuse_product);
  glUniform4fv( SpecularProduct,1, specular_product);
  glUniform4fv( LightPosition, 1, light_position);
  glUniform1f( Shininess,material_shininess);  
  glDrawArrays( GL_TRIANGLES, 0, NumVertices );
  
  glDrawArrays( GL_TRIANGLES, 0, NumVertices );
}

void 
callbackDisplay()
{
  glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );  
  v = RotateY(alpha/DegreesToRadians) * Translate(-vec3(cameraX,cameraY,cameraZ)) * RotateY(sphereRotation);

  glUniformMatrix4fv( View, 1, GL_TRUE, v );  
  sphere0();
  sphere1();
  sphere2();
  sphere3();
  sphere4();
  glutSwapBuffers();
}

void 
callbackReshape(int width, int height){
  glViewport( 0, 0, width, height );
  GLfloat left = -10.0, right = 10.0, bottom = -10.0, top = 10.0, zNear = 5.0, zFar = 100.0;
  GLfloat aspect = GLfloat(width)/height;
  if ( aspect > 1.0 ) {
    left *= aspect;
    right *= aspect;
  }
  else {
    top /= aspect;
    bottom /= aspect;
  }
  p = Frustum( left, right, bottom, top, zNear, zFar );
  glUniformMatrix4fv( Projection, 1, GL_TRUE, p );
}

void 
callbackKeyboard(unsigned char key, int x, int y){
  switch( key ) {
  case 'q': case 'Q': exit( EXIT_SUCCESS ); break;
  case 'i': case 'I': cameraX += sin(alpha); cameraZ -= cos(alpha); break;
  case 'm': case 'M': cameraX -= sin(alpha); cameraZ += cos(alpha); break;
  case 'j': case 'J': cameraX -= moveUnit*cos(alpha); cameraZ -+ moveUnit*sin(alpha); break;
  case 'k': case 'K': cameraX += moveUnit*cos(alpha); cameraZ -+ moveUnit*sin(alpha); break;
  case 'n': case 'N': left *= 0.9; right *= 0.9; break;
  case 'w': case 'W': left *= 1.1; right *= 1.1; break;
  case 'r': case 'R': cameraX =0; cameraY = 17.32; cameraZ = 30.0; alpha = 0; left = -10.0; right = 10.0;
    sphereRotation = 0; break;
  }
  glutPostRedisplay();
}

void callbackSpecialKey(int key, int x, int y){
  switch(key) {
  case GLUT_KEY_UP: cameraY += moveUnit; break;
  case GLUT_KEY_DOWN: cameraY -= moveUnit; break;
  case GLUT_KEY_LEFT: cameraX -= moveUnit; break;
  case GLUT_KEY_RIGHT: cameraX += moveUnit; break;
  }
  glutPostRedisplay();
}

void 
callbackIdle(){
	Theta += 0.05;
	glutPostRedisplay();
}

void 
callbackTimer(int){
	glutTimerFunc(1000/30, callbackTimer, 0);
	glutPostRedisplay();
}

void 
initCallbacks(){
	glutDisplayFunc(callbackDisplay);
	glutReshapeFunc(callbackReshape);
	glutKeyboardFunc(callbackKeyboard);
	glutSpecialFunc(callbackSpecialKey);
	glutIdleFunc(callbackIdle);
	glutTimerFunc(1000/30, callbackTimer, 0);
}

int main(int argc, char** argv){
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
	glutInitWindowPosition(0, 0);
	glutInitWindowSize(768, 768);
	glutCreateWindow("GLUT Harness");
	glewExperimental=GL_TRUE;
	glewInit();
	init();
	initCallbacks();
	glutMainLoop();
	return 0;
}
