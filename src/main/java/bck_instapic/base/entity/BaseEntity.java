package bck_instapic.base.entity;


import bck_instapic.core.LoggableEntity;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public abstract class BaseEntity extends LoggableEntity {
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;

}
