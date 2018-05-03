#ifndef CONVOLUTION_H
#define CONVOLUTION_H

/**
 * Returns the convolution of the given image and kernel. To bypass the not 
 * covered parts of the kernel the image is mirrored at its boundaries.
 * result: result of the convolution
 * img: input image
 * w: width of the image
 * h: height of the image
 * kernel: convolution kernel
 * w_k: width of the kernel
 * w_h: height of the kernel
 */
void convolve(float *result, float *img, int w, int h, const float *kernel, int w_k, int w_h);

#endif
