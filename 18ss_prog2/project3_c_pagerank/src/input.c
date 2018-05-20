#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <getopt.h>

#include "input.h"

int parse__command_line_arguments(int argc, char *const *argv,
  int *h, int *m, int *mvalue, int *p, int *pvalue, int *r, int *rvalue, int *s)
  {
  int c;
  optopt = 0;

  while ((c = getopt (argc, argv, "hm:p:r:s")) != -1)
    switch (c)
      {
      case 'h':
        if (*h == 1) {
          fprintf (stderr, "Error: Invoked option -h multiple times.\n");
          return 1;
        }
        *h = 1;
        break;
      case 'm':
        if (*m == 1) {
          fprintf (stderr, "Error: Invoked option -m multiple times.\n");
          return 1;
        }
        *m = 1;
        *mvalue = atoi(optarg);
        break;
      case 'p':
        if (*p == 1) {
          fprintf (stderr, "Error: Invoked option -p multiple times.\n");
          return 1;
        }
        *p = 1;
        *pvalue = atoi(optarg);
        break;
      case 'r':
        if (*r == 1) {
          fprintf (stderr, "Error: Invoked option -r multiple times.\n");
          return 1;
        }
        *r = 1;
        *rvalue = atoi(optarg);
        break;
      case 's':
        if (*s == 1) {
          fprintf (stderr, "Error: Invoked option -s multiple times.\n");
          return 1;
        }
        *s = 1;
        break;
      default:
        fprintf (stderr, "Error: Could not parse arguments.\n");
        return 1;
      }


  // Check if at least one required option was called
  if (*h + *m + *r + *s == 0 ) {
    fprintf (stderr, "Error: None of the required options called. ");
    fprintf (stderr, "To see help, call as follows:\n   %s -h\n", argv[0]);
    return 1;
  }

  // Check if any non-option arguments aside from filename remain
  if (argc - optind > 1) {
    for (int i = optind; i < argc - 1; ++i) {
      fprintf (stderr, "Error: Unknown argument: %s\n", argv[i]);
    }
    return 1;
  }

  // Check if pvalue is set, otherwise default to pvalue = 10
  if (*p == 1) {
    if (*pvalue <= 0 || *pvalue > 100) {
      fprintf (stderr, "Error: Argument %d of [-p] has to be ", *pvalue);
      fprintf(stderr, "an integer between 1 and 100.\n");
      return 1;
    }
  }
  else {
    *pvalue = 10;
  }

  // Check if mvalue > 0
  if (*m == 1 && *mvalue <= 0) {
    fprintf (stderr, "Error: Argument %d of [-m] has to be a positive integer.\n", *mvalue);
    return 1;
  }

  // Check if rvalue > 0
  if (*r == 1 && *rvalue <= 0) {
    fprintf (stderr, "Error: Argument %d of [-r] has to be a positive integer.\n", *rvalue);
    return 1;
  }

  return 0;
}
