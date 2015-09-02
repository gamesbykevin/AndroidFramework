/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamesbykevin.androidframework.resources;

/**
 * Here will be methods related to garbage collection
 * @author ABRAHAM
 */
public interface Disposable 
{
    /**
     * This method will perform final clean of objects
     */
    public void dispose();
}
