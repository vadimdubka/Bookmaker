package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import org.xml.sax.InputSource;

import java.io.File;

public interface DocumentDAO {
    
    File getFile(String path) throws DAOException;
    
    InputSource getInputSource(String path);
    
}
