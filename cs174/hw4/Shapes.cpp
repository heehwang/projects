#include "Angel.h"
#include "Shapes.h"
#include <cmath>

typedef Angel::vec4 color4;
typedef Angel::vec4 point4;
typedef Angel::vec3 point3;
typedef Angel::vec2 point2;

void setVertexAttrib(GLuint program, 
                     GLfloat* points,    GLsizeiptr psize, 
                     GLfloat* normals,   GLsizeiptr nsize,
                     GLfloat* texcoords, GLsizeiptr tsize)
{
    GLuint buffer[2];
    glGenBuffers( 2, buffer );

    glBindBuffer( GL_ARRAY_BUFFER, buffer[0] );
    glBufferData( GL_ARRAY_BUFFER, psize, points, GL_STATIC_DRAW );
    GLuint vPosition = glGetAttribLocation( program, "vPosition" );
    glEnableVertexAttribArray( vPosition );
    glVertexAttribPointer( vPosition, 4, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(0) );

    glBindBuffer( GL_ARRAY_BUFFER, buffer[1] );
    glBufferData( GL_ARRAY_BUFFER, nsize, normals, GL_STATIC_DRAW );
    GLuint vNormal = glGetAttribLocation( program, "vNormal" );
    glEnableVertexAttribArray( vNormal );
    glVertexAttribPointer( vNormal, 3, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(0) );

    if (texcoords)
    {
        GLuint tbuffer;
        glGenBuffers( 1, &tbuffer );
        glBindBuffer( GL_ARRAY_BUFFER, tbuffer );
        glBufferData( GL_ARRAY_BUFFER, tsize, texcoords, GL_STATIC_DRAW );
        GLuint vTexCoords = glGetAttribLocation( program, "vTexCoords" );
        glEnableVertexAttribArray( vTexCoords );
        glVertexAttribPointer( vTexCoords, 2, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(0) );
    }
    
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
    glBindVertexArray(0);
}

//----------------------------------------------------------------------------
// Cube

const int numCubeVertices = 36; //(6 faces)(2 triangles/face)(3 vertices/triangle)

point4 cubePoints [numCubeVertices];
point3 cubeNormals[numCubeVertices];
point2 cubeUV     [numCubeVertices];

// Vertices of a unit cube centered at origin, sides aligned with axes
point4 vertices[8] = {
    point4( -0.5, -0.5,  0.5, 1.0 ),
    point4( -0.5,  0.5,  0.5, 1.0 ),
    point4(  0.5,  0.5,  0.5, 1.0 ),
    point4(  0.5, -0.5,  0.5, 1.0 ),
    point4( -0.5, -0.5, -0.5, 1.0 ),
    point4( -0.5,  0.5, -0.5, 1.0 ),
    point4(  0.5,  0.5, -0.5, 1.0 ),
    point4(  0.5, -0.5, -0.5, 1.0 )
};

// quad generates two triangles for each face and assigns normals and texture coordinates
//    to the vertices
int Index = 0;
void quad( int a, int b, int c, int d, const point3& normal )
{
    cubePoints[Index] = vertices[a]; cubeNormals[Index] = normal; 
    cubeUV[Index] = point2(0.0f, 1.0f); Index++;
    cubePoints[Index] = vertices[b]; cubeNormals[Index] = normal;
    cubeUV[Index] = point2(0.0f, 0.0f); Index++;
    cubePoints[Index] = vertices[c]; cubeNormals[Index] = normal;
    cubeUV[Index] = point2(1.0f, 0.0f); Index++;
    cubePoints[Index] = vertices[a]; cubeNormals[Index] = normal;
    cubeUV[Index] = point2(0.0f, 1.0f); Index++;
    cubePoints[Index] = vertices[c]; cubeNormals[Index] = normal;
    cubeUV[Index] = point2(1.0f, 0.0f); Index++;
    cubePoints[Index] = vertices[d]; cubeNormals[Index] = normal;
    cubeUV[Index] = point2(1.0f, 1.0f); Index++;
}

// generate 12 triangles: 36 vertices, 36 normals, 36 texture coordinates
void colorcube()
{
    quad( 1, 0, 3, 2, point3( 0.0f,  0.0f,  1.0f) );
    quad( 2, 3, 7, 6, point3( 1.0f,  0.0f,  0.0f) );
    quad( 3, 0, 4, 7, point3( 0.0f, -1.0f,  0.0f) );
    quad( 6, 5, 1, 2, point3( 0.0f,  1.0f,  0.0f) );
    quad( 4, 5, 6, 7, point3( 0.0f,  0.0f, -1.0f) );
    quad( 5, 4, 0, 1, point3(-1.0f,  0.0f,  0.0f) );
}


