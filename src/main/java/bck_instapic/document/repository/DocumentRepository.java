package bck_instapic.document.repository;

import bck_instapic.mapper.primary.DocumentMapper;
import bck_instapic.document.model.Document;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class DocumentRepository {
    private final DocumentMapper documentMapper;

    public DocumentRepository(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Transactional
    public void addDocument(Document document) {
        documentMapper.insertDocument(document);
    }

    public Document getDocument(UUID id) {
        return documentMapper.getDocumentById(id);
    }

    @Transactional
    public void updateDocument(Document document) {
        documentMapper.updateDocument(document);
    }

    @Transactional
    public void deleteDocument(UUID id) {
        documentMapper.deleteDocument(id);
    }

    public List<Document> findSimilarDocuments(UUID ownerId, UUID groupId, float[] embedding, int limit) {
        return documentMapper.findSimilarDocuments(ownerId, groupId, embedding, limit);
    }

}
