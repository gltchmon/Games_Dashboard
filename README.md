# GAMES DASHBOARD
An application that interacts with the IGN DB API to extract specific information about several different games and track different games users has played. Users can make lists and manage different types of lists e.g., their favourite games lists.

This project is a follow up of countries dashboard project that was done for my university OOP module. I no longer have access to the API key for the countries dashboard nor the right amount of data necessary for the application to function correctly so I decided to do another project with a similar concept.

The main goal was to incorporate key OOP concepts into the project to demonstrate what we had learned. In this project I included the same concepts with slight improvements to make my code more concise and easier to work with. I also wanted to add additional features that I did not add to the last program.
### Lessons learned
From this project I have learned better ways to parse incoming JSON data and how to request data from API's. Because we could not use external libraries when I made the countries dashboard I had to manually parse the JSON string using the index of certain brackets and letters. In this project I learned how to use the JSON library to parse JSON data easily which limited errors. The previous project came with a wrapper class that sent requests and contained built in functions to retrieve data however for this project I had to make my own wrapper class to request specific data and handle it.
### Additional functionality 
While the previous dashboard included more graphics to demonstrate work with java swing graphics2D, this application includes more lists and being able to do more with the country(or in this case game) itself. You can create and delete new lists, add and move items to different lists instead of just having one simple list. This was something I wanted to have in the previous project but could not do so. 

This project was made on Intellij idea and uses Java and Java swing for the user interface.

A demo of the program can be found here: https://youtu.be/92HwdMl8vic

### Future functionality
This program is still being developed and improved. While I find the java swing ui quite limiting I want to improve on the overall look of the dashboard. I want to also include more features such as having the application remember all your lists instead of restarting every time the application is launched and being able to add notes to each game on your list such as reviews. 