/////////////////////////////////////////////////////////////////
// Crate
///////////////////////////////////////////////////////////////
const int numCrateVertices = 36; //(6 faces)(2 triangles/face)(3 vertices/triangle)

point4 cratePoints [numCrateVertices];
point3 crateNormals[numCrateVertices];
point2 crateUV     [numCrateVertices];

// Vertices of a unit cube centered at origin, sides aligned with axes
point4 vertices_1[8] = {
    point4( -0.5, -0.5,  0.5, 1.0 ),
    point4( -0.5,  0.5,  0.5, 1.0 ),
    point4(  0.5,  0.5,  0.5, 1.0 ),
    point4(  0.5, -0.5,  0.5, 1.0 ),
    point4( -0.5, -0.5, -0.5, 1.0 ),
    point4( -0.5,  0.5, -0.5, 1.0 ),
    point4(  0.5,  0.5, -0.5, 1.0 ),
    point4(  0.5, -0.5, -0.5, 1.0 )
};

// quad generates two triangles for each face and assigns normals and texture coordinates
//    to the vertices
int Index_1 = 0;
void quad_1( int a, int b, int c, int d, const point3& normal )
{
    cratePoints[Index_1] = vertices_1[a]; crateNormals[Index_1] = normal; 
    crateUV[Index_1] = point2(-0.5f, 1.5f); Index_1++;
    cratePoints[Index_1] = vertices_1[b]; crateNormals[Index_1] = normal;
    crateUV[Index_1] = point2(-0.5f, -0.5f); Index_1++;
    cratePoints[Index_1] = vertices_1[c]; crateNormals[Index_1] = normal;
    crateUV[Index_1] = point2(1.5f, -0.5f); Index_1++;
    cratePoints[Index_1] = vertices_1[a]; crateNormals[Index_1] = normal;
    crateUV[Index_1] = point2(-0.5f, 1.5f); Index_1++;
    cratePoints[Index_1] = vertices_1[c]; crateNormals[Index_1] = normal;
    crateUV[Index_1] = point2(1.5f, -0.5f); Index_1++;
    cratePoints[Index_1] = vertices_1[d]; crateNormals[Index_1] = normal;
    crateUV[Index_1] = point2(1.5f, 1.5f); Index_1++;
}

// generate 12 triangles: 36 vertices, 36 normals, 36 texture coordinates
void colorcrate()
{
    quad_1( 1, 0, 3, 2, point3( 0.0f,  0.0f,  1.0f) );
    quad_1( 2, 3, 7, 6, point3( 1.0f,  0.0f,  0.0f) );
    quad_1( 3, 0, 4, 7, point3( 0.0f, -1.0f,  0.0f) );
    quad_1( 6, 5, 1, 2, point3( 0.0f,  1.0f,  0.0f) );
    quad_1( 4, 5, 6, 7, point3( 0.0f,  0.0f, -1.0f) );
    quad_1( 5, 4, 0, 1, point3(-1.0f,  0.0f,  0.0f) );
}










// initialization
void generateCube(GLuint program, ShapeData* cubeData)
{
    colorcube();
    cubeData->numVertices = numCubeVertices;

    // Create a vertex array object
    glGenVertexArrays( 1, &cubeData->vao );
    glBindVertexArray( cubeData->vao );

    // Set vertex attributes
    setVertexAttrib(program, 
        (float*)cubePoints,  sizeof(cubePoints), 
        (float*)cubeNormals, sizeof(cubeNormals),
        (float*)cubeUV,      sizeof(cubeUV));
}

void generateCrate(GLuint program, ShapeData* crateData)
{
    colorcrate();
    crateData->numVertices = numCrateVertices;

    // Create a vertex array object
    glGenVertexArrays( 1, &crateData->vao );
    glBindVertexArray( crateData->vao );

    // Set vertex attributes
    setVertexAttrib(program, 
        (float*)cratePoints,  sizeof(cratePoints), 
        (float*)crateNormals, sizeof(crateNormals),
        (float*)crateUV,      sizeof(crateUV));
}
