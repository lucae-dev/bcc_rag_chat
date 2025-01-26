package bck_instapic.file.model;

import bck_instapic.base.entity.BaseEntity;
import lombok.*;

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
