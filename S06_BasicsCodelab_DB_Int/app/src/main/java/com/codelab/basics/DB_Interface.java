package com.codelab.basics;

import java.util.List;
import java.util.Map;

public interface DB_Interface {

    public int count();

    public int save(DataModel dataModel);

    public int update(DataModel dataModel);  // Not implemented

    public int deleteById(Long id);  // Not implemented

    int deleteById(int id);

    public List<DataModel> findAll();

    public String getNameById(Long id);  // Not implemented

    String getNameById(int id);
}
