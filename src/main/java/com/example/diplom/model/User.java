package com.example.diplom.model;

import com.example.diplom.model.Role;
import com.example.diplom.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Логин не может быть пустым")
    private String username;
    @NotEmpty(message = "ФИО не может быть пустым")
    private String fio;
    @NotEmpty(message = "Телефон не может быть пустым")
    @Pattern(regexp = "[+]+[0-9]{12}", message = "Введите корректный номер телефона")
    private String phoneUser;
    @NotEmpty(message = "Email не может быть пустым")
    @Email(message = "Введите корректный Email")
    private String emailUser;
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Order> orders;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "role_id")
    private Role role;
}
