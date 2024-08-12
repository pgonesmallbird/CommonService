package org.lgp.services.impl;

import org.lgp.domain.Achievement;
import org.lgp.mapper.AchievementMapper;
import org.lgp.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    private AchievementMapper achievementMapper;

    @Override
    public void insertBatch(List<Achievement> achievements) {
        achievementMapper.insertBatch(achievements);
    }
}