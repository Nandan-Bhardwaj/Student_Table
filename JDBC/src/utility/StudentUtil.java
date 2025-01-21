package utility;

import conn.JdbcConnec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import pojo.Student;


public class StudentUtil {

    private static Connection connectToDb() throws SQLException {
        Connection conn = JdbcConnec.getInstance().getConn();
        if (conn == null) {
            throw new SQLException("Unable to establish a database connection.");
        }
        return conn;
    }

    public static String createStudentTable() throws SQLException {
        PreparedStatement stmt = connectToDb().prepareStatement("CREATE TABLE if not exists Student(stu_id int primary key auto_increment, Stud_Name varchar(20) not null, Stud_course varchar(15) DEFAULT 'N/A' , Stu_age tinyint DEFAULT 0, pro_Img mediumblob );");
        return stmt.executeUpdate() >= 0 ? "Table Created Successfully" : "Error: Unable to Create Table";
    }

    public static String insertStudent(String name, String course, byte age) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        PreparedStatement stmt = connectToDb()
                .prepareStatement("insert into Student (Stud_name, Stud_course, Stu_age) values (?, ?, ?);");
        stmt.setString(1, name);
        stmt.setString(2, course);
        stmt.setInt(3, (int) age);

        return stmt.executeUpdate() > 0 ? "Student Added Successfully" : "Error: Student Not Added";

    }

    public static String updateStudent(int id, String name, String course, byte age) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Id and Name cannot be null.");
        }
        PreparedStatement stmt = connectToDb()
                .prepareStatement("UPDATE Student SET Stud_Name = ?, Stud_course = ?, Stu_age= ? WHERE stu_id = ?;");
        stmt.setString(1, name);
        stmt.setString(2, course);
        stmt.setInt(3, age);
        stmt.setInt(4, id);
        return stmt.executeUpdate() > 0 ? "Record Updated Successfully" : "Error: Id not found";
    }

    public static int saveMulRec(List<Student> ls) throws SQLException {

        if (ls == null || ls.isEmpty()) {
            throw new IllegalArgumentException("Student list is null or empty.");
        }
	        connectToDb().setAutoCommit(false);
	        PreparedStatement preStat = connectToDb().prepareStatement("Insert into Student (stud_name,Stu_age,Stud_course) values(?,?,?);");

	        int[] recs;
	        for (Student s:ls){
	            preStat.setString(1, s.getName());
	            preStat.setInt(2, s.getAge());
	            preStat.setString(3, s.getCourse());
                preStat.addBatch();
	        }
	        recs = preStat.executeBatch();
	        connectToDb().commit();
	        return recs.length;
	    }


    public static String deleteStudent(int id) throws SQLException {
        PreparedStatement stmt = connectToDb().prepareStatement("DELETE FROM Student WHERE stu_id=?");
        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0 ? "Record  at id: " + id + "deleted Successfully" : "Error: Id not found";
    }

    public static ResultSet searchStudentById(int id) throws SQLException {
        PreparedStatement stmt = connectToDb().prepareStatement("SELECT * FROM Student WHERE stu_id=?");
        stmt.setInt(1, id);
        return stmt.executeQuery();
    }

    public static ResultSet showAllRec() throws SQLException {
        PreparedStatement stmt = connectToDb().prepareStatement("SELECT * FROM Student");
        return stmt.executeQuery();
    }

    public static String uploadPhoto(File file, int id) throws SQLException, IOException {
        if (file == null || !file.exists()) {
            throw new IOException("File not found.");
        }
        PreparedStatement stmt = connectToDb().prepareStatement("update student set pro_img = ? where stu_id = ?;");
        stmt.setInt(2, id);
        stmt.setBinaryStream(1, new FileInputStream(file), new FileInputStream(file).available());
        return stmt.executeUpdate() >= 1 ? "Image Uploaded Successfully" : "Error: Unable to upload file";

    }

}