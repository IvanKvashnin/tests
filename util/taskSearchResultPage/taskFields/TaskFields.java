package ru.sber.gsz.autotests.util.taskSearchResultPage.taskFields;

import lombok.Getter;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

@Getter
public class TaskFields {
    private final String id;
    private final String type;
    private final String idOrganization;
    private final String idGsz;
    private final String gsz;
    private final String iniciator;
    private final String status;
    private final String mainExecutor;
    private final String roleMainExecutor;
    private final String revision;
    private final String overdue;
    private final String createDate;
    private final String actualEndDate;

    public static TaskFields createTaskFields(WebElement task) {
        return new TaskFields(task);
    }

    public TaskFields(WebElement task) {
        var elements = task.findElements(xpath(".//td"));
        var link = elements.get(0).findElement(xpath(".//a")).getAttribute("href");
        id = link.substring(link.lastIndexOf("/"));
        type = elements.get(1).getText();
        idOrganization = elements.get(2).getText();
        idGsz = elements.get(3).getText();
        gsz = elements.get(4).getText();
        iniciator = elements.get(5).getText();
        status = elements.get(6).getText();
        mainExecutor = elements.get(7).getText();
        roleMainExecutor = elements.get(8).getText();
        revision = elements.get(9).getText();
        overdue = elements.get(10).getText();
        createDate = elements.get(11).getText();
        actualEndDate = elements.get(12).getText();
    }
}
