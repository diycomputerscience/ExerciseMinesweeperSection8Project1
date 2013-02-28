<h1>Section 8 Project 1</h1>

<h2>Overview</h2>

In the previous project, we completed the link between the user interface and the backend Board class. We have build a game that a user can actually play.

We have something special planned for this project - we are going to add a new feature. We will add capability to save the state of the game and to  reload the saved state at a later time.

We have modified the UI class and added a menu to it. Run the UI class and click on the 'File' menu and notice there are options for _save_, _load'_, and _close_. These menu items are wired to methods in the ```Board``` class which actually perform these actions.

We will not tell you up front how the wiring is done. That is something you have to read the code and understand. However FYI, all the wiring is complete, but as things stand right now, the implementation for saving and loading  the board is empty.

That is all we are going to tell you - you have to complete the implementation, so that the details of a Board are actually saved to a file when the _save_ menu item is clicked and are loaded & rendered back to the Board, when the _load_ menu item is clicked.

You will also notice that we have not added any unit tests for this functionality. There is a reason for not being able to add tests. Try to look at the code and figure out if something keeps us from being able to test this new feature. Also try and think about how we can make it more testable. We will discuss testability of the code in the next section.

<h2>Steps For This Project</h2>
 1. Run ```AllTests``` and ensure that all the tests pass
 1. Follow the instructions written above and implement logic that will actually save and load the game 
