/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ue3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class PinnwandClient {

    public static String serviceName = "pinnwand";

    public static void main(String args[]) {

        Registry registry;

        Pinnwand pinnwand;

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            if (args.length > 0) {
                registry = LocateRegistry.getRegistry(args[0]);
            } else {
                registry = LocateRegistry.getRegistry();
            }

            pinnwand = (Pinnwand) registry.lookup(serviceName);
            try {
                //LoginFailedException test
                // pinnwand.putMessage("notpossible");
                pinnwand.login("yolo");

                System.out.println(pinnwand.getMessageCount());

                pinnwand.putMessage("test1");
                pinnwand.putMessage("test2");
                pinnwand.putMessage("test3");
                pinnwand.putMessage("test4");
                pinnwand.putMessage("test5");
                pinnwand.putMessage("test6");

                System.out.println(Arrays.toString(pinnwand.getMessages()));

                System.out.println(pinnwand.getMessage(3));
                //OutOfIndexException 
            //    System.out.println(pinnwand.getMessage(21));
                
               //TooManyMessagesException
                pinnwand.putMessage("test7");
                pinnwand.putMessage("test8");
                pinnwand.putMessage("test9");
                pinnwand.putMessage("test10");
                pinnwand.putMessage("test11");
                pinnwand.putMessage("test12");
                pinnwand.putMessage("test13");
                pinnwand.putMessage("test14");
                pinnwand.putMessage("test15");
                pinnwand.putMessage("test16");
                pinnwand.putMessage("test17");
                pinnwand.putMessage("test18");
                pinnwand.putMessage("test19");
                pinnwand.putMessage("test20");
                pinnwand.putMessage("jetzt kommt eine TooManyMessagesExceptions");

//MessageToLongException
//String repeated = new String(new char[161]).replace("\0", "a");
//pinnwand.putMessage(repeated);

            } catch (MessageToLongException | TooManyMessagesException | LoginFailedException | OutOfIndexException e) {
                System.out.println(e.getMessage());

            }

        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }

        //	try {
        //		pinnwand  = (Pinnwand) Naming.lookup("rmi://localhost/pinnwand");
        //		String message = pinnwand.getMessage(0);
        //                System.out.println(message);
        //                 
//		} catch (MalformedURLException | RemoteException | NotBoundException e) {
//			e.printStackTrace();
//		}
    }

}
