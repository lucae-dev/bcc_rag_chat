package bck_instapic.file.model;

import java.time.Instant;
import java.util.UUID;

public final class FileBuilder {
    private UUID agentId;
    private UUID chatId;
    private String fileName;
    private String fileType;
    private FileStatus status;
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;

    public FileBuilder withAgentId(UUID agentId) {
        this.agentId = agentId;
        return this;
    }

    public FileBuilder withChatId(UUID chatId) {
        this.chatId = chatId;
        return this;
    }

    public FileBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileBuilder withFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public FileBuilder withStatus(FileStatus status) {
        this.status = status;
        return this;
    }

    public FileBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public FileBuilder withInsertDate(Instant insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public FileBuilder withUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public FileBuilder withVersion(long version) {
        this.version = version;
        return this;
    }

    public FileBuilder withOrderUnique(long orderUnique) {
        this.orderUnique = orderUnique;
        return this;
    }

    public File build() {
        File file = new File();
        file.setAgentId(agentId);
        file.setChatId(chatId);
        file.setFileName(fileName);
        file.setFileType(fileType);
        file.setStatus(status);
        file.setId(id);
        file.setInsertDate(insertDate);
        file.setUpdateDate(updateDate);
        file.setVersion(version);
        file.setOrderUnique(orderUnique);
        return file;
    }
}
