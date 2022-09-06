package ua.goit.hw4.service.conventer;

import ua.goit.hw4.model.dao.CompanyDao;
import ua.goit.hw4.model.dto.CompanyDto;

public class CompanyConverter implements Converter<CompanyDto, CompanyDao> {
    @Override
    public CompanyDto from(CompanyDao companyDao) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(companyDao.getId());
        companyDto.setName(companyDao.getName());
        companyDto.setCountry(companyDao.getCountry());
        return companyDto;
    }

    @Override
    public CompanyDao to(CompanyDto companyDto) {
        CompanyDao companyDao = new CompanyDao();
        companyDao.setId(companyDto.getId());
        companyDao.setName(companyDto.getName());
        companyDao.setCountry(companyDto.getCountry());
        return companyDao;
    }
}
