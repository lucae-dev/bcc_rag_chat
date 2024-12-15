package bck_rag_chat.mapper.primary;

import bck_rag_chat.document.model.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Mapper
@Repository
public interface DocumentMapper {
    void insertDocument(Document document);

    Document getDocumentById(@Param("id") UUID id);

    void updateDocument(Document document);

    void deleteDocument(@Param("id") UUID id);

    List<Document> findSimilarDocuments(@Param("ownerId") UUID ownerId,
                                        @Param("groupId") UUID groupId,
                                        @Param("embedding") float[] embedding,
                                        @Param("limit") int limit);
}
