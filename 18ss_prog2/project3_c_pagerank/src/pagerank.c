#include "pagerank.h"
#include "graph.h"
#include "utils.h"


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
  unsigned nodes = nodecount(G);
  unsigned edges;
  int rng;
  double normalize = 1.0 / N;

  // Randomly choose starting point
  unsigned currentNode = randu(nodes);

  // Do N iterations of the random surfer
  while (N > 0) {
    // Are there outgoing edges?
    edges = outdeg(G, currentNode);

    // There are outgoing edges
    if (edges > 0) {
      // Decide if follow link or go to random page
      rng = randu(100);

      // Go to random page
      if (rng < p) {
        currentNode = randu(nodes);
        array[currentNode] += 1;
      }

      // Follow a link
      else {
        rng = randu(edges);
        currentNode = followEdge(G, currentNode, rng);
        array[currentNode] += 1;
      }
    }

    // There are no outgoing edges
    else {
      // Go to random page
      currentNode = randu(nodes);
      array[currentNode] += 1;
    }

    --N;
  }

  // normalize PageRank
  for (unsigned i = 0; i < nodes; ++i) {
    array[i] *= normalize;
  }
}
