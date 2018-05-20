#ifndef MATRIX_H
#define MATRIX_H

//  Datatype for matrices
typedef struct matrix matrix_t;


/*
  Initialize a (m x n)-matrix M with zeroes.
  You are responsible to free memory with matrixDestroy(M)!
*/
matrix_t *matrixInit(unsigned m, unsigned n);


// Destroy matrix and free memory
void matrixDestroy(matrix_t *A);


// Set the (i,j)-th entry of A to val
void matrixSet(matrix_t *A, unsigned i, unsigned j, double val);


// Return the (i,j)-th entry of A
double matrixGet(matrix_t *A, unsigned i, unsigned j);


/*
  Multiply a (m x n)-matrix A with a (n x l)-matrix B
  and store the result in a (m x l)-matrix C
*/
void matrixMult(matrix_t *A, matrix_t *B, matrix_t *C);


// Print matrix A
void matrixPrint(matrix_t *A);


// Copy matrix B into A
void matrixCopy(matrix_t *A, matrix_t *B);

#endif
