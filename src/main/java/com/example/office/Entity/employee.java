package com.example.office.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "employee")
@SQLDelete(sql = "UPDATE user SET isdeleted = true WHERE id = ?")
@Where(clause = "isdeleted = false")
public class employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int emplid;

    private String ename ;

    @Column(nullable = false,unique = true)
    private String email;

    private String password;
    private  String isdeleted;


}
