package pojo;

public class Student {
	
	private int id;
	private String name;
	private String course;
	private byte age;

    public Student() {
        super();
    }

    public Student(String name, String course, byte age) {
        super();
        this.name = name;
        this.course = course;
        this.age = age;
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		this.age = age;
	}
	
	
}

