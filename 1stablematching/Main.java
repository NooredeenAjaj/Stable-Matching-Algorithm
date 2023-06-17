import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    // The main method where the program starts.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a parser for the input.
        Parser parser = new Parser(scanner);
        // Parse the input file.
        parser.parseFile();

        // Create a solution with the parsed students and companies.
        Solution solution = new Solution(parser.getStudents(), parser.getCompanies());
        // Solve the problem.
        Company[] matchedCompanies = solution.solve();
        // Print the results.
        for (int i = 1; i < matchedCompanies.length; i++) {
            Company company = matchedCompanies[i];
            System.out.println(company.getCurrentStudent());
        }
    }

    // A class representing a company.
    static class Company extends Person {
        // The student matched to this company.
        private Integer matchedStudent = 0;

        public Company(int index, Integer[] preferences) {
            super(index, preferences);
        }

        public void setMatchedStudent(int studentIndex) {
            matchedStudent = studentIndex;
        }

        public Integer getCurrentStudent() {
            return matchedStudent;
        }

        public boolean hasMatchedStudent() {
            return matchedStudent != 0;
        }
    }

    // A class for parsing the input.
    static class Parser {
        private Scanner scanner;
        private int size = 0;
        private Boolean[] isCompanyParsed;
        private Company[] companies;
        private LinkedList<Student> students = new LinkedList<Student>();

        public Parser(Scanner scanner) {
            this.scanner = scanner;
        }

        public void parseFile() {
            size = scanner.nextInt();
            isCompanyParsed = new Boolean[size + 1];
            Arrays.fill(isCompanyParsed, true);
            companies = new Company[size + 1];
            scanner.nextLine();

            for (int i = 0; i < size * 2; i++) {
                int personIndex = scanner.nextInt();
                Integer[] companyPreferences = new Integer[size + 1];
                Integer[] studentPreferences = new Integer[size + 1];

                for (int j = 1; j <= size; j++) {
                    if (isCompanyParsed[personIndex]) {
                        companyPreferences[scanner.nextInt()] = j;
                    } else {
                        studentPreferences[j] = scanner.nextInt();
                    }
                }
                if (isCompanyParsed[personIndex]) {
                    companies[personIndex] = new Company(personIndex, companyPreferences);
                    isCompanyParsed[personIndex] = false;
                } else {
                    students.offer(new Student(personIndex, studentPreferences));
                }
            }

            scanner.close();
        }

        public Company[] getCompanies() {
            return companies;
        }

        public LinkedList<Student> getStudents() {
            return students;
        }
    }

    static class Person {
        private final int index;
        private Integer[] preferences;

        public Person(int index, Integer[] preferences) {
            this.index = index;
            this.preferences = preferences;
        }

        public int getIndex() {
            return index;
        }

        public Integer[] getPreferences() {
            return preferences;
        }
    }

    // The Solution class encapsulates the process of solving the problem.
static class Solution {
    private LinkedList<Student> students;
    private Company[] companies;
    private Map<Integer, Student> pairs;

    // The constructor accepts a list of students and an array of companies.
    public Solution(LinkedList<Student> students, Company[] companies) {
        this.students = students;
        this.companies = companies;
    }

    // The solve method implements the Gale-Shapley algorithm for finding a stable match between students and companies.
    public Company[] solve() {
        LinkedList<Student> remainingStudents = new LinkedList<>(students);
        pairs = new HashMap<>();

        // The main loop continues as long as there are unmatched students.
        while (!remainingStudents.isEmpty()) {
            // The student makes a proposal to the next company on their list.
            Student student = remainingStudents.poll();
            Company preferredCompany = companies[student.getTopChoice() + 1];

            // If the company has not been paired yet, they accept the student.
            if (!preferredCompany.hasMatchedStudent()) {
                pairs.put(preferredCompany.getIndex(), student);
                preferredCompany.setMatchedStudent(student.getIndex());

            // If the company prefers this student over their current match, they accept the student and the old match returns to the pool.
            } else if (preferredCompany.getPreferences()[preferredCompany.getCurrentStudent()] > preferredCompany.getPreferences()[student.getIndex()]) {
                remainingStudents.offer(pairs.get(preferredCompany.getIndex()));
                pairs.put(preferredCompany.getIndex(), student);
                preferredCompany.setMatchedStudent(student.getIndex());

            // If the company prefers their current match, the student returns to the pool.
            } else {
                remainingStudents.offer(student);
            }
        }
        // Return the array of companies, now with their matched students.
        return companies;
    }
}


    static class Student extends Person {
        private int nextPreferredCompanyIndex = -1;

        public Student(int index, Integer[] preferences) {
            super(index, preferences);
        }

        public int getTopChoice() {
            nextPreferredCompanyIndex++;
            return nextPreferredCompanyIndex;
        }
    }
}
