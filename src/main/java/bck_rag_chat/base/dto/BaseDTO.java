package bck_rag_chat.base.dto;

import java.time.Instant;
import java.util.UUID;

public abstract class BaseDTO {
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;

}
