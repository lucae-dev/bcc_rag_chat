package bck_instapic.base.entity;


import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public abstract class BaseEntity {
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;

}
