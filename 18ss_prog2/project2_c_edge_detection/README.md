# Edge detection
A C program used to detect edges in pgm files. Note that it assumes that the maximal value of the image file is set to 255.
Build with
```bash
$ make
```
and call with
```bash
$ ./edgedetection -T <threshold> <filename>
```
where \<threshold\> is an integer between 0 and 255 specifying which pixels are set to black and which to white.

A detailed project description can be found in 'project2_description.pdf'.
