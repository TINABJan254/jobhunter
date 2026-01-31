package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
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

    public List<Company> handleGetAllCompanies() {
        return this.companyRepository.findAll();
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
