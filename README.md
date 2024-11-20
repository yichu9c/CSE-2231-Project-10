Project: Tag Cloud Generator with Standard Java Components
Note: No late submissions will be accepted for this project!

Objectives
Familiarity with designing and coding a realistic component-based application program using only standard Java components.
Familiarity with using Java Collections Framework components (e.g., Map, List, and Collections from the java.util package).
Familiarity with using standard Java file I/O components (e.g., FileReader and FileWriter from the java.io package).
Exposure to handling checked exceptions (e.g., IOException) with the try-catch construct.
Note that in your solution you can only use components from the standard Java libraries. You cannot use any of the components from the OSU CSE components packages.

The Problem
Convert your solution to the previous project to generate tag clouds using only components from the standard Java libraries. Here are some specific requirements for this project:

The program should satisfy the functional requirements of the previous project.
You must use the FileReader/BufferedReader and FileWriter/BufferedWriter/PrintWriter components for all the file input and output needed.
You must use the Java Collections Framework components for all the data storing and sorting needed.
You must catch and handle appropriately (e.g., by outputing meaningful error messages) all the IOExceptions that may be thrown by the file I/O code.
These are the stated requirements for your program. If you have questions or need additional details, ask in class.

Setup
One member of the team should set up an Eclipse project for this assignment by copying your previous project into a new project in Eclipse (after copying the project and before sharing it with the repository, you should first disconnect the new copy from the repository: right click on the new project in the Package Explorer view, select Team > Disconnect...; then select Also delete the SVN meta information from the file system and click Yes). The new project should then be shared with the rest of the team by using the Subversion version control system as learned in the Version Control With Subversion lab.

Method
When you and your teammate(s) are done with the project, decide who is going to submit your solution. That team member should select your Eclipse project (not just some of the files, but the whole project) containing the complete group submission, create a zip archive of it, and submit the zip archive to the Carmen dropbox for this project, as described in Submitting a Project. Note that you will only be allowed one submission per group, that is, your group can submit as many times as you want, but only the last submission will be on Carmen and will be graded. Under no circumstance will teammates be allowed to submit separate solutions. Make sure that you and your partner(s) agree on what should be submitted.

Your grade will depend not merely on whether the final program meets the initial requirements, of course, but also on the general software quality factors you've learned in CSE 2221/2231: understandability, precision, appropriate use of existing software components, maintainability, adherence to coding standards, efficiency, and so forth.

As mentioned in the previous project, note that the documentation for the Comparator interface includes a warning about using a comparator capable of imposing an ordering that is not consistent with equals in certain situations. A comparator c is consistent with equals if c.compare(e1, e2) == 0 has the same boolean value as e1.equals(e2) for all e1 and e2. Some of the Java Collections Framework components do not work correctly when used with a comparator that is not consistent with equals. If you want to use such components, you must ensure that your two implementations of the Comparator interface are indeed consistent with equals.

Additional Activities
Here are some possible additional activities related to this project. Any extra work is strictly optional, for your own benefit, and will not directly affect your grade.

Modify the program so that it takes its inputs (input file name, output file name, and N) from the command line, instead of prompting the user. The command-line arguments are accessible by your program through the String[] args array parameter to the main method. To run your program inside Eclipse and pass command-line arguments to it, select Run > Run Configurations.... Select your Java class from the left side column. If you do not find your class, click on the New launch Configuration button, found above the class list. In the right tabbed panel, select Arguments and, in the Program arguments box, enter the command-line arguments, separated by spaces. Click Run.
Add appropriate error checks on the command line arguments and output meaningful error messages when something is wrong. Examples of things to check would include that there are three command-line arguments, that the input file exists, that the third argument is a number, that it is an integer, that it is positive, etc.
Time your implementation of the last two projects on some reasonably large input and compare their performance. You can use the Stopwatch components to time the program.
