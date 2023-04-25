package com.example.projetoaulaunc.data.repository;

import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.datasource.LoginDataSource;
import com.example.projetoaulaunc.data.datasource.ServiceDataSource;
import com.example.projetoaulaunc.domain.entity.ServiceEntity;

import java.util.List;
import java.util.Map;

public class ServiceRepository {
    private static volatile ServiceRepository instance;

    private final ServiceDataSource dataSource;

    private List<ServiceEntity> listServices;

    private ServiceRepository(ServiceDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ServiceRepository getInstance(ServiceDataSource dataSource) {
        if (instance == null) {
            instance = new ServiceRepository(dataSource);
        }
        return instance;
    }

    public Result<Boolean> setService(Map<String, Object> json ){
        return dataSource.setService(json);
    }

    private List<ServiceEntity> setListServices(List<ServiceEntity> list) {
        listServices.addAll(list);
        return listServices;
    }

    public Result<List<ServiceEntity>> getServices(String userId ){
        Result<List<ServiceEntity>> list = dataSource.getServices(userId);
        if( list instanceof Result.Success){
            setListServices(((Result.Success<List<ServiceEntity>>) list).getData());
        }
        return list;
    }
}
