#ifndef GRAPH_H
#define GRAPH_H

/*
  Datatype for directed graphs
  Note that parallel edges and self-edges are allowed!
*/
typedef struct graph graph_t;


/*
  Initialize an empty graph G.
  You are responsible to free memory with graphDestroy(*G)!
*/
graph_t *graphInit();

/*
  Reads a graph from file and return a pointer to it.
  You are responsible to free memory with graphDestroy(*G)!
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


#endif
