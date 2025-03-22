package bck_instapic.trainingbuckets.model;

import bck_instapic.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TrainingBucket extends BaseEntity {
    private UUID userId;
    private String s3Path;
    private String status;
}
