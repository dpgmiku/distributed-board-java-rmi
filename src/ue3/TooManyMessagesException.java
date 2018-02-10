/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ue3;

import java.rmi.RemoteException;

/**
 *
 * @author admin
 */
public class TooManyMessagesException extends RemoteException {
  
public TooManyMessagesException(){
    
    
super("There are too many messages in the store already");    
}
}
