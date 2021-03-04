package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {
    @FindBy(id="credential-url")
    private WebElement credentialurl;

    @FindBy(id="credential-username")
    private WebElement credentialname;

    @FindBy(id="credential-password")
    private WebElement credentialpassword;

    @FindBy(id="credential-close-btn")
    private WebElement close;

    @FindBy(id="credential-save-btn")
    private WebElement saveChanges;

    @FindBy(xpath="/html/body/div[1]/div/span")
    private WebElement successMsg;

    @FindBy(id="back-home")
    private WebElement backHomeButton;

    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void createUpdateCredential(String url, String userName, String password, String successMsg) throws InterruptedException {
        Thread.sleep(1000);
        this.credentialurl.sendKeys(url);
        this.credentialname.sendKeys(userName);
        this.credentialpassword.sendKeys(password);
        this.saveChanges.click();
        Thread.sleep(3000);
        Assertions.assertEquals(successMsg,this.successMsg.getText());
        this.backHomeButton.sendKeys(Keys.RETURN);
        Thread.sleep(1000);
    }

    public void verifyCredential(String url, String userName, String password) throws InterruptedException {
        Thread.sleep(6000);
        Assertions.assertEquals(url,this.credentialurl.getAttribute("value"));
        Assertions.assertEquals(userName,this.credentialname.getAttribute("value"));
        Assertions.assertEquals(password,this.credentialpassword.getAttribute("value"));
    }


}
