package bck_instapic.document.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Document {
    private UUID id;
    private String document;
    private UUID ownerId;
    private UUID groupId;
    private UUID parentDocumentId;
    private float[] embedding;
}
