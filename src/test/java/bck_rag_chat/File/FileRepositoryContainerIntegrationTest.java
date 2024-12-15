package bck_rag_chat.File;


import bck_rag_chat.BaseContainerIntegrationTest;
import bck_rag_chat.file.model.File;
import bck_rag_chat.file.model.FileBuilder;
import bck_rag_chat.file.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileRepositoryContainerIntegrationTest extends BaseContainerIntegrationTest {
    @Autowired private FileRepository fileRepository;

    @Test
    void insert_shouldInsert() {
        // given
        File fileToInsert = random(File.class);

        // when
        File insertedFile = fileRepository.insert(fileToInsert);

        // then
        File loadedFile = fileRepository.findById(insertedFile.getId());
        assertNotNull(loadedFile);
        assertThat(loadedFile).usingRecursiveComparison()
                .ignoringFields("insertDate")
                .isEqualTo(insertedFile);
        assertEquals(insertedFile.getInsertDate().toEpochMilli(), loadedFile.getInsertDate().toEpochMilli());
    }

    @Test
    void update_shouldUpdate() {
        // given
        File fileToInsert = random(File.class);
        File insertedFile = fileRepository.insert(fileToInsert);

        // when
        File fileToUpdate = random(FileBuilder.class)
                .withId(fileToInsert.getId())
                .withVersion(insertedFile.getVersion())
                .build();
        File updatedFile = fileRepository.update(fileToUpdate);

        // then
        assertNotNull(updatedFile);
        assertThat(updatedFile).usingRecursiveComparison()
                .ignoringFields("insertDate", "updateDate", "version")
                .isEqualTo(fileToUpdate);
        assertEquals(fileToUpdate.getInsertDate().toEpochMilli(), updatedFile.getInsertDate().toEpochMilli());
        assertEquals(fileToUpdate.getUpdateDate().toEpochMilli(), updatedFile.getUpdateDate().toEpochMilli());
        assertEquals(insertedFile.getVersion() + 1, updatedFile.getVersion());

        File loadedFile = fileRepository.findById(insertedFile.getId());
        assertNotNull(loadedFile);
        assertThat(loadedFile).usingRecursiveComparison()
                .ignoringFields("insertDate", "updateDate", "version")
                .isEqualTo(fileToUpdate);
        assertEquals(fileToUpdate.getInsertDate().toEpochMilli(), loadedFile.getInsertDate().toEpochMilli());
        assertEquals(fileToUpdate.getUpdateDate().toEpochMilli(), loadedFile.getUpdateDate().toEpochMilli());
        assertEquals(insertedFile.getVersion() + 1, loadedFile.getVersion());
    }
}
