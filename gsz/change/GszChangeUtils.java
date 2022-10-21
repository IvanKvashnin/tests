package ru.sber.gsz.autotests.gsz.change;

import com.sbt.bpspe.core.dto.entity.EmployeeDto;
import com.sbt.bpspe.core.dto.entity.EmployeeIdDto;
import ru.sber.gsz.autotests.dto.gsz.EmployeeCreateDto;
import ru.sber.gsz.autotests.dto.gsz.GszCreateDto;
import ru.sber.gsz.autotests.dto.gsz.GszTreeWithOrgDto;
import ru.sber.gsz.autotests.dto.gsz.common.GszStatus;
import ru.sber.gsz.autotests.dto.gsz.common.GszType;

import static com.sbt.bpspe.core.dto.common.EmployeeIdType.PERSONAL_NUMBER;
import static java.util.UUID.randomUUID;
import static ru.sber.gsz.autotests.dto.gsz.EmployeeCreateDto.employeeCreateDto;
import static ru.sber.gsz.autotests.dto.gsz.common.GszType.GSZ_KK;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.randomNumber;
import static ru.sber.gsz.autotests.utils.employee.EmployeeUtils.buildEmployee;

public class GszChangeUtils {
    public static GszTreeWithOrgDto createGsz(String gszName, GszStatus gszStatus, GszType type) {
        var gszId = randomUUID();
        return GszTreeWithOrgDto.builder()
                .root(GszCreateDto.builder()
                        .id(gszId)
                        .gszStatus(gszStatus)
                        .gszType(type)
                        .name(gszName)
                        .extId("" + randomNumber(8))
                        .build())
                .build();
    }

    public static GszTreeWithOrgDto createGsz(String gszName, GszStatus gszStatus) {
        return createGsz(gszName, gszStatus, GSZ_KK);
    }

    public static EmployeeDto getEmployeeDto() {
        return buildEmployee(EmployeeIdDto.builder()
                .employeeId("" + randomNumber(8))
                .type(PERSONAL_NUMBER)
                .build());
    }
}
