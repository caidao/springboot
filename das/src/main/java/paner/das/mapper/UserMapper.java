package paner.das.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by longwu on 15/12/8.
 */
public interface UserMapper {

    String getUserUtpRole(int userID);

    List<Map<String,Object>> getSelfDefByUserId(int userID);

    List<Map<String,Object>> getSelfHrMarket(Map<String, String> params);

    String getDepeManageRoleId(int userID);

    String getIsBreakfastAdmin(int userId);

    String getBodAdminByUserID(int userID);
}
