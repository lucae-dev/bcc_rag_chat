package bck_instapic.mapper.primary;

import bck_instapic.base.mapper.BaseMapper;
import bck_instapic.file.model.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<File> {
    List<File> findByStatus(String status);
}
