#ifndef DERIVATION_H
#define DERIVATION_H

extern const float sobel_x[9];
extern const float sobel_y[9];

/**
 * Computes the discrete derivation of the image in x direction.
 */
void derivation_x_direction(float *result, float *img, int w, int h);

/**
 * Computes the discrete derivation of the image in y direction.
 */
void derivation_y_direction(float *result, float *img, int w, int h);

/**
 * Computes the gradient magnitude of the discrete derivation in x and 
 * y direction.
 * result: gradient of d_x and d_y
 * d_x: discrete derivation in x direction
 * d_y: discrete derivation in y direction
 * w: width of result, d_x and d_y
 * h: height of result, d_y and d_y
 */
void gradient_magnitude(float *result, float *d_x, float *d_y, int w, int h);

#endif
