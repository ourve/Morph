package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.CommentDAL;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestComment;

import java.util.List;

public class CommentService {
    public static List<RestComment> getComment(String messageid) {
        return CommentDAL.getInstance().getComments(messageid);
    }

    public static void addComment(RestComment comment) throws MorphRestException{
        try {
            CommentDAL.getInstance().addComment(comment);
        }
        catch (RecordExistException e) {
            throw new MorphRestException(Constant.RECORD_EXIST, Constant.RECORD_EXIST_STR);
        }
    }
}