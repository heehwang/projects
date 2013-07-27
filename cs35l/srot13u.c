#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/stat.h>
#include<sys/types.h>

int numComp=0;
int rot13cmp(const void *name1, const void *name2);
int main(void){
  struct stat fileStat;
  fstat(0,&fileStat);
  if(S_ISREG(fileStat.st_mode))
    printf("stdin is a regular file and the size is %lu\n",fileStat.st_size);
  else{
    printf("stdin is not a regular file and the size is %lu\n",fileStat.st_size);
    exit(1);
  }

//Creating an array of character.
  char* buffer; 
  int memBuffer;
  if(fileStat.st_size != 0)
    memBuffer = sizeof(char*)*fileStat.st_size;
  else
    memBuffer = sizeof(char*)*1000;

  buffer=(char*)malloc(memBuffer); 

  int n=0;
  int readP;
  do{
    readP=read(0,buffer+n,1);
    n++;
    if(n>=memBuffer){
      memBuffer = memBuffer+sizeof(char*)*1000;
      buffer=(char*)realloc(buffer,memBuffer);
    }
  }
  while(readP>0);
 

  // Append '\n' in case stdin ends with non '\n' character.                          
  if(buffer[n-1]!='\n'){
    memBuffer++;
    buffer=(char*)realloc(buffer,memBuffer);    
    buffer[n] = '\n';
  }

  // ------------------------------------------Works good until here

// Create an array of pointer to buffer.
  char** head;
  int memHead = memBuffer;
  head = (char**)malloc(memHead); 
  

  // Assign starting addresses to the head
  head[0] = &buffer[0];
  int headIndex=1;
  int bufIndex=1;
  while(bufIndex<n){
    if(buffer[bufIndex]=='\n' && buffer[bufIndex+1]!='\n'){
      head[headIndex]=&buffer[bufIndex+1];
      headIndex++;
    }
    bufIndex++;
  }
      
  //--------------line66--------------------------

//Quicksort the array of pointer to character
  qsort(head, headIndex, sizeof(char*), rot13cmp); 
// Print out the sorted output
  int k;
  char c;
  int recordSize=0;
  for(k=0; k<headIndex; k++){
    c = *head[k];
    while(c != '\n' && c != EOF){
      recordSize++;
      c = *(head[k] + recordSize);
    }
    write(1,head[k],recordSize+1);
    recordSize=0;
  }
  fprintf(stderr,"Number of comparisons: %u\n",(unsigned)numComp);
  
// return the allocated memory.
   free(buffer);
   free(head);
  return 0;
}

int rot13cmp(const void *name1, const void *name2){
  numComp++;
  char const *a = *(char const **)name1;
  char const *b = *(char const **)name2;
  char* c = (char*)malloc(sizeof(char*)*100000);
  char* d = (char*)malloc(sizeof(char*)*100000);
  int i = 0;
  int j = 0;
  int k = 0;

  while(a[i] != '\n'){
    if((a[i] >=65 && a[i] <=77) || (a[i] >= 97 && a[i] <= 109)){
      c[i] = a[i]+13;
      i++;
    }
    else if((a[i] >=78 && a[i] <=90) || (a[i] >= 110 && a[i] <= 122)){
      c[i] = a[i]-13;
      i++;
    }
    else{
      c[i] = a[i];
      i++;
    }
  }

  while(b[j] != '\n'){
    if((b[j] >=65 && b[j] <=77) || (b[j] >= 97 && b[j] <= 109)){
      d[j] = b[j]+13;
      j++;
    }
    else if((b[j] >=78 && b[j] <=90) || (b[j] >= 110 && b[j] <= 122)){
      d[j] = b[j]-13;
      j++;
    }
    else{
      d[j] = b[j];
      j++;
    }
  }
  
  while(!(c[k] == '\n' && d[k] == '\n')){
    if (c[k] == '\n'){
      free(c);
      free(d);
      return -1;
    }
    else if (d[k] == '\n'){
      free(c);
      free(d);
      return 1;
    }
    else if (c[k] < d[k]){
      free(c);
      free(d);
      return -1;
    }
    else if (c[k] > d[k]){
      free(c);
      free(d);
      return 1;
    }
    else if (c[k] == d[k])
      k++;
  }
  free(c);
  free(d);
  return 0;
}

  

