import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pojo.Student;
import utility.StudentUtil;

public class App {

    public static void main(String[] args) throws SQLException, IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.print("1. Create Student Table\t");
            System.out.println("2. Add Student");
            System.out.print("3. Add Batch Student\t");
            System.out.println("4. Update Student");
            System.out.print("5. Delete Student\t");
            System.out.println("6. Search Student by ID");
            System.out.print("7. Show all Record\t");
            System.out.println("8. Upload Image");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    String tableCreated = StudentUtil.createStudentTable();
                    System.out.println(tableCreated);
                    break;

                case 2:
                    System.out.println("Enter student name: ");
                    String insName = scanner.next();
                    System.out.println("Enter student course: ");
                    String insCourse = scanner.next();
                    System.out.println("Enter student age: ");
                    byte insAge = scanner.nextByte();
                    String studentAdded = StudentUtil.insertStudent(insName, insCourse, insAge);
                    System.out.println(studentAdded);
                    break;

                case 3:
                    System.out.print("Enter number of students to be added: ");
                    int num = scanner.nextInt();

                    List<Student> st = new ArrayList<>();
                    
                    for (int i = 0; i < num; i++) {
                        System.out.println("Enter student name: ");
                        String Name = scanner.next();
                        System.out.println("Enter student course: ");
                        String Course = scanner.next();
                        System.out.println("Enter student age: ");
                        byte Age = scanner.nextByte();
                        st.add(new Student(Name, Course, Age));

                      //  String studentAdded = StudentUtil.insertStudent(insName, insCourse, insAge);
                      //  System.out.println(studentAdded);
                    }
                    int n = StudentUtil.saveMulRec(st);
                    System.out.println(n + " records added.");
                    if(num != n){
                        System.out.println("Error: Some records are not added.");
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student course: ");
                    String course = scanner.nextLine();
                    System.out.print("Enter student age: ");
                    byte age = scanner.nextByte();
                    String studentUpdated = StudentUtil.updateStudent(id, name, course, age);
                    System.out.println(studentUpdated);
                    break;

                case 5:
                    System.out.print("Enter student ID: ");
                    int deleteId = scanner.nextInt();
                    String studentDeleted = StudentUtil.deleteStudent(deleteId);
                    System.out.println(studentDeleted);
                    break;                

                case 6:
                    System.out.print("Enter student ID: ");
                    int searchId = scanner.nextInt();
                    ResultSet rs1 = StudentUtil.searchStudentById(searchId);
                    if (rs1.next()) {
                        System.out.println("Student ID: " + rs1.getInt("stu_id"));
                        System.out.println("Student Name: " + rs1.getString("Stud_Name"));
                        System.out.println("Student Course: " + rs1.getString("Stud_course"));
                        System.out.println("Student Age: " + rs1.getByte("Stu_age"));
                    } else {
                        System.out.println("Student Id not found.");
                    }
                    break;

                case 7:
                    ResultSet rs = StudentUtil.showAllRec();
                    System.out.println("id \t name \t\t course \t\t age \t image");
                    if(!rs.next()){
                        System.out.println("No records found.");
                    }
                    while (rs.next()) {
                        System.out.print(rs.getInt("stu_id") +" \t ");
                        System.out.print(rs.getString("Stud_Name") +" \t\t ");
                        System.out.print(rs.getString("Stud_course") +" \t\t ");
                        System.out.print(rs.getByte("Stu_age") +" \t ");
                        System.out.println(rs.getBlob("pro_img"));
                    }
                    break;

                case 8:
                    System.out.print("Enter file loaction: ");
                    String loc = scanner.next();
                    System.out.print("Enter student ID: ");
                    int imgId = scanner.nextInt();
                    String sc = StudentUtil.uploadPhoto(new File(loc), imgId);
                    System.out.print(sc);
                    break;

                case 9:
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }
        }
    }
}