## FishScape

<br>
Fishscape is a side scrolling 2D retro style graphics game. The objective is to direct the fish
by tapping on the screen.There are different types of food which have different effect on the fish.
There are enemies too which can take away the fish'slife. If a player touches the enemy, they lose.

<br>

### Project Setup

* [DI<a name="di"></a>](#di)
* [Domain<a name="domain"></a>](#domain)
  * [data<a name="data"></a>](#data)
  * [entity<a name="entity"></a>](#entity)
  * [repository<a name="repository"></a>](#repository)
  * [Room<a name="room"></a>](#room)
  * [SceneBase](#scenebase)
* [Shared](#shared)
  * [AnimationManager](#animationmanager)
  * [Console](#console)
  * [Extensions](#extenstions)
  * [FontManager](#fontmanager)
  * [FoodManager](#foodmanager)
  * [LifeFactory](#lifefactory)
  * [ScoreManager](#scoremanager)
  * [SoundManager](#soundmanager)
  * [ViewManager](#viewmanager)
* [UI](#ui)
  * [dialogs](#dialogs)
    * [High Score Dialog](#highScoreDialog)
  * [gameover](#gameover)
    * [Game Over Scene](#GameOverScene)
  * [lists](#list)
    * [HighScoreListAdapter](#HighScoreListAdapter)
  * [menu](#menu)
    * [Pause Menu](#pauseMenu)
  * [scene](#scene)
  * [start](#start)
* [App](#app)

### DI<a name="di"></a>
The DI(Dependency Injection) Module manages app dependency injection. <br>
Components like data base and repositories can be obtained from the DI  <br>
module via the corresponding <i>provide()</i> method.<br>

### Domain<a name="domain"></a>
The domain contains all business logic. There is no UI logic within the <br>
the domain.

##### data<a name="data"></a>
The data layer provide all business logic for data processing.
##### entity<a name="entity"></a>
The <i>entity</i> module contains game entities such as `Player`, `Food`...

##### repository<a name="repository"></a>
The `repository` module contains the game Repository. It performs business<br>
logic for processing data from both local and remote sources.

##### Room<a name="room"></a>
The room package manages all room database contents withing `FishScape`.


