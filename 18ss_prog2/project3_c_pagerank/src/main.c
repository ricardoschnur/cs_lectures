/*
 * main.c
 *
 * Programmierung 2 - Projekt 3 (PageRank)
 */

#include <stdio.h>
#include <stdlib.h>

#include "utils.h"
#include "graph.h"
#include "input.h"
#include "help.h"
#include "pagerank.h"

int main(int argc, char *const *argv) {
  // initialize the random number generator
  rand_init();


  // parse command line arguments
  int h, m, mvalue, p, pvalue, r, rvalue, s, parseerror;
  h = 0;
  m = 0;
  p = 0;
  r = 0;
  s = 0;
  parseerror = 1;

  parseerror = parse__command_line_arguments(argc, argv,
                  &h, &m, &mvalue, &p, &pvalue, &r, &rvalue, &s);
  if (parseerror != 0) {
    return 1;
  }


  // print help
  if (h == 1) {
    helpPrint();
  }


  // if [-h] was the only parameter then quit
  if (m + p + r + s == 0) {
    return 0;
  }


  // open file
  FILE *fp;
  fp = fopen(argv[argc-1], "r");
  if (fp == NULL) {
    fprintf(stderr, "Error: Could not open file %s\n", argv[argc-1]);
    return 1;
  }


  // read graph from file and then close file
  graph_t *G = graphFromFile(fp);
  fclose(fp);


  // Do stuff according to which options where called

  // print statistic of the graph
  if (s == 1) {
    stats(G);
  }


  // Calculate PageRank
  if (m == 1 || r == 1) {
    unsigned nodes = nodecount(G);

    // Case graph is empty
    if (nodes == 0) {
      graphDestroy(G);
      return 0;
    }

    // Case graph not empty
    double array[nodes];
    for (unsigned i = 0; i < nodes; ++i) {
      array[i] = 0;
    }

    // Calculate with Markow chain
    if (m == 1) {
      markow(G, array, mvalue, pvalue);
      pagerankPrint(G, array);
    }

    // Calculate with random surfer method
    if (r == 1) {
      randomsurfer(G, array, rvalue, pvalue);
      pagerankPrint(G, array);
    }
  }

  graphDestroy(G);
  return 0;
}
