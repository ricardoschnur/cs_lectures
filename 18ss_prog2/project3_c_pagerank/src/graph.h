#ifndef GRAPH_H
#define GRAPH_H

#include <stdio.h>

/*
  Datatype for directed graphs
  Note that parallel edges and self-edges are allowed!
*/
typedef struct graph graph_t;


/*
  Initialize an empty graph G.
  You are responsible to free memory with graphDestroy(G)!
*/
graph_t *graphInit();

/*
  Reads a graph from file and return a pointer to it.
  You are responsible to free memory with graphDestroy(G)!
*/
graph_t *graphFromFile(FILE *fp);


// Destroy graph and free memory
void graphDestroy(graph_t *G);


// prints the graph
void graphPrint(graph_t *G);


// Add a node with name label to the graph G
void addNode(graph_t *G, const char* label);


/*
  Add an edge from origin to destination to G.
  If either of these nodes does not exist, add them.
*/
void addEdge(graph_t *G, const char* origin, const char* destination);


/*
  Print some details of the graph:
  number of nodes, number of edges, minimal and maximal in-/ and out-degrees
*/
void stats(graph_t *G);


// Return the name of G, at most 257 characters
char *label(graph_t *G);


// Return the number of vertices of G
unsigned nodecount(graph_t *G);


// Return the number of edges of G
unsigned edgecount(graph_t *G);


// Return the name of the i-th node of G, 0 <= i < nodecount(G), at most 257 characters
char *nodelabel(graph_t *G, unsigned i);


// Return the outdegree of the i-th node of G
unsigned outdeg(graph_t *G, unsigned i);


// Return the destination of the j-th edge of the i-th node of G
unsigned followEdge(graph_t *G, unsigned i, unsigned j);


// Return the number of edges from the i-th to the j-tj node of G
unsigned localedgecount(graph_t *G, unsigned i, unsigned j);

#endif
