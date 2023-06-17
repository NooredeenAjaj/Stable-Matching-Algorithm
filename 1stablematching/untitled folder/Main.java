import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Parser ps = new Parser(scanner);
        ps.parseFile();

        Solution solution = new Solution(ps.getStudents(), ps.getcompanys());
        Company[] ss = solution.solve();
        for (int i = 1; i < ss.length; i++) {
            Company c = ss[i];
            System.out.println(c.currentStudent());

        }

    }

    static class Company extends Person

    {
        private Integer student = 0;

        public Company(int index, Integer[] companyPrefeses) {
            super(index, companyPrefeses);
        }

        public void setStudent(int a) {
            student = a;
        }

        public Integer currentStudent() {
            return student;
        }

        public boolean havePair() {
            return 0 != student;

        }

    }

    static class Parser {
        // private File file;
        private Scanner scaner;
        private int size = 0;
        private Boolean[] check;
        // private Person [] student;
        private Company[] company;
        private LinkedList<Student> studentList = new LinkedList<Student>();

        public Parser(Scanner sc) {
            // this.file = new File(filePath);
            this.scaner = sc;

        }

        public void parseFile() {
            Scanner scanner = scaner;
            size = scanner.nextInt();
            check = new Boolean[size + 1];
            Arrays.fill(check, true);
            // student = new Person[size] ;
            company = new Company[size + 1];
            scanner.nextLine();

            // String[] line = new String[size *2];
            for (int i = 0; i < (size * 2); i++) {

                int companyOrStudent = scanner.nextInt();

                Integer[] companyPrefeses = new Integer[size + 1];
                Integer[] StudentPrefeses = new Integer[size + 1];

                for (int j = 1; j <= size; j++) {

                    if (check[companyOrStudent]) {
                        companyPrefeses[scanner.nextInt()] = j;
                        // companyPrefeses[j] = scanner.nextInt();

                    } else {
                        StudentPrefeses[j] = scanner.nextInt();
                    }

                }
                if (check[companyOrStudent]) {

                    company[companyOrStudent] = new Company(companyOrStudent, companyPrefeses);

                    check[companyOrStudent] = false;
                } else {
                    studentList.offer(new Student(companyOrStudent, StudentPrefeses));
                }

            }

            scanner.close();

        }

        public Company[] getcompanys() {
            return company;
        }

        public LinkedList<Student> getStudents() {
            return studentList;
        }
    }

    static class Person {
        private final int index;
        private Integer[] preferences;

        public Person(int index, Integer[] preferences2) {
            this.index = index;

            this.preferences = preferences2;
        }

        public int getIndex() {
            return index;
        }

        public Integer[] getPreferences() {
            return preferences;
        }
    }

    static class Solution {
        private LinkedList<Student> students;
        private Company[] companies;
        private Map<Integer, Student> pairs;

        public Solution(LinkedList<Student> students, Company[] companies) {
            this.students = students;
            this.companies = companies;
        }

        public Company[] solve() {
            LinkedList<Student> p = students;
            pairs = new HashMap<Integer, Student>();

            while (p.size() != 0) {
                Student s = p.poll();

                Company c = companies[s.getPreferences()[s.preferMost() + 1]];

                if (!c.havePair()) {
                    pairs.put(c.getIndex(), s);
                    c.setStudent(s.getIndex());

                } else if (c.getPreferences()[c.currentStudent()] > c.getPreferences()[s.getIndex()]) {
                    p.offer(pairs.get(c.getIndex()));
                    pairs.put(c.getIndex(), s);
                    c.setStudent(s.getIndex());

                }

                else {
                    p.offer(s);

                }

            }
            return companies;

        }
    }

    static class Student extends Person {
        private int nextCompany = -1;

        public Student(int index, Integer[] preferences) {
            super(index, preferences);
        }

        public int preferMost() {
            nextCompany++;

            return nextCompany;

        }

    }

}
