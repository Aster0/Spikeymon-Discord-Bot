
package data;

import net.dv8tion.jda.api.entities.User;

import java.util.LinkedList;
import java.util.List;

public class GDDUser {

    private User user;

    public GDDUser(User user)
    {
        this.user = user;
    }

    public GDDUser()
    {

    }

    public User getUser() {
        return user;
    }

    public List<String> getData() {
        return data;
    }

    private List<String> data = new LinkedList<>(); // 0 name, 1 class, 2 school email
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
