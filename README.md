# Stable-Matching-Algorithm
This repository contains an implementation of the Stable Matching Algorithm, specifically designed for the EDAF05 course at Lund University. It uses the Gale-Shapley algorithm to find stable matchings between students and companies, given their respective preference lists.


# Files

Main.java: This is the main class that ties together the parsing and solution classes and outputs the results.
Company.java: This class defines the Company objects that participate in the matching.
Student.java: This class defines the Student objects that participate in the matching.
Person.java: This class defines the common attributes and behaviours of Students and Companies.
Parser.java: This class is responsible for reading the input data files and creating Student and Company objects.
Solution.java: This class contains the implementation of the Gale-Shapley algorithm, which finds the stable matching.
check_solution.sh: This is a Bash script used for testing the solution on multiple test cases.

# Usage

You can run the application by executing the Main.java file. The application reads data from standard input.

To test the application with multiple test cases, you can use the provided Bash script. It takes a command that runs your solution as an argument, and tests your solution on every .in file in the data directory and its subdirectories. If your solution passes a test, it prints "Correct!", otherwise, it prints the name of the input file that caused the test to fail and exits.

Make the script executable with the following command:

bash
Copy code
chmod +x check_solution.sh
You can then run the script with one of the following commands, depending on how you run your solution:

bash
Copy code

./check_solution.sh java Main

# Dependencies

Java (for running the application)
Python 3 (for the output validator used by the test script)

# Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
