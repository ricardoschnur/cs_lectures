#ifndef INPUT_H
#define INPUT_H

/*
  Parses the command line arguments
   - Return value: 1 if error occured, otherwise 0
   - h, m, p, r, s are set to 1 if
      the respective option was called, otherwise 0.
   - mvalue, pvalue, rvalue are set to the respective input values,
      if no flag [-p] then pvalue defaults to 10.
   - Error if an option is called multiple times or with invalid arguments.
*/
int parse__command_line_arguments(int argc, char *const *argv,
  int *h, int *m, int *mvalue, int *p, int *pvalue, int *r, int *rvalue, int *s);

#endif
