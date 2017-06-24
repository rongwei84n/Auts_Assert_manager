package com.phicomm.smarthome.sharedwifi.repository;

import com.phicomm.smarthome.sharedwifi.model.router.RouterItemModel;
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
public interface RouterItemModelJpaGerepository extends JpaRepository<RouterItemModel, Long> {

    /**
     *  DESC:
     *      JPA数据仓储(Gerepository)使用@Query事物方式
     * */

    /**
     *  SELECT EXECUTOR
     * */

    @Query(value = "SELECT routerItemModel FROM RouterItemModel routerItemModel WHERE routerItemModel.routerIp =:routerIp " +
                    "AND routerItemModel.routerMac =:routerMac " +
                    "AND routerItemModel.model =:model")
    public RouterItemModel findByRouterIpAndRouterMacAndModel(@Param("routerIp") String routerIp,
                                                              @Param("routerMac")String routerMac,
                                                              @Param("model")String model);



    @Query(value = "SELECT routerItemModel FROM RouterItemModel routerItemModel WHERE routerItemModel.routerIp =:routerIp " +
                    "AND routerItemModel.routerMac =:routerMac " +
                    "AND routerItemModel.model =:model AND routerItemModel.romVersion =:romVersion")
    public List<RouterItemModel> isExistsRouterModel(@Param("routerIp") String routerIp,
                                                     @Param("routerMac")String routerMac,
                                                     @Param("model")String model,
                                                     @Param("romVersion")String romVersion);
													 
    @Query(value = "SELECT routerItemModel FROM RouterItemModel routerItemModel WHERE routerItemModel.routerMac =:routerMac")
    public List<RouterItemModel> isExistsByFindRouterMac4RouterModel(@Param("routerMac")String routerMac);



}
