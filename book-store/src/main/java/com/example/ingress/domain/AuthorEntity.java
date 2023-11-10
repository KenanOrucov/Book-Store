package com.example.ingress.domain;

import com.example.ingress.observer.Observer;
import com.example.ingress.observer.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "author")
public class AuthorEntity implements Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_student",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<StudentEntity> studentEntities;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AuthorEntity user = (AuthorEntity) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public void register(StudentEntity observer) {
        studentEntities.add(observer);
    }

    @Override
    public void unregister(StudentEntity observer) {
        int obIndex = studentEntities.indexOf(observer);
        System.out.println("Observer " + (obIndex +1) + " deleted");
        studentEntities.remove(obIndex);
    }

    @Override
    public void notifyObserver(BookEntity bookEntity, AuthorEntity authorEntity /* , MailService mailService */) {
        for (Observer observer: studentEntities){
            observer.update(bookEntity, authorEntity /* , MailService mailService */);
        }
    }
}
