# evolving-shape-images

Evolving shape images is a small JavaFX application, implementing a genetic algorithm which evolves a population of images consisting of triangles to look more and more similar to a reference image selected by the user. The population of the genetic algorithm is a number of triangle images, and their genes are the triangles and their colors and positions. 

The algorithm proceeds through generations, and tries to create new and improved ones, with as high fitness as possible. The image with the highest fitness, where fitness is calculated as the similarity compared to the original image, will be drawn on the screen. The fitness function is a simple method for comparing the pixels between an image and the original image.

For each generation, the most fit individuals out of randomly selected subsets of the population are selected for crossover to produce new individuals. The individuals inherit genes from their parents, but these genes may also be mutated with a selected probability. The mutation is important for obtaining diversity in the next generation, and for allowing to explore different triangle setups.

The GUI:

![gui](https://user-images.githubusercontent.com/5596268/30002311-5d37047e-90a6-11e7-9751-7c274c7cae8a.png)

----------

Below is an example of how the most fit shape image improves during 69000 generations. The images consist of 70 triangles, the populations contained 50 images, and the mutation rate was 0.03.

![5](https://user-images.githubusercontent.com/5596268/30002489-f1be5c9c-90aa-11e7-89b5-a488794cf568.png) ![53](https://user-images.githubusercontent.com/5596268/30002492-fae94f84-90aa-11e7-8b84-c0472136feaf.png) ![300](https://user-images.githubusercontent.com/5596268/30002497-0bb04a2a-90ab-11e7-8874-948b13632bbb.png) ![9913](https://user-images.githubusercontent.com/5596268/30002503-1b8cb762-90ab-11e7-8d3e-813f9c91cb46.png) 
Generations 5, 53, 300, 9913. Similarity 70.55% - 89.8%

![14846](https://user-images.githubusercontent.com/5596268/30002507-2383ad2c-90ab-11e7-9060-44e6a54638b0.png) ![26724](https://user-images.githubusercontent.com/5596268/30002287-ef64df66-90a5-11e7-96d1-a8f7b80a53bf.png) ![69340](https://user-images.githubusercontent.com/5596268/30002292-0457f624-90a6-11e7-8c6e-1c306a70fc3c.png)

Generations 14846, 26724, 69340. Similarity 90.22% - 91.94%

-----------

The image below, hiding a famous painting, only contains 30 triangles.

![41442](https://user-images.githubusercontent.com/5596268/30002591-4ce11f2c-90ad-11e7-994f-28222f7e7b10.png)

This was the result after 41442 generations.
