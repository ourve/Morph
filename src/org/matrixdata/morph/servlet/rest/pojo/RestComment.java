package org.matrixdata.morph.servlet.rest.pojo;

public class RestComment {
    public String commentid;

    public String username;

    public String content;

    public String timestamp;

    public String parentMessageid;

    // If comment is for a public message, parentCommentid should be "".
    public String parentCommentid;

    public RestComment() {

    }

    public RestComment(String commentidIn,
                    String usernameIn,
                    String contentIn,
                    String timestampIn,
                    String parentMessageidIn,
                    String parentCommentidIn) {
        commentid = commentidIn;
        username = usernameIn;
        content = contentIn;
        timestamp = timestampIn;
        parentMessageid = parentMessageidIn;
        parentCommentid = parentCommentidIn;
    }
}