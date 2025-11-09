package com.example.RecetarioApp.MV.services;

import java.util.List;

public interface GenericCrud <RS,RQ,ID>{
    List<RS> findAll();
    RS create(RQ request);
    RS update(ID id,RQ request);
    void delete(ID id);
}
