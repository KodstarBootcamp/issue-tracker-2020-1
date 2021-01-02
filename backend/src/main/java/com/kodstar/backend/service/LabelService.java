package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.model.entity.LabelEntity;

import java.util.Collection;
import java.util.Set;

public interface LabelService  extends Converter<Label, LabelEntity> {

    Collection<Label> getAllLabels();
    Label saveLabelEntity(Label label);
    Label findById(Long id);
    Label updateLabelEntity(Long id, Label label);
    void deleteLabel(Long id);
    void saveAll(Set<LabelEntity> labels);

    //to use in issue service
    Collection<LabelEntity> findAll();
}
