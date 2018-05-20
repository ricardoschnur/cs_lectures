#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "graph.h"

/*
  -------------------------------------------------------------------------
  Types and structs
  -------------------------------------------------------------------------
*/

/*
  Linked list containing the nodes to which there is an edge
*/

typedef struct edge {
  unsigned destination;        // position of destination
  struct edge *next;           // pointer to next edge
} edge_t;


/*
  a node of a directed graph
*/

typedef struct node {
  char label[257];          // name of the node (max. 256 characters)
  unsigned position;        // nodes are ordered
  unsigned indeg;           // in-degree of the node
  unsigned outdeg;          // out-degree of the node
  edge_t *edges;            // outgoing edges
  struct node *next;        // pointer to next node
} node_t;


/*
  struct for directed graph
*/

typedef struct graph {
  char label[257];
  unsigned nodecount;   // number of vertices
  unsigned edgecount;   // number of edges
  node_t *head;         // pointer to first vertex
} graph_t;


/*
  -------------------------------------------------------------------------
  Static functions
  -------------------------------------------------------------------------
*/

/*
  Checks whether str is a valid name for an idenfitier.
  Constraints:
    - max. 256 characters
    - first character has to be a letter
    - remaining characteres have to be letters or numbers
*/
static int isidentifier(const char *str){
  // Check if str actually points to a string
  if (str == NULL) {
    return 0;
  }

  int i = 0;

  // Check if label longer than 256 characters
  while (str[i] != '\0' && i < 256) {
    ++i;
  }
  if (i == 256 && str[i] != '\0') {
    return 0;
  }

  // Check if first character is a letter
  if (!isalpha(str[0])) {
    return 0;
  }

  i = 1;
  while (str[i] != '\0') {
    if (!isalnum(str[i])) {
      return 0;
    }
    ++i;
  }

  return 1;
}

//  Initialize a node with name label and increase nodecount of G by 1
static node_t *nodeInit(graph_t *G, const char* label) {
  node_t *V = malloc( sizeof(V[0]) );  // allocate space for the node

  // Copy label
  int i = 0;
  while (label[i] != '\0' && i < 256) {
    (V->label)[i] = label[i];
    ++i;
  }
  // Check if label longer than 256 characters
  if (i == 256 && label[i] != '\0') {
    fprintf(stderr, "Error: Label of node exceeds ");
    fprintf(stderr, "maximal number of characters (256)\n");
    exit(1);                 // Stop program with error code 1
  }
  (V->label)[i] = '\0';

  V->position = G->nodecount;
  V->indeg = 0;
  V->outdeg = 0;
  V->next = NULL;
  V->edges = NULL;

  G->nodecount += 1;   // increase nodecount of G

  return V;
}


//  Initialize edge E from V to W
static void edgeInit(graph_t *G, node_t *V, node_t *W) {
  edge_t *E = malloc( sizeof(E[0]) );  // allocate space for the node

  E->destination = W->position;        // edge points to W
  E->next = NULL;                      // no next edge

  G->edgecount += 1;
  V->outdeg += 1;
  W->indeg += 1;

  // Find last existing edge in V
  if (V->edges == NULL) {
    V->edges = E;
    return;
  }
  else {
    edge_t *p = V->edges;
    if (p != NULL) {
      edge_t *q = p->next;
      while (q != NULL) {
        p = q;
        q = q->next;
      }
    }
    p->next = E;
  }

}


// Destroy edge_t and free memory
static void edgeDestroy(edge_t *V){
  if (V != NULL) {
    edge_t *p = V->next;
    edgeDestroy(p);
  }

  free(V);
}


// Destroy node_t and free memory
static void nodeDestroy(node_t *V){
  if (V != NULL) {
    edgeDestroy(V->edges);
    nodeDestroy(V->next);
  }

  free(V);
}


/*
  -------------------------------------------------------------------------
  Global functions
  -------------------------------------------------------------------------
*/

graph_t *graphInit(const char* label) {
  graph_t *G = malloc( sizeof(G[0]) );  // allocate space for the graph
  G->nodecount = 0;                     // has no nodes
  G->edgecount = 0;                     // has no edges
  G->head = NULL;                       // no first node

  // Set label of G to label
  if (!isidentifier(label)) {
    printf("Error: '%s' is not a valid identifier.\n", label);
    exit(1);
  }
  strcpy(G->label, label);

  return G;
}


void graphDestroy(graph_t *G){
  if (G != NULL) {
    nodeDestroy(G->head);
  }

  free(G);
}


