package datafactory.card.postComment;
import dataobjects.card.postComment.CommentDetails;
import utilities.JavaHelpers;

public class CommentData {

    CommentDetails commentDetails = new CommentDetails();
    JavaHelpers javaHelpers = new JavaHelpers();

    public CommentDetails getComment(){
        commentDetails.setComment(javaHelpers.getAlphaString(10));
        return commentDetails;
    }
}
