package cz.horejsi.employeemanager.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String jobTitle;
    private String imageUrl;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!name.equals(employee.name)) return false;
        if (!surname.equals(employee.surname)) return false;
        if (!phone.equals(employee.phone)) return false;
        if (!email.equals(employee.email)) return false;
        if (!jobTitle.equals(employee.jobTitle)) return false;
        return imageUrl.equals(employee.imageUrl);
    }
}
