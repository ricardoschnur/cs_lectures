#include "pagerank.h"
#include "graph.h"
#include "utils.h"
#include "matrix.h"


void pagerankPrint(graph_t *G, double *array) {
  unsigned nodes = nodecount(G);

  for (unsigned i = 0; i < nodes; i++) {
    printf("%s    %.10f\n", nodelabel(G, i), array[i]);
  }
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


void markow(graph_t *G, double *array, int N, int p) {
  unsigned nodes = nodecount(G);
  unsigned edges;
  double normalize = 1.0 / nodes;
  double prob, out;

  // Initialize Markow matrix M and probability vector v
  matrix_t *M = matrixInit(nodes, nodes);
  matrix_t *v = matrixInit(1, nodes);
  matrix_t *w = matrixInit(1, nodes);

  // Set values of M and v
  for (unsigned i = 0; i < nodes; ++i) {
    matrixSet(v, 0, i, normalize);

    for (unsigned j = 0; j < nodes; ++j) {
      out = outdeg(G, i);

      // Calculate transition probability from node i to node j
      if ( out == 0 ) {
        prob = normalize;
      }
      else {
        edges = localedgecount(G, i, j);
        prob = p / 100.0 * normalize + (1.0 - p / 100.0) / out * edges;
      }

      // Set value of M[i, j]
      matrixSet(M, i, j, prob);
    }
  }

  // Calculate v * M^N
  while (N > 0) {
    matrixMult(v, M, w);
    matrixCopy(v, w);
    --N;
  }

  // Set values in array
  for (unsigned i = 0; i < nodes; ++i) {
    array[i] = matrixGet(v, 0, i);
  }

  matrixDestroy(M);
  matrixDestroy(v);
  matrixDestroy(w);
  (void)p;
}
