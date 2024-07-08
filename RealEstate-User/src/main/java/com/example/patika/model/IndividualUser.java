package com.example.patika.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "individual_users")
public class IndividualUser extends User{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "advert_balance")
    private int advertBalance;

    @Column(name = "end_date_of_package")
    private LocalDate endDateOfPackage = LocalDate.now().minusDays(1); // paket son kullanma tarihi oluşturulma tarihinden 1 gün öncesi olacak şekilde oluşur

    public IndividualUser(int id, String email, String password, String photo, String phoneNumber, String firstName, String lastName) {
        super(id, email, password, photo, phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
