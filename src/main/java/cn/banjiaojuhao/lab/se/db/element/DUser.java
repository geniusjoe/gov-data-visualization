package cn.banjiaojuhao.lab.se.db.element;

import javax.persistence.*;

@Entity
@Table(name = "DUSER")
public class DUser {
    @Id
    @GeneratedValue
    @Column(name = "uid")
    private int uid;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "user_type")
    private int userType;
    @Column(name = "department")
    private String department;
    @Column(name = "position")
    private String position;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    public DUser(int component1, String component2, String component3, String component4, int component5, String component6, String component7, String component8, String component9) {
        uid = component1;
        nickName = component2;
        username = component3;
        password = component4;
        userType = component5;
        department = component6;
        position = component7;
        phone = component8;
        email = component9;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
