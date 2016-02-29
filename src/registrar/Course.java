package registrar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bjackson on 2/21/2016.
 */
public class Course {

    private Set<Student> roster;
    private List<Student> waitlist;
    private String number;
    private String name;
    private int limit;

    public Course(){
        roster = new HashSet<>();
        waitlist = new ArrayList<>();
        limit = Integer.MAX_VALUE;
    }

    public void setCatalogNumber(String number){
        this.number = number;
    }

    public void setTitle(String title){
        this.name = title;
    }

    public int getEnrollmentLimit(){
        return limit;
    }

    public boolean setEnrollmentLimit(int limit){
        if (roster.size() <= limit && limit > 0){
            if(limit > this.getEnrollmentLimit()) {
                while(getWaitList().size() != 0){
                    for(int j=limit-this.getEnrollmentLimit(); j>= 0; j++){
                        Student s = waitlist.remove(0);
                        roster.add(s);
                        s.enrollIn(this);
                    }
                }
            }
            this.limit = limit;
            return true;
        }
        return false;
    }

    public Set<Student> getStudents(){
        return roster;
    }

    public List<Student> getWaitList(){
        return waitlist;
    }

    public boolean enrollStudent(Student student){
        if (roster.contains(student)){
            return true;
        }
        if (roster.size() >= limit){
            if (waitlist.contains(student)){
                return false;
            }
            waitlist.add(student);
            return false;
        }
        roster.add(student);
        student.enrollIn(this);
        waitlist.remove(student);
        return true;
    }

    public void dropStudent(Student s){
        if (roster.contains(s)) {
            roster.remove(s);
            s.drop(this);
            if (waitlist.size() > 0) {
                Student toEnroll = waitlist.remove(0);
                roster.add(toEnroll);
                toEnroll.enrollIn(this);
            }
        }
        else if (waitlist.contains(s)){
            waitlist.remove(s);
        }
    }

    public void removeEnrollmentLimit(Course course){
        course.setEnrollmentLimit(Integer.MAX_VALUE);
    }

}