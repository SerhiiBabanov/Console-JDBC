package ua.goit.hw4.service;

import ua.goit.hw4.model.SkillLevel;
import ua.goit.hw4.model.dao.DeveloperDao;
import ua.goit.hw4.model.dao.DeveloperSkillRelationDao;
import ua.goit.hw4.model.dao.SkillDao;
import ua.goit.hw4.model.dto.DeveloperDto;
import ua.goit.hw4.repository.DeveloperRepository;
import ua.goit.hw4.repository.DeveloperSkillRelationRepository;
import ua.goit.hw4.repository.SkillRepository;
import ua.goit.hw4.service.conventer.DeveloperConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperSkillRelationRepository developerSkillRelationRepository;
    private final SkillRepository skillRepository;
    private final DeveloperConverter developerConverter;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperSkillRelationRepository developerSkillRelationRepository, SkillRepository skillRepository, DeveloperConverter developerConverter) {
        this.developerRepository = developerRepository;
        this.developerSkillRelationRepository = developerSkillRelationRepository;
        this.skillRepository = skillRepository;
        this.developerConverter = developerConverter;
    }

    public DeveloperDto create(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerRepository.save(developerConverter.to(developerDto));
        return developerConverter.from(developerDao);
    }

    public Optional<DeveloperDto> getById(Long id) {
        Optional<DeveloperDao> developerDao = developerRepository.findById(id);
        return developerDao.map(developerConverter::from);
    }
    public List<DeveloperDto> getAll(){
        return developerRepository.findAll().stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }

    public DeveloperDto update(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerRepository.update(developerConverter.to(developerDto));
        return developerConverter.from(developerDao);
    }

    public void delete(DeveloperDto developerDto) {
        developerRepository.delete(developerConverter.to(developerDto));
    }
    public List<DeveloperDto> getByProjectId(Long id){
        return developerRepository.getByProjectId(id).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }
    public List<DeveloperDto> getBySkillId(Long id){
        return developerRepository.getBySkillId(id).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }
    public long addSkill(Long developerId, Long skillId){
        DeveloperSkillRelationDao dao = new DeveloperSkillRelationDao();
        dao.setDeveloperId(developerId);
        dao.setSkillId(skillId);
        return developerSkillRelationRepository.save(dao).getId();
    }
    public void deleteSkill(Long developerId, Long skillId){
        DeveloperSkillRelationDao dao = new DeveloperSkillRelationDao();
        dao.setDeveloperId(developerId);
        dao.setSkillId(skillId);
        developerSkillRelationRepository.delete(dao);
    }
    public List<DeveloperDto> getBySkillLevel(SkillLevel skillLevel){
        List<Long> skillIds = skillRepository.findByLevel(skillLevel).stream()
                .map(SkillDao::getId)
                .collect(Collectors.toList());
        return developerRepository.getBySkillIdList(skillIds).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }
    public List<DeveloperDto> getBySkillLanguage(String language){
        List<Long> skillIds = skillRepository.findByLanguage(language).stream()
                .map(SkillDao::getId)
                .collect(Collectors.toList());
        return developerRepository.getBySkillIdList(skillIds).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }

}
