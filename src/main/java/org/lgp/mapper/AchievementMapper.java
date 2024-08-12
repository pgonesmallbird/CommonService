package org.lgp.mapper;

import org.lgp.domain.Achievement;

import java.util.List;

public interface AchievementMapper {
    void insertBatch(List<Achievement> list);
}
