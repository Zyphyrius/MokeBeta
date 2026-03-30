# Moke Beta

## What is it?

**Moke** is video game version of a card game me and my friends made back in grade 9, but with changes to make it more feesible to code and remove the multiplayer aspect. It is essentially chess but each character has some unique ability and health points. Depending on the complexity this game ends up being, I can adjust the abilities to be more simple. I eventually want to turn this game into a rouge-like game with endless possibilities and upgrades, but for this project I want to just focus on the core gameplay (hence Moke *Beta*). The game I will make for this project will just consist of:
- Multiple unique characters with some ability
- A board characters can move around and attack on
- A **very** simple enemy AI that can interact with the game
- A victory or defeat screen depending on the match outcome

## Why?

I intend for this game to be simple and usable by anyone who wants to play a fun video game. Eventually the goal is to upload it to Steam and make it a proper game, but for now it will just be the core combat. I always liked video games and card games, so moving my own card game into a video game format sounds incredibly fun and interesting!

## User Stories

- As a user, I want to be able to add an arbitrary number of ally and enemy characters to the game, with a corresponding board size
- As a user, I want to be able to move my characters on their turns
- As a user, I want to be able to attack with my characters
- As a user, I want to be able to view each character's abilities
- As a user, I want to be able to view the turn order, which is a list containing all characters
- As a user, I want to be able to view the boardstate of the game
- As a user, I want to be able to pause, save, and leave the game on my turn, or just leave by conceding
- As a user, I want to be able to reload my saved game

## Phase 4 Task 2 Event Log Example:
Started game. Added [Hot Mould, Fridge Wagon Motor] and [Murky Water Berserker, Murky Water Nuke Rain]
Hot Mould moved up
Hot Mould attacked Murky Water Nuke Rain
Hot Mould moved down
New turn
Fridge Wagon Motor moved up
Fridge Wagon Motor moved up
Fridge Wagon Motor moved up
Fridge Wagon Motor moved left
Fridge Wagon Motor attacked Murky Water Nuke Rain
Fridge Wagon Motor moved right
Fridge Wagon Motor moved right
Fridge Wagon Motor moved down
New turn
Murky Water Berserker moved down
Murky Water Berserker moved right
Murky Water Berserker moved right
Murky Water Berserker moved right
Murky Water Berserker attacked Fridge Wagon Motor
New turn
Murky Water Nuke Rain moved right
Murky Water Nuke Rain moved down
Murky Water Nuke Rain moved down
Murky Water Nuke Rain moved right
Murky Water Nuke Rain attacked Fridge Wagon Motor
New turn
Hot Mould attacked Murky Water Nuke Rain
New turn
New turn
Murky Water Berserker attacked Fridge Wagon Motor
Murky Water Berserker attacked Fridge Wagon Motor
Murky Water Berserker attacked Fridge Wagon Motor
Murky Water Berserker attacked Fridge Wagon Motor
Murky Water Berserker attacked Fridge Wagon Motor
New turn
Hot Mould attacked Murky Water Berserker
New turn
Fridge Wagon Motor attacked Murky Water Berserker
Fridge Wagon Motor moved left
Fridge Wagon Motor moved left
Fridge Wagon Motor moved left
Fridge Wagon Motor moved left
Fridge Wagon Motor moved down
Fridge Wagon Motor moved right
Fridge Wagon Motor moved down
New turn
Murky Water Berserker moved down
Murky Water Berserker moved down
Murky Water Berserker attacked Hot Mould
Murky Water Berserker attacked Hot Mould
Murky Water Berserker attacked Hot Mould
New turn
Hot Mould attacked Murky Water Berserker
New turn
Fridge Wagon Motor moved right
Fridge Wagon Motor attacked Murky Water Berserker

## Phase 4 Task 3 Reflection:
One thing that possibly could have been improved is removing my reliance on the MokeGUI class to run the game. I'm not sure if it's possible, considering how much the game relies on player input. At the very least, it might be more practical to have MokeGame have a field of EnemyAI, creating a bi-directional relationship. This would remove EnemyAI's dependency on MokeGUI, making it more smooth.

Another possible change would be to remove GameGUI and make it a generic JPanel underneath MokeGUI. It doesn't do too much besides carry on the update method to its fields. It could all be handled by MokeGUI instead to make things less cluttered. 