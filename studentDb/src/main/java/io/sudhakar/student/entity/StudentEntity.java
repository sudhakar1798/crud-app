package io.sudhakar.student.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Slf4j

@Table(name = "student", schema = "public")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "place")
    private String place;

    @Column(name = "course")
    private String course;

    @Column(name = "age")
    private int age;

    @OneToMany(orphanRemoval = true, mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Set<AddressEntity> addressEntities = new HashSet<>();

    public void setAddressEntitiesList(Set<AddressEntity> addressEntityList) {
        this.addressEntities.clear();
        if (!addressEntityList.isEmpty()) {
            addressEntityList.forEach(addressEntity -> {
                addressEntity.setStudentEntity(this);
                this.addressEntities.add(addressEntity);
            });
        }

    }

}