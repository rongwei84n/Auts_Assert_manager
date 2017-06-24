package com.phicomm.smarthome.sharedwifi.repository;

import com.phicomm.smarthome.sharedwifi.model.user.Guest4RouterModel;
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
public interface RouterGuestModelJpaGerepository extends JpaRepository<Guest4RouterModel, Long> {

    @Query(value = "SELECT g4rm FROM Guest4RouterModel g4rm WHERE g4rm.deviceMac =:deviceMac AND g4rm.routerMac =:routerMac")
    public Guest4RouterModel findByDeviceMacAndRouterMac(@Param("deviceMac")String deviceMac, @Param("routerMac")String routerMac);

    @Query(value = "SELECT g4rm FROM Guest4RouterModel g4rm WHERE g4rm.routerMac =:routerMac")
    public List<Guest4RouterModel> findByRouterMac(@Param("routerMac")String routerMac);

}
