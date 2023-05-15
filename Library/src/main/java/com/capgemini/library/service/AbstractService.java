package com.capgemini.library.service;

import java.util.List;

import com.capgemini.library.ServiceException;

public interface AbstractService<T> {

	public void create(T pojo) throws ServiceException;

	public List<T> readAll() throws ServiceException;

	public T readById(String id) throws ServiceException;

	public void update(T pojo) throws ServiceException;

	public void deleteById(String id) throws ServiceException;

}
