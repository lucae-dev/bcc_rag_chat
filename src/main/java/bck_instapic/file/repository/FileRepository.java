package bck_instapic.file.repository;

import bck_instapic.base.mapper.BaseMapper;
import bck_instapic.base.repository.BaseRepository;
import bck_instapic.file.model.File;
import bck_instapic.file.model.FileStatus;
import bck_instapic.mapper.primary.FileMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class FileRepository extends BaseRepository<File> {
    private final FileMapper fileMapper;

    public FileRepository(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    protected BaseMapper<File> getMapper() {
        return fileMapper;
    }

    public List<File> findByStatus(FileStatus status) {
        return fileMapper.findByStatus(status.name());
    }
}
