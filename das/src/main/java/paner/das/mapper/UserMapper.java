package paner.das.mapper;

import paner.das.entity.QQUserinfoModel;

import java.util.List;
import java.util.Map;

/**
 * Created by pan on 15/12/8.
 */
public interface UserMapper {

    String getUser(int userID);

    void addQQUserinfo(QQUserinfoModel model);

}
