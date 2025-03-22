package bck_instapic.mapper.primary;

import bck_instapic.base.mapper.BaseMapper;
import bck_instapic.trainingbuckets.model.TrainingBucket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface TrainingBucketMapper extends BaseMapper<TrainingBucket> {

    TrainingBucket findById(@Param("id") UUID id);

    List<TrainingBucket> findByUserId(@Param("userId") UUID userId);

    List<TrainingBucket> findByParams(@Param("id") UUID id,
                                      @Param("userId") UUID userId,
                                      @Param("s3Path") String s3Path,
                                      @Param("status") String status);

}