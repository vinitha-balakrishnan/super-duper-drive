package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {
    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    @FindBy(xpath = "//*[@id=\"nav-notes-tab\"]")
    private WebElement fileTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "show-note-btn")
    private WebElement addanoteButton;

    @FindBy(id = "userTable")
    private WebElement noteTable;

    @FindBy(xpath = "//*[@id=\"userTable\"]/thead/tr/th")
    private List<WebElement> noteTableCol;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]")
    private List<WebElement> noteTableRow;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "show-credential-btn")
    private WebElement addcredButton;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/thead/tr/th")
    private List<WebElement> credenTableCol;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]")
    private List<WebElement> credenTableRow;

    @FindBy(id = "credential-display-url")
    private WebElement credenTableURL;

    @FindBy(id = "credential-display-username")
    private WebElement credenTableUserName;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[3]")
    private WebElement credenTablePassword;

    @FindBy(xpath="/html/body/div[1]/div/span")
    private WebElement successMsg;

    @FindBy(id="back-home")
    private WebElement backHomeButton;





    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void createNote() {
        this.noteTab.isDisplayed();
        this.noteTab.click();
        this.addanoteButton.click();
    }

    public void createCredential() {
        this.credentialsTab.isDisplayed();
        this.credentialsTab.click();
        this.addcredButton.click();
    }

    public int editDeleteNote(String title, String description) throws InterruptedException {
        int rowNum = 0;
        this.noteTab.click();
        Thread.sleep(6000);
        int row = this.noteTableRow.size();
        int col = this.noteTableCol.size();
        int i = 0;
        System.out.println("No of Rows in Note Table: " + row);
        System.out.println("No of Cols in Note Table: " + col);
        for (i = 1; i <= row; i++) {
            String titleText = this.noteTable.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[ " + i + " ]/th")).getText();
            String descText = this.noteTable.findElement(By.xpath("//*[@id=\"userTable\"]/tbody//tr[" + i + "]/td[2]")).getText();
            System.out.println("Title: " + titleText);
            System.out.println("Description: " + descText);
            if (titleText.equals(title) && descText.equals(description)) {
                System.out.println("Expected Note found");
                rowNum =  i;
                break;
            }
        }
        return rowNum;
    }

    public void verifyNoteExists(String title, String description) throws InterruptedException {
        this.noteTab.sendKeys(Keys.RETURN);
        this.noteTab.sendKeys(Keys.F5);
        Thread.sleep(6000);
        int row = this.noteTableRow.size();
        int col = this.noteTableCol.size();
        System.out.println("No of Rows in Note Table: " + row);
        System.out.println("No of Cols in Note Table: " + col);
        int i = 0;
        for (i = 1; i <= row; i++) {
            String titleText = this.noteTable.findElement(By.xpath("//*[@id=\"userTable\"]/tbody/tr[ " + i + " ]/th")).getText();
            System.out.println("Title: " + titleText);
            String descText = this.noteTable.findElement(By.xpath("//*[@id=\"userTable\"]/tbody//tr[" + i + "]/td[2]")).getText();
            System.out.println("Description: " + descText);
            if (titleText.equals(title) && descText.equals(description)) {
                System.out.println("Note exists");
                Assertions.assertEquals(title,titleText);
                Assertions.assertEquals(description,descText);
            }
        }
    }
    public void verifyDeletedNoteExists(String title, String description) throws InterruptedException {
        this.noteTab.click();
        Thread.sleep(6000);
        Boolean isDoesNotExist = false;
        int row = this.noteTableRow.size();
        int col = this.noteTableCol.size();
        if (row >0) {
            int i = 0;
            for (i = 1; i <= row; i++) {
                String titleText = this.noteTable.findElement(By.xpath("//*[@id=\"userTable\"]/tbody//tr[ " + i + " ]/th")).getText();
                String descText = this.noteTable.findElement(By.xpath("//*[@id=\"userTable\"]/tbody//tr[" + i + "]/td[2]")).getText();
                if (titleText.equals(title) && descText.equals(description)) {
                    isDoesNotExist = false;
                } else {
                    isDoesNotExist = true;
                }
            }
        }
        else{
            isDoesNotExist = true;
        }
        Assertions.assertEquals(true,isDoesNotExist);
    }

    public int editDeleteCredentials(String URL, String username) throws InterruptedException {
        int rowNum = 0;
        this.credentialsTab.click();
        Thread.sleep(6000);
        int row = this.credenTableRow.size();
        int col = this.credenTableCol.size();
        int i = 0;
        System.out.println("No of Rows in Credential Table: " + row);
        System.out.println("No of Cols in Credential Table: " + col);

        String URLText = credenTableURL.getText();
        String usernameText = credenTableUserName.getText();
        if (URLText.equals(URL) && usernameText.equals(username)) {
            System.out.println("Expected credential found");
            rowNum = 1;
        }
        return rowNum;
    }

    public void verifyCredentialExists(String URL, String username, String password) throws InterruptedException {
        this.credentialsTab.sendKeys(Keys.RETURN);
        int i = 0;
        Thread.sleep(6000);
        String URLText = credenTableURL.getText();
        String usernameText = credenTableUserName.getText();
        System.out.println(credenTableUserName.getAttribute("value"));
        if (URLText.equals(URL) && usernameText.equals(username)) {
            System.out.println("Expected credential found");
        }
        Assertions.assertEquals(URL, URLText);
        Assertions.assertEquals(username, usernameText);
        Assertions.assertNotEquals(password,this.credenTablePassword.getText());
    }
    public void verifyDeletedCredentialExists(String URL, String username) throws InterruptedException {
        this.credentialsTab.click();
        Thread.sleep(6000);
        Boolean isDoesNotExist = false;
        int row = this.credenTableRow.size();
        int col = this.credenTableCol.size();
        if(row > 0) {
            int i = 0;
            for (i = 1; i <= row; i++) {
                String URLText = this.credentialTable.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[" + i + "]/th")).getAttribute("value");
                String usernameText = this.credentialTable.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[1]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("value");
                if (URLText.equals(URL) && usernameText.equals(username)) {
                    isDoesNotExist = false;
                } else {
                    isDoesNotExist = true;
                }
            }
        }
        else{
            isDoesNotExist = true;
        }
        Assertions.assertEquals(true,isDoesNotExist);
    }

    public void goBackToHomePage() {
        this.backHomeButton.sendKeys(Keys.RETURN);
    }


}
