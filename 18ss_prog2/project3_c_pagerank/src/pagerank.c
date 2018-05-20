#include "pagerank.h"
#include "graph.h"


void pagerankPrint(graph_t *G, double *array) {
  unsigned nodes = nodecount(G);

  for (unsigned i = 0; i < nodes; i++) {
    printf("%s    %.10f\n", nodelabel(G, i), array[i]);
  }
}


void markow(graph_t *G, double *array, int N, int p) {
  // TODO
  (void)G;
  (void)array;
  (void)N;
  (void)p;
  return;
}


void randomsurfer(graph_t *G, double *array, int N, int p) {
  // TODO
  (void)G;
  (void)array;
  (void)N;
  (void)p;
  return;
}
