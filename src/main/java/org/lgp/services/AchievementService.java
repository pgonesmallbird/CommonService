package org.lgp.services;

import org.lgp.domain.Achievement;

import java.util.List;

public interface AchievementService {
    /**
     * 批量插入
     *
     * @param achievements
     */
    void insertBatch(List<Achievement> achievements);
}
