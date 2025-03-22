package bck_instapic.trainingbuckets.repository;

import bck_instapic.base.mapper.BaseMapper;
import bck_instapic.base.repository.BaseRepository;
import bck_instapic.mapper.primary.TrainingBucketMapper;
import bck_instapic.trainingbuckets.model.TrainingBucket;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class TrainingBucketRepository extends BaseRepository<TrainingBucket> {

    private final TrainingBucketMapper trainingBucketMapper;
    private final SqlSessionFactory sqlSessionFactory;

    public TrainingBucketRepository(TrainingBucketMapper trainingBucketMapper,
                                    SqlSessionFactory sqlSessionFactory) {
        this.trainingBucketMapper = trainingBucketMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    protected BaseMapper<TrainingBucket> getMapper() {
        return trainingBucketMapper;
    }

    public TrainingBucket findById(UUID id) {
        return trainingBucketMapper.findById(id);
    }

    public List<TrainingBucket> findByUserId(UUID userId) {
        return trainingBucketMapper.findByUserId(userId);
    }

    public void save(TrainingBucket bucket) {
        if (bucket.getId() == null) {
            bucket.setId(UUID.randomUUID());
            // set default fields like insertDate, status, etc.
            trainingBucketMapper.insert(bucket);
        } else {
            // update fields like updateDate, version, etc.
            trainingBucketMapper.update(bucket);
        }
    }

    public void delete(UUID id) {
        trainingBucketMapper.delete(id);
    }
}