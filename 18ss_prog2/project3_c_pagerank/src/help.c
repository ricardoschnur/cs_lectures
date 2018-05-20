#include <stdio.h>

void print_help() {
  printf("This program calculates the PageRank of a graph.\n\n");
  printf("    pagerank [OPTIONS] [FILE]\n\n");
  printf("    -h\n      show help\n\n");
  printf("    -s\n      print statistics of the graph\n\n");
  printf("    -m N\n      calculate PageRank via Markow chains, do N steps\n\n");
  printf("    -r N\n      calculate PageRank via random surfer method, do N>0 steps\n\n");
  printf("    -p N\n      set parameter in PageRank algorthim to N%%, where 0 < N <= 100 is an integer\n\n");
  printf("At least one of the options in {h, s, m, r} has to be specified.\n");
  printf("It is possible to call multiple options, but each option may only be called once at most.\n");
  printf("If an option other than 'h' is called then [FILE] is required and must be a \n");
  printf("DOT (graph description language) file containing a directed graph.\n");
  printf("Note that only edges of the kind 'origin -> destination;' are allowed.\n");
}
