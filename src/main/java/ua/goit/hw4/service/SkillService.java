package ua.goit.hw4.service;

import ua.goit.hw4.model.SkillLevel;
import ua.goit.hw4.model.dao.SkillDao;
import ua.goit.hw4.model.dto.SkillDto;
import ua.goit.hw4.repository.SkillRepository;
import ua.goit.hw4.service.conventer.SkillConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<SkillDto> getAll(){
        return skillRepository.findAll().stream()
                .map(skillConverter::from)
                .collect(Collectors.toList());
    }
    public List<SkillDto> getByLanguage(String language){
        return skillRepository.findByLanguage(language).stream()
                .map(skillConverter::from)
                .collect(Collectors.toList());
    }
    public List<SkillDto> getByLevel(SkillLevel skillLevel){
        return skillRepository.findByLevel(skillLevel).stream()
                .map(skillConverter::from)
                .collect(Collectors.toList());
    }
}
