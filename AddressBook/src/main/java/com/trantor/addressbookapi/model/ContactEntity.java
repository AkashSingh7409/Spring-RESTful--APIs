package com.trantor.addressbookapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONTACT_TABLE")
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Contact_Id")
    private Long contactId;

    @NotNull(message = "firstName should not be empty")
    private String firstName;

    private String lastName;

    private String emailAddress;

    private String createdBy;

    @CreationTimestamp

    private Date createdDate;


    private String updatedBy;

    @UpdateTimestamp

    private Date updatedDate;

    private String isActive;

    @OneToMany(targetEntity = MobileEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "Contact_Id",referencedColumnName = "Contact_Id")
    private List<MobileEntity> mobileEntities;

}
