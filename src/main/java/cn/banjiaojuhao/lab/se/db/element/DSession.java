package cn.banjiaojuhao.lab.se.db.element;

import javax.persistence.*;

@Entity
@Table(name = "SESSION")
public class DSession {
    @Id
    @GeneratedValue
    @Column(name = "sid")
    private String sid;
    @Column(name = "uid")
    private int uid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
