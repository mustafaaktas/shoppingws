package com.shoppingws.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shoppingws.model.base.AbstractBaseObject;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Role extends AbstractBaseObject<Role> implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;
    private long 		id;

    @NotNull(message = "Rol Adı boş olamaz!")
    @Size(min = 3 , max = 15 , message = "Rol Adı en az 3 en fazla 15 karakter olabilir!")
    private String 		roleName; // varchar(30)

    @NotNull(message = "Rol Kodu boş olamaz!")
    @Size(min = 3, max = 10 , message = "Rol Kodu en az 3 en fazla 10 karakter olabilir!")
    private String 		roleCode; // varchar(20)

    private String 		roleDesc; // varchar(100)

    public Role() {
        super();
    }

    public Role(long id, String rolKod,String rolAd) {
        super();
        this.id = id;
        this.roleName = rolAd;
        this.roleCode = rolKod;
    }
    public Role(String rolKod,String rolAd) {
        this(0l,rolKod,rolAd);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String getAuthority() {
        return roleCode;
    }

}

