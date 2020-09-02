package com.homework.nix.jdbc.entity;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Operation {

    private Long id;

    private Long accountId;

    private Long amount;

    private String description;

    private Instant timestamp;
}
