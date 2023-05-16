package com.capgemini.library.service.model;

import com.capgemini.library.ServiceException;
import com.capgemini.library.model.Reserva;
import com.capgemini.library.service.AbstractService;

public interface ReservaService extends AbstractService<Reserva>{
	    
    void verifyReservas(String copiaId) throws ServiceException;
    
    public void checkReservas() throws ServiceException;
    
    public void linkReservaToCopia(String reservaID, String copiaID) throws ServiceException;
    
    public void linkReservaToLector(String reservaID, String lectorID) throws ServiceException;
}
