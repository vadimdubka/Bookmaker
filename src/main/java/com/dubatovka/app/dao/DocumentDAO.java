package com.dubatovka.app.dao;

import com.dubatovka.app.dao.exception.DAOException;
import org.xml.sax.InputSource;

import java.io.File;

public interface DocumentDAO {
    
    public abstract File getFile(String path) throws DAOException;
    
    public abstract InputSource getInputSource(String path);
    
}
