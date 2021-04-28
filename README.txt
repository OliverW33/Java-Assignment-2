Oliver Williams - B9020208

-----------------This is my Read me file for JAVA Assignment 2 - Server Assignment ---------------

- CONTENTS OF MY ARCHIVE - 

1. ChannelClass.java - My ChannelClass.java file is where i created my channel class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

2. MessageClass.java - My MessageClass.java file is where i created my message class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

3. SubscribeRequestClass.java - My SubscribeRequestClass.java file is where i created my subscription class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

4. UnsubscribeRequestClass.java - My UnsubscribeRequestClass.java file is where i created my unsubscription class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

5. GetRequestClass.java - My GetRequestClass.java file is where i created my get request class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

6. OpenRequestClass.java - My OpenRequestClass.java file is where i created my open request class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

7. PublishRequestClass.java - My PublishRequestClass.java file is where i created my publish request class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

8. SuccessResponseClass.java - My SuccessResponseClass.java file is where i created my success response class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

9. ErrorResponseClass.java - My ErrorResponseClass.java file is where i created my error response class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

10. MessageListResponseClass.java - My MessageListResponseClass.java file is where i created my message list response class, I instantiated the class constructors, getters and setters, and private and public variables
used for declaration purposes to introduce methods of inheritance and returning data in the main file.

11. ServerClass.java - This class is the main thread to my assignment, it establishes the connection between the client and the server and contains my mutlithreaded runnable method which allows multiple instances of clients on the server,
the server's job is to accept and understand the clients request, in whihc once received i have dealt with appropriatley.

12. ClientClass.java - This is my own client which was used previously to test and debug the server

13. WriteToFileClass.java - This is my file in which i took the requests and persisted and saved them to a file

13. json-simple-1.1.1.jar - Executable jar file

- HOW TO COMPILE MY APPLICATION -

HOW I COMPILED AND RAN MY APPLICATION ON COMMAND LINE - 

1. To compile my application on command line, i first began by locating the parent folder to where my Java files were stored, they were stored in my package directory
in my src folder, so i then knew the correct path to the project folder. I began to use the following command line structure to type in my entry and be present in the working folder:

C:\Users\<'My Username'>\cd folder\cd folder\cd folder\cd folder\cd Java Assignment 2 FINAL\cd src

Result: 

C:\Users\<'My Username'>\OneDrive\Desktop\SE projects\JAVA\Java Assignment 2 FINAL\src>

2. After i had a functional directory to my working folder, i then needed to start compiling my java files so they can be run on the command line, my next stage involves using javac
in the current directory and selecting the java files i want to compile, this was efficient to do in 1 line:

C:\Users\<'My Username'>\folder\folder\folder\folder\Java Assignment 2 FINALL\src>javac File1.java File2.java File3.java File4.java / *.java

Result: 

C:\Users\<'My Username'>\OneDrive\Desktop\SE projects\JAVA\Java Assignment 2 FINAL\src>javac *.java (selects all files, if not, type out each file name)

C:\Users\<'My Username'>\OneDrive\Desktop\SE projects\JAVA\Java Assignment 2 FINAL\src\ass1\nsd>

The compilation of the files was successful and in my working folder, i acquired the .class files which in result allows the command line to interpret the files and run them

3. My next stage was to run the Server after compilation, to run the server successfully on command line, i must run it with my executable java file so a local package can be instantiated in the working folder, so i would enter the following

C:\Users\<'My Username'>\folder\folder\folder\folder\Java Assignment 2 FINAL\src>java -cp .;json-simple-1.1.1.jar File1.java

Result:

C:\Users\<'My Username'>\OneDrive\Desktop\SE projects\JAVA\Java Assignment 2 FINAL\src>java -cp .;json-simple-1.1.1.jar ServerClass.java

Server is Running!

4. After doing so resulted in my server being successfully compiled and ran on the command line! 

5. The next step is to run the test client on the server, so they can send requests and we can recieve them on port 12345, therfore allowing my server to deal with them appropriatley

6. Navigate to the working folder and enter ther current directory

C:\Users\<'My Username'>\cd folder\cd folder\cd TestClient

C:\Users\<'My Username'>\Desktop\TestClient>

7. Then i must run the client on the relevant host and port connection

C:\Users\<'My Username'>\Desktop\TestClient>java -classpath .;json-simple-1.1.1.jar DemoClient localhost 12345 1 R

8. Once this is ran, the client will begin to send requests and the server shall, if successful, return a success response back to the client

9. If the throughput of the server wants to be changed, this is acheived by entering any 1 of the 3 arguements

C:\Users\<'My Username'>\Desktop\TestClient>java -classpath .;json-simple-1.1.1.jar DemoClient localhost 12345 1 L (low throughput)

C:\Users\<'My Username'>\Desktop\TestClient>java -classpath .;json-simple-1.1.1.jar DemoClient localhost 12345 1 R (medium throughput)

C:\Users\<'My Username'>\Desktop\TestClient>java -classpath .;json-simple-1.1.1.jar DemoClient localhost 12345 1 R (high throughput)


- HOW TO COMPILE AND RUN MY PROGRAM ON COMMAND LINE -

1. After downloading the zip file to my application, the first stage is to locate where you stored the folder, after acknowledging the location,
you can begin to type the path directory towards the working folder using the same method as i demonstrated above.

2. At this stage you have successfully set up the path directory to the working folder, the next stage is to check if the class files are present
for my appliation, in which suggesting the program should still be compiled, this step can be skipped if the files are present and should be able to ran from command line, 
but if not, we must compile the files inside the working folder.

3. Once you have made a directory with the correct working folder, your next step is to set up the classpath to the working folder in your enviroment variables system. find the control panel and edit system variables, in which
you can chanage the current classpath to the working folder directory.
 
 3. If the working method is correct and you have made sure to compile with the right classpath, the application will have successfully compiled and is ready to be ran! the next stage involves simply running the package file
 in the working folder directory so the entire application can be ran together, this is achieved by implementing this structure below:
 