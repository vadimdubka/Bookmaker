package com.dubatovka.app.dao.impl;

import com.dubatovka.app.dao.DocumentDAO;
import com.dubatovka.app.dao.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DocumentDAOImpl implements DocumentDAO {
    private static final Logger logger = LogManager.getLogger(DocumentDAOImpl.class);
    
    @Override
    public File getFile(String path) throws DAOException {
        URL fileURL = DocumentDAOImpl.class.getClassLoader().getResource(path);
        
        if (fileURL == null) {
            logger.log(Level.ERROR, "File on path" + path + " is not available");
            throw new DAOException("File on path" + path + " is not available");
        }
        
        URI fileURI;
        try {
            fileURI = fileURL.toURI();
        } catch (URISyntaxException e) {
            logger.log(Level.ERROR, "File on path" + path + " is not available");
            throw new DAOException("File on path" + path + " is not available", e);
        }
        
        File file = new File(fileURI);
        
        return file;
    }
    
    public InputSource getInputSource(String path) {
        InputStream inputStream = DocumentDAOImpl.class.getClassLoader().getResourceAsStream(path);
        InputSource inputSource = new InputSource(inputStream);
        return inputSource;
    }
    
}
