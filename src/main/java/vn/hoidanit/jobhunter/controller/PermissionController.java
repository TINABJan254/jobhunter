package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Permission;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.PermissionService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/permissions/{id}")
    @ApiMessage("Get permission by id")
    public ResponseEntity<Permission> getPermission(@PathVariable Long id) throws IdInvalidException {
        Permission permission = this.permissionService.fetchById(id);

        if (permission == null) {
            throw new IdInvalidException("Permission not exist");
        }

        return ResponseEntity.ok(permission);
    }

    @GetMapping("/permissions")
    @ApiMessage("Fetch permissions")
    public ResponseEntity<ResultPaginationDTO> fetchAll(@Filter Specification<Permission> spec, Pageable pageable) {
        return  ResponseEntity.ok(this.permissionService.fetchAll(spec, pageable));
    }

    @PostMapping("/permissions")
    @ApiMessage("Creat permission")
    public ResponseEntity<Permission> save(@Valid @RequestBody Permission permission) throws IdInvalidException {
        if (this.permissionService.isPermissionExist(permission)) {
            throw new IdInvalidException("Permission existed");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.save(permission));
    }

    @PutMapping("/permissions")
    @ApiMessage("Update permission")
    public ResponseEntity<Permission> update(@Valid @RequestBody Permission permission) throws IdInvalidException {

        if (this.permissionService.fetchById(permission.getId()) == null ) {
            throw new IdInvalidException("Permission not existed with id: " + permission.getId());
        }

        if (this.permissionService.isPermissionExist(permission)) {
            // check name (update name -> ok)
            if (this.permissionService.isSameName(permission)) {
                throw new IdInvalidException("Permission đã tồn tại.");
            }
        }

        return ResponseEntity.ok(this.permissionService.update(permission));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("Delete permission")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws IdInvalidException {
        if (this.permissionService.fetchById(id) == null) {
            throw new IdInvalidException("Permission not existed with id: " + id);
        }

        this.permissionService.delete(id);
        return ResponseEntity.ok(null);
    }


}
