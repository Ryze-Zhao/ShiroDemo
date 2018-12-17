package com.zhaolearn.shirosso.domain;


import javax.persistence.*;

@Entity
@Table( name ="permission" )
public class Permission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "perm_name")
    private String permName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", permName='" + permName + '\'' +
                '}';
    }
}
