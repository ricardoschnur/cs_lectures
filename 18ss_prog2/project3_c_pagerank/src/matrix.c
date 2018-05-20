#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

#include "matrix.h"


typedef struct matrix {
  double **values;
  unsigned rows;
  unsigned cols;
} matrix_t;


matrix_t *matrixInit(unsigned m, unsigned n) {
  matrix_t *A = malloc( sizeof(A[0]) );
  A->rows = m;
  A->cols = n;

  // Allocate columns
  A->values = calloc( m, sizeof( (A->values)[0] ) );
  if (A->values == NULL) {
    fprintf(stderr, "Error: Could not initialize matrix.\n");
    exit(1);
  }

  // Allocate rows
  for (unsigned i = 0; i < m; ++i) {
    (A->values)[i] = calloc( n, sizeof( (A->values)[i][0] ) );

    if ((A->values)[i] == NULL) {
      fprintf(stderr, "Error: Could not initialize matrix.\n");
      exit(1);
    }
  }

  return A;
}


void matrixDestroy(matrix_t *A) {
  if (A->values == NULL) {
    return;
  }

  for (unsigned i = 0; i < A->rows; ++i) {
    free( (A->values)[i] );
  }

  free(A);
}


void matrixSet(matrix_t *A, unsigned i, unsigned j, double val) {
  if (i >= A->rows || j > A->cols) {
    fprintf(stderr, "Error: Out of bounds.\n");
    exit(1);
  }

  (A->values)[i][j] = val;
}



double matrixGet(matrix_t *A, unsigned i, unsigned j) {
  if (i >= A->rows || j > A->cols) {
    fprintf(stderr, "Error: Out of bounds.\n");
    exit(1);
  }

  return (A->values)[i][j];
}


void matrixMult(matrix_t *A, matrix_t *B, matrix_t *C) {
  double sum;

  for (unsigned i = 0; i < A->rows; ++i) {
    for (unsigned j = 0; j < B->cols; ++j) {
      sum = 0;

      for (unsigned k = 0; k < A->cols; ++k) {
        sum = sum + (A->values)[i][k] * (B->values)[k][j];
      }

      (C->values)[i][j] = sum;
    }
  }
}


void matrixPrint(matrix_t *A) {
  for (unsigned i = 0; i < A->rows; ++i) {
    for (unsigned j = 0; j < A->cols; ++j) {
      printf("%.10f  ", (A->values)[i][j]);
    }
    printf("\n");
  }
}


void matrixCopy(matrix_t *A, matrix_t *B) {
  if (A->rows != B->rows || A->cols != B->cols) {
    fprintf(stderr, "Error: Dimension do not match.\n");
    exit(1);
  }

  for (unsigned i = 0; i < A->rows; ++i) {
    for (unsigned j = 0; j < A->cols; ++j) {
      (A->values)[i][j] = (B->values)[i][j];
    }
  }
}
