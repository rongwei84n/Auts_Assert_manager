package com.phicomm.smarthome.sharedwifi.repository;

import com.phicomm.smarthome.sharedwifi.model.router.RouterWhiteListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * PROJECT_NAME: fhicomm.smarthome.model.jpa
 * PACKAGE_NAME: com.fhicomm.sharedwifi.repository
 * DESCRIPTION:
 * AUTHOR: liang04.zhang
 * DATE: 2017/6/6
 */
public interface RouterWhiteListModelJpaGerepository extends JpaRepository<RouterWhiteListModel, Long> {

    /**
     *  DESC:
     *      JPA数据仓储(Gerepository)使用@Query事物方式
     * */

    /**
     *  SELECT EXECUTOR
     * */
    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.id =:id")
    public RouterWhiteListModel findById(@Param("id") long id);

    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.createTime =:createTime")
    public List<RouterWhiteListModel> findByCreateTime(@Param("createTime") long createTime);

    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.host =:host")
    public List<RouterWhiteListModel> findByHost(@Param("host") String host);

    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.protocol =:protocol")
    public List<RouterWhiteListModel> findByProtocol(@Param("protocol") String protocol);

    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.configId =:configId")
    public List<RouterWhiteListModel> findByConfigId(@Param("configId") long configId);

    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.updateTime =:updateTime")
    public List<RouterWhiteListModel> findByUpdaterTime(@Param("updateTime") long updateTime);

    @Query(value = "SELECT rwlm FROM RouterWhiteListModel rwlm WHERE rwlm.configId =:configId " +
                                                                "AND rwlm.port =:port " +
                                                                "AND rwlm.host =:host " +
                                                                "AND rwlm.protocol =:protocol")
    public List<RouterWhiteListModel> isExistsRouterWhiteListModel(@Param("configId") long configId,
                                                                   @Param("host") String host,
                                                                   @Param("port") int port,
                                                                   @Param("protocol") String protocol);


}
