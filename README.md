# Animosity

This project is a demonstration of _Flocking behaviours_ and _Genetic algorithms_. Creatures are created and seek plants as a source of food, predators will seek herbivore creatures and eat them if capable of catching them. All creatures have a maximum lifetime and health value and if any of them reach 0 they will die; the health value will increase if the creature is able to eat but the lifetime value ensures it won't be able to live forever.

All values and parameters for the creatures can be adjusted via the UI and will be in place for all the new creatures generated. The graphs show the number of creatures alive (including plants and predators) and species-specific data over time.

The start button will generate an arbitrary amount of creatures to ensure enough DNA variety (see next paragraph), the simulation can be halted and resumed (Note that resuming will still generate more creatures). The most interesting results can be obtained if the simulation is started with arbitrary parameters and then not interfered with; it is still possible for the user to generate more creatures of a specific type if needed.

### Genetic Algorithm

The initial DNA parameters for the creatures are pseudo-random and control attraction and repulsion forces for food, predators and other creatures. Creature replication simply copies those parameters from the old creature to the new one with a low chance of mutation, for this system to work it must have enough DNA variety at the start of the simulation to avoid creatures dying out because of bad DNA parameters. In this case there is now fitness function and the creatures are passively evolving: the better creatures have a higher change of surviving for a longer period and have a higher reproduction rate. 

### Known Issues

 Simultaneous high amounts of creatures and predators can result in the simulation slowing down and cause low FPS. This is done to allow calculations to perform correctly although recovery takes longer than expected.
The UI scroll bar will not respond to the mouse scroll wheel if the mouse pointer is in the simulation panel (you need to drag the scoll bar down manually).
Generating creatures too quickly with the __Generate Creature__ and __Generate Predator__ button can cause the simulation to freeze (you then need to stop and resume again). This is because you are adding creatures to the Arraylist and it causes some loops to break.

### To do list

- [ ] Mutation rate controller.
- [ ] DNA parameters visualization.
- [ ] DNA parameters controller and adjusting method.
- [ ] Possible optimizations in the main run() method and vector calculations.
