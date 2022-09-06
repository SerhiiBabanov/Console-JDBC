package ua.goit.hw4.service.conventer;

import ua.goit.hw4.model.dao.SkillDao;
import ua.goit.hw4.model.dto.SkillDto;

public class SkillConverter implements Converter<SkillDto, SkillDao> {
    @Override
    public SkillDto from(SkillDao skillDao) {
        SkillDto skillDto = new SkillDto();
        skillDto.setId(skillDao.getId());
        skillDto.setLanguage(skillDao.getLanguage());
        skillDto.setLevel(skillDao.getLevel());
        return skillDto;
    }

    @Override
    public SkillDao to(SkillDto skillDto) {
        SkillDao skillDao = new SkillDao();
        skillDao.setId(skillDto.getId());
        skillDao.setLanguage(skillDto.getLanguage());
        skillDao.setLevel(skillDto.getLevel());
        return skillDao;
    }
}
