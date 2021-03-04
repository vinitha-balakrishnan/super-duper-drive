package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {
    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="note-close-btn")
    private WebElement close;

    @FindBy(id="save-changes")
    private WebElement saveChanges;

    @FindBy(xpath="/html/body/div[1]/div/span")
    private WebElement successMsg;

    @FindBy(id="back-home")
    private WebElement backHomeButton;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void createUpdateNote(String title, String description, String successMsg) throws InterruptedException {
        this.noteTitle.sendKeys(title);
        this.noteDescription.sendKeys(description);
        this.saveChanges.click();
        Thread.sleep(3000);
        Assertions.assertEquals(successMsg,this.successMsg.getText());
        this.backHomeButton.sendKeys(Keys.RETURN);;
        /*if (this.backHomeButton.isDisplayed()){
            this.backHomeButton.click();
        }*/
        Thread.sleep(1000);
    }
}
