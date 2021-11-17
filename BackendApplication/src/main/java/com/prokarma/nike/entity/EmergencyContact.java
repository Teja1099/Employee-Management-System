package com.prokarma.nike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(
                name = "name",
                column = @Column(name = "emergencyContactName")
        ),
        @AttributeOverride(
                name = "contact",
                column = @Column(name = "emergencyContact")
        )}
)
public class EmergencyContact {
    private String name;
    private Long contact;
}
