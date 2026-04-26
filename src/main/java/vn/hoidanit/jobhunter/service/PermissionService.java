package vn.hoidanit.jobhunter.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import vn.hoidanit.jobhunter.domain.Permission;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.PermissionRepository;

import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository){
        this.permissionRepository = permissionRepository;
    }

    public boolean isPermissionExist(Permission permission) {
        return this.permissionRepository.existsByModuleAndApiPathAndMethod(permission.getModule(),
                permission.getApiPath(), permission.getMethod());
    }

    public Permission save(Permission permission) {
        return this.permissionRepository.save(permission);
    }

    public Permission fetchById(Long id) {
        return this.permissionRepository.findById(id).orElse(null);
    }

    public ResultPaginationDTO fetchAll(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> permissionPage = this.permissionRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setTotal(permissionPage.getTotalElements());
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        rs.setMeta(meta);
        rs.setResult(permissionPage.getContent());
        return rs;
    }

    public Permission update(Permission permission) {
        Permission permissionDb = fetchById(permission.getId());

        if (permissionDb != null) {
            permissionDb.setApiPath(permission.getApiPath());
            permissionDb.setModule(permission.getModule());
            permissionDb.setMethod(permission.getMethod());

            return this.permissionRepository.save(permissionDb);
        }

        return null;
    }

    public void delete(long id) {
        // delete permission_role
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        Permission currentPermission = permissionOptional.get();
        currentPermission.getRoles().forEach(role -> role.getPermissions().remove(currentPermission));

        // delete permission
        this.permissionRepository.delete(currentPermission);
    }


}
