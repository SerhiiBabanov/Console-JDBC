package ua.goit.hw4.service;

import ua.goit.hw4.model.dao.SkillDao;
import ua.goit.hw4.model.dto.SkillDto;
import ua.goit.hw4.repository.SkillRepository;
import ua.goit.hw4.service.conventer.SkillConverter;

import java.util.Optional;

public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillConverter skillConverter;

    public SkillService(SkillRepository skillRepository, SkillConverter skillConverter) {
        this.skillRepository = skillRepository;
        this.skillConverter = skillConverter;
    }

    public SkillDto create(SkillDto skillDto) {
        SkillDao skillDao = skillRepository.save(skillConverter.to(skillDto));
        return skillConverter.from(skillDao);
    }

    public Optional<SkillDto> getById(Long id) {
        Optional<SkillDao> skillDao = skillRepository.findById(id);
        return skillDao.map(skillConverter::from);
    }

    public SkillDto update(SkillDto skillDto) {
        SkillDao skillDao = skillRepository.update(skillConverter.to(skillDto));
        return skillConverter.from(skillDao);
    }

    public void delete(SkillDto skillDto) {
        skillRepository.delete(skillConverter.to(skillDto));
    }
}
