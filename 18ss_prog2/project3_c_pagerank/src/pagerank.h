#ifndef PAGERANK_H
#define PAGERANK_H

#include "graph.h"

/*
  the i-th element of array contains the PageRank value of the i_th node of G
*/
void pagerankPrint(graph_t *G, double *array);


/*
  Calculate the PageRank of G via the random surfer method with N
  iterations and coefficient p. Array has to be initialized with zeroes.
  Result: array[i] contains the PageRank of the i-th node of G
*/
void randomsurfer(graph_t *G, double *array, int N, int p);


/*
  Calculate the PageRank of G via Markow chains with N iterations
  and coefficient p. Array has to be initialized with zeroes.
  Result: array[i] contains the PageRank of the i-th node of G
*/
void markow(graph_t *G, double *array, int N, int p);

#endif
