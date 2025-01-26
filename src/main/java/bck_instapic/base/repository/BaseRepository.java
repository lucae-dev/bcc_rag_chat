package bck_instapic.base.repository;

import bck_instapic.base.entity.BaseEntity;
import bck_instapic.base.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Transactional
public abstract class BaseRepository<T extends BaseEntity> {

    public static final long ORDER_UNIQUE_RANDOM_PART_ORDER = 100000L;

    protected abstract BaseMapper<T> getMapper();

    @Transactional
    public T insert(T entity) {
        entity = validateAndAutocompleteInsert(entity);
        getMapper().insert(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        entity = validateAndAutocompleteUpdate(entity);
        getMapper().update(entity);
        return entity;
    }

    private T validateAndAutocompleteUpdate(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("tried to update null entity");
        }
        if (entity.getId() == null) {
            throw new IllegalArgumentException("tried to update entity with null id");
        }
        return incrementVersion(entity);
    }

    private T incrementVersion(T entity) {
        entity.setVersion(entity.getVersion() + 1);
        return entity;
    }

    @Transactional
    public void delete(UUID id) {
        getMapper().delete(id);
    }

    @Transactional
    public T findById(UUID id) {
        return getMapper().findById(id);
    }

    private T validateAndAutocompleteInsert(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("tried to insert null entity");
        }
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getInsertDate() == null) {
            entity.setInsertDate(Instant.now());
        }
        Instant insertDate = entity.getInsertDate();
        entity.setUpdateDate(Instant.now());
        entity.setOrderUnique(calculateOrderUnique(insertDate));
        return entity;
    }

    private long calculateOrderUnique(Instant insertDate) {
        return insertDate.toEpochMilli() * ORDER_UNIQUE_RANDOM_PART_ORDER + (long) (Math.random() % ORDER_UNIQUE_RANDOM_PART_ORDER);
    }

    private long calculateOrderUnique() {
        return Instant.now().toEpochMilli() * ORDER_UNIQUE_RANDOM_PART_ORDER + (long) (Math.random() % ORDER_UNIQUE_RANDOM_PART_ORDER);
    }
}