void addNode(graph_t *G, const char* label) {
  // If G is empty add node and return
  if (G->nodecount == 0) {
    G->head = nodeInit(G, label);
    return;
  }

  /*
    If G is not empty, check if the node already exists.
    In case it does exist, return without doing anything.
  */
  node_t *V = G->head;  // V points to the first node of G
  if ( strcmp(label, V->label) == 0 ) {
    return;
  }

  for (unsigned i = 1; i < G->nodecount ; ++i) {
    V = V->next;        // Change pointer to next node

    // Compare labels
    if ( strcmp(label, V->label) == 0 ) {
      return;
    }
  }

  // V now points to the last node in G
  V->next = nodeInit(G, label);
}


void stats(graph_t *G) {
  unsigned minin, maxin, minout, maxout;
  node_t *V = G->head;

  // Check if G is empty
  if (G->nodecount == 0) {
  minin = 0;
  maxin = 0;
  minout = 0;
  maxout = 0;
  }

  // G is not empty
  else {
    minin = V->indeg;
    maxin = V->indeg;
    minout = V->outdeg;
    maxout = V->outdeg;

    for (unsigned i = 1; i < G->nodecount; ++i) {
      V = V->next;

      if(V->indeg < minin) {
        minin = V->indeg;
      }

      if(V->indeg > maxin) {
        maxin = V->indeg;
      }

      if(V->outdeg < minout) {
        minout = V->outdeg;
      }

      if(V->outdeg > maxout) {
        maxout = V->outdeg;
      }
    }
  }

  printf("%s:\n", G->label);
  printf("- num nodes: %u\n", G->nodecount);
  printf("- num edges: %u\n", G->edgecount);
  printf("- indegree: %u-%u\n", minin, maxin);
  printf("- outdegree: %u-%u\n", minout, maxout);
}


void addEdge(graph_t *G, const char* origin, const char* destination){
  // If they do not exist, add nodes to graph
  addNode(G, origin);
  addNode(G, destination);

  // Find origin
  node_t *V = G->head;   // V points to the first node of G
  while (strcmp(origin, V->label) != 0) {
    V = V->next;
  }

  // Find destination
  node_t *W = G->head;   // W points to the first node of G
  while (strcmp(destination, W->label) != 0) {
    W = W->next;
  }

  edgeInit(G, V, W);
  return;
}


void graphPrint(graph_t *G){
  printf("digraph %s {\n", G->label);

  node_t *V = G->head;
  while (V != NULL) {
    edge_t *E = V->edges;
    while (E != NULL) {
      node_t *W = G->head;
      for (unsigned i = 0; i < E->destination; ++i) {
        W = W->next;
      }
      printf("%s -> %s;\n", V->label, W->label);
      E = E->next;
    }
    V = V->next;
  }

  printf("}\n");
}


graph_t *graphFromFile(FILE *fp) {
  char string[257];

  // Check if file starts with keyword 'digraph'
  fscanf(fp, "%s", string);
  if (strcmp(string, "digraph") != 0) {
    printf("Error: Could not read graph from file.\n");
    exit(1);
  }

  // Read name and initialize the graph
  fscanf(fp, "%s", string);
  if (!isidentifier(string)) {
    printf("Error: '%s' is not a valid identifier.\n", string);
    exit(1);
  }
  graph_t *G = graphInit(string);

  // Check for '{'
  fscanf(fp, "%s", string);
  if (strcmp(string, "{") != 0) {
    printf("Error: Could not read graph from file.\n");
    exit(1);
  }

  // Get edges
  char origin[257], destination[257];
  while (fscanf(fp, "%s -> %[^;];", origin, destination) == 2) {
    if (!isidentifier(origin)) {
      printf("Error: '%s' is not a valid identifier.\n", origin);
      exit(1);
    }
    if (!isidentifier(destination)) {
      printf("Error: '%s' is not a valid identifier.\n", destination);
      exit(1);
    }

    addEdge(G, origin, destination);
  }

  // Check if all edges were read or if there was an error
  if (strcmp(origin, "}") != 0) {
    printf("Error: Could not read graph from file.\n");
    exit(1);
  }

  return G;
}


char *label(graph_t *G) {
  return G->label;
}


unsigned nodecount(graph_t *G) {
  return G->nodecount;
}


unsigned edgecount(graph_t *G) {
  return G->edgecount;
}


char *nodelabel(graph_t *G, unsigned i) {
  node_t *p = G->head;

  while (i > 0) {
    p = p->next;
    --i;
  }

  return p->label;
}
