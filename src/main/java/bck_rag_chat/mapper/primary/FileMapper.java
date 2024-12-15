package bck_rag_chat.mapper.primary;

import bck_rag_chat.base.mapper.BaseMapper;
import bck_rag_chat.file.model.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<File> {
    List<File> findByStatus(String status);
}
