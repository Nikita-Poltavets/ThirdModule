package com.homework.nix.hibernate.entity;


import javax.persistence.*;

@Entity
@DiscriminatorValue("income")
public class Income extends Operation {
}
