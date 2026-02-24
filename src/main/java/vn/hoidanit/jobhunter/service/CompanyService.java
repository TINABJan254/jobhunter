package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO handleGetAllCompanies(Specification<Company> spec, Pageable pageable) {
        Page<Company> resPage = this.companyRepository.findAll(spec, pageable);

        ResultPaginationDTO dto = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber() + 1); //lay tu fe gui len
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(resPage.getTotalPages());
        mt.setTotal(resPage.getTotalElements());

        dto.setResult(resPage.getContent());
        dto.setMeta(mt);

        return dto;
    }

    public Company handleUpdateCompany(Company c) {
        Optional<Company> optionalCompany = this.companyRepository.findById(c.getId());
        if (optionalCompany.isPresent()) {
            Company curCompany = optionalCompany.get();
            curCompany.setName(c.getName());
            curCompany.setDescription(c.getDescription());
            curCompany.setAddress(c.getAddress());
            curCompany.setLogo(c.getLogo());

            return this.companyRepository.save(curCompany);
        }

        return null;
    }

    public void handleDelCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
