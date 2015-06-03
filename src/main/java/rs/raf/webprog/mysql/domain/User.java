package rs.raf.webprog.mysql.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(name = "user")
public class User implements java.io.Serializable  {
    private static final long serialVersionUID = -4863040932014032728L;

    @Id
    @Column(name = "username")
    private String username;

    @Column
    private String password;


    public User() { }

    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password+"]";
    }
}
