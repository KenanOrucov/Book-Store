package com.example.ingress.domain;

import com.example.ingress.observer.Observer;
import lombok.*;

import javax.persistence.*;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@Entity
@Table(name = "student")
public class StudentEntity implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name = "student_authorities",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<AuthorityEntity> authorityEntities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "students_books",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private List<BookEntity> bookEntities;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        StudentEntity user = (StudentEntity) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public void update(BookEntity bookEntity, AuthorEntity authorEntity /* , MailService mailService */) {
//        MailService mailService = new MailService();
//        mailService.sendMail(bookEntity, this);
        System.out.println("Book name is: " + bookEntity.getName() + "\nAuthor is: " + bookEntity.getAuthorEntity().getName());
        log.info("Book name is: " + bookEntity.getName() + "\nAuthor is: " + bookEntity.getAuthorEntity().getName());
    }
}
