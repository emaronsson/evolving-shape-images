# evolving-shape-images

Evolving shape images is a small JavaFX application, implementing a genetic algorithm which evolves a population of images consisting of triangles to look more and more similar to a reference image selected by the user. The population of the genetic algorithm is a number of triangle images, and their genes are the triangles and their colors and positions. 

The algorithm proceeds through generations, and tries to create new and improved ones, with as high fitness as possible. The image with the highest fitness, where fitness is calculated as the similarity compared to the original image, will be drawn on the screen. The fitness function is a simple method for comparing the pixels between an image and the original image.

For each generation, the most fit individuals out of randomly selected subsets of the population are selected for crossover to produce new individuals. The individuals inherit genes from their parents, but these genes may also be mutated with a selected probability. The mutation is important for obtaining diversity in the next generation, and for allowing to explore different triangle setups.

The GUI:

![gui](https://user-images.githubusercontent.com/5596268/30002311-5d37047e-90a6-11e7-9751-7c274c7cae8a.png)


