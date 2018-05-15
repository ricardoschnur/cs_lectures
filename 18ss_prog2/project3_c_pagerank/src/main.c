/*
 * main.c
 *
 * Programmierung 2 - Projekt 3 (PageRank)
 */

#include <stdio.h>
#include <stdlib.h>

#include "utils.h"

int main(int argc, char *const *argv) {
  // initialize the random number generator
  rand_init();

  printf("You gave %d command line arguments%c\n",
      argc-1, argc==1 ? '.' : ':');
  for (unsigned i = 1; i < argc; ++i)
    printf("  - %s\n", argv[i]);

  exit(0);
}
