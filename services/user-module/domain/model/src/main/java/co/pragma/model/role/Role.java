package co.pragma.model.role;

import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;

public record Role(
        RoleId id,
        RoleName name,
        RoleDescription description
) {
}
