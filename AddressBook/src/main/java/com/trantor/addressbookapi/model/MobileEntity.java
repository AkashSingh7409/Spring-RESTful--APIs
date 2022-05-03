package com.trantor.addressbookapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "MOBILE_TABLE")
public class MobileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer mobileId;
    private String mobileNumber;
    private String countryCode;
    private String type;
    private String createdBy;
    @CreationTimestamp
    private Date createdDate;
    private String updatedBy;
    @UpdateTimestamp
    private Date updatedDate;


}
