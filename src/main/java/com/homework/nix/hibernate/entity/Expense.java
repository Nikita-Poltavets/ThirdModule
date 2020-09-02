package com.homework.nix.hibernate.entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("expense")
public class Expense extends Operation{
}
