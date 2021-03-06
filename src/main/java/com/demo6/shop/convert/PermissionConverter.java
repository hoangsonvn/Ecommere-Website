package com.demo6.shop.convert;

import com.demo6.shop.entity.Permission;
import com.demo6.shop.dto.PermissionDTO;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter {
    public PermissionDTO toDto(Permission permission){
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permission.getId());
        permissionDTO.setPermissionKey(permission.getPermissionKey());
        permissionDTO.setPermissionName(permission.getPermissionName());
        permissionDTO.setDescription(permission.getDescription());
        return permissionDTO;
    }
    public Permission toEntity(PermissionDTO permissionDTO){
        Permission permission = new Permission();
        permission.setId(permissionDTO.getId());
        permission.setPermissionKey(permissionDTO.getPermissionKey());
        permission.setPermissionName(permissionDTO.getPermissionName());
        permission.setDescription(permissionDTO.getDescription());
        return permission;
    }
}
