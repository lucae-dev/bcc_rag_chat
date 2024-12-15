package bck_rag_chat.file.model;

import bck_rag_chat.base.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true, includeFieldNames = true, doNotUseGetters = true)
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity {
    private UUID agentId;
    private UUID chatId;
    private String fileName;
    private String fileType;
    private FileStatus status;
}
