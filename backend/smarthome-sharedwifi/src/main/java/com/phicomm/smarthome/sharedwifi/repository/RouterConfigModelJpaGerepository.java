package com.phicomm.smarthome.sharedwifi.repository;

import com.phicomm.smarthome.sharedwifi.model.router.RouterConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * PROJECT_NAME: liang04-smarthome-sharedwifi
 * PACKAGE_NAME: com.phicomm.smarthome.sharedwifi.repository
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/13
 */
public interface RouterConfigModelJpaGerepository extends JpaRepository<RouterConfigModel, Long> {

        /**
         *  DESC:
         *      JPA数据仓储(Gerepository)使用@Query事物方式
         * */

        /**
         *  SELECT EXECUTOR
         * */

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.id =:id")
        public RouterConfigModel findById(@Param("id") long id);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.routerId =:routerId")
        public RouterConfigModel findByRouterId(@Param("routerId") long routerId);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.configVersion =:configVersion")
        public RouterConfigModel findByConfigVersion(@Param("configVersion") String configVersion);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.isForbidden =:isForbidden")
        public List<RouterConfigModel> findByIsForbidden(@Param("isForbidden") int isForbidden);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.portalUrl =:portalUrl")
        public List<RouterConfigModel> findByLoginUrl(@Param("portalUrl") String portalUrl);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.createTime =:createTime")
        public List<RouterConfigModel> findByCreateTime(@Param("createTime") String createTime);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.updateTime =:updateTime")
        public List<RouterConfigModel> findByUpdaterTime(@Param("updateTime") String updateTime);

        @Query(value = "SELECT routerConfigModel FROM RouterConfigModel routerConfigModel WHERE routerConfigModel.routerId =:routerId " +
                                                                                           "AND routerConfigModel.configVersion =:configVersion " +
                                                                                           "AND routerConfigModel.isForbidden =:isForbidden " +
                                                                                           "AND routerConfigModel.portalUrl =:portalUrl")
        public List<RouterConfigModel> isExistsRouterConfigModel(@Param("routerId") long routerId,
                                                                 @Param("configVersion") String configVersion,
                                                                 @Param("isForbidden") int isForbidden,
                                                                 @Param("portalUrl") String portalUrl);
}
