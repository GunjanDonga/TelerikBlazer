package pageobjects.card.cardOverview;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.grid.common.CommonPO;
import java.util.List;

public class CardOverviewPO extends CommonPO {
    public CardOverviewPO(WebDriver driver) {
        super(driver);
    }
    @FindBy(css = "h1.kd-title")
    private WebElement title;

    @FindBy(css = "textarea.k-input-inner")
    private WebElement commentTextarea;

    @FindBy(xpath = "//span[text()='Post']")
    private WebElement postButton;

    @FindBy(css = "span[class='comment-text']")
    private List<WebElement> commentedText;

    @FindBy(css = ".k-button-text")
    private WebElement okShareButton;

    @FindBy(css = ".k-window-content div:nth-child(2)")
    private WebElement sharedPostText;

    @FindBy(css = ".k-window-title")
    private WebElement sharedPostTitle;

    @FindBy(css = "div[role='dialog']")
    private WebElement sharedPopupDialogBox;

    /**
     * Click On Ok Button
     *
     * @throws InterruptedException Exception
     */
    public void clickOnOkButton() throws InterruptedException {
        selenium.clickOn(okShareButton);
    }

    /**
     * Get Commented Text
     *
     * @return commented text
     */
    public List<String> getCommentedText(){
        return commentedText.stream().map(WebElement::getText).toList();
    }

    /**
     * Click On Post Button
     *
     * @throws InterruptedException Exception
     */
    public void clickOnPostButton() throws InterruptedException {
        selenium.hardWait(2);
        selenium.clickOn(postButton);
    }

    /**
     * Enter Text Into Comment Box
     *
     * @param comment comment
     */
    public void enterTextIntoCommentBox(String comment){
        selenium.enterText(commentTextarea,comment,true);
    }

    /**
     * Get Shared Popup Dialog Box
     *
     * @return boolean
     */
    public boolean getSharedPopupDialogBox() {
        return selenium.isElementPresent(sharedPopupDialogBox);
    }
    /**
     * Get Shared Post Text
     *
     * @return text
     */
    public String getSharedPostText() {
        return selenium.getText(sharedPostText);
    }

    /**
     * Get Shared Post Title
     *
     * @return title
     */
    public String getSharedPostTitle() {
        return selenium.getText(sharedPostTitle);
    }

    /**
     * Get Title
     *
     * @return title
     */
    public String getTitle(){
        return selenium.getText(title);
    }

    /**
     * Click On Comment Icon
     *
     * @param cardName card name
     * @throws InterruptedException Exception
     */
    public void clickOnCommentIcon(String cardName) throws InterruptedException {
        selenium.clickOn(By.xpath("//div[text()='"+cardName+"']/ancestor::div[contains(@class,'k-card-header')]/following-sibling::div//span[contains(@class,'k-svg-i-comment')]"));
    }

    /**
     * Click On Like Icon
     *
     * @param cardName card name
     * @throws InterruptedException Exception
     */
    public void clickOnLikeIcon(String cardName) throws InterruptedException {
        selenium.clickOn(By.xpath("//div[text()='"+cardName+"']/ancestor::div[contains(@class,'k-card-header')]/following-sibling::div//span[contains(@class,'k-svg-i-heart')]"));
    }

    /**
     * Click On Share Icon
     *
     * @param cardName card name
     * @throws InterruptedException Exception
     */
    public void clickOnShareIcon(String cardName) throws InterruptedException {
        selenium.clickOn(By.xpath("//div[text()='"+cardName+"']/ancestor::div[contains(@class,'k-card-header')]/following-sibling::div//span[contains(@class,'k-svg-i-share')]"));
    }

    /**
     * Get Like Count
     *
     * @param cardName card name
     * @return card name
     */
    public int getLikeCount(String cardName) {
        String likes=  selenium.getText(By.xpath("//div[text()='"+cardName+"']/ancestor::div[contains(@class,'k-card-header')]/following-sibling::div/span"));
        String[] parts = likes.split(" ");
        return Integer.parseInt(parts[0]);
    }

    /**
     * Click On Comment Like Icon
     *
     * @param comment comment
     * @throws InterruptedException Exception
     */
    public void clickOnCommentLikeIcon(String comment) throws InterruptedException {
        selenium.clickOn(By.xpath("//span[text()='"+comment+"']/ancestor::div[@class='k-hbox mb-xs']/following-sibling::div//span"));
    }

    /**
     * Get Comment Like Count
     *
     * @param comment comment
     * @return comment like
     */
    public int getCommentLikeCount(String comment){
        String likes = selenium.getText(By.xpath("//span[text()='"+comment+"']/ancestor::div[@class='k-hbox mb-xs']//span[@class='comment-data']/span"));
        String[] parts = likes.split(" ");
        return Integer.parseInt(parts[0]);
    }
}
