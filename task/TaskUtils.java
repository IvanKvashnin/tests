package ru.sber.gsz.autotests.task;

import ru.sber.gsz.autotests.client.criteria.CriterionClient;
import ru.sber.gsz.autotests.client.task.TaskClient;
import ru.sber.gsz.autotests.dto.criterion.CriterionDto;
import ru.sber.gsz.autotests.dto.criterion.CriterionType;
import ru.sber.gsz.autotests.dto.executor.ExecutorType;
import ru.sber.gsz.autotests.dto.executor.UnderwriterDecision;
import ru.sber.gsz.autotests.dto.gsz.OrganizationCreateDto;
import ru.sber.gsz.autotests.dto.tasks.CreateTaskEfsResponse;

import java.util.List;
import java.util.UUID;

import static java.util.List.of;
import static ru.sber.gsz.autotests.dto.criterion.CriterionExecutorDecision.AGREE;
import static ru.sber.gsz.autotests.dto.criterion.CriterionType.ECONOMIC;
import static ru.sber.gsz.autotests.dto.criterion.CriterionType.JURISTIC;
import static ru.sber.gsz.autotests.dto.executor.ExecutorType.VKO_EMPLOYEE;
import static ru.sber.gsz.autotests.dto.tasks.CrmOrgSegment.LARGE;
import static ru.sber.gsz.autotests.utils.common.RandomUtils.generateGszName;
import static ru.sber.gsz.autotests.utils.organizations.OrgUtils.createOrg;
import static ru.sber.gsz.autotests.utils.task.TaskUtils.buildCreateUserTask;

public class TaskUtils {

    public static String createTaskFromUser(TaskClient taskClient, ExecutorType executorType) {
        return taskClient.createTaskFromUser(
                buildCreateUserTask(
                        generateGszName(),
                        createOrg(null).withSegment(LARGE),
                        executorType,
                        of(createOrg(null).withSegment(LARGE)),
                        null
                )
        ).toString();
    }

    public static String goToVkoSubmittedFirsLap(TaskClient taskClient, CriterionClient criterionClient) {
        var instanceId = getTask(taskClient).getId();
        /* ВКО */
        return getSubmittedEmployee(taskClient, criterionClient, instanceId, JURISTIC);
    }

    public static String goToPmSubmittedFirstLap(TaskClient taskClient, CriterionClient criterionClient) {
        /* VKO -> PM */
        return getSubmittedVkoPm(taskClient, criterionClient, getTask(taskClient).getId());
    }

    public static String goToArSubmitted(TaskClient taskClient, CriterionClient criterionClient) {
        /* VKO -> PM -> AR */
        return getArSubmitted(taskClient, criterionClient);
    }

    public static String goToVkoSubmittedSecondLap(TaskClient taskClient, CriterionClient criterionClient) {
        /* VKO -> PM -> AR -> VKO */
        return getVkoSubmittedSecondLap(taskClient, criterionClient);
    }

    public static String goToPmSubmittedSecondLap(TaskClient taskClient, CriterionClient criterionClient) {
        /* VKO -> PM -> AR -> VKO -> PM */
        return getPmSubmittedSecondLap(taskClient, criterionClient);
    }

    public static String goToFinalArSubmitted(TaskClient taskClient, CriterionClient criterionClient) {
        /* VKO -> PM -> AR -> VKO -> PM -> Final AR */
        return getFinalAr(taskClient, criterionClient);
    }

    private static String getArSubmitted(TaskClient taskClient, CriterionClient criterionClient) {
        var instanceId = getTask(taskClient).getId();
        /* VKO -> PM*/
        getSubmittedVkoPm(taskClient, criterionClient, instanceId);
        /* AR */
        getSubmittedAr(taskClient, instanceId);
        return instanceId.toString();
    }

    private static String getVkoSubmittedSecondLap(TaskClient taskClient, CriterionClient criterionClient) {
        var instanceId = getTask(taskClient).getId();
        /* VKO -> PM*/
        getSubmittedVkoPm(taskClient, criterionClient, instanceId);
        /* AR */
        getSubmittedAr(taskClient, instanceId);
        /* VKO */
        getSubmittedEmployee(taskClient, criterionClient, instanceId, JURISTIC);
        return instanceId.toString();
    }

    private static String getPmSubmittedSecondLap(TaskClient taskClient, CriterionClient criterionClient) {
        var instanceId = getTask(taskClient).getId();
        /* VKO -> PM */
        getSubmittedVkoPm(taskClient, criterionClient, instanceId);
        /* AR */
        getSubmittedAr(taskClient, instanceId);
        /* VKO -> PM */
        getSubmittedVkoPm(taskClient, criterionClient, instanceId);
        return instanceId.toString();
    }

    private static String getFinalAr(TaskClient taskClient, CriterionClient criterionClient) {
        var instanceId = getTask(taskClient).getId();
        getSubmittedVkoPm(taskClient, criterionClient, instanceId);
        /* AR */
        getSubmittedAr(taskClient, instanceId);
        /* VKO -> PM */
        getSubmittedVkoPm(taskClient, criterionClient, instanceId);
        /* Final AR */
        getSubmittedAr(taskClient, instanceId);
        return instanceId.toString();
    }

    private static String getSubmittedVkoPm(TaskClient taskClient, CriterionClient criterionClient, UUID instanceId) {
        /* VKO */
        getSubmittedEmployee(taskClient, criterionClient, instanceId, JURISTIC);
        /* PM */
        getSubmittedEmployee(taskClient, criterionClient, instanceId, ECONOMIC);
        return instanceId.toString();

    }

    private static String getSubmittedEmployee(TaskClient taskClient, CriterionClient criterionClient, UUID instanceId, CriterionType criterionType) {
        taskClient.getTask(instanceId)
                .addCriterionWithComment(getLinkedOrgs().get(0).getEpkId(), false, getCriteria(criterionClient), criterionType)
                .setDecisionForAllCriteria(AGREE)
                .addDefaultJudgment()
                .confirm()
                .save(taskClient)
                .submit()
                .save(taskClient);
        return instanceId.toString();
    }

    private static void getSubmittedAr(TaskClient taskClient, UUID instanceId) {
        taskClient.initCuratorAndCheck(instanceId.toString());
        taskClient.getTask(instanceId)
                .setUwDecisionForAllCriteria(UnderwriterDecision.FOR_REVISION)
                .addDefaultJudgment()
                .confirm()
                .save(taskClient)
                .submit()
                .save(taskClient);
    }

    private static List<CriterionDto> getCriteria(CriterionClient criterionClient) {
        return criterionClient.getAllCriteria().getItems();
    }

    private static OrganizationCreateDto getMainOrg() {
        return createOrg(null).withSegment(LARGE);
    }

    private static List<OrganizationCreateDto> getLinkedOrgs() {
        return List.of(createOrg(null).withSegment(LARGE), createOrg(null).withSegment(LARGE));
    }

    private static CreateTaskEfsResponse getTask(TaskClient taskClient) {
        return taskClient.createTaskFromUserAllInfo(buildCreateUserTask(
                generateGszName(), getMainOrg(), VKO_EMPLOYEE, getLinkedOrgs(), null));
    }
}
