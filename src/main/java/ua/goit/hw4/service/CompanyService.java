package ua.goit.hw4.service;

import ua.goit.hw4.model.dao.CompanyDao;
import ua.goit.hw4.model.dto.CompanyDto;
import ua.goit.hw4.repository.CompanyRepository;
import ua.goit.hw4.service.conventer.CompanyConverter;

import java.util.Optional;

public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    public CompanyDto create(CompanyDto companyDto) {
        CompanyDao companyDao = companyRepository.save(companyConverter.to(companyDto));
        return companyConverter.from(companyDao);
    }

    public Optional<CompanyDto> getById(Long id) {
        Optional<CompanyDao> companyDao = companyRepository.findById(id);
        return companyDao.map(companyConverter::from);
    }

    public CompanyDto update(CompanyDto companyDto) {
        CompanyDao companyDao = companyRepository.update(companyConverter.to(companyDto));
        return companyConverter.from(companyDao);
    }

    public void delete(CompanyDto companyDto) {
        companyRepository.delete(companyConverter.to(companyDto));
    }

}
