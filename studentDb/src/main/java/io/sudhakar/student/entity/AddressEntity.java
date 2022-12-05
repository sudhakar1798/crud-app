package io.sudhakar.student.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address", schema = "public")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "area")
    private String area;

    @Column(name = "pincode")
    private int pincode;

    @Column(name = "district")
    private String district;

    @Column(name = "student_id", insertable = false, updatable = false)
    private int studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity studentEntity;

}
