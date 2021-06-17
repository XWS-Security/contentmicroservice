package org.nistagram.contentmicroservice.data.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends User{
    private transient final String administrationRole = "ADMINISTRATOR_ROLE";

    @Override
    public String getAdministrationRole() {
        return administrationRole;
    }
}
