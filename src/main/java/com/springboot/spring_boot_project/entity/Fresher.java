package com.springboot.spring_boot_project.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Fresher",
        schema = "fresher_management"
        )
public class Fresher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "programmingLanguage")
    @ElementCollection
    Set<String> programmingLanguage = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "center_id", referencedColumnName = "id")
    @JsonIgnore
    Center center;
    @OneToOne(mappedBy = "fresher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    Assignment assignment;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserInfo user;
}
