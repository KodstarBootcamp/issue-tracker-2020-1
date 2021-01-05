package com.kodstar.backend.model.entity;

import com.kodstar.backend.model.annotations.EmailValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Size(max = 20)
    private String username;

    @EmailValidator
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
