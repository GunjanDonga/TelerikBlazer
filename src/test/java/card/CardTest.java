package card;
import base.BaseTest;
import datafactory.card.postComment.CommentData;
import dataobjects.card.postComment.CommentDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.card.cardOverview.CardOverviewPO;
import utilities.Constants;

public class CardTest extends BaseTest {

    String expectedTitle = "Blazor Card - Overview";
    String cardName = "Traditions";

    @Test
    public void verifyCardLikeIncreasedAndDecreasedWhenClickOnLikeIconRespectively() throws InterruptedException {

        CardOverviewPO cardOverviewPO = new CardOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.CardURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to card overview page");
        Assert.assertEquals(cardOverviewPO.getTitle(), expectedTitle, "Title of page doesn't matched");

        Reporter.log("Step 3: Click on Like icon of card");
        int expectedIncreasedLikes = cardOverviewPO.getLikeCount(cardName);
        cardOverviewPO.clickOnLikeIcon(cardName);

        Reporter.log("Step 4: Verify that the like count is increased by 1");
        int actualIncreasedLikes = cardOverviewPO.getLikeCount(cardName);
        Assert.assertEquals(actualIncreasedLikes,expectedIncreasedLikes+1,"Like count doesn't matched");

        Reporter.log("Step 5: Again click on already clicked Like icon of card");
        cardOverviewPO.clickOnLikeIcon(cardName);
        int actualDecreasedLikes = cardOverviewPO.getLikeCount(cardName);

        Reporter.log("Step 6: Verify that the like count is decreased by 1");
        Assert.assertEquals(actualDecreasedLikes,actualIncreasedLikes-1,"Like count doesn't matched");
    }

    @Test
    public void verifySharePopupIsDisplayWhenClickOnShareIcon() throws InterruptedException {

        CardOverviewPO cardOverviewPO = new CardOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.CardURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to card overview page");
        Assert.assertEquals(cardOverviewPO.getTitle(), expectedTitle, "Title of page doesn't match");

        Reporter.log("Step 3: Click on Share icon of card");
        cardOverviewPO.clickOnShareIcon(cardName);

        Reporter.log("Step 4: Verify that the shared popup is opened and post is shared");
        Assert.assertEquals(cardOverviewPO.getSharedPostTitle(),"Shared Post","Title doesn't matched");
        Assert.assertTrue(cardOverviewPO.getSharedPostText().contains("post is shared."),"Post doesn't shared");

        Reporter.log("Step 5: Click on Ok button of post");
        cardOverviewPO.clickOnOkButton();

        Reporter.log("Step 6: Verify that the shared popup is closed ");
        Assert.assertFalse(cardOverviewPO.getSharedPopupDialogBox(),"Dialog-box is present");
    }

    @Test
    public void verifyThatCommentPostedSuccessfully() throws InterruptedException {

        CardOverviewPO cardOverviewPO = new CardOverviewPO(driver);

        CommentDetails commentDetails = new CommentData().getComment();
        String comment = commentDetails.getComment();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.CardURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to card overview page");
        Assert.assertEquals(cardOverviewPO.getTitle(), expectedTitle, "Title of page doesn't match");
        cardOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Click on comment icon, enter comment and click on Post button");
        cardOverviewPO.clickOnCommentIcon(cardName);
        cardOverviewPO.enterTextIntoCommentBox(comment);
        cardOverviewPO.clickOnPostButton();

        Reporter.log("Step 4: Verify that comment has been posted successfully ");
        Assert.assertTrue(cardOverviewPO.getCommentedText().stream().anyMatch(el->el.equals(comment)),"Comment doesn't match");
    }

    @Test
    public void verifyLikeOfCommentIncreasedAndDecreasedWhenClickOnLikeIconRespectively() throws InterruptedException {

        CardOverviewPO cardOverviewPO = new CardOverviewPO(driver);

        CommentDetails commentDetails = new CommentData().getComment();
        String comment = commentDetails.getComment();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.CardURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to card overview page");
        Assert.assertEquals(cardOverviewPO.getTitle(), expectedTitle, "Title of page doesn't match");
        cardOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Click on comment icon, enter comment and click on Post button");
        cardOverviewPO.clickOnCommentIcon(cardName);
        cardOverviewPO.enterTextIntoCommentBox(comment);
        cardOverviewPO.clickOnPostButton();

        Reporter.log("Step 4: Verify that comment has been posted successfully ");
        Assert.assertTrue(cardOverviewPO.getCommentedText().stream().anyMatch(el->el.equals(comment)),"Comment doesn't match");

        Reporter.log("Step 5: Click on like icon of comment");
        int expectedCommentLikeCount= cardOverviewPO.getCommentLikeCount(comment);
        cardOverviewPO.clickOnCommentLikeIcon(comment);

        Reporter.log("Step 6: Verify that the like count is increased by 1");
        int actualLikes = cardOverviewPO.getCommentLikeCount(comment);
        Assert.assertEquals(actualLikes,expectedCommentLikeCount+1,"Comment Like count doesn't matched");

        Reporter.log("Step 7: Again click on already clicked Like icon of card");
        cardOverviewPO.clickOnCommentLikeIcon(comment);
        int actualDecreasedLikes = cardOverviewPO.getCommentLikeCount(comment);

        Reporter.log("Step 8: Verify that the like count is decreased by 1");
        Assert.assertEquals(actualDecreasedLikes,actualLikes-1,"Comment Like count doesn't matched");
    }
    }
