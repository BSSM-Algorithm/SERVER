package bssm.bssm_algorithm.domain.user.domain.entity;

import bssm.bssm_algorithm.domain.user.domain.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_entity")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String studentNumber;

    @Column
    private String className;

    @Builder(builderMethodName = "signupBuilder")
    public UserEntity(String username, String password, String studentNumber, String name, String className) {
        this.username = username;
        this.password = password;
        this.className = className;
        this.name = name;
        this.studentNumber = studentNumber;
        this.role = Role.USER;
    }
}
