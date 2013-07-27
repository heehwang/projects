#include<stdio.h>
#include<stdlib.h>

int rot13cmp(const void *name1, const void *name2);

int main(void){


//Creating an array of character.
  char c = 0; 
  char* buffer= &c; 
  int memBuffer = sizeof(char)*1000;
  buffer=(char*)malloc(memBuffer); 
  if(buffer==NULL){
    fprintf(stderr,"malloc for buffer %u bytes failed",(unsigned)memBuffer);
    exit(EXIT_FAILURE);
  }
  int n = 0;
  c = getchar();
  while(c != EOF){
    buffer[n] = c;
    c = getchar();
    n++;
    if(n > memBuffer){
      memBuffer = memBuffer+sizeof(char)*1000;
      buffer=(char*)realloc(buffer, memBuffer);
      if(buffer==NULL){
	fprintf(stderr,"realloc for buffer %u bytes failed",(unsigned)memBuffer);
	exit(EXIT_FAILURE);
      }
    }
  }
// Append '\n' in case stdin ends with non '\n' character.                       
  if(buffer[n-1]!='\n')
    buffer[n] = '\n';

// Create an array of pointer to buffer.
  char** head;
  int memHead = sizeof(char*)*1000;
  head = (char**)malloc(memHead); 
  if(head==NULL){
    fprintf(stderr,"malloc for head %u bytes failed",(unsigned)memHead);
    exit(EXIT_FAILURE);
  }

  // Assign starting addresses to the head
  head[0] = &buffer[0];
  int i=1;
  int j=1;
  while(j<n-1){
    if(buffer[j]=='\n' && buffer[j+1]!='\n'){
      head[i]=&buffer[j+1];
      i++;
      if(i > memHead){
	memHead = memHead+ sizeof(char*)*1000;
	head=(char**)realloc(head, memHead);
	if(head==NULL){
	  fprintf(stderr,"realloc for head %u bytes failed",(unsigned)memHead);
	  exit(EXIT_FAILURE);
	}
      }
    }
    j++;
  }

//Quicksort the array of pointer to character
  qsort(head, i, sizeof(char*), rot13cmp); 
// Print out the sorted output
  int k;
  for(k=0; k<i; k++){
    c = *head[k];
    while(c != '\n' && c != EOF){
      putchar(c);
	c = *(++head[k]);
      }
    putchar('\n');
    }
  
// return the allocated memory.
   free(buffer);
   free(head);
  return 0;
}

int rot13cmp(const void *name1, const void *name2){

  char const *a = *(char const **)name1;
  char const *b = *(char const **)name2;
  int i = 0;
  int j = 0;
  char* c;
  char* d;
  c=(char*)malloc(sizeof(char*)*1000);
  if(c==NULL){
    fprintf(stderr,"malloc for c %u bytes failed",(unsigned)sizeof(char*)*1000);
    exit(EXIT_FAILURE);
  }
  d=(char*)malloc(sizeof(char*)*1000);
  if(d==NULL){
    fprintf(stderr,"malloc for d %u bytes failed",(unsigned)sizeof(char*)*1000);
    exit(EXIT_FAILURE);
  }

  while(a[i] != '\n'){
    if((a[i] >=65 && a[i] <=77) || (a[i] >= 97 && a[i] <= 109)){
      c[i] = a[i]+13;
      i++;
    }
    else if((a[i] >=78 && a[i] <=90) || (a[i] >= 110 && a[i] <= 122)){
      c[i] = a[i]-13;
      i++;
    }
    else
      c[i] = a[i];
      i++;
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

  int k = 0;
  
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

  

