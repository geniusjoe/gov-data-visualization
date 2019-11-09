package cn.banjiaojuhao.lab.se.db.element;

import javax.persistence.*;

@Entity
@Table(name = "SESSION")
public class DSession {
    @Id
    @GeneratedValue
    @Column(name = "sid")
    String sid;
    @Column(name = "id")
    int uid;
}
