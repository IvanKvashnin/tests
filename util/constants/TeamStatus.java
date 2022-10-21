package ru.sber.gsz.autotests.util.constants;

import ru.sber.gsz.autotests.dto.executor.ExecutorStatus;
import ru.sber.gsz.autotests.dto.executor.ExecutorType;
import ru.sber.lm.Pair;

import java.util.List;

import static ru.sber.gsz.autotests.dto.executor.ExecutorStatus.*;
import static ru.sber.gsz.autotests.dto.executor.ExecutorType.*;
import static ru.sber.lm.Pair.pair;

public class TeamStatus {

    public static final List<Pair<ExecutorType, ExecutorStatus>> VKO_EXECUTOR_TEAM_STATUS = List.of(
            pair(VKO_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> REASSIGN_VKO_TEAM_STATUS = List.of(
            pair(VKO_EMPLOYEE, CANCELLED),
            pair(VKO_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> PM_EXECUTOR_TEAM_STATUS = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> PM_SUBMITTED_TEAM_STATUS = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> VKO_EXECUTOR_TEAM_STATUS_SECOND_LAP = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(VKO_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> PM_EXECUTOR_TEAM_STATUS_SECOND_LAP = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> PM_SUBMITTED_TEAM_STATUS_SECOND_LAP = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> FINAL_AR_SUBMITTED_TEAM_STATUS = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> REASSIGN_PM_TEAM_STATUS = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, CANCELLED),
            pair(PM_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> REASSIGN_VKO_TEAM_STATUS_SECOND_LAP = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(VKO_EMPLOYEE, CANCELLED),
            pair(VKO_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );

    public static final List<Pair<ExecutorType, ExecutorStatus>> REASSIGN_PM_TEAM_STATUS_SECOND_LAP = List.of(
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, SUBMITTED),
            pair(CURATOR_EMPLOYEE, SUBMITTED),
            pair(VKO_EMPLOYEE, SUBMITTED),
            pair(PM_EMPLOYEE, CANCELLED),
            pair(PM_EMPLOYEE, PLANNED),
            pair(PM_EMPLOYEE, MARKED),
            pair(CURATOR_EMPLOYEE, MARKED)
    );
}
