package co.pragma.model.user;

import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.user.valueObject.*;

public record User(
        UserId id,
        UserName name,
        UserLastname lastname,
        UserDateBirth dateBirth,
        UserAddress address,
        UserPhoneNumber phoneNumber,
        UserEmail email,
        UserSalary salary,
        RoleId roleId
) {
}
