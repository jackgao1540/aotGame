# Attack on Titan 2D Simulator 

## A video game based on one of my favourite animes

- What will this application do?
  - The final stage of this application will be a cute video game where chibi titans are attacking your town, and you have to defeat them.
- Who will use this app?
  - People who want to have fun playing a cute game based on a famous anime.
- Why is this project of interest to me?
  - Because I really like AOT and video games. This first phase will simply implement a system for modelling the environment. The graphics and physics of the game have already been deeply brainstormed by me but it is a little too early to implement them fully as of right now.

## User Stories
- As a user, I want to be able to add multiple items to my inventory.
- As a user, I want to be able to sell items from my inventory.
- As a user, I want to be able to defeat titans that are attacking my village.
- As a user, I want to be able to check if I defeated all the titans and saved my village.
- As a user, I want to be able to earn money and points/score every time that I defeat a titan.
- As a user, I want to be able to save my session/game.
- As a user, I want to be able to load my saved games.

## Note
- To buy items, type the name of the item in the shop (case insensitive).
- To sell an item in your inventory, type the name of the item in the textfield.
- You cannot have more than one copy of a distinct item.
- There are two rounds in this game, however, this can be ignored
- if you save after a victory then load the game again, allowing for 
- infinite rounds. This is a feature, not a bug.

## Phase 4: Task 2
sample event log:
- Item Founding Titan Fluid purchased for $999
- Item Mikasa's Red Scarf purchased for $130
- Item Sword purchased for $10
- Item ODM Gear purchased for $50
- Item Sword sold from inventory for $10
- Item Thunder Spear purchased for $100
- Item Meat Pie for Sasha purchased for $3
- Process finished with exit code 0

## Phase 4: Task 3
What I would change if I had more time
- Firstly, it's clear that in the UML class diagram of this program that there are many redundancies within my class design.
- If the AttackOnTitanApp already has a GameState, 
- then it should probably not also need to include a separate
- version of another Player, ListOfTitans, etc. when 
- all that information is already contained within its own GameState.
- Admittedly, this was probably so that it would be easier to 
- generate a new GameState by first initializing those fields and then
- constructing a GameState, but still this could be improved.
- Next, it's clear that if each of the non-abstract classes that
- in some way extend the Collidable class implement the Writable interface
- then the Collidable class itself should just implement the 
- Writable interface, and each class should overwrite the toJson() method.
- Lastly, I would also work on making more varied and complex enemies,
- more interesting items, custom graphics, making the game more realistic
- by adding a grappling hook and also collision detection for the townhouses,
- more rounds, and an easier to use user interface.
